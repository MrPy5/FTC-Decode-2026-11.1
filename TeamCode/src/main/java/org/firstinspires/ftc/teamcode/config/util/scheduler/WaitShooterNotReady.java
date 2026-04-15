package org.firstinspires.ftc.teamcode.config.util.scheduler;

import org.firstinspires.ftc.teamcode.config.subsystems.Shooter;

public class WaitShooterNotReady extends Command {
    private Shooter shooter;

    public WaitShooterNotReady(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double time) {
    if (shooter.getShooterState() == Shooter.ShooterState.NOTREADY) finished = true;
    }

    @Override
    public void reset() {
        finished = false;
    }
}