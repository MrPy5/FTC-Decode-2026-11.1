package org.firstinspires.ftc.teamcode.opmodes.testing;


import com.acmerobotics.dashboard.FtcDashboard;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.Storage;
import org.firstinspires.ftc.teamcode.config.util.CyclingList;
import org.firstinspires.ftc.teamcode.config.util.OpMode;

//shooting
//turn button
//turning could be faster
//take out reset pose button in robot

@TeleOp(name="Full Turret Test")
public class FullTurretTest extends LinearOpMode {

        @Override
        public void runOpMode () {

            FtcDashboard dashboard = FtcDashboard.getInstance();
            dashboard.setTelemetryTransmissionInterval(50);

            CyclingList loopTimes = new CyclingList(5);
            ElapsedTime loopTimer = new ElapsedTime();

            //Create new robot instance
            Robot robot = new Robot(hardwareMap, OpMode.TELEOP, Storage.getStoredAlliance(), Storage.getStoredMotif(), Storage.getStoredFollower(), gamepad1, gamepad2, telemetry, dashboard);

            Gamepad c1, c2; // Creates gamepads



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

            int targetRPM = 0;
            int turretAngle = 0;
            robot.turret.setAngle(0);
            //robot.turret.setState(CrTurret.TurretState.TRACK);
            robot.follower.setPose(new Pose(72, 72, 0));
            robot.shooter.unblock();
            while (opModeIsActive()) {
                loopTimer.reset();

                robot.updateGamepads();
                c1 = robot.getC1();

                //Get state of each component on robot
                robot.update();

                robot.shooter.setRPM(targetRPM);

                if (c1.dpad_up) {  // Intake and Transfer on
                    robot.transfer.intakeTransfer();
                    robot.intake.intake();
                }
                if (c1.dpad_down) { // Intake and Transfer off
                    robot.transfer.stop();
                    robot.intake.stopIntake();
                }

               /* if (c1.squareWasPressed()) {
                    robot.turret.incrementAngle(-15);
                }
                if (c1.circleWasPressed()) {
                    robot.turret.incrementAngle(15);
                }*/


                 if (c1.dpadRightWasPressed()) {
                    turretAngle = -45;
                    robot.turret.setAngle(turretAngle);
                }
                if (c1.dpadLeftWasPressed()) {
                    turretAngle = 45;
                    robot.turret.setAngle(turretAngle);
                }
                if (c1.triangleWasPressed()) {
                    turretAngle = 0;
                    robot.turret.setAngle(turretAngle);
                }

                if (c1.circleWasPressed()) {
                    turretAngle = -90;
                    robot.turret.setAngle(turretAngle);
                }
                if (c1.squareWasPressed()) {
                    turretAngle = 90;
                    robot.turret.setAngle(turretAngle);
                }


                if (c1.leftBumperWasPressed()) {
                    targetRPM = targetRPM - 100;
                }
                if (c1.rightBumperWasPressed()) {
                    targetRPM = targetRPM + 100;
                }


                robot.updateHardware();
                robot.doDashboard();

                loopTimes.add(loopTimer.milliseconds(), robot.getMilliseconds());
                telemetry.addData("angle", Math.toDegrees(robot.follower.getHeading()));
                telemetry.addData("turret Angle", turretAngle);
               // telemetry.addData("left tick", robot.turret.angleToTicks(turretAngle));
                telemetry.addData("loop", loopTimes.average());
                telemetry.addData("rpm", targetRPM);
                telemetry.addData("turretAngle", robot.turret.getAngle());
                telemetry.update();

            }


        }
}




