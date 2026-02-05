package org.firstinspires.ftc.teamcode.config.util;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.subsystems.Indexer;
import org.firstinspires.ftc.teamcode.config.util.scheduler.InstantCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.SequentialCommand;
import org.firstinspires.ftc.teamcode.config.util.scheduler.Wait;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitForIndexer;
import org.firstinspires.ftc.teamcode.config.util.scheduler.WaitForShooter;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

public class Commands {

    public final SequentialCommand stopEverything;
    public final SequentialCommand settle;
    public final SequentialCommand settleAuto;
    public final SequentialCommand stopIntaking;
    public final SequentialCommand stopIntakingAuto;
    public final SequentialCommand settleAutoNoSortTwo;
    public final SequentialCommand startIntaking;
    public final SequentialCommand startIntakingAuto;
    public final SequentialCommand shoot;
    public final SequentialCommand shootAuto;
    public final SequentialCommand shootByColor;
    public final SequentialCommand shootAutoNoSort;
    public final SequentialCommand waitForIndexer;
    public final SequentialCommand kickRest;
    public final SequentialCommand safeKickRest;
    public final SequentialCommand shootToNextBall;
    public final SequentialCommand settleAutoNoSort;
    public final SequentialCommand stopIntakingAutoNoSort;
    public final SequentialCommand stopIntakingAutoNoSortEject;

    public final SequentialCommand rotateForTeleop;
    public final SequentialCommand settleTeleop;

    public Commands(Robot robot) {
        settleTeleop = new SequentialCommand(
                new InstantCommand(() -> robot.indexer.rotateClockwise()),
                new Wait(1500),
                new InstantCommand(() -> robot.indexer.rotateCounterClockwise()),
                new Wait(1500)
        );

        stopEverything = new SequentialCommand(
                new InstantCommand(() -> robot.shooter.stop()),
                new InstantCommand(() -> robot.intake.stopIntake()),
                new InstantCommand(() -> robot.intake.closeDoor()));

        waitForIndexer = new SequentialCommand(
                new WaitForIndexer(robot.indexer));

        rotateForTeleop = new SequentialCommand(
                new InstantCommand(() -> robot.indexer.rotateByBall(1, Indexer.Direction.CLOCKWISE, ConfigConstants.SETTLE_SPEED)),
                waitForIndexer,
                new Wait(150));
        settle = new SequentialCommand(
                new InstantCommand(() -> robot.indexer.slowIndexer()),
                new InstantCommand(() -> robot.indexer.rotateByBall(1, Indexer.Direction.CLOCKWISE, ConfigConstants.SETTLE_SPEED)),
                waitForIndexer,
                new InstantCommand(() -> robot.indexer.regularSpeedIndexer()));
        settleAuto = new SequentialCommand(

                new InstantCommand(() -> robot.indexer.rotateByBall(1, Indexer.Direction.COUNTERCLOCKWISE, ConfigConstants.SETTLE_SPEED)),
                waitForIndexer,
                new InstantCommand(() -> robot.intake.stopIntake()),

                new InstantCommand(() -> robot.indexer.slowIndexer()),
                new InstantCommand(() -> robot.indexer.rotateByBall(1, Indexer.Direction.CLOCKWISE, ConfigConstants.SETTLE_SPEED)),
                waitForIndexer,
                new Wait(150),
                new InstantCommand(() -> robot.indexer.rotateByColors(Data.getCurrentMotifColor())),
                waitForIndexer,
                new InstantCommand(() -> robot.indexer.regularSpeedIndexer()));
        settleAutoNoSort = new SequentialCommand(

                new InstantCommand(() -> robot.indexer.rotateByBall(1, Indexer.Direction.COUNTERCLOCKWISE, ConfigConstants.SETTLE_SPEED)),
                waitForIndexer,
                new InstantCommand(() -> robot.intake.stopIntake()),

                new InstantCommand(() -> robot.indexer.slowIndexer()),
                new InstantCommand(() -> robot.indexer.rotateByBall(1, Indexer.Direction.CLOCKWISE, ConfigConstants.SETTLE_SPEED)),
                waitForIndexer,
                new InstantCommand(() -> robot.indexer.regularSpeedIndexer()));
        settleAutoNoSortTwo = new SequentialCommand(

                new InstantCommand(() -> robot.indexer.rotateByBall(1, Indexer.Direction.COUNTERCLOCKWISE, ConfigConstants.SETTLE_SPEED)),
                waitForIndexer,

                new InstantCommand(() -> robot.indexer.slowIndexer()),
                new InstantCommand(() -> robot.indexer.rotateByBall(1, Indexer.Direction.CLOCKWISE, ConfigConstants.SETTLE_SPEED)),
                waitForIndexer,
                new InstantCommand(() -> robot.indexer.regularSpeedIndexer()));

        kickRest = new SequentialCommand( // quick kick rest when the kick doesn't have to be very accurate
                new InstantCommand(() -> robot.shooter.kick()),
               // new InstantCommand(() -> Data.log("kick", "started up")),
                new Wait(75),
                new InstantCommand(() -> robot.shooter.rest()),
               // new InstantCommand(() -> Data.log("kick", "started down")),
                new Wait(0));
        safeKickRest = new SequentialCommand( // more safe kick rest that gives balls more time
                new InstantCommand(() -> robot.shooter.kick()),
                new Wait(75),
                new InstantCommand(() -> robot.shooter.rest()),
                new Wait(0));



        stopIntakingAuto = new SequentialCommand(
                new InstantCommand(() -> robot.intake.closeDoor()),
                new Wait(350),
                new InstantCommand(() -> robot.indexer.stopIntaking()),
                settleAuto,
                new InstantCommand(() -> robot.shooter.unblock()));
        stopIntakingAutoNoSort = new SequentialCommand(

                new InstantCommand(() -> robot.intake.closeDoor()),
                new Wait(350),
                new InstantCommand(() -> robot.indexer.stopIntaking()),
                settleAutoNoSort,
                new InstantCommand(() -> robot.shooter.unblock()));
        stopIntakingAutoNoSortEject = new SequentialCommand(

                new InstantCommand(() -> robot.intake.closeDoor()),
                new Wait(350),
                new InstantCommand(() -> robot.intake.outtake()),
                new InstantCommand(() -> robot.indexer.stopIntaking()),
                settleAutoNoSortTwo,
                new InstantCommand(() -> robot.intake.stopIntake()),
                new InstantCommand(() -> robot.shooter.unblock()));


        stopIntaking = new SequentialCommand(
                new InstantCommand(() -> robot.indexer.stopIntaking()),
                new InstantCommand(() -> robot.intake.indexDoor()),
                new InstantCommand(() -> robot.intake.stopIntake()),
                new Wait(200),
                new InstantCommand(() -> robot.shooter.unblock()),
                settle,
                new InstantCommand(() -> robot.intake.closeDoor()),
                new InstantCommand(() -> robot.indexer.slowIndexer()),
                new InstantCommand(() -> robot.indexer.rotateToNextBall()),
                waitForIndexer,
                new InstantCommand(() -> robot.indexer.regularSpeedIndexer()),
                new WaitForShooter(robot.shooter));

        startIntaking = new SequentialCommand(
                new InstantCommand(() -> robot.indexer.regularSpeedIndexer()),
                new InstantCommand(() -> robot.intake.outtake()),
                new InstantCommand(() -> robot.intake.openDoor()),
                new InstantCommand(() -> robot.shooter.stop()),
                new InstantCommand(() -> robot.shooter.block()),
                new InstantCommand(() -> robot.indexer.startIntaking()),
                new Wait(200),
                new InstantCommand(() -> robot.intake.intake()));

        startIntakingAuto = new SequentialCommand(
                new InstantCommand(() -> robot.intake.intake()),
                new InstantCommand(() -> robot.intake.openDoor()),
                new InstantCommand(() -> robot.shooter.block()),
                new InstantCommand(() -> robot.indexer.startIntakingAuto()));

        shoot = new SequentialCommand( // teleop: no sort, shoot balls, do not look for next ball
             //   new WaitForShooter(robot.shooter),
                kickRest,
                new InstantCommand(() -> robot.indexer.rotateByBall(1, Indexer.Direction.CLOCKWISE)),
                waitForIndexer);

        shootToNextBall = new SequentialCommand( // teleop: no sort, shoot next ball
                new Wait(50),
                new InstantCommand(Data::updateIndexerCam),
                new InstantCommand(() -> robot.indexer.slowIndexer()),
                new InstantCommand(() -> robot.indexer.rotateToNextBall()),
                waitForIndexer,
                kickRest,
                new InstantCommand(() -> robot.indexer.regularSpeedIndexer()));

        shootByColor = new SequentialCommand( // teleop: shooting with sorting
                new WaitForShooter(robot.shooter),
                new InstantCommand(() -> robot.indexer.rotateByColors(Data.getCurrentMotifColor())),
                waitForIndexer,
                safeKickRest,
                new InstantCommand(Data::addBallToClassifier));

        shootAuto = new SequentialCommand( // auto: shooting with sorting
                new InstantCommand(Data::updateIndexerCam),
                new InstantCommand(() -> robot.indexer.rotateByColors(Data.getCurrentMotifColor())),
                waitForIndexer,

                safeKickRest,
                new InstantCommand(Data::addBallToClassifier),
                new Wait(150)
        );

        shootAutoNoSort = new SequentialCommand( // auto: shooting without sorting
                kickRest,
                new InstantCommand(Data::readyToMoveOn),
                new InstantCommand(() -> robot.indexer.rotateByBall(1, Indexer.Direction.CLOCKWISE)),
                waitForIndexer);


    }
}
