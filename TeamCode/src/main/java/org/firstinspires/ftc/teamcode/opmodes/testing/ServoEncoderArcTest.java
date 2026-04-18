package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

import java.lang.reflect.Array;
import java.util.ArrayList;

@TeleOp(name = "Arc Test")
//http://192.168.43.1:8080/dash
public class ServoEncoderArcTest extends LinearOpMode {


    @Override
    public void runOpMode() {

        DcMotorEx turretEncoder;
        Servo testServo;

        testServo = hardwareMap.get(Servo.class, "ts right");

        turretEncoder = hardwareMap.get(DcMotorEx.class, ConfigConstants.TURRET_ENCODER);

        turretEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double[] angles = {0.88,0.805,0.73,0.655,0.58,0.505,0.43,0.355,0.28,0.205,0.13};
        ArrayList<double[]> positions = new ArrayList<>();
        int curAngle = -1;

        waitForStart();

        while (opModeIsActive()) {
            double position = turretEncoder.getCurrentPosition();
            double rotations = (position / ConfigConstants.TICKS_PER_ENCODER_REVOLUTION) * (ConfigConstants.ENCODER_TEETH / ConfigConstants.TURRET_TEETH);
            double angle = rotations * 360;


            if (gamepad1.psWasPressed()) {
                turretEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            if (gamepad1.triangleWasPressed() && curAngle < 9) {
                curAngle += 1;
                testServo.setPosition(angles[curAngle]);
            }
            if (gamepad1.circleWasPressed()) {
                positions.add(new double[]{angles[curAngle], position});
            }


            telemetry.addData("angle", angle);
            for (double[] pos : positions) {
                telemetry.addData("pos", pos.toString());
            }
            telemetry.update();




        }
    }
}