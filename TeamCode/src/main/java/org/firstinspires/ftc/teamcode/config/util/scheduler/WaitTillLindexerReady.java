package org.firstinspires.ftc.teamcode.config.util.scheduler;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.subsystems.Lindexer;

public class WaitTillLindexerReady extends Command {
    private Lindexer lindexer;

    public WaitTillLindexerReady(Lindexer lindexer) {
        this.lindexer = lindexer;
    }


    @Override
    public void update(double time) {
        if (lindexer.getLindexerState() == Lindexer.LindexerState.READY) finished = true;
    }

    @Override
    public void reset() {
        finished = false;
    }
}