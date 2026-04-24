package org.firstinspires.ftc.teamcode.opmodes.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.subsystems.Turret;
import org.firstinspires.ftc.teamcode.config.util.OpMode;
import org.firstinspires.ftc.teamcode.config.util.scheduler.InstantCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.SequentialCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.Wait;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitFollower;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitFollowerOrThreeBalls;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitParametric;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitShooter;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitUntil;
import org.firstinspires.ftc.teamcode.opmodes.auto.paths.close.ClosePaths;
import org.firstinspires.ftc.teamcode.opmodes.auto.paths.far.FarPaths;

import java.util.function.BooleanSupplier;

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

        robot.turret.setState(Turret.TurretState.TRACK);

        SequentialCommand shootPreload = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.setRPM(2300)),
                new InstantCommand(() -> robot.intake.intake()),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.shootPreload)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.shooter.unblock()),
                new InstantCommand(() -> robot.intake.lift()),
                new Wait(100),
                new InstantCommand(() -> robot.turret.setAngle(3)),
                new WaitShooter(robot.shooter),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(300),
                new InstantCommand(() -> robot.transfer.stop())
        );

        SequentialCommand spike1 = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.turret.setState(Turret.TurretState.TRACK)),
                new InstantCommand(() -> robot.turret.setSOTM(true)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.driveToSpike1Bezier)),
                new WaitUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() {
                        return robot.follower.getCurrentTValue() > 0.4;
                    }
                }),
                new InstantCommand(() -> robot.follower.setMaxPower(0.4)),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.follower.setMaxPower(1)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike1ToPark)),
                new InstantCommand(() -> robot.intake.lift()),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.shooter.unblock()),
                new WaitUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() {
                        return robot.follower.getCurrentTValue() > 0.4;
                    }
                }),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(500),
                new InstantCommand(() -> robot.transfer.stop())
        );

        SequentialCommand spike2 = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.driveToSpike2Bezier)),
                new WaitUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() {
                        return robot.follower.getCurrentTValue() > 0.5;
                    }
                }),
                new InstantCommand(() -> robot.follower.setMaxPower(0.5)),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.follower.setMaxPower(1)),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike2ToShoot)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.shooter.unblock()),
                new InstantCommand(() -> robot.intake.lift()),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(300),
                new InstantCommand(() -> robot.transfer.stop())
        );

        SequentialCommand spike3 = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.driveToSpike3)),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike3)),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.spike3ToShoot)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.shooter.unblock()),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(500),
                new InstantCommand(() -> robot.transfer.stop())
        );
        SequentialCommand gateBall = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.driveToGate)),
                new WaitParametric(robot.follower),
                new Wait(500),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.backupFronGate)),
                new WaitParametric(robot.follower),
                new Wait(800),
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.gateAngleToShoot)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.shooter.unblock()),
                new InstantCommand(() -> robot.intake.lift()),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(300),
                new InstantCommand(() -> robot.transfer.stop())
        );

        SequentialCommand park = new SequentialCommand(
                new InstantCommand(() -> robot.follower.followPath(ClosePaths.parkPath)),
                new WaitFollower(robot.follower)
        );

        pathSchedule = new SequentialCommand(
                shootPreload,
                spike2,
                gateBall,
                gateBall,
                gateBall,
                spike1,
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