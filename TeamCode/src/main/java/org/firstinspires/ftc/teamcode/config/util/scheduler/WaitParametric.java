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
        if (follower.isRobotStuck() || follower.atParametricEnd() || (follower.getCurrentTValue() > 0.3 && follower.getVelocity().getMagnitude() < 5)) finished = true;
    }

    @Override
    public void reset() {
        finished = false;
    }
}