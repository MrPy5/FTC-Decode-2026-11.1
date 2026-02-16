package org.firstinspires.ftc.teamcode.config.util.scheduler;

import com.pedropathing.follower.Follower;

public class WaitFollowerOrStuck extends Command {
    private Follower follower;

    public WaitFollowerOrStuck(Follower follower) {
        this.follower = follower;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double time) {
        if (!follower.isBusy() || follower.isRobotStuck() || (follower.getVelocity().getXComponent() < 0.25 && follower.getVelocity().getYComponent() < 0.25)) finished = true;
    }

    @Override
    public void reset() {
        finished = false;
    }
}