package org.firstinspires.ftc.teamcode.config.util;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class CachedMotor {

    DcMotorEx motor;
    double lastPower = 0;
    double lastRPM = 0;
    double lastAngle = 0;
    double lastVelocity = 0;
    final double secondsPerMinute = 60;
    double ticksPerRevolution = 0;
    public CachedMotor(DcMotorEx motor) {
        this.motor = motor;
    }
    public CachedMotor(DcMotorEx motor, double ticksPerRevolution) {
        this.motor = motor;
        this.ticksPerRevolution = ticksPerRevolution;
    }

    public void setDirection(DcMotorEx.Direction direction) {
        motor.setDirection(direction);
    }
    public void setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior zeroPowerBehavior) {
        motor.setZeroPowerBehavior(zeroPowerBehavior);
    }
    public void setMode(DcMotorEx.RunMode runMode) {
        motor.setMode(runMode);
    }

    public void setPIDFCoefficients(DcMotorEx.RunMode runMode, PIDFCoefficients pidfCoefficients) {
        motor.setPIDFCoefficients(runMode, pidfCoefficients);
    }
    public PIDFCoefficients getPIDFCoefficients(DcMotorEx.RunMode runMode) {
        motor.resetDeviceConfigurationForOpMode();
        return motor.getPIDFCoefficients(runMode);
    }
    public void setPositionPIDFCoefficients(double p) {
        motor.setPositionPIDFCoefficients(p);
    }

    public void setVelocity(Double velocity) {
        if (lastVelocity != velocity) {
            lastVelocity = velocity;
            motor.setVelocity(velocity);
        }
    }
    public double getVelocity() {
        return motor.getVelocity();
    }
    public double getAngularVelocity() {
        return motor.getVelocity() * (1.0 / ticksPerRevolution) * 360.0;
    }

    public void setRPM(Double RPM) {
        if(lastRPM != RPM) {
            lastRPM = RPM;
            motor.setVelocity(RPM * ticksPerRevolution / secondsPerMinute);
        }
    }
    public double getRPM() {
        return motor.getVelocity() * secondsPerMinute / ticksPerRevolution;
    }

    public void setPower(double power) {
        if (power != lastPower) {
            lastPower = power;
            motor.setPower(power);
        }
    }
    public double getPower() {
        return lastPower;
    }
    public void setAngle(double angle) {
        if (lastAngle != angle) {
            lastAngle = angle;
            motor.setTargetPosition((int) Math.round((angle / 360.0) * ticksPerRevolution));
        }

    }

    public double getAngle() {
        int ticks = motor.getCurrentPosition();
        return (ticks / ticksPerRevolution) * 360.0;

    }
    public boolean isBusy() {
        return motor.isBusy();
    }

    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    }
    public void setTargetPosition(int position) {
        motor.setTargetPosition(position);
    }
    public int getTargetPosition() {

        return motor.getTargetPosition();
    }
    public double getCurrent() {
        return motor.getCurrent(CurrentUnit.AMPS);
    }



}
