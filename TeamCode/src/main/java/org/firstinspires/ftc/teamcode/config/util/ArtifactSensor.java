package org.firstinspires.ftc.teamcode.config.util;

import android.util.Log;

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

    RevColorSensorV3 colorSensorOne;
    RevColorSensorV3 colorSensorTwo;
    ElapsedTime ballTimer = new ElapsedTime();
    CyclingList hueList = new CyclingList(2);
    public double hue = 0;
    public double distance = 0;
    boolean ballTimerStarted = false;
    public ArtifactSensor(RevColorSensorV3 colorSensorOne, RevColorSensorV3 colorSensorTwo) {
       // this.colorSensorOne = colorSensorOne;
        this.colorSensorTwo = colorSensorTwo;

       // this.colorSensorOne.setGain(20);
        this.colorSensorTwo.setGain(15f);
    }
    public void update() {
       // hue = getHue();
       //distance = getDistance();
    }
    public double getDistance() {
        return colorSensorTwo.getDistance(DistanceUnit.CM);
    }


    public double getHue() {
        float[] hsvValues = new float[3];

        android.graphics.Color.RGBToHSV(
                colorSensorTwo.red() * 12,
                colorSensorTwo.green() * 8,
                colorSensorTwo.blue() * 12,
                hsvValues
        );
        return hsvValues[0];
    }

    public Color getBall(Robot robot) {

        distance = getDistance();

        if (distance < 3 && (ballTimer.milliseconds() > 50  || !ballTimerStarted)) {
            if (!ballTimerStarted) {
                ballTimer.reset();
                ballTimerStarted = true;
                return Color.EMPTY;
            }
            else {
                robot.log("color", String.valueOf(hueList.values.size()));
                robot.log("color", String.valueOf(hueList.average()));
                ballTimerStarted = false;


                if (hueList.average() > 230) {
                    hueList.reset();
                    return Color.PURPLE;
                } else {
                    hueList.reset();
                    return Color.GREEN;
                }

           }
        }
        else if (distance < 4.4 && ballTimerStarted) {
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

}
