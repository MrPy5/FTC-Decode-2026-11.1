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
import org.firstinspires.ftc.teamcode.config.subsystems.Ascent;
import org.firstinspires.ftc.teamcode.config.subsystems.Intake;
import org.firstinspires.ftc.teamcode.config.subsystems.Lindexer;
import org.firstinspires.ftc.teamcode.config.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.config.subsystems.vision.TagCamera;
import org.firstinspires.ftc.teamcode.config.util.Alliance;
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
            boolean prevHasBall = false;


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
            robot.lindexer.getLindexerColor().time = 200;
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
              //  boolean psPressed = c1.psWasPressed();
                /*if (robot.getRobotState() == RobotState.INTAKE) {
                    if (robot.intake.getArtifactSensor().hasBall() && !prevHasBall) {
                        gamepad1.rumble(200);
                        prevHasBall = true;
                    }
                    if (!robot.intake.getArtifactSensor().hasBall()) {
                        prevHasBall = false;
                    }
                }*/
                if (motifMode && robot.getRobotState() == RobotState.INTAKE && robot.lindexer.numOfBalls() == 3 && !prevHasBall) {
                    gamepad1.rumble(1000);
                    prevHasBall = true;
                }
                if (motifMode && robot.getRobotState() == RobotState.INTAKE && robot.lindexer.numOfBalls() != 3)  {
                    prevHasBall = false;
                }

                if (robot.getRobotState() == RobotState.INTAKE) {
                    if (robot.intake.getArtifactSensor().hasBall()) {
                        robot.intake.lift();
                    }
                    else {
                        robot.intake.drop();
                    }
                }

                if ((c1.rightBumperWasPressed()) && robot.getRobotState() != RobotState.SHOOT) {
                    robot.scheduler.clear();
                    robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds());
                    robot.setRobotState(RobotState.SHOOT);

                    if (motifMode) {
                        robot.scheduler.schedule(robot.commands.stopLindexing, robot.getMilliseconds());
                    }

                    robot.chassis.targetHeading = Math.toRadians(robot.chassis.degreesAwayPinpoint(robot));


                }
                    //Intake
                else if ((c2.left_trigger > ConfigConstants.TRIGGER_SENSITIVITY) && robot.getRobotState() != RobotState.INTAKE) {
                    robot.scheduler.clear();
                    robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds());
                    robot.setRobotState(RobotState.INTAKE);

                    if (motifMode) {
                        robot.scheduler.schedule(robot.commands.startLindexing, robot.getMilliseconds());
                        robot.lindexer.empty();
                    }
                    else {
                        robot.transfer.unblock();
                    }

                }

                if (c1.psWasPressed()) {
                    if (robot.getRobotState() != RobotState.PARK) {
                        robot.setRobotState(RobotState.PARK);
                        robot.shooter.stop();
                        robot.intake.stopIntake();
                        robot.transfer.stop();
                    }
                    else {
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
                }

                if (robot.getRobotState() == RobotState.PARK) {
                    if (c1.touchpadWasPressed()) {
                        if (robot.ascent.getAscentState() == Ascent.AscentState.DOWN) {
                            robot.ascent.ascend();
                        }
                        else {
                            robot.ascent.descend();
                        }
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
                if (robot.getRobotState() == RobotState.PARK) {
                    if (robot.getAlliance() == Alliance.BLUE) {
                        if (c1.dpadLeftWasPressed()) {
                            robot.chassis.parkHeading = 180;
                        }
                        if (c1.dpadDownWasPressed()) {
                            robot.chassis.parkHeading = 270;
                        }
                        if (c1.dpadUpWasPressed()) {
                            robot.chassis.parkHeading = 90;
                        }
                        if (c1.dpadRightWasPressed()) {
                            robot.chassis.parkHeading = 0;
                        }
                    }
                    else {
                        if (c1.dpadLeftWasPressed()) {
                            robot.chassis.parkHeading = 0;
                        }
                        if (c1.dpadDownWasPressed()) {
                            robot.chassis.parkHeading = 90;
                        }
                        if (c1.dpadUpWasPressed()) {
                            robot.chassis.parkHeading = 270;
                        }
                        if (c1.dpadRightWasPressed()) {
                            robot.chassis.parkHeading = 180;
                        }
                    }
                }
                else {
                    if (motifMode) {
                        if (c2.dpadUpWasPressed()) {
                            robot.intake.outtake();
                        }
                        if (c2.dpadDownWasPressed()) {
                            robot.intake.intake();
                        }
                    }
                    else {
                        if (c2.dpadDownWasPressed()) {
                            robot.shooter.decreaseManualRPMAdjustment();
                            gamepad2.rumble(200);
                        }
                        if (c2.dpadUpWasPressed()) {
                            robot.shooter.increaseManualRPMAdjustment();
                            gamepad2.rumble(200);
                        }
                    }
                }

                if (robot.getRobotState() != RobotState.PARK) {
                    robot.shooter.spinAtCalculatedSpeed(robot.tagCamera.range(), robot);
                }

                //RPM and shooting
                if (robot.getRobotState() == RobotState.SHOOT) {

                    if ((c2.right_trigger > ConfigConstants.TRIGGER_SENSITIVITY) && (robot.shooter.getShooterState() == Shooter.ShooterState.READY || startedShooting) && Math.abs(Math.toDegrees(robot.chassis.getHeadingError(robot))) < 10) {
                        startedShooting = true;
                        if (robot.scheduler.isIdle()) {
                            if (motifMode && robot.lindexer.numOfBalls() > 0) {
                                robot.scheduler.schedule(robot.commands.teleopMotif, robot.getMilliseconds());
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




                //Motif mode (# of balls on ramp)
                if (c2.triangleWasPressed()) {
                    robot.classifier.addBall();
                    gamepad2.rumble(250);
                }

                if (c2.crossWasPressed()) {
                    robot.classifier.subtractBall();
                    gamepad2.rumble(1000);
                }

                if (robot.getRobotState() == RobotState.PARK) {
                    if (c2.circleWasPressed()) {
                        robot.chassis.parkHeadingOffset += 3;
                        gamepad2.rumble(0, 1, 200);
                    }
                    if (c2.squareWasPressed()) {
                        robot.chassis.parkHeadingOffset -= 3;
                        gamepad2.rumble(1, 0, 200);
                    }
                }
                else {
                    if (c2.circleWasPressed()) {
                        robot.chassis.degreeOffset += 0.5;
                        gamepad2.rumble(0, 1, 200);
                    }
                    if (c2.squareWasPressed()) {
                        robot.chassis.degreeOffset -= 0.5;
                        gamepad2.rumble(1, 0, 200);
                    }
                }

                if (c2.psWasPressed()) {
                    if (robot.getAlliance() == Alliance.RED) {
                        robot.follower.setPose(ConfigConstants.RED_FIELD_RESET);
                    }
                    else {
                        robot.follower.setPose(ConfigConstants.BLUE_FIELD_RESET);
                    }
                }

                if (c1.shareWasPressed()) {
                    robot.scheduler.clear();
                    robot.setRobotState(RobotState.INTAKE);
                    robot.scheduler.schedule(robot.commands.resetEverything, robot.getMilliseconds());
                }


                //update everything
                robot.updateHardware();
                robot.doDashboard();
                //loopTimes.add(loopTimer.milliseconds(), robot.getMilliseconds());
                //telemetry.addData("loop", loopTimes.average());
                telemetry.addData("left", robot.lindexer.getLeftBall());
                telemetry.addData("right", robot.lindexer.getRightBall());
                telemetry.addData("center", robot.lindexer.getCenterBall());
                telemetry.addLine();
                telemetry.addData("offset", robot.chassis.degreeOffset);

                telemetry.addData("motifMode", motifMode);
                telemetry.addData("RPM addition", robot.shooter.getManualAdjustment());
                telemetry.addData("mode", robot.getRobotState());
                telemetry.addLine();
                telemetry.addData("balls on ramp", robot.classifier.getBallsOnClassifier());

                telemetry.update();

            }

            robot.stop();

        }
}




