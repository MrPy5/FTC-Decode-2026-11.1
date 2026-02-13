package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.util.CachedMotor;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

public class Transfer {



    HardwareMap hardwareMap;
    CachedMotor transferMotor;
    Servo transferBlocker;

    double transferRPM = 0;
    double transferPower = 0;


    public Transfer(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;


        transferMotor = new CachedMotor(hardwareMap.get(DcMotorEx.class, ConfigConstants.GREEN_WHEEL), ConfigConstants.TRANSFER_CPR);
        transferMotor.setDirection(DcMotorEx.Direction.FORWARD); //intake with positive value

        transferMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        transferBlocker = hardwareMap.get(Servo.class, ConfigConstants.TRANSFER_BLOCKER);

    }
    public void update(Robot.RobotState robotState) {
        if (robotState == Robot.RobotState.INTAKE && transferMotor.getCurrent() > 4) {
            stop();
        }
    }
    public void updatePower() {

        transferMotor.setPower(transferPower);
    }

    public void intakeTransfer() {
        setTransferPower(ConfigConstants.TRANSFER_INTAKE_RPM);
    }

    public void intakeTransferSlow() {
        setTransferPower(ConfigConstants.TRANSFER_INTAKE_SLOW_RPM);
    }

    public void outtakeTransfer() {
        setTransferPower(ConfigConstants.TRANSFER_OUTTAKE_RPM);
    }
    public void stop() {
        setTransferPower(0);
    }

    public void setTransferRPM(double rpm) {
        transferRPM = rpm;
    }

    public void setTransferPower(double transferPower) {
        this.transferPower = transferPower;
    }

    public void block() {
        transferBlocker.setPosition(ConfigConstants.TRANSFER_BLOCK);
    }
    public void unblock() {
        transferBlocker.setPosition(ConfigConstants.TRANSFER_UNBLOCK);
    }

    public CachedMotor getTransferMotor() {
        return transferMotor;
    }
}
