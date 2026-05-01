package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.hardware.andymark.AndyMarkColorSensor;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.Robot.RobotState;
import org.firstinspires.ftc.teamcode.config.util.ArtifactSensor;
import org.firstinspires.ftc.teamcode.config.util.Color;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

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
    ElapsedTime waitTimer = new ElapsedTime();
    boolean waitStarted = false;

    boolean index = false;

    Robot robot;
    HardwareMap hardwareMap;
    Servo leftLindexer;
    Servo rightLindexer;

    ArtifactSensor lindexerColorCenter;
    ArtifactSensor lindexerColorLeft;
    ArtifactSensor lindexerColorRight;

    Color leftBall = Color.EMPTY;
    Color centerBall = Color.EMPTY;
    Color rightBall = Color.EMPTY;


    LindexerState lindexerState = LindexerState.READY;
    LindexerPosition lindexerPosition = LindexerPosition.LEFT;

    public Lindexer(HardwareMap hardwareMap, Robot robot) {
        this.hardwareMap = hardwareMap;
        this.robot = robot;

        leftLindexer = hardwareMap.get(Servo.class, ConfigConstants.LEFT_LINDEXER);
        rightLindexer = hardwareMap.get(Servo.class, ConfigConstants.RIGHT_LINDEXER);


        lindexerColorCenter = new ArtifactSensor(hardwareMap.get(RevColorSensorV3.class, ConfigConstants.LINDEX_COLOR_CENTER), robot);
        lindexerColorLeft = new ArtifactSensor(hardwareMap.get(RevColorSensorV3.class, ConfigConstants.LINDEX_COLOR_LEFT), robot);
        lindexerColorRight = new ArtifactSensor(hardwareMap.get(RevColorSensorV3.class, ConfigConstants.LINDEX_COLOR_RIGHT), robot);


    }

    //Util functions
    public void update() {

        if (lindexerState == LindexerState.READY) {
            if (index && robot.getRobotState() == RobotState.INTAKE && numOfBalls() == 0 && robot.intake.getArtifactSensor().getState() && !waitStarted) {
                waitTimer.reset();
                waitStarted = true;
            }
            if (waitTimer.milliseconds() > 0 && waitStarted) {
                rightCenter();
                leftBall = Color.PURPLE;
                waitStarted = false;
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



    public ArtifactSensor getLindexerColor() {
        return lindexerColorCenter;
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

    public void empty() {
        leftBall = Color.EMPTY;
        centerBall = Color.EMPTY;
        rightBall = Color.EMPTY;
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

    public void stopIntakingAndLindex() {

        leftBall = lindexerColorLeft.getBall();
        rightBall = lindexerColorCenter.getBall();
        centerBall = assignCenterBall(leftBall, rightBall);

       /* robot.log("COLOR", leftBall.toString());
        robot.log("COLOR", rightBall.toString());
        robot.log("COLOR", centerBall.toString());*/

        Color nextBall = robot.classifier.getNextColor(robot.getMotif());
        robot.transfer.intakeTransfer();
        if (matchesColor(centerBall, nextBall)) {
            robot.scheduler.schedule(robot.commands.acceptCenterBall, robot.getMilliseconds());
        }
        else if (matchesColor(rightBall, nextBall)) {
            robot.scheduler.schedule(robot.commands.acceptRightBall, robot.getMilliseconds());
        }
        else if (matchesColor(leftBall, nextBall)) {
            robot.scheduler.schedule(robot.commands.acceptLeftBall, robot.getMilliseconds());
        }

    }

    public Color assignCenterBall(Color left, Color right) {
        if (left == Color.GREEN || right == Color.GREEN) {
            return Color.PURPLE;
        }
        else {
            return Color.GREEN;
        }
    }

    public void leftBallToCenter(Color centerBall) {
        this.leftBall = this.centerBall;
        this.centerBall = centerBall;
    }
    public void rightBallToCenter(Color centerBall) {
        this.rightBall = this.centerBall;
        this.centerBall = centerBall;
    }
}
