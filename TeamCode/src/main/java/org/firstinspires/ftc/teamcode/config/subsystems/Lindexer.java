package org.firstinspires.ftc.teamcode.config.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.util.CachedMotor;
import org.firstinspires.ftc.teamcode.config.util.Color;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

@Config
public class Lindexer {


    HardwareMap hardwareMap;
    Servo leftLindexer;
    Servo rightLindexer;

    Color leftBall = Color.EMPTY;
    Color centerBall = Color.EMPTY;
    Color rightBall = Color.EMPTY;



    public Lindexer(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        leftLindexer = hardwareMap.get(Servo.class, ConfigConstants.LEFT_LINDEXER);
        rightLindexer = hardwareMap.get(Servo.class, ConfigConstants.RIGHT_LINDEXER);


    }

    //Util functions
    public void update() {

    }


    public void leftIn() {
        leftLindexer.setPosition(ConfigConstants.LEFT_LIN_IN);
    }
    public void leftOut() {
        leftLindexer.setPosition(ConfigConstants.LEFT_LIN_OUT);
    }
    public void rightIn() {
        leftLindexer.setPosition(ConfigConstants.RIGHT_LIN_IN);
    }
    public void rightOut() {
        leftLindexer.setPosition(ConfigConstants.RIGHT_LIN_OUT);
    }
    public void clear() {
        leftOut();
        rightOut();
    }
}
