package org.firstinspires.ftc.teamcode.config.util.scheduler;

import java.util.function.BooleanSupplier;

public class WaitUntil extends Command {
    private final BooleanSupplier condition;

    public WaitUntil(BooleanSupplier condition) {
        this.condition = condition;
    }

    @Override
    public void update(double time) {
        if (condition.getAsBoolean()) finished = true;
    }

    @Override
    public void reset() {
        finished = false;
    }
}