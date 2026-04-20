package org.firstinspires.ftc.teamcode.config.util.scheduler;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.subsystems.Shooter;

public class WaitShooterNotReady extends Command {
    private final Shooter shooter;
    private ElapsedTime timer = new ElapsedTime();

    public WaitShooterNotReady(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override
    public void start() {
        timer.reset();
    }

    @Override
    public void update(double time) {
        if (timer.milliseconds() >= 500 || shooter.getShooterState() == Shooter.ShooterState.NOTREADY) finished = true;
    }

    @Override
    public void reset() {
        finished = false;
    }
}