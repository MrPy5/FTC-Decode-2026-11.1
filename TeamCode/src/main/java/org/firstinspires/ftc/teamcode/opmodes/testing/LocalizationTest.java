package org.firstinspires.ftc.teamcode.opmodes.testing;


import com.acmerobotics.dashboard.FtcDashboard;
import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.Robot.RobotState;
import org.firstinspires.ftc.teamcode.config.Storage;
import org.firstinspires.ftc.teamcode.config.subsystems.Ascent;
import org.firstinspires.ftc.teamcode.config.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.config.subsystems.vision.LimelightCamera;
import org.firstinspires.ftc.teamcode.config.util.Alliance;
import org.firstinspires.ftc.teamcode.config.util.CyclingList;
import org.firstinspires.ftc.teamcode.config.util.OpMode;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

//shooting
//turn button
//turning could be faster
//take out reset pose button in robot

@TeleOp(name="Localization Test")
@Disabled
public class LocalizationTest extends LinearOpMode {

        @Override
        public void runOpMode () {
            LimelightCamera ll = new LimelightCamera(hardwareMap, null);


            waitForStart();


            while (opModeIsActive()) {
                LLResult result = ll.getResult();

                double rawX = result.getBotpose().getPosition().x;
                double rawY =  result.getBotpose().getPosition().y;

                double pedroX = (rawY / DistanceUnit.mPerInch);
                double pedroY = (rawX / DistanceUnit.mPerInch);






                Pose asPedro = FTCCoordinates.INSTANCE.convertToPedro(
                        new Pose(pedroX, pedroY, result.getBotpose().getOrientation().getYaw(AngleUnit.RADIANS), PedroCoordinates.INSTANCE));
                asPedro = new Pose(142 - asPedro.getX(), asPedro.getY());
                telemetry.addData("x", (asPedro.getX()));
                telemetry.addData("y", (asPedro.getY()));
                //telemetry.addData("heading", ll.getBotPose().getOrientation().getYaw(AngleUnit.DEGREES));
                telemetry.update();

            }


        }
}




