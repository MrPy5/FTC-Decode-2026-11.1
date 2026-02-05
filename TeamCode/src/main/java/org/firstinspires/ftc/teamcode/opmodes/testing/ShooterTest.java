package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.util.CyclingList;

@TeleOp(name = "Shooter Test")

//http://192.168.43.1:8080/dash
public class ShooterTest extends LinearOpMode {


    @Override
    public void runOpMode() {
        double secondsPerMinute = 60;
        double ticksPerRevolution = 28.0;
        DcMotorEx sml = hardwareMap.get(DcMotorEx.class, "ShooterLeft");
        DcMotorEx smr = hardwareMap.get(DcMotorEx.class, "ShooterRight");

        sml.setDirection(DcMotorSimple.Direction.REVERSE);
        sml.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        sml.setPIDFCoefficients(DcMotor.RunMode.RUN_WITHOUT_ENCODER, new PIDFCoefficients(100, 0, 1, 14));

        smr.setDirection(DcMotorSimple.Direction.FORWARD);
        smr.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        smr.setPIDFCoefficients(DcMotor.RunMode.RUN_WITHOUT_ENCODER, new PIDFCoefficients(100, 0, 1, 14));

        ElapsedTime gameTimer = new ElapsedTime();

        int targetRPM = 2000;
        int rpm;
        boolean on = true;
        double lowRPMTime = -1;
        double lastSpinUpElapsedTime = 0;
        FtcDashboard dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(50);
        CyclingList loopTime = new CyclingList(100);

        waitForStart();
        gameTimer.reset();




        while (opModeIsActive()) {

            //shooterMotor.setPower(power);
            sml.setVelocity(targetRPM * ticksPerRevolution / secondsPerMinute);
            smr.setVelocity(targetRPM * ticksPerRevolution / secondsPerMinute);

            rpm = (int) (sml.getVelocity() * secondsPerMinute / ticksPerRevolution);

            //On and off
            if (gamepad1.psWasPressed()) {
                on = !on;
                if (on) {
                    targetRPM = 2000;
                }
                else {
                    targetRPM = 0;
                }
            }

            //Change direction
            if (gamepad1.optionsWasPressed()) {
                if (sml.getDirection() == DcMotorEx.Direction.FORWARD) {
                    sml.setDirection(DcMotorSimple.Direction.REVERSE);
                    smr.setDirection(DcMotorSimple.Direction.FORWARD);
                }
                else {
                    sml.setDirection(DcMotorSimple.Direction.FORWARD);
                    smr.setDirection(DcMotorSimple.Direction.REVERSE);
                }
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

            dashboard.sendTelemetryPacket(packet);



        }
    }
}