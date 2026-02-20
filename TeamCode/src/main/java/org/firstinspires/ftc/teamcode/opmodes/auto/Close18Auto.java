package org.firstinspires.ftc.teamcode.opmodes.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.util.OpMode;
import org.firstinspires.ftc.teamcode.config.util.scheduler.InstantCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.SequentialCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.Wait;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitFollower;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitShooter;
import org.firstinspires.ftc.teamcode.opmodes.auto.paths.close.ClosePaths;
import org.firstinspires.ftc.teamcode.opmodes.auto.paths.far.FarPaths;

@Autonomous(name = "Close FAST")
public class Close18Auto extends com.qualcomm.robotcore.eventloop.opmode.OpMode {


    Robot robot;
    SequentialCommand pathSchedule;


    @Override
    public void init() {

        robot = new Robot(hardwareMap, OpMode.AUTONOMOUS, null, null, null, gamepad1, gamepad2, telemetry, null);
        robot.initAutoPositions();
        ClosePaths.useBluePaths();


    }

    @Override
    public void init_loop() {
        robot.initLoopAuto(ClosePaths::useBluePaths, ClosePaths::useRedPaths, true);
        robot.initLoopTelemetry();
        telemetry.update();
    }

    @Override
    public void start() {
        robot.startAuto(ClosePaths::buildPaths, ClosePaths.startPose);

        SequentialCommand shootPreload = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.setRPM(2700)),
                new InstantCommand(() -> robot.intake.intake()),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.shootPreload)),
                new Wait(300),
                new WaitShooter(robot.shooter),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.transfer.stop())
        );

        SequentialCommand spike1 = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.driveToSpike1)),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike1)),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike1ToShoot)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(500),
                new InstantCommand(() -> robot.transfer.stop())
        );

        SequentialCommand spike2 = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.driveToSpike2)),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike2)),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike2ToShoot)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(500),
                new InstantCommand(() -> robot.transfer.stop())
        );

        SequentialCommand spike3 = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.driveToSpike3)),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike3)),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike3ToShoot)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(500),
                new InstantCommand(() -> robot.transfer.stop())
        );

        SequentialCommand park = new SequentialCommand(
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.parkPath)),
                new WaitFollower(robot.follower)
        );

        pathSchedule = new SequentialCommand(
                shootPreload,
                spike1,
                spike2,
                spike3,
                park
        );
        pathSchedule.start();
    }

    @Override
    public void loop() {
        pathSchedule.update(robot.getMilliseconds());
        robot.update();
        robot.updateHardware();

    }
    @Override
    public void stop() {
        robot.stop();
    }

}