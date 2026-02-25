package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.util.CachedMotor;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

public class Ascent {
    public enum AscentState {
        UP,
        DOWN
    }


    HardwareMap hardwareMap;
    Servo ascentLeft;
    Servo ascentRight;

    AscentState ascentState = AscentState.DOWN;

    public Ascent(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        ascentLeft = hardwareMap.get(Servo.class, ConfigConstants.ASCENT_LEFT);
        ascentRight = hardwareMap.get(Servo.class, ConfigConstants.ASCENT_RIGHT);

    }


    public void ascend() {
        ascentLeft.setPosition(ConfigConstants.ASCEND_LEFT);
        ascentRight.setPosition(ConfigConstants.ASCEND_RIGHT);

        ascentState = AscentState.UP;
    }
    public void descend() {
        ascentLeft.setPosition(ConfigConstants.DESCEND_LEFT);
        ascentRight.setPosition(ConfigConstants.DESCEND_RIGHT);

        ascentState = AscentState.DOWN;
    }

    public AscentState getAscentState() {
        return ascentState;
    }
}
