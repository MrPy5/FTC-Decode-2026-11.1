package org.firstinspires.ftc.teamcode.config.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
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
    CachedMotor shooterMotor;
    Servo blocker;

    double targetShooterRPM = 0;
    double lastRPM = ConfigConstants.DEFAULT_RPM;
    double manualAdjustment = 0;
    CyclingList shooterRPM = new CyclingList(5);



    ShooterState shooterState = ShooterState.NOTREADY;

    public Shooter(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        shooterMotor = new CachedMotor(hardwareMap.get(DcMotorEx.class, ConfigConstants.SHOOTER), ConfigConstants.SHOOTER_CPR);
        shooterMotor.setDirection(DcMotorEx.Direction.REVERSE);

        shooterMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        shooterMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        shooterMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, ConfigConstants.SHOOTER_PID);

        blocker = hardwareMap.get(Servo.class, ConfigConstants.BLOCKER);


    }

    //Util functions
    public void update(Robot robot) {
        shooterRPM.add(robot.shooter.getShooterMotor().getRPM(), robot.getMilliseconds());

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

        shooterMotor.setRPM(targetShooterRPM);
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
        blocker.setPosition(ConfigConstants.BLOCKER_BLOCK);
    }
    public void unblock() {
        blocker.setPosition(ConfigConstants.BLOCKER_UNBLOCK);
    }

    public CachedMotor getShooterMotor() {
        return shooterMotor;
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
