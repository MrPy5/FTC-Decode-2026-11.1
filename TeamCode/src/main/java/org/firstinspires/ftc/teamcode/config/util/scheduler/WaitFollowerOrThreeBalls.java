package org.firstinspires.ftc.teamcode.config.util.scheduler;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.config.Robot;

public class WaitFollowerOrThreeBalls extends Command {
    private Follower follower;
    private Robot robot;

    public WaitFollowerOrThreeBalls(Follower follower, Robot robot) {
        this.follower = follower;
        this.robot = robot;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double time) {
        if (!follower.isBusy() || robot.intake.getArtifactSensor().hasBall()) finished = true;
    }

    @Override
    public void reset() {
        finished = false;
    }
}