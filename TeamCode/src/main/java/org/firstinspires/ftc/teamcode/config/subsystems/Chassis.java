package org.firstinspires.ftc.teamcode.config.subsystems;

import static org.firstinspires.ftc.teamcode.config.util.Alliance.BLUE;
import static org.firstinspires.ftc.teamcode.config.util.Alliance.RED;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.MathFunctions;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.subsystems.vision.TagCamera;
import org.firstinspires.ftc.teamcode.config.util.Alliance;
import org.firstinspires.ftc.teamcode.config.util.CachedMotor;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Chassis {



    HardwareMap hardwareMap;

    VoltageSensor voltageSensor;

    public ElapsedTime justLetGoOfStickTimer = new ElapsedTime();
    public boolean justLetGoOfStick = false;
    public boolean justLetGoOfStickPreviously = false;
    public boolean firstTurn = false;
    public boolean noSticks = false;
    public boolean turnCompleted = true;
    public double targetHeading = 0;
    public double degreeOffset = 0;
    public double parkHeading = 180;
    public ElapsedTime turnTimer = new ElapsedTime();

    public Chassis(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");

    }


    public double getVoltage() {
        return clamp(voltageSensor.getVoltage(), 11, 13);
    }

    public double getClosestVal(List<Double> values, double inputVal) {
        double distance = Math.abs(values.get(0) - inputVal);
        int idx = 0;
        for (int c = 1; c < values.size(); c++) {
            double cdistance = Math.abs(values.get(c) - inputVal);
            if (cdistance < distance) {
                idx = c;
                distance = cdistance;
            }
        }

        return values.get(idx);
    }
    public static double getInterpolatedOffset(Map<Double, Double> angleMap, double inputAngle) {

        if (angleMap == null || angleMap.size() < 2) {
            throw new IllegalArgumentException("Map must contain at least two points.");
        }

        // Sort the angle keys
        List<Double> angles = new ArrayList<>(angleMap.keySet());
        Collections.sort(angles);

        // Clamp if outside range
        if (inputAngle <= angles.get(0)) {
            return angleMap.get(angles.get(0));
        }

        if (inputAngle >= angles.get(angles.size() - 1)) {
            return angleMap.get(angles.get(angles.size() - 1));
        }

        // Find surrounding angles
        for (int i = 0; i < angles.size() - 1; i++) {
            double lowerAngle = angles.get(i);
            double upperAngle = angles.get(i + 1);

            if (inputAngle >= lowerAngle && inputAngle <= upperAngle) {

                double lowerOffset = angleMap.get(lowerAngle);
                double upperOffset = angleMap.get(upperAngle);

                // Linear interpolation
                double t = (inputAngle - lowerAngle) / (upperAngle - lowerAngle);

                return lowerOffset + t * (upperOffset - lowerOffset);
            }
        }

        // Should never happen
        return 0;
    }

    public double calculateOffset(Robot robot, TagCamera tagCamera) {

        double combined = tagCamera.combined();

        double offset;
        if (tagCamera.range() > ConfigConstants.NEAR_VS_FAR) {
            if (robot.getAlliance() == BLUE) {

                offset = getInterpolatedOffset(ConfigConstants.FAR_OFFSET_MAP_BLUE, combined);
            }
            else {


                offset = getInterpolatedOffset(ConfigConstants.FAR_OFFSET_MAP_RED, combined);
            }


        }
        else {
            if (robot.getAlliance() == BLUE) {

                offset = getInterpolatedOffset(ConfigConstants.CLOSE_OFFSET_MAP_BLUE, combined);
            }
            else {

                offset = getInterpolatedOffset(ConfigConstants.CLOSE_OFFSET_MAP_RED, combined);
            }


        }

        return offset;
    }

    public double turnPower(Robot robot, TagCamera tagCamera) {


        if (!justLetGoOfStick) {
            double degrees = tagCamera.degreesAway(calculateOffset(robot, tagCamera));

            if (tagCamera.hasTag() && tagCamera.tagValid(robot)) {

                firstTurn = false;
                if (noSticks) {
                    if (turnCompleted && turnTimer.milliseconds() > 0) {
                        targetHeading = robot.follower.getHeading() + Math.toRadians(degrees);
                        turnCompleted = false;
                        turnTimer.reset();
                    }
                } else {
                    targetHeading = robot.follower.getHeading() + (Math.toRadians(degrees) * 1.3);
                }
            }

            double error = getHeadingError(robot);
            double sign = error / Math.abs(error);
            double kd = ConfigConstants.TURN_kD;
            double powerError = (error * ConfigConstants.TURN_kP) - (robot.follower.getAngularVelocity() * kd);

            double degreeError = Math.toDegrees(error);

            double boost = ConfigConstants.BOOST_MULTIPLIER * sign * Math.min(1, Math.abs(error) / 12);
            powerError += boost;

            double clampedPowerError = clamp(powerError, -1, 1);
            if (Math.abs(clampedPowerError) < 0.07) {
                clampedPowerError = 0.07 * sign;
            }

            if (Math.abs(degreeError) < 1 && !turnCompleted) {
                turnCompleted = true;
                turnTimer.reset();
            }


            if (Math.abs(degreeError) < 0.5) {
                return 0;
            }
            else {
                return clampedPowerError * getVoltageMultiplier();
            }
        }
        else {
            return 0;
        }

    }
    public double inchesAwayPinpoint(Robot robot) {
        double x = robot.follower.getPose().getX();
        double y = robot.follower.getPose().getY();
        return Math.sqrt((((140 - x) * (140 - x))   + ((140 - y) * (140 - y))));
    }
    public double degreesAwayPinpoint(Robot robot) {
        double x = robot.follower.getPose().getX();
        double y = robot.follower.getPose().getY();
        if (robot.tagCamera.range() > ConfigConstants.NEAR_VS_FAR) {
            if (robot.getAlliance() == BLUE) {
                return Math.toDegrees(Math.atan((-(137 - y)) / (140 - x))) * -1;
            } else {
                return Math.toDegrees(Math.atan(((y - 3)) / (140 - x))) * -1;
            }
        }
        else {
            if (robot.getAlliance() == BLUE) {
                return Math.toDegrees(Math.atan((-(140 - y)) / (138 - x))) * -1;
            } else {
                return Math.toDegrees(Math.atan(((y)) / (138 - x))) * -1;
            }
        }
    }

    public double turnPowerWithPinpoint(Robot robot) {


        if (!justLetGoOfStick) {
            double degrees = degreesAwayPinpoint(robot) - degreeOffset;


            targetHeading = Math.toRadians(degrees);




            double error = getHeadingError(robot);
            double sign = error / Math.abs(error);
            double kd = ConfigConstants.TURN_kD;
            double powerError = (error * ConfigConstants.TURN_kP) - (robot.follower.getAngularVelocity() * kd) ;
            double boost = ConfigConstants.BOOST_MULTIPLIER * sign * Math.min(1, Math.abs(error) / 12);
            //powerError += boost;
            double degreeError = Math.toDegrees(error);
            double clampedPowerError = clamp(powerError, -1, 1);
            if (Math.abs(clampedPowerError) < 0.06) {
                clampedPowerError = 0.06 * sign;
            }

            if (Math.abs(degreeError) < 0.5) {
                return 0;
            }
            else {
                return clampedPowerError * getVoltageMultiplier();
            }
        }
        else {
            return 0;
        }

    }
    public double turnPowerWithPinpointToPark(Robot robot) {

        if (!justLetGoOfStick) {

            targetHeading = Math.toRadians(parkHeading);


            double error = getHeadingError(robot);
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

    public double getHeadingError(Robot robot) {
        double headingError = MathFunctions.getTurnDirection(robot.follower.getPose().getHeading(), targetHeading) * MathFunctions.getSmallestAngleDifference(robot.follower.getPose().getHeading(), targetHeading);
        return headingError;
    }
    public double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    public double getVoltageMultiplier() {
        return 14 / getVoltage();
    }
    public double[] calculateDrivePowers(Gamepad c1, double driveDampeneing, double strafeDampening, double turnDampening, Robot robot) {
        double drivePower;
        double strafePower;
        double turnPower;
        if (robot.getRobotState() == Robot.RobotState.PARK) {
            driveDampeneing = 0.3;
            strafeDampening = 0.3;
            turnDampening = 0.3;
        }
        if (c1.left_trigger_pressed) {
            driveDampeneing = 0.3;
            strafeDampening = 0.3;
            turnDampening = 0.3;
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



        if (robot.getRobotState() == Robot.RobotState.SHOOT) {

            if (Math.abs(c1.right_stick_x) <= ConfigConstants.STICK_AT_ZERO_DISTANCE) {

                turnPower = turnPowerWithPinpoint(robot);

            }
        }

        if (robot.getRobotState() == Robot.RobotState.PARK) {



            turnPower = turnPowerWithPinpointToPark(robot);


        }

        return new double[]{drivePower, strafePower, turnPower};
    }

    public double getVoltageScalar() {
        return (getVoltage() - 11) / (13-11);
    }

}
