package org.firstinspires.ftc.teamcode.config.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.Robot.RobotState;
import org.firstinspires.ftc.teamcode.config.util.ArtifactSensor;
import org.firstinspires.ftc.teamcode.config.util.CachedMotor;
import org.firstinspires.ftc.teamcode.config.util.Color;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;
import org.opencv.ml.EM;

public class Lindexer {

    public enum LindexerState {
        READY,
        NOTREADY,
        WAITING
    }
    public enum LindexerPosition {
        LEFT,
        CLEAR,
        RIGHT
    }

    ElapsedTime moveTime = new ElapsedTime();

    boolean index = false;


    HardwareMap hardwareMap;
    Servo leftLindexer;
    Servo rightLindexer;

    ArtifactSensor lindexerColor;

    Color leftBall = Color.EMPTY;
    Color centerBall = Color.EMPTY;
    Color rightBall = Color.EMPTY;

    int green = 400000000;
    int purple = 100000000;


    LindexerState lindexerState = LindexerState.READY;
    LindexerPosition lindexerPosition = LindexerPosition.LEFT;

    public Lindexer(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        leftLindexer = hardwareMap.get(Servo.class, ConfigConstants.LEFT_LINDEXER);
        rightLindexer = hardwareMap.get(Servo.class, ConfigConstants.RIGHT_LINDEXER);


        lindexerColor = new ArtifactSensor(null, hardwareMap.get(RevColorSensorV3.class, ConfigConstants.LINDEX_COLOR_RIGHT));


    }

    //Util functions
    public void update(Robot robot) {
        //lindexerColor.update();

        if (lindexerState == LindexerState.READY) {
            if (index && robot.getRobotState() == RobotState.INTAKE && numOfBalls() != 3) {

                Color ballColor = lindexerColor.getBall(robot);
                if (ballColor != Color.EMPTY) {
                    if (ballColor == robot.classifier.getNextColor(robot.getMotif()) && centerBall == Color.EMPTY) {
                        robot.transfer.unblock();
                        centerBall = ballColor;
                        lindexerState = LindexerState.NOTREADY;
                        robot.log("color", "moved ball to center");
                    }
                    else if (ballColor != robot.classifier.getNextColor(robot.getMotif()) || centerBall != Color.EMPTY) {
                        if (lindexerPosition == LindexerPosition.LEFT) {
                            if (centerBall == Color.EMPTY && leftBall != Color.EMPTY) {
                                clear();
                                robot.log("color", "cleared");
                            }
                            else {
                                rightCenter();
                                robot.log("color", "right");
                            }
                            leftBall = ballColor;
                        } else if (lindexerPosition == LindexerPosition.RIGHT) {
                            if (centerBall == Color.EMPTY && rightBall != Color.EMPTY) {
                                clear();
                                robot.log("color", "cleared");
                            }
                            else {
                                leftCenter();
                                robot.log("color", "left");
                            }
                            rightBall = ballColor;
                        }
                    }
                }


            }
        }
        else if (lindexerState == LindexerState.NOTREADY) {
            moveTime.reset();
            lindexerState = LindexerState.WAITING;
        }
        else if (lindexerState == LindexerState.WAITING && moveTime.milliseconds() > ConfigConstants.MOVE_MILLISECONDS)  {
            lindexerState = LindexerState.READY;
        }
    }


    private void leftIn() {
        leftLindexer.setPosition(ConfigConstants.LEFT_LIN_IN);
    }
    private void leftOut() {
        leftLindexer.setPosition(ConfigConstants.LEFT_LIN_OUT);
    }
    private void rightIn() {
        rightLindexer.setPosition(ConfigConstants.RIGHT_LIN_IN);
    }
    private void rightOut() {
        rightLindexer.setPosition(ConfigConstants.RIGHT_LIN_OUT);
    }


    public void leftCenter() {
        leftIn();
        rightOut();
        lindexerPosition = LindexerPosition.LEFT;
        lindexerState = LindexerState.NOTREADY;
    }
    public void rightCenter() {
        rightIn();
        leftOut();
        lindexerPosition = LindexerPosition.RIGHT;
        lindexerState = LindexerState.NOTREADY;
    }
    public void clear() {
        leftOut();
        rightOut();
        lindexerPosition = LindexerPosition.CLEAR;
        lindexerState = LindexerState.NOTREADY;
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
        if (matchesColor(centerBall, desiredColor)){
            centerBall = Color.EMPTY;
        }
        else if (matchesColor(leftBall, desiredColor)) {
            leftBall = Color.EMPTY;
            leftCenter();

        }
        else if (matchesColor(rightBall, desiredColor)) {
            rightBall = Color.EMPTY;
            rightCenter();
        }
        else {
            if (matchesColor(leftBall, Color.BOTH)) {
                leftBall = Color.EMPTY;
                leftCenter();

            }
            else {
                rightBall = Color.EMPTY;
                rightCenter();
            }
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

    public ArtifactSensor getLindexerColor() {
        return lindexerColor;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }

    public LindexerPosition getLindexerPosition() {
        return lindexerPosition;
    }
    public int numOfBalls() {
        int balls = 0;
        if (leftBall != Color.EMPTY) {
            balls += 1;
        }
        if (rightBall != Color.EMPTY) {
            balls += 1;
        }
        if (centerBall != Color.EMPTY) {
            balls += 1;
        }
        return balls;
    }

    public LindexerState getLindexerState() {
        return lindexerState;
    }
}
