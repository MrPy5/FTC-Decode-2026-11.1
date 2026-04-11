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
    Robot robot;
    CyclingList hueList = new CyclingList(5);
    public double hue = 0;

    public ArtifactSensor(RevColorSensorV3 colorSensor, Robot robot) {

        this.colorSensor= colorSensor;
        this.colorSensor.setGain(5f);
        this.robot = robot;
    }
    public void update() {
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

    public Color getBall() {
        if (getHue() > 220) {
            return Color.PURPLE;
        }
        else {
            return Color.GREEN;
        }

    }

    public RevColorSensorV3 getColorSensor() {
        return colorSensor;
    }
}
