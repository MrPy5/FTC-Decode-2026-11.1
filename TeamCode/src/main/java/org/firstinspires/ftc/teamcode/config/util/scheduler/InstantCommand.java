package org.firstinspires.ftc.teamcode.config.util.scheduler;

public class InstantCommand extends Command {
    private final Runnable action;
    private boolean done = false;

    public InstantCommand(Runnable action) {
        this.action = action;
    }

    @Override
    public void start(double time) {
        action.run();   // execute the passed lambda
        done = true;
        finished = true;
    }

    @Override
    public void update(double time) {
        // Nothing to do — it’s instant
    }

    @Override
    public boolean isFinished() {
        return done;
    }

    @Override
    public void reset() {
        done = false;
        finished = false;
    }
}