package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.config.util.CyclingList;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

@TeleOp(name = "Encoder Test")
@Disabled
//http://192.168.43.1:8080/dash
public class EncoderTest extends LinearOpMode {


    @Override
    public void runOpMode() {

        DcMotorEx turretEncoder;
        Servo testServo;

        testServo = hardwareMap.get(Servo.class, "ts right");

        turretEncoder = hardwareMap.get(DcMotorEx.class, ConfigConstants.TURRET_ENCODER);

        turretEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double currentPosition = 0.5;

        waitForStart();

        while (opModeIsActive()) {
            double position = turretEncoder.getCurrentPosition();
            double rotations = (position / ConfigConstants.TICKS_PER_ENCODER_REVOLUTION) * (ConfigConstants.ENCODER_TEETH / ConfigConstants.TURRET_TEETH);
            double angle = rotations * 360;


            if (gamepad1.rightBumperWasPressed()) {

                currentPosition += .05;

            }
            if (gamepad1.leftBumperWasPressed()) {
                currentPosition -= .05;

            }

            if (gamepad1.circleWasPressed()) {
                currentPosition += .005;

            }

            if (gamepad1.squareWasPressed()) {
                currentPosition -= .005;

            }
            if (gamepad1.psWasPressed()) {
                turretEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }


            testServo.setPosition(currentPosition);

            telemetry.addData("Servo Angle", currentPosition);
            telemetry.addData("angle", angle);
            telemetry.update();




        }
    }
}