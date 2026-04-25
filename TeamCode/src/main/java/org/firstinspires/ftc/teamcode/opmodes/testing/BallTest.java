package org.firstinspires.ftc.teamcode.opmodes.testing;


import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.config.subsystems.vision.LimelightCamera;

//shooting
//turn button
//turning could be faster
//take out reset pose button in robot

@TeleOp(name="Ball Test")

public class BallTest extends LinearOpMode {

        @Override
        public void runOpMode () {
            LimelightCamera ll = new LimelightCamera(hardwareMap, null);

            ll.switchToBallDetection();

            waitForStart();


            while (opModeIsActive()) {
                double[] llpython = ll.getPython();

                telemetry.addData("ball", llpython[0]);
                telemetry.addData("angle", llpython[1]);
                //telemetry.addData("heading", ll.getBotPose().getOrientation().getYaw(AngleUnit.DEGREES));
                telemetry.update();

            }


        }
}




