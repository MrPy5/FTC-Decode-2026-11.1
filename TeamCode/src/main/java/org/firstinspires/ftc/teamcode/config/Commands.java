package org.firstinspires.ftc.teamcode.config;

import org.firstinspires.ftc.teamcode.config.util.scheduler.InstantCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.SequentialCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.Wait;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitTillLindexerReady;

public class Commands {

    public final SequentialCommand stopEverything, stopIntaking, startIntaking, shootLindexing, startLindexing, stopLindexing, resetEverything, teleopMotif, acceptCenterBall, acceptLeftBall, acceptRightBall;


    public Commands(Robot robot) {

        stopEverything = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.stop()),
                new InstantCommand(() -> robot.transfer.stop()),
                new InstantCommand(() -> robot.intake.stopIntake()),
                new InstantCommand(() -> robot.intake.lift()),
                new InstantCommand(() -> robot.transfer.unblock()),
                new InstantCommand(() -> robot.shooter.unblock()),
                new InstantCommand(() -> robot.lindexer.leftCenter()));


        stopIntaking = new SequentialCommand(
                new InstantCommand(() -> robot.transfer.stop()));

        startIntaking = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.block()),
                new InstantCommand(() -> robot.intake.intake()),
                new InstantCommand(() -> robot.intake.drop()),
                new InstantCommand(() -> robot.lindexer.leftCenter()),
                new Wait(300),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(800),
                new InstantCommand(() -> robot.transfer.startCheckingNow()));

        startLindexing = new SequentialCommand(
                new InstantCommand(() -> robot.transfer.block()),
                new InstantCommand(() -> robot.lindexer.leftCenter())
        );
        stopLindexing = new SequentialCommand(
                new InstantCommand(() -> robot.lindexer.stopIntakingAndLindex()));

        shootLindexing = new SequentialCommand(
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new Wait(500),
                new InstantCommand(() -> robot.lindexer.moveToNextBall(robot.classifier.getNextColor(robot.getMotif()))),
                //new WaitTillLindexerReady(robot.lindexer),
                //new Wait(300),
                new InstantCommand(() -> robot.classifier.addBall())
        );

        resetEverything = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.unblock()),
                new Wait(300),
                new InstantCommand(() -> robot.ascent.descend()),
                new InstantCommand(() -> robot.intake.drop()),
                new InstantCommand(() -> robot.transfer.unblock()),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),

                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new InstantCommand(() -> robot.intake.intake()),
                new InstantCommand(() -> robot.lindexer.clear()),
                new WaitTillLindexerReady(robot.lindexer),
                new Wait(500),
                new InstantCommand(() -> robot.lindexer.leftCenter()),
                new WaitTillLindexerReady(robot.lindexer),
                new Wait(500),
                new InstantCommand(() -> robot.lindexer.rightCenter()),
                new WaitTillLindexerReady(robot.lindexer),
                new Wait(1000),
                startIntaking
        );

        teleopMotif = new SequentialCommand(
              shootLindexing
        );
        acceptCenterBall = new SequentialCommand(
                new InstantCommand(() -> robot.lindexer.clear()),
                new WaitTillLindexerReady(robot.lindexer),
                new InstantCommand(() -> robot.transfer.unblock()),
                new Wait(500),
                new InstantCommand(() -> robot.transfer.stop()),
                new InstantCommand(() -> robot.shooter.unblock())


        );
        acceptLeftBall = new SequentialCommand(
                new InstantCommand(() -> robot.lindexer.leftCenter()),
                new WaitTillLindexerReady(robot.lindexer),
                new InstantCommand(() -> robot.transfer.unblock()),
                new Wait(500),
                new InstantCommand(() -> robot.lindexer.clear()),
                new Wait(500),
                new InstantCommand(() -> robot.transfer.stop()),
                new InstantCommand(() -> robot.shooter.unblock())
        );
        acceptRightBall = new SequentialCommand(
                new InstantCommand(() -> robot.transfer.unblock()),
                new Wait(500),
                new InstantCommand(() -> robot.lindexer.clear()),
                new Wait(500),
                new InstantCommand(() -> robot.transfer.stop()),
                new InstantCommand(() -> robot.shooter.unblock())
        );





    }
}
