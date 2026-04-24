package org.firstinspires.ftc.teamcode.config.subsystems;

import static org.firstinspires.ftc.teamcode.config.util.Alliance.BLUE;
import static org.firstinspires.ftc.teamcode.config.util.Alliance.RED;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.util.Alliance;
import org.firstinspires.ftc.teamcode.config.util.CachedMotor;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Chassis {


    Robot robot;
    HardwareMap hardwareMap;

    VoltageSensor voltageSensor;

    public ElapsedTime justLetGoOfStickTimer = new ElapsedTime();
    public boolean justLetGoOfStick = false;
    public boolean justLetGoOfStickPreviously = false;
    public boolean firstTurn = false;
    public boolean noSticks = false;
    public boolean turnCompleted = true;
    public double targetHeading = 0;
    public double degreeOffset = 2;
    public double parkHeading = 180;
    public double parkHeadingOffset = 0;
    public ElapsedTime turnTimer = new ElapsedTime();

    public Chassis(HardwareMap hardwareMap, Robot robot) {
        this.hardwareMap = hardwareMap;
        this.robot = robot;

        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");

    }


    public double getVoltage() {
        return clamp(voltageSensor.getVoltage(), 11, 13);
    }



    public double inchesAwayPinpoint(Pose position) {
        double x = position.getX();
        double y = position.getY();
        if (robot.getAlliance() == BLUE) {
            return Math.sqrt((((ConfigConstants.GOAL_BLUE.getX() - x) * (ConfigConstants.GOAL_BLUE.getX() - x)) + ((ConfigConstants.GOAL_BLUE.getY() - y) * (ConfigConstants.GOAL_BLUE.getY() - y))));
        }
        else {
            return Math.sqrt((((ConfigConstants.GOAL_RED.getX() - x) * (ConfigConstants.GOAL_RED.getX() - x)) + ((ConfigConstants.GOAL_RED.getY() - y) * (ConfigConstants.GOAL_RED.getY() - y))));

        }
    }

    public double predictedInchesAway() {
        double x = turretPose().getX();
        double y = turretPose().getY();
        if (!inFar() && robot.turret.getSOTM()) {


            double robotVx = robot.follower.getVelocity().getXComponent();
            double robotVy = robot.follower.getVelocity().getYComponent();

            double airTime = inchesAwayPinpoint(new Pose(x, y)) / 130;

            double predictedX = x + (robotVx * airTime);
            double predictedY = y + (robotVy * airTime);

            x = predictedX;
            y = predictedY;
            return inchesAwayPinpoint(new Pose(x, y));
        }
        else {
            return inchesAwayPinpoint(new Pose(x, y));
        }
    }
    public double degreesAwayTurret(Pose position) {
        double x = position.getX();
        double y = position.getY();

        if (inFar()) {
            if (robot.getAlliance() == BLUE) {
                return Math.toDegrees(Math.atan2(ConfigConstants.targetPointFarBlue.getY() - y, ConfigConstants.targetPointFarBlue.getX() - x));
            } else {
                return Math.toDegrees(Math.atan2(ConfigConstants.targetPointFarRed.getY() - y, ConfigConstants.targetPointFarRed.getX() - x));

            }
        }
        else {
            if (robot.getAlliance() == BLUE) {
                return Math.toDegrees(Math.atan2(ConfigConstants.targetPointCloseBlue.getY() - y, ConfigConstants.targetPointCloseBlue.getX() - x));
            } else {
                double dx = ConfigConstants.targetPointCloseRed.getX() - x; //62
                double dy = ConfigConstants.targetPointCloseRed.getY() - y; //-65
                return Math.toDegrees(Math.atan2(dy, dx));

            }
        }
    }

    public double turnPowerWithPinpointToPark() {

        if (!justLetGoOfStick) {

            targetHeading = Math.toRadians(parkHeading + parkHeadingOffset);


            double error = getHeadingError();
            double sign = error / Math.abs(error);
            double kd = 0.09;
            double kp = 0.9;
            double powerError = (error * kp) - (robot.follower.getAngularVelocity() * kd);
            double degreeError = Math.toDegrees(error);
            double clampedPowerError = clamp(powerError, -1, 1);
            if (Math.abs(clampedPowerError) < 0.06) {
                clampedPowerError = 0.06 * sign;
            }

            if (Math.abs(degreeError) < 0.5) {
                return 0;
            } else {
                return clampedPowerError * getVoltageMultiplier();
            }

        }
        else {
            return 0;
        }

    }

    public double getHeadingError() {
        double headingError = MathFunctions.getTurnDirection(robot.follower.getPose().getHeading(), targetHeading) * MathFunctions.getSmallestAngleDifference(robot.follower.getPose().getHeading(), targetHeading);
        return headingError;
    }
    public double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    public double getVoltageMultiplier() {
        return 14 / getVoltage();
    }
    public double[] calculateDrivePowers(Gamepad c1, double driveDampeneing, double strafeDampening, double turnDampening) {
        double drivePower;
        double strafePower;
        double turnPower;
        if (robot.getRobotState() == Robot.RobotState.PARK) {
            driveDampeneing = 0.15;
            strafeDampening = 0.2;
            turnDampening = 0.1;
        }
        drivePower = -c1.left_stick_y * driveDampeneing;
        strafePower = -c1.left_stick_x * strafeDampening;
        turnPower = -c1.right_stick_x * turnDampening;

        if (c1.left_stick_x == 0 && c1.left_stick_y == 0) {
            noSticks = true;

            if (!justLetGoOfStickPreviously) {
                justLetGoOfStick = true;
                justLetGoOfStickPreviously = true;
                justLetGoOfStickTimer.reset();
            }
        }

        if (c1.left_stick_x != 0 || c1.left_stick_y != 0) {
            justLetGoOfStickPreviously = false;
            noSticks = false;
        }

        if (justLetGoOfStick && justLetGoOfStickTimer.milliseconds() > 200) {
            justLetGoOfStick = false;
            turnCompleted = true;
        }

        if (robot.getRobotState() == Robot.RobotState.PARK && robot.ascent.getAscentState() == Ascent.AscentState.NOTASCENDED) {



            turnPower = turnPowerWithPinpointToPark();


        }

        return new double[]{drivePower, strafePower, turnPower};
    }

    public double getVoltageScalar() {
        return (getVoltage() - 11) / (13-11);
    }

    public Pose turretPose() {
        double x = robot.follower.getPose().getX();
        double y = robot.follower.getPose().getY();
        double heading = robot.follower.getHeading();
        double newX = x - (3.5 * Math.cos(-heading));
        double newY = y + (3.5 * Math.sin(-heading));

        x = newX;
        y = newY;

        return new Pose(x, y, heading);
    }

    public boolean inFar() {
        return robot.follower.getPose().getX() < 45;
    }

}
