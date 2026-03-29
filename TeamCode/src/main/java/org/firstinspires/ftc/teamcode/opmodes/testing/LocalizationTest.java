package org.firstinspires.ftc.teamcode.opmodes.testing;


import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
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
public class LocalizationTest extends LinearOpMode {

        @Override
        public void runOpMode () {
            LimelightCamera ll = new LimelightCamera(hardwareMap);


            waitForStart();


            while (opModeIsActive()) {
                telemetry.addData("x", (ll.getBotPose().getPosition().x * 39.701) + 72);

                telemetry.addData("y", (ll.getBotPose().getPosition().y * -39.701) + 72);
                telemetry.addData("heading", ll.getBotPose().getOrientation().getYaw(AngleUnit.DEGREES));
                telemetry.update();

            }


        }
}




