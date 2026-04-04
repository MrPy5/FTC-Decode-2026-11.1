package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.config.util.CyclingList;

@TeleOp(name = "Shooter Test")
//http://192.168.43.1:8080/dash
public class ShooterTest extends LinearOpMode {


    @Override
    public void runOpMode() {
        double secondsPerMinute = 60;
        double ticksPerRevolution = 28.0;

        double turretMiddleAim = 0.5;
        double turretChangeAmt = 0.1;
        double turretCurrentAim = turretMiddleAim;
        double turretPrevAim = 0;

        DcMotorEx sml = hardwareMap.get(DcMotorEx.class, "shooter left");
        DcMotorEx smr = hardwareMap.get(DcMotorEx.class, "shooter right");
        DcMotorEx tm = hardwareMap.get(DcMotorEx.class, "transfer motor");
        DcMotorEx im = hardwareMap.get(DcMotorEx.class, "intake motor");

        Servo turretServoLeft = hardwareMap.get(Servo.class, "ts left");
        Servo turretServoRight = hardwareMap.get(Servo.class, "ts right");

        sml.setDirection(DcMotorSimple.Direction.REVERSE);
        sml.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        sml.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(200, 0, 1, 15));

        smr.setDirection(DcMotorSimple.Direction.FORWARD);
        smr.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        smr.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(200, 0, 1, 15));

        im.setDirection(DcMotorSimple.Direction.REVERSE);

        ElapsedTime gameTimer = new ElapsedTime();

        int targetRPM = 0;
        int rpm;
        boolean on = false;
        FtcDashboard dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(50);
        CyclingList loopTime = new CyclingList(100);

        waitForStart();
        gameTimer.reset();

        turretServoLeft.setPosition(turretCurrentAim);
        turretServoRight.setPosition(turretCurrentAim);

        while (opModeIsActive()) {

            //shooterMotor.setPower(power);
            sml.setVelocity(targetRPM * ticksPerRevolution / secondsPerMinute);
            smr.setVelocity(targetRPM * ticksPerRevolution / secondsPerMinute);

            rpm = (int) (sml.getVelocity() * secondsPerMinute / ticksPerRevolution);

            //On and off
            if (gamepad1.psWasPressed()) {
                on = !on;
                if (on) {
                    targetRPM = 3000;
                }
                else {
                    targetRPM = 0;
                }
            }
            /*
            if (gamepad1.crossWasPressed()) {
                sml.setDirection(DcMotorSimple.Direction.REVERSE);
                smr.setDirection(DcMotorSimple.Direction.FORWARD);
            }
            if (gamepad1.squareWasPressed()) {
                sml.setDirection(DcMotorSimple.Direction.FORWARD);
                smr.setDirection(DcMotorSimple.Direction.REVERSE);
            }

             */

            if (gamepad1.dpad_up) {  // Intake and Transfer on
                tm.setPower(1.0);
                im.setPower(1.0);
            }
            if (gamepad1.dpad_down) { // Intake and Transfer off
                tm.setPower(0);
                im.setPower(0);
            }

            if (gamepad1.squareWasReleased()) {  // Turns turret Left
                turretCurrentAim = turretCurrentAim + turretChangeAmt;
            }
            if (gamepad1.circleWasReleased()) {  // Turns turret Right
                turretCurrentAim = turretCurrentAim - turretChangeAmt;
            }
            if (turretPrevAim != turretCurrentAim) {
                turretServoLeft.setPosition(turretCurrentAim);
                turretServoRight.setPosition(turretCurrentAim);
                turretPrevAim = turretCurrentAim;
            }

            //Power changing
            if (gamepad1.dpadLeftWasPressed()) {
                targetRPM = targetRPM - 50;
            }
            if (gamepad1.dpadRightWasPressed()) {
                targetRPM = targetRPM + 50;
            }
            if (gamepad1.leftBumperWasPressed()) {
                targetRPM = targetRPM - 100;
            }
            if (gamepad1.rightBumperWasPressed()) {
                targetRPM = targetRPM + 100;
            }


            loopTime.add(rpm, gameTimer.milliseconds());

            telemetry.addData("On?: ", on);

            telemetry.addData("Target RPM: ", targetRPM);
            telemetry.addData("Current RPM: ", rpm);

            telemetry.update();

            TelemetryPacket packet = new TelemetryPacket();
            packet.put("target", targetRPM);
            packet.put("current", rpm);
            packet.put("lowest", 0);
            packet.put("highest", 6000);
            packet.put("left", sml.getCurrent(CurrentUnit.AMPS));
            packet.put("right", smr.getCurrent(CurrentUnit.AMPS));

            dashboard.sendTelemetryPacket(packet);



        }
    }
}