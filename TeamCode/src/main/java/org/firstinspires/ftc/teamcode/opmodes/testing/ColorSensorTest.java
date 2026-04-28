package org.firstinspires.ftc.teamcode.opmodes.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "Color Test")

//http://192.168.43.1:8080/dash
public class ColorSensorTest extends LinearOpMode {


    @Override
    public void runOpMode() {
        RevColorSensorV3 colorLeft = hardwareMap.get(RevColorSensorV3.class, "lindex color left");
        RevColorSensorV3 colorRight = hardwareMap.get(RevColorSensorV3.class, "lindex color right");
        RevColorSensorV3 colorCenter = hardwareMap.get(RevColorSensorV3.class, "lindex color center");
        colorLeft.setGain(5f);
        colorRight.setGain(5f);
        colorCenter.setGain(5f);
        FtcDashboard dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(50);


        waitForStart();




        while (opModeIsActive()) {

           // telemetry.addData("ball", getDistance(color) < 4.7 ? "yes" : "no");
            telemetry.addData("ball", getHue(colorLeft) > 220 ? "purple" : "green");
            telemetry.addData("ball", getHue(colorRight) > 220 ? "purple" : "green");
            telemetry.addData("ball", getHue(colorCenter) > 220 ? "purple" : "green");
            telemetry.update();

            TelemetryPacket packet = new TelemetryPacket();

            packet.put("hueL", getHue(colorLeft));
            packet.put("hueR", getHue(colorRight));
            packet.put("hueC", getHue(colorCenter));
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