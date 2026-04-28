package org.firstinspires.ftc.teamcode.config.util.scheduler;

import java.util.function.Supplier;

/**
 * A command that selects one of several commands based on a selector function.
 * Useful for multi-way branching (like a switch statement).
 */
public class SelectCommand extends Command {
    private final Supplier<Integer> selector;
    private final Command[] commands;
    private Command selectedCommand;

    public SelectCommand(Supplier<Integer> selector, Command... commands) {
        this.selector = selector;
        this.commands = commands;
    }

    @Override
    public void start() {
        int index = selector.get();
        if (index >= 0 && index < commands.length) {
            selectedCommand = commands[index];
            selectedCommand.start();
            selectedCommand.started = true;
        } else {
            // Invalid index, finish immediately
            finished = true;
        }
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