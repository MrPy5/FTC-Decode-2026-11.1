package org.firstinspires.ftc.teamcode.config.util.scheduler;

public class Wait extends Command {
    private final double duration;
    private double startTime;

    public Wait(double milliseconds) {
        this.duration = milliseconds;
    }

    @Override
    public void start(double time) {
        startTime = time;
    }

    @Override
    public void update(double time) {
        if (time - startTime >= duration) finished = true;
    }

    @Override
    public void reset() {
        finished = false;
    }
}