package org.firstinspires.ftc.teamcode.config.util.scheduler;

import com.pedropathing.follower.Follower;

public class WaitParametric extends Command {
    private Follower follower;

    public WaitParametric(Follower follower) {
        this.follower = follower;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double time) {
        if (follower.atParametricEnd()) finished = true;
    }

    @Override
    public void reset() {
        finished = false;
    }
}