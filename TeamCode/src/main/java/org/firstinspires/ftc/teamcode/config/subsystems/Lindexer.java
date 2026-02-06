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

    public enum LindexerState {
        INDEX,
        NONINDEX
    }

    HardwareMap hardwareMap;
    Servo leftLindexer;
    Servo rightLindexer;
    Servo intakeBlocker;

    Color leftBall = Color.EMPTY;
    Color centerBall = Color.EMPTY;
    Color rightBall = Color.EMPTY;


    LindexerState lindexerState = LindexerState.NONINDEX;

    public Lindexer(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        leftLindexer = hardwareMap.get(Servo.class, ConfigConstants.LEFT_LINDEXER);
        rightLindexer = hardwareMap.get(Servo.class, ConfigConstants.RIGHT_LINDEXER);

        intakeBlocker = hardwareMap.get(Servo.class, ConfigConstants.INTAKE_BLOCKER);


    }

    //Util functions
    public void update() {
        if (lindexerState == LindexerState.INDEX) {

        }
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
    public void leftCenter() {
        leftIn();
        rightOut();
    }
    public void rightCenter() {
        rightIn();
        leftOut();
    }
    public void clear() {
        leftOut();
        rightOut();
    }

    public void block() {
        intakeBlocker.setPosition(ConfigConstants.LINDEX_BLOCK);
    }
    public void unblock() {
        intakeBlocker.setPosition(ConfigConstants.LINDEX_UNBLOCK);
    }
    public boolean matchesColor(Color ball, Color color) {
        if (ball.equals(color)) {
            return true;
        }
        else if (color.equals(Color.BOTH) && !ball.equals(Color.EMPTY)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void moveToNextBall(Color desiredColor) {
        if (matchesColor(leftBall, desiredColor)) {
            leftCenter();

        }
        else if (matchesColor(rightBall, desiredColor)) {
            rightCenter();
        }
        else if (matchesColor(centerBall, desiredColor)){
            clear();
            unblock();

        }
    }

    public void setLindexerState(LindexerState lindexerState) {
        this.lindexerState = lindexerState;
    }
}
