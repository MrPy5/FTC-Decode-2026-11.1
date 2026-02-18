package org.firstinspires.ftc.teamcode.opmodes.teleop;


import com.acmerobotics.dashboard.FtcDashboard;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.Robot.RobotState;
import org.firstinspires.ftc.teamcode.config.Storage;
import org.firstinspires.ftc.teamcode.config.subsystems.Intake;
import org.firstinspires.ftc.teamcode.config.subsystems.Lindexer;
import org.firstinspires.ftc.teamcode.config.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.config.subsystems.vision.TagCamera;
import org.firstinspires.ftc.teamcode.config.util.Color;
import org.firstinspires.ftc.teamcode.config.util.CyclingList;
import org.firstinspires.ftc.teamcode.config.util.OpMode;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

//shooting
//turn button
//turning could be faster
//take out reset pose button in robot

@TeleOp(name="Game Teleop")
public class GameTeleop extends LinearOpMode {

        @Override
        public void runOpMode () {

            FtcDashboard dashboard = FtcDashboard.getInstance();
            dashboard.setTelemetryTransmissionInterval(50);

            CyclingList loopTimes = new CyclingList(5);
            ElapsedTime loopTimer = new ElapsedTime();

            //Create new robot instance
            Robot robot = new Robot(hardwareMap, OpMode.TELEOP, Storage.getStoredAlliance(), Storage.getStoredMotif(), Storage.getStoredFollower(), gamepad1, gamepad2, telemetry, dashboard);

            Gamepad c1, c2; // Creates gamepads

            boolean motifMode = false;
            boolean startedShooting = false;


            //reset follower back to full speed
            robot.initTeleop();

            while (opModeInInit()) {
                //Change alliance color or motif
                robot.initLoopTeleop();

                //Display changes
                robot.initLoopTelemetry();
                telemetry.update();
            }

            //Start gametimer, hardware reset before start (kicker down, others), other...
            robot.startTeleop();
           // robot.tagCamera.getVisionPortal().stopStreaming();
            while (opModeIsActive()) {
                loopTimer.reset();
                //Pull new gamepad values and freeze state
                robot.updateGamepads();
                c1 = robot.getC1();
                c2 = robot.getC2();

                //Get state of each component on robot
                robot.update();

                //Switching modes between intake and shoot
                    //Shoot
                boolean psPressed = c1.psWasPressed();
                if (c1.rightTriggerWasPressed() && robot.getRobotState() == RobotState.INTAKE) {
                    robot.intake.lift();
                    robot.shooter.unblock();
                }
                if ((psPressed || c1.rightBumperWasPressed()) && robot.getRobotState() != RobotState.SHOOT) {
                    robot.scheduler.clear();
                    robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds());
                    robot.setRobotState(RobotState.SHOOT);

                    if (motifMode) {
                        robot.scheduler.schedule(robot.commands.stopLindexing, robot.getMilliseconds());
                    }

                    robot.chassis.targetHeading = Math.toRadians(robot.chassis.degreesAwayPinpoint(robot));


                }
                    //Intake
                else if ((psPressed || c2.left_trigger > ConfigConstants.TRIGGER_SENSITIVITY) && robot.getRobotState() != RobotState.INTAKE) {
                    robot.scheduler.clear();
                    robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds());
                    robot.setRobotState(RobotState.INTAKE);

                    if (motifMode) {
                        robot.scheduler.schedule(robot.commands.startLindexing, robot.getMilliseconds());
                    }
                    else {
                        robot.transfer.unblock();
                    }

                }

                //Motif mode activation
                if (c2.shareWasPressed()) {
                    motifMode = !motifMode;

                    if (motifMode) {
                        robot.lindexer.setIndex(true);

                        if (robot.getRobotState() == RobotState.INTAKE) {
                            robot.scheduler.schedule(robot.commands.startLindexing, robot.getMilliseconds());
                        }
                        if (robot.getRobotState() == RobotState.SHOOT) {
                            robot.transfer.unblock();
                        }
                    }
                    else {
                        robot.lindexer.setIndex(false);
                    }
                }

                //RPM addition
                if (c1.dpadDownWasPressed()) {
                    robot.shooter.decreaseManualRPMAdjustment();
                }
                if (c1.dpadUpWasPressed()) {
                    robot.shooter.increaseManualRPMAdjustment();
                }

                if (robot.usePinpoint) {
                    robot.shooter.spinAtCalculatedSpeed(robot.chassis.inchesAwayPinpoint(robot));
                }
                else {
                    robot.shooter.spinAtCalculatedSpeed(robot.tagCamera.range());
                }
                //RPM and shooting
                if (robot.getRobotState() == RobotState.SHOOT) {

                    if ((c2.right_trigger > ConfigConstants.TRIGGER_SENSITIVITY || c1.right_trigger > ConfigConstants.TRIGGER_SENSITIVITY) && (robot.shooter.getShooterState() == Shooter.ShooterState.READY || startedShooting)) {
                        startedShooting = true;
                        if (robot.scheduler.isIdle()) {
                            if (motifMode && robot.lindexer.numOfBalls() > 0) {
                                robot.scheduler.schedule(robot.commands.shootLindexing, robot.getMilliseconds());
                            }
                            if (!motifMode) {
                                robot.transfer.intakeTransfer();
                            }
                        }
                    }
                    else {
                        startedShooting = false;
                        if (!motifMode) {
                            robot.transfer.stop();
                        }
                    }


                }


                //Driving code

                double[] drivePowers = robot.chassis.calculateDrivePowers(c1, ConfigConstants.DRIVE_DAMPENING, ConfigConstants.STRAFE_DAMPENING, ConfigConstants.TURN_DAMPENING, robot);
                robot.follower.setTeleOpDrive(
                        drivePowers[0],
                        drivePowers[1],
                        drivePowers[2],
                        true // Robot Centric NOT field centric
                );


                //Manual Stuff for Controller 2
                if (c2.dpadDownWasPressed()) {
                    robot.intake.intake();
                }
                if (c2.dpadUpWasPressed()) {
                    robot.intake.outtake();
                    gamepad2.rumble(500);
                }
                if (c2.dpadRightWasPressed()) {
                    robot.intake.stopIntake();
                }

                //Motif mode (# of balls on ramp)
                if (c2.triangleWasPressed()) {
                    robot.classifier.addBall();
                    gamepad2.rumble(250);
                }

                if (c2.crossWasPressed()) {
                    robot.classifier.subtractBall();
                    gamepad2.rumble(1000);
                }

                if (c2.circleWasPressed()) {
                    robot.classifier.reset();
                    gamepad2.rumble(1000);
                }

                if (c1.triangleWasPressed()) {
                    robot.lindexer.clear();
                }

                if (c1.touchpadWasPressed()) {
                    robot.usePinpoint = !robot.usePinpoint;
                }


                //update everything
                robot.updateHardware();
                robot.doDashboard();
                loopTimes.add(loopTimer.milliseconds(), robot.getMilliseconds());


                telemetry.addData("targetHEading", robot.chassis.degreesAwayPinpoint(robot));
                telemetry.addData("rpm", robot.shooter.getTargetShooterRPM());
                telemetry.addData("error", Math.toDegrees(robot.chassis.getHeadingError(robot)));
                telemetry.addData("dist", robot.tagCamera.range());
                telemetry.addData("c", robot.tagCamera.combined());
                telemetry.addData("offset", robot.chassis.calculateOffset(robot, robot.tagCamera));
              //  telemetry.addData("pinpoint", robot.chassis.inchesAwayPinpoint(robot));
              //  telemetry.addData("@", Math.toDegrees(robot.chassis.getHeadingError(robot)));
               // telemetry.addData("ready", robot.lindexer.getLindexerState());
                //telemetry.addData("c", robot.tagCamera.combined());
            //    telemetry.addData("nextBall", robot.classifier.getNextColor(robot.getMotif()));
                telemetry.addData("motifMode", motifMode);
             //   telemetry.addData("left", robot.lindexer.getLeftBall());
             //   telemetry.addData("center", robot.lindexer.getCenterBall());
             //   telemetry.addData("right", robot.lindexer.getRightBall());
               /* telemetry.addLine();*/
                telemetry.addData("ready", robot.shooter.getShooterState());
                telemetry.addData("RPM addition", robot.shooter.getManualAdjustment());
                telemetry.addData("mode", robot.getRobotState());
                telemetry.addLine();
                telemetry.addData("balls on ramp", robot.classifier.getBallsOnClassifier());

                telemetry.update();

            }

            robot.stop();

        }
}




