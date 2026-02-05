package org.firstinspires.ftc.teamcode.config;

import org.firstinspires.ftc.teamcode.config.util.scheduler.InstantCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.SequentialCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.Wait;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

public class Commands {

    public final SequentialCommand stopEverything, stopIntaking, startIntaking, shoot;


    public Commands(Robot robot) {

        stopEverything = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.stop()),
                new InstantCommand(() -> robot.intake.stopIntake()));


        stopIntaking = new SequentialCommand(

                new InstantCommand(() -> robot.intake.stopIntake()),
                new InstantCommand(() -> robot.shooter.unblock()));

        startIntaking = new SequentialCommand(
                new InstantCommand(() -> robot.intake.intake()));


        shoot = new SequentialCommand( // teleop: no sort, shoot balls, do not look for next ball
        );




    }
}
