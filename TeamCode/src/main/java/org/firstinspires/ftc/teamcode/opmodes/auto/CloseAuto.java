package org.firstinspires.ftc.teamcode.opmodes.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.Storage;
import org.firstinspires.ftc.teamcode.config.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.config.subsystems.Turret;
import org.firstinspires.ftc.teamcode.config.subsystems.vision.LimelightCamera;
import org.firstinspires.ftc.teamcode.config.util.OpMode;
import org.firstinspires.ftc.teamcode.config.util.scheduler.InstantCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.SequentialCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.Wait;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitParametric;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitParametric;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitScheduler;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitShooter;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitShooterNotReady;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitUntil;
import org.firstinspires.ftc.teamcode.opmodes.auto.paths.close.ClosePaths;
import org.firstinspires.ftc.teamcode.opmodes.auto.paths.far.FarPaths;

import java.util.function.BooleanSupplier;

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
        robot.turret.setState(Turret.TurretState.TRACK);
        robot.limelightCamera.setCurrentMode(LimelightCamera.TagMode.MOTIF);

        SequentialCommand shoot = new SequentialCommand(
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.shootLindexing, robot.getMilliseconds())),
               // new WaitShooterNotReady(robot.shooter),
                new Wait(700),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.shootLindexing, robot.getMilliseconds())),
               // new WaitShooterNotReady(robot.shooter),
                new Wait(700),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.shootLindexing, robot.getMilliseconds())),
               // new WaitShooterNotReady(robot.shooter),
                new Wait(700)
        );
        SequentialCommand shootPreload = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.setRPM(2350)),
                new InstantCommand(() -> robot.intake.intake()),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.shootPreload)),

                new WaitShooter(robot.shooter),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(300),
                new InstantCommand(() -> robot.transfer.stop())
                /*new InstantCommand(() -> robot.follower.followPath(ClosePaths.scanMotif)),
                new WaitParametric(robot.follower),
                new Wait(500),
                new InstantCommand(() -> robot.setMotif(robot.limelightCamera.getMotif())),
                new InstantCommand(() -> robot.lindexer.setIndex(true))*/
        );
        SequentialCommand spike1 = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.setRPM(2350)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startLindexing, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.driveToSpike1)),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.follower.setMaxPower(0.4)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike1)),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.follower.setMaxPower(0.7)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike1ToGateTurn)),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopLindexing, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.setMaxPower(0.6)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.gateTurnToGatePush1)),
                new InstantCommand(() -> robot.follower.setMaxPower(1)),
                new WaitParametric(robot.follower),
                new Wait(1000),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.gatePushToShoot1)),

                new WaitParametric(robot.follower),

                shoot
        );

        SequentialCommand spike2 = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startLindexing, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.driveToSpike2)),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.follower.setMaxPower(0.4)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike2)),
                new WaitParametric(robot.follower),
               // new Wait(0),
                new InstantCommand(() -> robot.follower.setMaxPower(1)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike2Reverse)),
                new Wait(100),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopLindexing, robot.getMilliseconds())),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike2ToShoot)),

                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.intake.intake()),

                shoot
        );
        SequentialCommand spike3 = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startLindexing, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.driveToSpike3)),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.follower.setMaxPower(0.4)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike3)),
                new WaitParametric(robot.follower),

                new InstantCommand(() -> robot.follower.setMaxPower(1)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike3ToShoot)),

                new Wait(100),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopLindexing, robot.getMilliseconds())),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.intake.intake()),

                shoot
        );
        SequentialCommand park = new SequentialCommand(
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.parkPath)),
                new WaitParametric(robot.follower)
        );

        pathSchedule = new SequentialCommand(
                shootPreload,
              //  spike1,
              //  spike2,
              //  spike3,
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