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

@Config
public class Shooter {

    public enum ShooterState {
        READY,
        NOTREADY
    }


    HardwareMap hardwareMap;
    CachedMotor shooterMotorLeft;
    CachedMotor shooterMotorRight;
    Servo blocker;

    double targetShooterRPM = 0;
    double lastRPM = ConfigConstants.DEFAULT_RPM;
    double manualAdjustment = 0;
    CyclingList shooterRPM = new CyclingList(5);



    ShooterState shooterState = ShooterState.NOTREADY;

    public Shooter(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        shooterMotorLeft = new CachedMotor(hardwareMap.get(DcMotorEx.class, ConfigConstants.SHOOTER_LEFT), ConfigConstants.SHOOTER_CPR);
        shooterMotorLeft.setDirection(DcMotorEx.Direction.FORWARD);

        shooterMotorLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        shooterMotorLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        shooterMotorLeft.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, ConfigConstants.SHOOTER_PID);

        shooterMotorRight = new CachedMotor(hardwareMap.get(DcMotorEx.class, ConfigConstants.SHOOTER_RIGHT), ConfigConstants.SHOOTER_CPR);
        shooterMotorRight.setDirection(DcMotorEx.Direction.REVERSE);

        shooterMotorRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        shooterMotorRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        shooterMotorLeft.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, ConfigConstants.SHOOTER_PID);

        blocker = hardwareMap.get(Servo.class, ConfigConstants.SHOOTER_BLOCKER);


    }

    //Util functions
    public void update(Robot robot) {
        shooterRPM.add(robot.shooter.shooterMotorLeft.getRPM(), robot.getMilliseconds());

        setShooterState(getShooterReady(robot.shooter.getTargetShooterRPM()));
    }
    public Shooter.ShooterState getShooterReady(double currentShooterTargetRPM) {
        if (shooterRPM.getValues().size() < shooterRPM.getMaxSize()) {
            return Shooter.ShooterState.NOTREADY;
        }
        else {
            if (Math.abs(shooterRPM.averageROC()) < ConfigConstants.RPM_ROC_BOUND && Math.abs(shooterRPM.average() - currentShooterTargetRPM) < ConfigConstants.RPM_DISTANCE_BOUND) {
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

        shooterMotorLeft.setRPM(targetShooterRPM);
        shooterMotorRight.setRPM(targetShooterRPM);
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
        return shooterMotorLeft;
    }

    public void increaseManualRPMAdjustment() {
        manualAdjustment += ConfigConstants.RPM_ADJUST_AMOUNT;
    }
    public void decreaseManualRPMAdjustment() {
        manualAdjustment -= ConfigConstants.RPM_ADJUST_AMOUNT;
    }
    public void spinAtCalculatedSpeed(double range) {
        setRPM(calculateRPM(range));
    }
    public double calculateRPM(double range) {
        double rpm = 0;
        rpm = multiRPM(range) + manualAdjustment;

        return rpm;
    }
    public double multiRPM(double range) {

        if (range > ConfigConstants.FURTHEST_DIST) {
            lastRPM = ConfigConstants.FURTHEST_RPM;
            return ConfigConstants.FURTHEST_RPM;
        }
        else if (range > ConfigConstants.BACK_TRIANGLE_DIST) {
            lastRPM = ConfigConstants.BACK_TRIANGLE_RPM;
            return ConfigConstants.BACK_TRIANGLE_RPM;
        }
        else if (range > ConfigConstants.FAR_FRONT_TRAINGLE_DIST) {
            lastRPM = ConfigConstants.FAR_FRONT_TRIANGLE_RPM;
            return ConfigConstants.FAR_FRONT_TRIANGLE_RPM;
        }
        else if (range > ConfigConstants.MID_FRONT_TRIANGLE_DIST) {
            lastRPM = ConfigConstants.MID_FRONT_TRIANGLE_RPM;
            return ConfigConstants.MID_FRONT_TRIANGLE_RPM;
        }
        else if (range > ConfigConstants.FRONT_TRIANGLE_DIST) {
            lastRPM = ConfigConstants.FRONT_TRIANGLE_RPM;
            return ConfigConstants.FRONT_TRIANGLE_RPM;
        }
        else if (range > ConfigConstants.CLOSEST_DIST) {
            lastRPM = ConfigConstants.CLOSEST_RPM;
            return ConfigConstants.CLOSEST_RPM;
        }
        else {
            return lastRPM;
        }

    }

    public double getManualAdjustment() {
        return manualAdjustment;
    }
}
