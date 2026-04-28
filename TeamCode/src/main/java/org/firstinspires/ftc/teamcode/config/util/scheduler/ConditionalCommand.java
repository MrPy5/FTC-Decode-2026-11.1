package org.firstinspires.ftc.teamcode.config.util.scheduler;

import java.util.function.BooleanSupplier;

public class ConditionalCommand extends Command {
    private final BooleanSupplier condition;
    private final Command onTrue;
    private final Command onFalse;
    private Command selectedCommand;

    public ConditionalCommand(BooleanSupplier condition, Command onTrue, Command onFalse) {
        this.condition = condition;
        this.onTrue = onTrue;
        this.onFalse = onFalse;
    }

    @Override
    public void start() {
        // Evaluate condition when command starts
        if (condition.getAsBoolean()) {
            selectedCommand = onTrue;
        } else {
            selectedCommand = onFalse;
        }
        selectedCommand.start();
        selectedCommand.started = true;
    }

    @Override
    public void update(double time) {
        if (selectedCommand != null) {
            selectedCommand.update(time);
            if (selectedCommand.isFinished()) {
                finished = true;
            }
        }
    }

    @Override
    public void reset() {
        if (selectedCommand != null) {
            selectedCommand.reset();
        }
        selectedCommand = null;
        finished = false;
    }
}