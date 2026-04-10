package org.firstinspires.ftc.teamcode.config.subsystems;

import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Turret {
    public enum TurretState {
        TRACK,
        HOLD
    }

    Robot robot;
    HardwareMap hardwareMap;
    Servo turretLeft;
    Servo turretRight;

    TurretState turretState = TurretState.TRACK;

    double angle = 0;

    public Turret(HardwareMap hardwareMap, Robot robot) {
        this.hardwareMap = hardwareMap;
        this.robot = robot;

        turretLeft = hardwareMap.get(Servo.class, ConfigConstants.TURRET_LEFT);
        turretRight = hardwareMap.get(Servo.class, ConfigConstants.TURRET_RIGHT);


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

           // double fieldVx = robotVx * Math.cos(heading) - robotVy * Math.sin(heading);
           // double fieldVy = robotVx * Math.sin(heading) + robotVy * Math.cos(heading);

            double airTime = robot.chassis.inchesAwayPinpoint() / 145;

            double predictedX = x + (robotVx * airTime);
            double predictedY = y + (robotVy * airTime);

            setAngle(
                    -Math.toDegrees(robot.follower.getHeading()) + robot.chassis.degreeOffset +
                            robot.chassis.degreesAwayTurret(new Pose(predictedX, predictedY))
            );
        }
    }
    public void setAngle(double angle) {
        this.angle = angle;
        double ticks = angleToTicks(wrapAngle(angle));

        if (Math.abs(getEncoderVelocity()) < .1) {
            double difference = angle - getEncoderAngle();

            if (Math.abs(difference) > 1) {
                ticks = angleToTicks(wrapAngle(angle + (2 * Math.signum(difference))));

            }
        }
        turretLeft.setPosition(ticks);
        turretRight.setPosition(ticks + ConfigConstants.TURRET_TICK_OFFSET_FOR_RIGHT);
    }

    public double angleToTicks(double angle) {
        double ticks = ConfigConstants.TURRET_ZERO + (angle * getInterpolatedOffset(ConfigConstants.TICKS_PER_TURRET_DEGREE_MAP, angle));
        ticks = Math.min(ConfigConstants.TURRET_MAX, ticks);
        ticks = Math.max(ConfigConstants.TURRET_MIN, ticks);
        return ticks;
    }

    public double wrapAngle(double angle) {
        angle = angle % 360;

        if (angle > 180) {
            angle = -(360 - angle);
        }
        return angle;
    }
    public void setState(TurretState state) {
        turretState = state;
    }

    public double getAngle() {
        return angle;
    }
    public void incrementAngle(double increment) {
        this.angle += increment;
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

    public boolean atAngle() {
        if (Math.abs(angle - getEncoderAngle()) < 2) {
            return true;
        }
        else {
            return false;
        }
    }

}
