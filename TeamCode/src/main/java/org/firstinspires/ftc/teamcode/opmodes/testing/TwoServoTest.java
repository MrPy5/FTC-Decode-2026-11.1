

package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "Two Servo Test")
@Disabled

public class TwoServoTest extends LinearOpMode {


    public Servo testServo;
    public Servo testServo2;

    @Override
    public void runOpMode() {

        testServo = hardwareMap.get(Servo.class, "left lindexer");
        testServo2 = hardwareMap.get(Servo.class, "right lindexer");


        double currentPosition = 0.5;
        double currentPosition2 = 0.5;

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
            if (gamepad1.leftTriggerWasPressed()) {
                currentPosition2 -= 0.5;
            }
            if (gamepad1.rightTriggerWasPressed()) {
                currentPosition2 += 0.5;
            }

            testServo.setPosition(currentPosition);
            testServo2.setPosition(currentPosition2);

            telemetry.addData("Servo Angle", currentPosition);
            telemetry.addData("Servo Angle2", currentPosition2);
            telemetry.update();

        }
    }
}