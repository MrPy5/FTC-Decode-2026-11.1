package org.firstinspires.ftc.teamcode.config.util;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ArtifactSensor {

    RevColorSensorV3 colorSensorOne;
    RevColorSensorV3 colorSensorTwo;
    public ArtifactSensor(RevColorSensorV3 colorSensorOne, RevColorSensorV3 colorSensorTwo) {
        this.colorSensorOne = colorSensorOne;
        this.colorSensorTwo = colorSensorTwo;
    }
    public double getDistance() {
        return (colorSensorOne.getDistance(DistanceUnit.INCH) + colorSensorTwo.getDistance(DistanceUnit.INCH)) / 2;
    }
    public float getColorGreen() {
        return JavaUtil.colorToHue(colorSensorOne.getNormalizedColors().toColor());
    }
    public double getColorRed() {
        return colorSensorTwo.argb() / 1000000.0;
    }

    public Color getBall() {
        if (getDistance() < 2) {
            if (getColorRed() > 1400) {
                return Color.PURPLE;
            }
            else {
                return Color.GREEN;
            }
        }
        else {
            return Color.EMPTY;
        }
    }

}
