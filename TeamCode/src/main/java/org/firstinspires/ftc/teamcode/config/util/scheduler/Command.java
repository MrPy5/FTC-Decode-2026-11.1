package org.firstinspires.ftc.teamcode.config.util.scheduler;

public abstract class Command {
    public boolean started = false;
    public boolean finished = false;

    public void start() {}


    public abstract void update(double time);
    public boolean isFinished() { return finished; }
    public void reset(){}
}