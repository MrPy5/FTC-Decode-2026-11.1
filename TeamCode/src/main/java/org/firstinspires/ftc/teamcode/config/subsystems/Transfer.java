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


    Robot robot;
    HardwareMap hardwareMap;
    CachedMotor transferMotor;
    Servo transferBlocker;

    public boolean motorStopped = false;

    double transferRPM = 0;
    double transferPower = 0;

    public boolean startChecking = false;


    public Transfer(HardwareMap hardwareMap, Robot robot) {
        this.hardwareMap = hardwareMap;
        this.robot = robot;


        transferMotor = new CachedMotor(hardwareMap.get(DcMotorEx.class, ConfigConstants.GREEN_WHEEL), ConfigConstants.TRANSFER_CPR);
        transferMotor.setDirection(DcMotorEx.Direction.FORWARD); //intake with positive value

        transferMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        transferMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, ConfigConstants.TRANSFER_PID);


        transferBlocker = hardwareMap.get(Servo.class, ConfigConstants.TRANSFER_BLOCKER);

    }
    public void update() {
        if (transferMotor.getVelocity() < 1700 && robot.getRobotState() == Robot.RobotState.INTAKE && startChecking) {
            stop();
            motorStopped = true; // for transfer to shooting mode;
            startChecking = false;
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
    public void startCheckingNow() {
        startChecking = true;
    }
}
