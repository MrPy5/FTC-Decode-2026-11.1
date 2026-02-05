package org.firstinspires.ftc.teamcode.config.util.scheduler;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Wait extends Command {
    private final double duration;
    private double startTime;
    private ElapsedTime timer = new ElapsedTime();

    public Wait(double milliseconds) {
        this.duration = milliseconds;
    }

    @Override
    public void start() {
        timer.reset();
    }

    @Override
    public void update(double time) {
        if (timer.milliseconds() >= duration) finished = true;
    }

    @Override
    public void reset() {
        finished = false;
    }
}