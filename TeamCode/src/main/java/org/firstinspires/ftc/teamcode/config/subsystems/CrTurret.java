package org.firstinspires.ftc.teamcode.config.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Config
public class CrTurret {
    public enum TurretState {
        TRACK,
        HOLD
    }

    Robot robot;
    HardwareMap hardwareMap;
    CRServo turretLeft;
    CRServo turretRight;

    TurretState turretState = TurretState.TRACK;

    double angle = 0;
    public static double P = 0.02;
    public static double D = 0.0017;
    public static double min = 0.07;

    public CrTurret(HardwareMap hardwareMap, Robot robot) {
        this.hardwareMap = hardwareMap;
        this.robot = robot;

        turretLeft = hardwareMap.get(CRServo.class, ConfigConstants.TURRET_LEFT);
        turretRight = hardwareMap.get(CRServo.class, ConfigConstants.TURRET_RIGHT);


    }


    public TurretState getTurretState() {
        return turretState;
    }

    public void update() {
        if (turretState == TurretState.TRACK) {
            // Current position
            double x = robot.chassis.turretPose().getX();
            double y = robot.chassis.turretPose().getY();

            double heading = robot.follower.getHeading(); // radians

            double robotVx = robot.follower.getVelocity().getXComponent();
            double robotVy = robot.follower.getVelocity().getYComponent();

            double fieldVx = robotVx * Math.cos(-heading) + robotVy * Math.sin(-heading);
            double fieldVy = -robotVx * Math.sin(-heading) + robotVy * Math.cos(-heading);

            double airTime = robot.chassis.inchesAwayPinpoint(new Pose(x, y)) / 90;

            double predictedX = x + (fieldVx * airTime);
            double predictedY = y + (fieldVy * airTime);

            setAngle(
                    -Math.toDegrees(robot.follower.getHeading()) + robot.chassis.degreeOffset +
                            robot.chassis.degreesAwayTurret(new Pose(predictedX, predictedY))
            );
        }

        setPower();
    }
    public void setAngle(double angle) {
        this.angle = wrapAngle(angle);

    }


    public double wrapAngle(double angle) {
        angle = angle % 360;

        if (angle > 180) {
            angle = -(360 - angle);
        }

        if (angle > 90) {
            angle = 90;
        }
        else if (angle < -90) {
            angle = -90;
        }
        return angle;
    }
    public void setState(TurretState state) {
        turretState = state;
    }

    public double getAngle() {
        return angle;
    }
    public void setPower() {
        double power = getPowerPID();
        turretLeft.setPower(power);

        turretRight.setPower(power);
    }
    public double getPowerPID() {
        /*double angleRemaining = angle - getEncoderAngle();
        double direction = Math.signum(angleRemaining);
        double velocity = getEncoderVelocity();

        double power = (angleRemaining * P) - (velocity * D);
        double absScale = Math.min(Math.abs(power), 1);
       // double minnedPower = Math.max(absScale, min);
        double minnedPower = (absScale * 0.93) + min;
        return minnedPower * direction;*/

        return 0;



    }

    public double getEncoderAngle() {
        double position = robot.intake.getIntakeMotor().getCurrentPosition();

        double rotations = (position / ConfigConstants.TICKS_PER_ENCODER_REVOLUTION) * (ConfigConstants.ENCODER_TEETH / ConfigConstants.TURRET_TEETH);
        double angle = rotations * 360;
        return angle;
    }
    public double getEncoderVelocity() {
        double velocityTicks = robot.intake.getIntakeMotor().getVelocity();

        double angularVelcocity = (velocityTicks / ConfigConstants.TICKS_PER_ENCODER_REVOLUTION) * (ConfigConstants.ENCODER_TEETH / ConfigConstants.TURRET_TEETH);
        double velocity = angularVelcocity * 360;
        return velocity;
    }

}
