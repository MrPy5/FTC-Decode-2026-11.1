package org.firstinspires.ftc.teamcode.opmodes.auto;


import com.pedropathing.geometry.BezierLine;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.subsystems.Turret;
import org.firstinspires.ftc.teamcode.config.subsystems.vision.LimelightCamera;
import org.firstinspires.ftc.teamcode.config.util.OpMode;
import org.firstinspires.ftc.teamcode.config.util.scheduler.InstantCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.SequentialCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.Wait;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitFollower;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitFollowerOrStuck;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitFollowerOrThreeBalls;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitParametric;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitParametricOrThreeBalls;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitScheduler;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitShooter;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitUntil;
import org.firstinspires.ftc.teamcode.opmodes.auto.paths.close.ClosePaths;
import org.firstinspires.ftc.teamcode.opmodes.auto.paths.far.FarPaths;

import java.util.function.BooleanSupplier;

@Autonomous(name = "Far")
public class FarAuto extends com.qualcomm.robotcore.eventloop.opmode.OpMode {


    Robot robot;
    SequentialCommand pathSchedule;


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
        robot.turret.setState(Turret.TurretState.TRACK);
        robot.limelightCamera.switchToBallDetection();
        robot.limelightCamera.setCurrentMode(LimelightCamera.TagMode.BALL);

        SequentialCommand shootPreload = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.setRPM(2950)),

                new InstantCommand(() -> robot.intake.intake()),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.shooter.unblock()),
                new WaitShooter(robot.shooter),
                new InstantCommand(() ->robot.transfer.intakeTransfer()),
                new Wait(500),
                new InstantCommand(() -> robot.transfer.stop())
        );

        SequentialCommand spike3 = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.driveToSpike3)),
                new WaitUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() {
                        return robot.follower.getCurrentTValue() > 0.5;
                    }
                }),
                new InstantCommand(() -> robot.follower.setMaxPower(0.5)),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.follower.setMaxPower(1)),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.spike3ToShoot)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.shooter.unblock()),
                new WaitParametric(robot.follower),
                new Wait(200),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(300),
                new InstantCommand(() -> robot.transfer.stop())
        );

        SequentialCommand getGateBall = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),

                new InstantCommand(() -> robot.follower.followPath(FarPaths.driveToGateOverflow)),
                new WaitUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() {
                        return robot.follower.getCurrentTValue() > 0.9;
                    }
                }),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.gateOverFlow)),
                new WaitUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() {
                        return robot.follower.atParametricEnd() || (robot.follower.getCurrentTValue() > 0.5 && robot.follower.getVelocity().getMagnitude() < 2) || robot.intake.getArtifactSensor().hasBall();
                    }
                }),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.gateOverFlowToShoot)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.shooter.unblock()),
                new WaitParametric(robot.follower),
                new Wait(200),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(300),
                new InstantCommand(() -> robot.transfer.stop())
        );
        SequentialCommand humanPlayerToGate = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),

                new InstantCommand(() -> robot.follower.followPath(FarPaths.driveToHP)),
                new WaitUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() {
                        return robot.follower.getCurrentTValue() > 0.95;
                    }
                }),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.hpToGateOverFlow)),
                new WaitUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() {
                        return robot.follower.atParametricEnd() || (robot.follower.getCurrentTValue() > 0.5 && robot.follower.getVelocity().getMagnitude() < 2) || robot.intake.getArtifactSensor().hasBall();
                    }
                }),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.gateOverFlowToShoot)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.shooter.unblock()),
                new WaitParametric(robot.follower),
                new Wait(200),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(300),
                new InstantCommand(() -> robot.transfer.stop())
        );
        SequentialCommand humanPlayer2 = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.driveToHP)),
                new WaitUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() {
                        return robot.follower.atParametricEnd() || (robot.follower.getCurrentTValue() > 0.5 && robot.follower.getVelocity().getMagnitude() < 2) || robot.intake.getArtifactSensor().hasBall();
                    }
                }),
                new Wait(300),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.backupHP)),
                new WaitUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() {
                        return robot.follower.atParametricEnd() || (robot.follower.getCurrentTValue() > 0.5 && robot.follower.getVelocity().getMagnitude() < 2) || robot.intake.getArtifactSensor().hasBall();
                    }
                }),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.returnHP)),
                new WaitUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() {
                        return robot.follower.atParametricEnd() || (robot.follower.getCurrentTValue() > 0.5 && robot.follower.getVelocity().getMagnitude() < 2) || robot.intake.getArtifactSensor().hasBall();
                    }
                }),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.hpToShoot)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.shooter.unblock()),
                new WaitParametric(robot.follower),
                new Wait(200),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(300),
                new InstantCommand(() -> robot.transfer.stop())
        );
        SequentialCommand visionBall = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),

                new InstantCommand(() -> robot.follower.followPath(robot.limelightCamera.getBallPath())),
                new WaitUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() {
                        return robot.follower.atParametricEnd() || (robot.follower.getCurrentTValue() > 0.5 && robot.follower.getVelocity().getMagnitude() < 2) || robot.intake.getArtifactSensor().hasBall();
                    }
                }),
                new Wait(500),
                new InstantCommand(() -> robot.follower.followPath(robot.follower.pathBuilder().addPath(new BezierLine(robot.follower.getPose(), FarPaths.shootPose))
                        .setLinearHeadingInterpolation(FarPaths.shootPose.getHeading(), FarPaths.shootPose.getHeading())
                        .build())),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.shooter.unblock()),
                new WaitParametric(robot.follower),
                new Wait(200),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(300),
                new InstantCommand(() -> robot.transfer.stop())
        );


        SequentialCommand park = new SequentialCommand(
                new InstantCommand(() -> robot.follower.followPath(FarPaths.parkPath)),
                new WaitFollower(robot.follower)
        );

        pathSchedule = new SequentialCommand(
                shootPreload,
                humanPlayer2,
                spike3,
                visionBall,
                visionBall,
                visionBall,
                visionBall,
                park
        );
        pathSchedule.start();
    }

    @Override
    public void loop() {
        pathSchedule.update(robot.getMilliseconds());
        robot.update();
        robot.updateHardware();
        telemetry.addData("python", robot.limelightCamera.getPython()[0]);
        telemetry.update();
    }
    @Override
    public void stop() {
        robot.stop();
    }

}