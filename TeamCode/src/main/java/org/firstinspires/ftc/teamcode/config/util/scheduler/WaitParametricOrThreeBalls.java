package org.firstinspires.ftc.teamcode.config.util.scheduler;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.Robot;

public class WaitParametricOrThreeBalls extends Command {
    private Follower follower;
    private Robot robot;
    private ElapsedTime timer = new ElapsedTime();
    boolean timerStarted = false;

    public WaitParametricOrThreeBalls(Follower follower, Robot robot) {
        this.follower = follower;
        this.robot = robot;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double time) {
        if (follower.isRobotStuck() || (robot.intake.getArtifactSensor().hasBall() && robot.transfer.motorStopped) || follower.atParametricEnd() || (follower.getCurrentTValue() > 0.5 && follower.getVelocity().getMagnitude() < 5)) finished = true;
        if (follower.getVelocity().getMagnitude() < 2 && !timerStarted) {
            timerStarted = true;
            timer.reset();
        }
        if (follower.getVelocity().getMagnitude() > 2) {
            timerStarted = false;
        }
        if (timer.milliseconds() > 1000 && timerStarted) {
            finished = true;
        }
    }

    @Override
    public void reset() {
        finished = false;
        timerStarted = false;
    }
}