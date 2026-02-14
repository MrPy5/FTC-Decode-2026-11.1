package org.firstinspires.ftc.teamcode.config.util;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ArtifactSensor {

    RevColorSensorV3 colorSensorOne;
    RevColorSensorV3 colorSensorTwo;
    ElapsedTime ballTimer = new ElapsedTime();
    CyclingList hueList = new CyclingList(15);
    boolean ballTimerStarted = false;
    public ArtifactSensor(RevColorSensorV3 colorSensorOne, RevColorSensorV3 colorSensorTwo) {
       // this.colorSensorOne = colorSensorOne;
        this.colorSensorTwo = colorSensorTwo;

       // this.colorSensorOne.setGain(20);
        this.colorSensorTwo.setGain(20);
    }
    public void update() {
        if (ballTimerStarted) {
            hueList.add(getHue(), 0);
        }
        else {
            hueList.reset();
        }
    }
    public double getDistance() {
        return colorSensorTwo.getDistance(DistanceUnit.INCH);
    }
    public double getDistanceOne() {
        return (colorSensorOne.getDistance(DistanceUnit.INCH));
    }
    public float getColorGreen() {
        if (getDistanceOne() > 2) {
            return colorSensorTwo.green();
        }
        else {
            return colorSensorOne.green();
        }
    }
    public double getColorBlue() {
        if (getDistanceOne() > 2) {
            return colorSensorTwo.blue();
        }
        else {
            return colorSensorOne.blue();
        }
    }
    public double getHue() {
        float[] hsvValues = new float[3];

        android.graphics.Color.RGBToHSV(
                colorSensorTwo.red() * 8,
                colorSensorTwo.green() * 8,
                colorSensorTwo.blue() * 8,
                hsvValues
        );
        return hsvValues[0];
    }

    public Color getBall() {


        if (getDistance() < 1.2 && (ballTimer.milliseconds() > 0  || !ballTimerStarted)) {
            if (!ballTimerStarted) {
                ballTimer.reset();
                ballTimerStarted = true;
                return Color.EMPTY;
            }
            else {
                ballTimerStarted = false;
                if (getHue() > 180) {
                    return Color.PURPLE;
                } else {
                    return Color.GREEN;
                }
            }
        }
        else {
            return Color.EMPTY;
        }


    }

}
