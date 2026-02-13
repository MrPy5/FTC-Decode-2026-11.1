package org.firstinspires.ftc.teamcode.config;

import org.firstinspires.ftc.teamcode.config.util.scheduler.InstantCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.SequentialCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.Wait;

public class Commands {

    public final SequentialCommand stopEverything, stopIntaking, startIntaking, shootLindexing, startLindexing, stopLindexing;


    public Commands(Robot robot) {

        stopEverything = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.stop()),
                new InstantCommand(() -> robot.transfer.stop()),
                new InstantCommand(() -> robot.intake.stopIntake()),
                new InstantCommand(() -> robot.intake.lift()),
                new InstantCommand(() -> robot.intake.unblock()),
                new InstantCommand(() -> robot.transfer.unblock()),
                new InstantCommand(() -> robot.shooter.unblock()),
                new InstantCommand(() -> robot.lindexer.leftCenter()));


        stopIntaking = new SequentialCommand(
                new InstantCommand(() -> robot.transfer.stop()),
                new InstantCommand(() -> robot.transfer.unblock()),
                new InstantCommand(() -> robot.intake.lift()),
                new InstantCommand(() -> robot.shooter.unblock()));

        startIntaking = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.stop()),
                new InstantCommand(() -> robot.transfer.intakeTransferSlow()),
                new InstantCommand(() -> robot.shooter.block()),
                new InstantCommand(() -> robot.intake.intake()),
                new InstantCommand(() -> robot.intake.drop()),
                new InstantCommand(() -> robot.intake.unblock()),
                new InstantCommand(() -> robot.lindexer.leftCenter()));
        startLindexing = new SequentialCommand(
                new InstantCommand(() -> robot.transfer.block()));
        stopLindexing = new SequentialCommand(
                new InstantCommand(() -> robot.intake.block()));

        shootLindexing = new SequentialCommand(
                new InstantCommand(() -> robot.intake.stopIntake()),
                new InstantCommand(() -> robot.lindexer.moveToNextBall(robot.classifier.getNextColor(robot.getMotif()))),
                new Wait(1000),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new InstantCommand(() -> robot.intake.intake()),
                new Wait(500),
                new InstantCommand(() -> robot.classifier.addBall())
        );





    }
}
