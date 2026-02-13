

package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "Servo Test")

public class ServoTest extends LinearOpMode {


    public Servo testServo;

    @Override
    public void runOpMode() {

        testServo = hardwareMap.get(Servo.class, "intake blocker");


        double currentPosition = 0.5;

        waitForStart();

        while (opModeIsActive()) {

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

            testServo.setPosition(currentPosition);

            telemetry.addData("Servo Angle", currentPosition);
            telemetry.update();

        }
    }
}