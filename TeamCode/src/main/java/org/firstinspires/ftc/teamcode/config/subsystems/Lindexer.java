package org.firstinspires.ftc.teamcode.config.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
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
    public void update(Gamepad c2) {
        if (lindexerState == LindexerState.INDEX) {
            if (c2.leftBumperWasPressed()) {
                rightCenter();
            }
            if (c2.rightBumperWasPressed()) {
                clear();
            }
        }
    }


    public void leftIn() {
        leftLindexer.setPosition(ConfigConstants.LEFT_LIN_IN);
    }
    public void leftOut() {
        leftLindexer.setPosition(ConfigConstants.LEFT_LIN_OUT);
    }
    public void rightIn() {
        rightLindexer.setPosition(ConfigConstants.RIGHT_LIN_IN);
    }
    public void rightOut() {
        rightLindexer.setPosition(ConfigConstants.RIGHT_LIN_OUT);
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
            leftBall = Color.EMPTY;
            leftCenter();

        }
        else if (matchesColor(rightBall, desiredColor)) {
            rightBall = Color.EMPTY;
            rightCenter();
        }
        else if (matchesColor(centerBall, desiredColor)){
            centerBall = Color.EMPTY;
            clear();
            unblock();
        }

    }

    public void setLindexerState(LindexerState lindexerState) {
        this.lindexerState = lindexerState;
    }

    public void setLeftBall(Color leftBall) {
        this.leftBall = leftBall;
    }

    public void setRightBall(Color rightBall) {
        this.rightBall = rightBall;
    }

    public void setCenterBall(Color centerBall) {
        this.centerBall = centerBall;
    }

    public Color getLeftBall() {
        return leftBall;
    }

    public Color getRightBall() {
        return rightBall;
    }

    public Color getCenterBall() {
        return centerBall;
    }
}
