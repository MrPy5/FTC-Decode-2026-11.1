package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.util.CachedMotor;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

public class Transfer {



    HardwareMap hardwareMap;
    CachedMotor spindleMotor;
    CachedMotor greenWheelMotor;
    Servo transferBlocker;

    double spindlePower = 0;
    double greenWheelPower = 0;


    public Transfer(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        spindleMotor = new CachedMotor(hardwareMap.get(DcMotorEx.class, ConfigConstants.SPINDLE));
        spindleMotor.setDirection(DcMotorEx.Direction.REVERSE); //intake with positive value

        spindleMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        spindleMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        greenWheelMotor = new CachedMotor(hardwareMap.get(DcMotorEx.class, ConfigConstants.GREEN_WHEEL));
        greenWheelMotor.setDirection(DcMotorEx.Direction.REVERSE); //intake with positive value

        greenWheelMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        greenWheelMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        transferBlocker = hardwareMap.get(Servo.class, ConfigConstants.TRANSFER_BLOCKER);

    }

    public void updatePower() {
        spindleMotor.setPower(spindlePower);
        greenWheelMotor.setPower(greenWheelPower);
    }

    //Additional functions
    public void intake() {
        setSpindlePower(0);
        setGreenWheelPower(0);
    }
    public void outtake() {
        setSpindlePower(0);
        setGreenWheelPower(0);
    }
    public void stop() {
        setSpindlePower(0);
        setGreenWheelPower(0);
    }
    public void setSpindlePower(double power) {
        spindlePower = power;
    }
    public void setGreenWheelPower(double power) {
        greenWheelPower = power;
    }

    public void block() {
        transferBlocker.setPosition(ConfigConstants.TRANSFER_BLOCK);
    }
    public void unblock() {
        transferBlocker.setPosition(ConfigConstants.TRANSFER_UNBLOCK);
    }




}
