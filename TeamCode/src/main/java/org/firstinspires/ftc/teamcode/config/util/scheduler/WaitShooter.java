package org.firstinspires.ftc.teamcode.config.util.scheduler;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.config.subsystems.Shooter;

public class WaitShooter extends Command {
    private Shooter shooter;

    public WaitShooter(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double time) {
    if (shooter.getShooterState() == Shooter.ShooterState.READY) finished = true;
    }

    @Override
    public void reset() {
        finished = false;
    }
}