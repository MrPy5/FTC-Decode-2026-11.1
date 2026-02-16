package org.firstinspires.ftc.teamcode.config.util.scheduler;

import com.pedropathing.follower.Follower;

public class WaitScheduler extends Command {
    private CommandScheduler scheduler;

    public WaitScheduler(CommandScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double time) {
        if (scheduler.isIdle()) finished = true;
    }

    @Override
    public void reset() {
        finished = false;
    }
}