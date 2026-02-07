package org.firstinspires.ftc.teamcode.config;

import org.firstinspires.ftc.teamcode.config.subsystems.Lindexer;
import org.firstinspires.ftc.teamcode.config.util.scheduler.InstantCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.SequentialCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.Wait;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

public class Commands {

    public final SequentialCommand stopEverything, stopIntaking, startIntaking, shoot, shootLindexing, startLindexingDuringIntake, startIntakingLindex;


    public Commands(Robot robot) {

        stopEverything = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.stop()),
                new InstantCommand(() -> robot.intake.stopIntake()));


        stopIntaking = new SequentialCommand(

                new InstantCommand(() -> robot.intake.intake()),
              //  new InstantCommand(() -> robot.transfer.stopIntakeSpindle()),
                new InstantCommand(() -> robot.shooter.unblock()),
                new InstantCommand(() -> robot.lindexer.setLindexerState(Lindexer.LindexerState.NONINDEX)));

        startIntaking = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.stop()),
                new InstantCommand(() -> robot.transfer.stop()),
                new InstantCommand(() -> robot.intake.intake()),
                new InstantCommand(() -> robot.lindexer.leftCenter()));
        startIntakingLindex = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.stop()),
                new InstantCommand(() -> robot.transfer.stop()),
                new InstantCommand(() -> robot.intake.intake()),
                new InstantCommand(() -> robot.lindexer.leftCenter()),
                new InstantCommand(() -> robot.lindexer.setLindexerState(Lindexer.LindexerState.INDEX)));

        startLindexingDuringIntake = new SequentialCommand(
                new InstantCommand(() -> robot.transfer.block()),
                new InstantCommand(() -> robot.lindexer.leftCenter()),
                new InstantCommand(() -> robot.lindexer.setLindexerState(Lindexer.LindexerState.INDEX)));

        shootLindexing = new SequentialCommand(
                new InstantCommand(() -> robot.intake.stopIntake()),
                new InstantCommand(() -> robot.lindexer.moveToNextBall(robot.classifier.getNextColor(robot.getMotif()))),
                new Wait(1000),
                new InstantCommand(() -> robot.transfer.intakeTransfer()),
                new InstantCommand(() -> robot.intake.intake()),
                new Wait(500),
                new InstantCommand(() -> robot.classifier.addBall())
        );



        shoot = new SequentialCommand( // teleop: no sort, shoot balls, do not look for next ball
        );




    }
}
