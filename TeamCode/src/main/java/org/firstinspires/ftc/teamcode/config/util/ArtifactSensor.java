package org.firstinspires.ftc.teamcode.config.util;

import android.util.Log;

import com.qualcomm.hardware.andymark.AndyMarkColorSensor;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

public class ArtifactSensor {

    RevColorSensorV3 colorSensor;
    ElapsedTime ballTimer = new ElapsedTime();
    CyclingList hueList = new CyclingList(5);
    public double hue = 0;
    public double distance = 0;
    boolean ballTimerStarted = false;
    public double time = 50;
    public ArtifactSensor(RevColorSensorV3 colorSensor) {
       // this.colorSensorOne = colorSensorOne;
        this.colorSensor= colorSensor;

       // this.colorSensorOne.setGain(20);
        this.colorSensor.setGain(5f);
    }
    public void update() {
       // hue = getHue();
       //distance = getDistance();
    }
    public double getDistance() {
        return colorSensor.getDistance(DistanceUnit.CM);
    }


    public double getHue() {
        float[] hsvValues = new float[3];

        android.graphics.Color.RGBToHSV(
                colorSensor.red() * 12,
                colorSensor.green() * 8,
                colorSensor.blue() * 12,
                hsvValues
        );
        return hsvValues[0];
    }

    public Color getBall(Robot robot) {

        distance = getDistance();

        if (distance < 4.7 && (ballTimer.milliseconds() > time  || !ballTimerStarted)) {
            if (!ballTimerStarted) {
                ballTimer.reset();
                ballTimerStarted = true;
                return Color.EMPTY;
            }
            else {
                robot.log("color", String.valueOf(hueList.values.size()));
                robot.log("color", String.valueOf(hueList.average()));
                ballTimerStarted = false;


                if (hueList.average() > 220) {
                    hueList.reset();
                    return Color.PURPLE;
                } else {
                    hueList.reset();
                    return Color.GREEN;
                }

           }
        }
        else if (distance < 4.7 && ballTimerStarted) {
            hue = getHue();
            hueList.add(hue, 0);
            return Color.EMPTY;
        }
        else {
            ballTimerStarted = false;
            hueList.reset();
            return Color.EMPTY;
        }

    }

    public RevColorSensorV3 getColorSensor() {
        return colorSensor;
    }
}
