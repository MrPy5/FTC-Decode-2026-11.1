package org.firstinspires.ftc.teamcode.opmodes.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.config.Robot;
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

        SequentialCommand shootPreload = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.setRPM(3100)),
                new InstantCommand(() -> robot.intake.intake()),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.shootPreload)),
                new WaitFollower(robot.follower),
                new WaitShooter(robot.shooter),
                new InstantCommand(() ->robot.transfer.intakeTransfer()),
                new Wait(500),
                new InstantCommand(() -> robot.transfer.stop())
        );
        SequentialCommand humanPlayer = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.driveToHP)),
                new InstantCommand(() -> robot.follower.setMaxPower(0.7)),
                new WaitParametric(robot.follower),
                new Wait(750),
               /* new InstantCommand(() -> robot.follower.followPath(FarPaths.backupHP)),
                new WaitParametric(robot.follower),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.returnHP)),
                new WaitParametric(robot.follower),
                new Wait(500),*/
                new InstantCommand(() -> robot.follower.setMaxPower(1)),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.hpToShoot)),
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
                new InstantCommand(() -> robot.follower.followPath(FarPaths.driveToSpike3)),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.follower.setMaxPower(0.7)),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.spike3)),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.follower.setMaxPower(1)),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.spike3ToShoot)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(500),
                new InstantCommand(() -> robot.transfer.stop())
        );

        SequentialCommand getGateBall = new SequentialCommand(
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.INTAKE)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds())),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.driveToGateOverflow)),
                new WaitFollowerOrStuck(robot.follower),
                new InstantCommand(() -> robot.follower.setMaxPower(0.5)),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.gateOverFlow)),
                new WaitFollowerOrStuck(robot.follower),
                new InstantCommand(() -> robot.follower.setMaxPower(1)),
                new InstantCommand(() -> robot.follower.followPath(FarPaths.gateOverFlowToShoot)),
                new InstantCommand(() -> robot.setRobotState(Robot.RobotState.SHOOT)),
                new InstantCommand(() -> robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds())),
                new WaitFollower(robot.follower),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(500),
                new InstantCommand(() -> robot.transfer.stop())
        );

        SequentialCommand park = new SequentialCommand(
                new InstantCommand(() -> robot.follower.followPath(FarPaths.parkPath)),
                new WaitFollower(robot.follower)
        );

        pathSchedule = new SequentialCommand(
                shootPreload,
                humanPlayer,
                spike3,
                getGateBall,
                getGateBall,
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