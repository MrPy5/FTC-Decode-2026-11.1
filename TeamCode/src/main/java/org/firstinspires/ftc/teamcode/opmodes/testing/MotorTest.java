

package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "Motor Test")

public class MotorTest extends LinearOpMode {


    public DcMotorEx testMotor;

    @Override
    public void runOpMode() {

        testMotor = hardwareMap.get(DcMotorEx.class, "shooter right");



        waitForStart();

        while (opModeIsActive()) {

            testMotor.setPower(.3);

            telemetry.addData("shooter", testMotor.getVelocity());
            telemetry.update();

        }
    }
}