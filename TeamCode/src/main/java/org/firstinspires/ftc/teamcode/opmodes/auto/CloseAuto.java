package org.firstinspires.ftc.teamcode.opmodes.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.Storage;
import org.firstinspires.ftc.teamcode.config.util.OpMode;
import org.firstinspires.ftc.teamcode.config.util.scheduler.InstantCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.SequentialCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.Wait;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitFollower;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitFollowerOrStuck;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitParametric;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitScheduler;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitShooter;
import org.firstinspires.ftc.teamcode.opmodes.auto.paths.close.ClosePaths;
import org.firstinspires.ftc.teamcode.opmodes.auto.paths.far.FarPaths;

@Autonomous(name = "Close")
public class CloseAuto extends com.qualcomm.robotcore.eventloop.opmode.OpMode {


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
        SequentialCommand shoot = new SequentialCommand(
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.shootLindexing, robot.getMilliseconds())),
                new Wait(350),
                new WaitScheduler(robot.scheduler),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.shootLindexing, robot.getMilliseconds())),
                new Wait(750),
                new WaitScheduler(robot.scheduler),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.shootLindexing, robot.getMilliseconds())),
                new Wait(350),
                new WaitScheduler(robot.scheduler)
        );
        SequentialCommand shootPreload = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.setRPM(2500)),
                new InstantCommand(() -> robot.intake.intake()),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.shootPreload)),

                new WaitShooter(robot.shooter),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(300),
                new InstantCommand(() -> robot.transfer.stop()),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.scanMotif)),
                //Supplier<Boolean> condition
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.setMotifByTag(robot.tagCamera.getMostPopularMotifTag())),
                new InstantCommand(() -> robot.tagCamera.getVisionPortal().close()),
                new InstantCommand(() -> robot.lindexer.setIndex(true))
        );
        SequentialCommand spike1 = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.setRPM(2450)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startLindexing, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.driveToSpike1)),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.follower.setMaxPower(0.3)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike1)),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.intake.lift()),
                //new Wait(500),
                new InstantCommand(() -> robot.follower.setMaxPower(0.7)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike1ToGateTurn)),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.follower.setMaxPower(0.6)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.gateTurnToGatePush1)),
                new InstantCommand(() -> robot.follower.setMaxPower(1)),
                new WaitFollowerOrStuck(robot.follower),
                new Wait(500),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.gatePushToShoot1)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopLindexing, robot.getMilliseconds())),
                new WaitFollower(robot.follower),

                shoot
        );

        SequentialCommand spike2 = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startLindexing, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.driveToSpike2)),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.follower.setMaxPower(0.3)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike2)),
                new WaitFollowerOrStuck(robot.follower),
                new InstantCommand(() -> robot.intake.lift()),
               // new Wait(0),
                new InstantCommand(() -> robot.follower.setMaxPower(1)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike2Reverse)),

                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike2ToShoot)),
                new Wait(300),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopLindexing, robot.getMilliseconds())),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.intake.intake()),
                shoot
        );
        SequentialCommand spike3 = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startLindexing, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.driveToSpike3)),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.follower.setMaxPower(0.3)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike3)),
                new WaitFollowerOrStuck(robot.follower),
                new InstantCommand(() -> robot.intake.lift()),
              //  new Wait(500),
                new InstantCommand(() -> robot.follower.setMaxPower(1)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike3ToShoot)),

                new Wait(300),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopLindexing, robot.getMilliseconds())),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.intake.intake()),
                shoot
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