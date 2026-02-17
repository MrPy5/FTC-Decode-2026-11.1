package org.firstinspires.ftc.teamcode.opmodes.auto;


import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.util.OpMode;
import org.firstinspires.ftc.teamcode.config.util.scheduler.InstantCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.SequentialCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.Wait;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitFollower;
import org.firstinspires.ftc.teamcode.opmodes.auto.paths.far.FarPaths;

@Autonomous(name = "reset")
public class Reset extends com.qualcomm.robotcore.eventloop.opmode.OpMode {


    Robot robot;



    @Override
    public void init() {

        robot = new Robot(hardwareMap, OpMode.AUTONOMOUS, null, null, null, gamepad1, gamepad2, telemetry, null);
        robot.initAutoPositions();


        robot.follower.setStartingPose(new Pose(72,72,0));
        robot.follower.update();

    }

    @Override
    public void init_loop() {
        robot.initLoopAuto(FarPaths::useBluePaths, FarPaths::useRedPaths, true);
        robot.initLoopTelemetry();
        telemetry.update();
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        robot.update();
        robot.updateHardware();

    }
    @Override
    public void stop() {
        robot.stop();
    }


}