package org.firstinspires.ftc.teamcode.config.util.scheduler;

import com.pedropathing.follower.Follower;

public class WaitFollower extends Command {
    private Follower follower;

    public WaitFollower(Follower follower) {
        this.follower = follower;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double time) {
        if (!follower.isBusy()) finished = true;
    }

    @Override
    public void reset() {
        finished = false;
    }
}