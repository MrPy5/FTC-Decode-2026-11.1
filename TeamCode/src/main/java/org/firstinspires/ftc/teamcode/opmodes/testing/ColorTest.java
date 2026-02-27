package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.config.util.CyclingList;

@TeleOp(name = "Color Test")

//http://192.168.43.1:8080/dash
public class ColorTest extends LinearOpMode {


    @Override
    public void runOpMode() {
        RevColorSensorV3 color = hardwareMap.get(RevColorSensorV3.class, "lindex color left");
        color.setGain(5f);
        FtcDashboard dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(50);


        waitForStart();




        while (opModeIsActive()) {


            TelemetryPacket packet = new TelemetryPacket();
            packet.put("dist", getDistance(color));
            packet.put("hue", getHue(color));
            dashboard.sendTelemetryPacket(packet);



        }
    }
    public double getDistance(RevColorSensorV3 colorSensor) {
        return colorSensor.getDistance(DistanceUnit.CM);
    }


    public double getHue(RevColorSensorV3 colorSensor) {
        float[] hsvValues = new float[3];

        android.graphics.Color.RGBToHSV(
                colorSensor.red() * 12,
                colorSensor.green() * 8,
                colorSensor.blue() * 12,
                hsvValues
        );
        return hsvValues[0];
    }
}