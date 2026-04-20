package org.firstinspires.ftc.teamcode.config.subsystems;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.util.CachedMotor;
import org.firstinspires.ftc.teamcode.config.util.CyclingList;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Shooter {

    public enum ShooterState {
        READY,
        NOTREADY
    }

    Robot robot;
    HardwareMap hardwareMap;
    CachedMotor shooterMotorLeft;
    CachedMotor shooterMotorRight;
    Servo blocker;

    double targetShooterRPM = 0;
    double lastRPM = ConfigConstants.DEFAULT_RPM;
    double manualAdjustmentFront = 0;
    double manualAdjustmentBack = 0;
    public boolean droppedActivateBangBang;
    boolean shooting;
    public double power;
    public boolean motifMode;
    public double adder = 0;
    public CyclingList shooterRPM = new CyclingList(5);



    ShooterState shooterState = ShooterState.NOTREADY;

    public Shooter(HardwareMap hardwareMap, Robot robot) {
        this.hardwareMap = hardwareMap;
        this.robot = robot;

        shooterMotorLeft = new CachedMotor(hardwareMap.get(DcMotorEx.class, ConfigConstants.SHOOTER_LEFT), ConfigConstants.SHOOTER_CPR);
        shooterMotorLeft.setDirection(DcMotorEx.Direction.FORWARD);
        shooterMotorLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        shooterMotorLeft.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, ConfigConstants.SHOOTER_PID);

        shooterMotorRight = new CachedMotor(hardwareMap.get(DcMotorEx.class, ConfigConstants.SHOOTER_RIGHT), ConfigConstants.SHOOTER_CPR);
        shooterMotorRight.setDirection(DcMotorEx.Direction.REVERSE);
        shooterMotorRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        shooterMotorRight.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, ConfigConstants.SHOOTER_PID);

        blocker = hardwareMap.get(Servo.class, ConfigConstants.SHOOTER_BLOCKER);


    }

    //Util functions
    public void update() {
        shooterRPM.add(robot.shooter.shooterMotorRight.getRPM(), robot.getMilliseconds());

        setShooterState(getShooterReady());

        if (shooting && targetShooterRPM - shooterRPM.average() > 30 && !motifMode) {
            droppedActivateBangBang = true;

        }
        else {
            droppedActivateBangBang = false;
        }


    }
    public Shooter.ShooterState getShooterReady() {
        if (shooterRPM.getValues().size() < shooterRPM.getMaxSize()) {
            return Shooter.ShooterState.NOTREADY;
        }
        else {
            if (Math.abs(shooterRPM.averageROC()) < ConfigConstants.RPM_ROC_BOUND && Math.abs(shooterRPM.average() - targetShooterRPM) < ConfigConstants.RPM_DISTANCE_BOUND) {
                return Shooter.ShooterState.READY;
            } else {
                return Shooter.ShooterState.NOTREADY;
            }
        }
    }

    public ShooterState getShooterState() {
        return shooterState;
    }

    public void setShooterState(ShooterState shooterState) {
        this.shooterState = shooterState;
    }

    public void updatePower() {
        if (!droppedActivateBangBang) {
            adder = 0;
        }
        else {
            adder = 600;
        }
        if (motifMode) {
            adder = -100;
        }
        shooterMotorLeft.setRPM(targetShooterRPM + adder);
        shooterMotorRight.setRPM(targetShooterRPM + adder);


    }

    public double getTargetShooterRPM() {
        return targetShooterRPM;
    }

    public void stop() {
        setRPM(0);
    }

    public void setRPM(double rpm) {
        targetShooterRPM = rpm;
    }

    public void block() {
        blocker.setPosition(ConfigConstants.SHOOTER_BLOCK);
    }
    public void unblock() {
        blocker.setPosition(ConfigConstants.SHOOTER_UNBLOCK);
    }

    public CachedMotor getShooterMotor() {
        return shooterMotorRight;
    }

    public void increaseManualRPMAdjustment() {

        manualAdjustmentBack += ConfigConstants.RPM_ADJUST_AMOUNT;


    }
    public void decreaseManualRPMAdjustment() {

        manualAdjustmentBack -= ConfigConstants.RPM_ADJUST_AMOUNT;


    }


    public void spinAtCalculatedSpeed(double range) {
        setRPM(calculateRPM(range) - (robot.chassis.getVoltageScalar() * 125));
    }
    public double calculateRPM(double range) {
        if (range > ConfigConstants.NEAR_VS_FAR) {
            return multiRPM(range) + manualAdjustmentBack;
        }
        else {
            return multiRPM(range) + manualAdjustmentFront;
        }

    }
    public double multiRPM(double range) {

        if (range == -1) {
            return lastRPM;
        }
        else {
            double rpm = getInterpolatedOffset(ConfigConstants.RPM_MAP, range);
            lastRPM = rpm;
            return rpm;
        }

    }

    public double getManualAdjustmentFront() {
        return manualAdjustmentFront;
    }
    public double getManualAdjustmentBack() {
        return manualAdjustmentBack;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public CachedMotor getShooterMotorLeft() {
        return shooterMotorLeft;
    }

    public CachedMotor getShooterMotorRight() {
        return shooterMotorRight;
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
}
