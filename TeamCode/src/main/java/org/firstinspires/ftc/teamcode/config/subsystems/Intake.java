package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.config.util.CachedMotor;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

public class Intake {


    public enum IntakeState {
        INTAKE,
        OUTTAKE,
        OFF
    }

    HardwareMap hardwareMap;
    CachedMotor intakeMotor;

    double intakePower = 0;

    IntakeState intakeState = IntakeState.OFF;

    public Intake(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        intakeMotor = new CachedMotor(hardwareMap.get(DcMotorEx.class, ConfigConstants.INTAKE));
        intakeMotor.setDirection(DcMotorEx.Direction.REVERSE); //intake with positive value

        intakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    //Util functions
    public IntakeState getIntakeState() {
        return intakeState;
    }

    public void updatePower() {
        intakeMotor.setPower(intakePower);
    }

    //Additional functions
    public void intake() {
        setPower(ConfigConstants.INTAKE_POWER);
        intakeState = IntakeState.INTAKE;
    }
    public void outtake() {
        setPower(ConfigConstants.OUTTAKE_POWER);
        intakeState = IntakeState.OUTTAKE;
    }
    public void stopIntake() {
        setPower(0);
        intakeState = IntakeState.OFF;
    }
    public void setPower(double power) {
        intakePower = power;
    }

    public CachedMotor getIntakeMotor() {
        return intakeMotor;
    }



}
