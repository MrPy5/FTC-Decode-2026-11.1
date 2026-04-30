package org.firstinspires.ftc.teamcode.opmodes.teleop;


import android.view.WindowId;

import com.acmerobotics.dashboard.FtcDashboard;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.Robot.RobotState;
import org.firstinspires.ftc.teamcode.config.Storage;
import org.firstinspires.ftc.teamcode.config.subsystems.Ascent;
import org.firstinspires.ftc.teamcode.config.subsystems.Intake;
import org.firstinspires.ftc.teamcode.config.subsystems.Lindexer;
import org.firstinspires.ftc.teamcode.config.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.config.subsystems.Turret;
import org.firstinspires.ftc.teamcode.config.subsystems.vision.LimelightCamera;
import org.firstinspires.ftc.teamcode.config.subsystems.vision.TagCamera;
import org.firstinspires.ftc.teamcode.config.util.Alliance;
import org.firstinspires.ftc.teamcode.config.util.Color;
import org.firstinspires.ftc.teamcode.config.util.CyclingList;
import org.firstinspires.ftc.teamcode.config.util.OpMode;
import org.firstinspires.ftc.teamcode.config.util.scheduler.InstantCommand;
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
            boolean parkStarted = false;
            boolean trigReleased = false;
            boolean trigPressedLast = false;

            double xPark = 0;
            double yPark = 0;

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

            robot.limelightCamera.switchToTagDetection();
            robot.limelightCamera.setCurrentMode(LimelightCamera.TagMode.OFF);

            while (opModeIsActive()) {
                //loopTimer.reset();
                //Pull new gamepad values and freeze state
                robot.updateGamepads();
                c1 = robot.getC1();
                c2 = robot.getC2();

                //Get state of each component on robot
                robot.update();

                //Switching modes between intake and shoot
                    //Shoot


                if (robot.intake.getArtifactSensor().hasBall()) {
                    robot.intake.lift();
                }
                else {
                    robot.intake.drop();
                }

                if (c1.left_bumper) {
                    robot.turret.setSOTM(true);
                }
                else {
                    robot.turret.setSOTM(false);
                }
                if (robot.getRobotState() != RobotState.PARK) {
                    if (robot.transfer.motorStopped && robot.getRobotState() != RobotState.SHOOT) {
                        robot.scheduler.schedule(robot.commands.stopIntaking, robot.getMilliseconds());
                        robot.setRobotState(RobotState.SHOOT);

                        if (motifMode) {
                            robot.scheduler.schedule(robot.commands.stopLindexing, robot.getMilliseconds());
                        } else {
                            robot.shooter.unblock();
                        }

                        robot.transfer.motorStopped = false;

                    }

                    //Intake
                    if (trigReleased /*c2.rightTriggerWasPressed()*/ && robot.getRobotState() != RobotState.INTAKE) {

                        robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds());
                        robot.setRobotState(RobotState.INTAKE);

                        if (motifMode) {
                            robot.scheduler.schedule(robot.commands.startLindexing, robot.getMilliseconds());
                            robot.lindexer.empty();

                        } else {
                            robot.transfer.unblock();
                        }
                      //  robot.shooter.block(); // change trig released
                        robot.shooter.setShooting(false);
                        robot.transfer.motorStopped = false;

                    }
                }

                if (c1.psWasPressed()) {
                    if (robot.getRobotState() != RobotState.PARK) {
                        robot.setRobotState(RobotState.PARK);
                        robot.scheduler.clear();
                        robot.shooter.stop();
                        robot.intake.stopIntake();
                        robot.transfer.stop();
                        robot.turret.setState(Turret.TurretState.HOLD);
                        robot.turret.setAngle(90);
                        robot.ascent.getAscentLeft().setPosition(ConfigConstants.DOWN_LEFT);
                        robot.ascent.getAscentRight().setPosition(ConfigConstants.DOWN_RIGHT);

                        c1.touchpadWasPressed();

                       /* if (robot.getAlliance() == Alliance.BLUE) {
                            robot.follower.followPath(robot.follower.pathBuilder()
                                    .addPath(new BezierLine(robot.follower.getPose(), new Pose(28, 38, 3.13)))
                                    .setConstantHeadingInterpolation(3.14)
                                    .build());
                            parkStarted = true;
                        }
                        else {
                            robot.follower.followPath(robot.follower.pathBuilder()
                                    .addPath(new BezierLine(robot.follower.getPose(), new Pose(28, 109, 3.13)))
                                    .setConstantHeadingInterpolation(3.14)
                                    .build());
                            parkStarted = true;
                        }*/
                    }
                    else {
                        robot.scheduler.clear();
                        robot.chassis.parkHeading = 180;
                        robot.ascent.getAscentLeft().setPosition(ConfigConstants.DESCEND_LEFT);
                        robot.ascent.getAscentRight().setPosition(ConfigConstants.DESCEND_RIGHT);
                        robot.scheduler.schedule(robot.commands.startIntaking, robot.getMilliseconds());
                        robot.setRobotState(RobotState.INTAKE);
                        robot.turret.setState(Turret.TurretState.TRACK);
                        robot.transfer.motorStopped = false;
                        robot.shooter.setShooting(false);

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
                        robot.follower.breakFollowing();
                        robot.follower.startTeleopDrive(ConfigConstants.USE_BRAKE_MODE);
                        if (robot.ascent.getAscentState() == Ascent.AscentState.NOTASCENDED) {
                            robot.ascent.ascend();

                        }
                        else {
                            robot.ascent.descend();
                            robot.ascent.getAscentLeft().setPosition(ConfigConstants.DOWN_LEFT);
                            robot.ascent.getAscentRight().setPosition(ConfigConstants.DOWN_RIGHT);
                        }
                    }
                }

                //Motif mode activation
                if (c2.shareWasPressed()) {
                    motifMode = !motifMode;
                    robot.shooter.motifMode = !robot.shooter.motifMode;

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
                        robot.transfer.unblock();
                    }
                }

                //RPM addition
                if (robot.getRobotState() == RobotState.PARK) {
                    if (robot.getAlliance() == Alliance.BLUE) {
                        if (c1.dpadLeftWasPressed()) {
                            robot.chassis.parkHeading = 180;
                            robot.follower.breakFollowing();
                            robot.follower.startTeleopDrive(ConfigConstants.USE_BRAKE_MODE);
                        }
                        if (c1.dpadDownWasPressed()) {
                            robot.chassis.parkHeading = 270;
                            robot.follower.breakFollowing();
                            robot.follower.startTeleopDrive(ConfigConstants.USE_BRAKE_MODE);

                        }
                        if (c1.dpadUpWasPressed()) {
                            robot.chassis.parkHeading = 90;
                            robot.follower.breakFollowing();
                            robot.follower.startTeleopDrive(ConfigConstants.USE_BRAKE_MODE);
                        }
                        if (c1.dpadRightWasPressed()) {
                            robot.chassis.parkHeading = 0;
                            robot.follower.breakFollowing();
                            robot.follower.startTeleopDrive(ConfigConstants.USE_BRAKE_MODE);
                        }
                    }
                    else {
                        if (c1.dpadLeftWasPressed()) {
                            robot.chassis.parkHeading = 0;
                            robot.follower.breakFollowing();
                            robot.follower.startTeleopDrive(ConfigConstants.USE_BRAKE_MODE);
                        }
                        if (c1.dpadDownWasPressed()) {
                            robot.chassis.parkHeading = 90;
                            robot.follower.breakFollowing();
                            robot.follower.startTeleopDrive(ConfigConstants.USE_BRAKE_MODE);
                        }
                        if (c1.dpadUpWasPressed()) {
                            robot.chassis.parkHeading = 270;
                            robot.follower.breakFollowing();
                            robot.follower.startTeleopDrive(ConfigConstants.USE_BRAKE_MODE);
                        }
                        if (c1.dpadRightWasPressed()) {
                            robot.chassis.parkHeading = 180;
                            robot.follower.breakFollowing();
                            robot.follower.startTeleopDrive(ConfigConstants.USE_BRAKE_MODE);
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

                    robot.shooter.spinAtCalculatedSpeed(robot.chassis.predictedInchesAway());

                }
                //RPM and shooting
                if (robot.getRobotState() == RobotState.SHOOT) {

                    if ((c1.right_trigger > ConfigConstants.TRIGGER_SENSITIVITY ) && ((robot.shooter.getShooterState() == Shooter.ShooterState.READY /*&& robot.turret.atAngle() || robot.turret.getSOTM())*/|| startedShooting))) {
                        startedShooting = true;
                        if (robot.scheduler.isIdle()) {
                            if (motifMode && robot.lindexer.numOfBalls() > 0) {
                                robot.scheduler.schedule(robot.commands.shootLindexing, robot.getMilliseconds());
                            }
                            if (!motifMode) {
                                robot.transfer.shoot();
                                robot.intake.intake();
                            }
                        }
                        robot.shooter.setShooting(true);
                    }
                    else {
                        startedShooting = false;
                        robot.shooter.setShooting(false);
                    }

                }


                //Driving code

                double[] drivePowers = robot.chassis.calculateDrivePowers(c1, ConfigConstants.DRIVE_DAMPENING, ConfigConstants.STRAFE_DAMPENING, ConfigConstants.TURN_DAMPENING);

                robot.follower.setTeleOpDrive(
                        drivePowers[0],
                        drivePowers[1],
                        drivePowers[2],
                        true
                );

                if (parkStarted && ((c1.left_stick_x != 0 || c1.left_stick_y != 0))) {
                    robot.follower.breakFollowing();
                    robot.follower.startTeleopDrive(ConfigConstants.USE_BRAKE_MODE);

                    parkStarted = false;
                }

                boolean c2Triangle = c2.triangleWasPressed();
                boolean c2Cross = c2.crossWasPressed();

                //Motif mode (# of balls on ramp)
                if (c2Triangle && robot.getRobotState() != RobotState.PARK) {
                    robot.classifier.addBall();
                    gamepad2.rumble(250);
                }

                if (c2Cross && robot.getRobotState() != RobotState.PARK) {
                    robot.classifier.subtractBall();
                    gamepad2.rumble(1000);
                }

                if (robot.getRobotState() == RobotState.PARK) {
                    if (c2.dpadRightWasPressed()) {
                        robot.chassis.parkHeadingOffset += 3;
                        gamepad2.rumble(0, 1, 200);
                    }
                    if (c2.dpadLeftWasPressed()) {
                        robot.chassis.parkHeadingOffset -= 3;
                        gamepad2.rumble(1, 0, 200);
                    }
                }
                else {
                    if (c2.dpadLeftWasPressed()) {
                        robot.chassis.degreeOffset += 1;
                        gamepad2.rumble(0, 1, 200);
                    }
                    if (c2.dpadRightWasPressed()) {
                        robot.chassis.degreeOffset -= 1;
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
                if (c2.right_bumper && robot.getRobotState() != RobotState.PARK) {
                    if (robot.limelightCamera.getTagCount() != 0) {
                        robot.limelightCamera.recordPosition();
                        robot.indicator.alert = true;
                    }

                }
                if (c2.rightBumperWasReleased() && robot.getRobotState() != RobotState.PARK) {
                    robot.indicator.alert = false;
                    robot.follower.setPose(robot.limelightCamera.avgPose());
                    robot.chassis.degreeOffset = 2;
                }

               /* if (robot.getRobotState() == RobotState.PARK) {
                    boolean triangle = c2Triangle;
                    boolean cross = c2Cross;
                    boolean square = c2.squareWasPressed();
                    boolean circle = c2.circleWasPressed();
                    if (robot.getAlliance() == Alliance.BLUE) {
                        if (triangle) {
                            yPark += 1.5;
                        }
                        if (cross) {
                            yPark -= 1.5;
                        }
                        if (square) {
                            xPark -= 1.5;
                        }
                        if (circle) {
                            xPark += 1.5;
                        }
                    }
                    else {
                        if (triangle) {
                            yPark -= 1.5;
                        }
                        if (cross) {
                            yPark += 1.5;
                        }
                        if (square) {
                            xPark += 1.5;
                        }
                        if (circle) {
                            xPark -= 1.5;
                        }
                    }
                    if (triangle || cross || square || circle) {
                        robot.follower.followPath(robot.follower.pathBuilder()
                                .addPath(new BezierLine(robot.follower.getPose(), new Pose(robot.follower.getPose().getX() + xPark, robot.follower.getPose().getY()  + yPark, Math.toRadians(robot.chassis.parkHeading))))
                                .setConstantHeadingInterpolation( Math.toRadians(robot.chassis.parkHeading + robot.chassis.parkHeadingOffset))
                                .build());
                        parkStarted = true;

                    }


                }*/

                if (trigReleased) {
                    trigReleased = false;
                }

                if (c1.right_trigger > ConfigConstants.TRIGGER_SENSITIVITY) {
                    trigPressedLast = true;
                }
                else {
                    if (trigPressedLast) {
                        trigReleased = true;
                        trigPressedLast = false;
                    }
                }

                //update everything
                robot.updateHardware();
                robot.doDashboard();
                /*loopTimes.add(loopTimer.milliseconds(), robot.getMilliseconds());
                telemetry.addData("loop", loopTimes.average());

                 */
               // telemetry.addLine();
              /*  telemetry.addData("x", robot.follower.getPose().getX());
                telemetry.addData("y", robot.follower.getPose().getY());
                telemetry.addLine();
                telemetry.addData("heading", Math.toDegrees(robot.follower.getHeading()));
                telemetry.addData("math angle", robot.chassis.degreesAwayTurret(robot.chassis.turretPose()));
                telemetry.addData("encoder angle", robot.turret.getEncoderAngle());
                telemetry.addData("target angle", robot.turret.getAngle());
                telemetry.addLine();*/
                //telemetry.addData("distance", robot.chassis.predictedInchesAway());
                telemetry.addData("rpm", robot.shooter.getTargetShooterRPM());
                telemetry.addData("rpm increase front", robot.shooter.getManualAdjustmentFront());
                telemetry.addData("rpm increase back", robot.shooter.getManualAdjustmentBack());
                telemetry.addLine();
                telemetry.addData("offset", robot.chassis.degreeOffset);
                telemetry.addLine();
                //telemetry.addData("rpm", robot.shooter.shooterRPM.average());
                //telemetry.addLine();
                telemetry.addData("motifMode", motifMode);
                telemetry.addData("mode", robot.getRobotState());
                telemetry.addLine();
                telemetry.addData("balls on ramp", robot.classifier.getBallsOnClassifier());


                telemetry.update();

            }

            robot.stop();

        }
}




