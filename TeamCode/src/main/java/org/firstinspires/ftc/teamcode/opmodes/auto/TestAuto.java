package org.firstinspires.ftc.teamcode.opmodes.auto;


import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.util.OpMode;
import org.firstinspires.ftc.teamcode.config.util.scheduler.CommandScheduler;
import org.firstinspires.ftc.teamcode.config.util.scheduler.InstantCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.SequentialCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.Wait;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitFollower;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;
import org.firstinspires.ftc.teamcode.opmodes.auto.paths.far.FarPaths;

@Autonomous(name = "Auto Far")
public class TestAuto extends com.qualcomm.robotcore.eventloop.opmode.OpMode {


    Robot robot;
    SequentialCommand pathSchedule = new SequentialCommand(
            new InstantCommand(() -> robot.follower.followPath(FarPaths.parkPath)),
            new WaitFollower(robot.follower),
            new Wait(1000),
            new InstantCommand(() -> robot.follower.followPath(FarPaths.shootPreload)),
            new WaitFollower(robot.follower)

    );


    @Override
    public void init() {

        robot = new Robot(hardwareMap, OpMode.AUTONOMOUS, null, null, null, gamepad1, gamepad2, telemetry, null);
        robot.initAutoPositions();
        FarPaths.useBluePaths();
    }

    @Override
    public void init_loop() {
        robot.initLoopAuto(FarPaths::useBluePaths, FarPaths::useRedPaths, true);
        robot.initLoopTelemetry();
        telemetry.update();
    }

    @Override
    public void start() {
        robot.startAuto(FarPaths::buildPaths, FarPaths.startPose);
        pathSchedule.start();
    }

    @Override
    public void loop() {
        pathSchedule.update(robot.getMilliseconds());
        robot.update();
        robot.updateHardware();

    }


}