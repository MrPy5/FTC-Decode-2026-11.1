package org.firstinspires.ftc.teamcode.config.util.scheduler;

import java.util.Arrays;
import java.util.List;

public class SequentialCommand extends Command {
    private final List<Command> commands;
    private int current = 0;

    public SequentialCommand(Command... cmds) {
        commands = Arrays.asList(cmds);
    }

    @Override
    public void start() {
        if (!commands.isEmpty()) {
            commands.get(0).start();
            commands.get(0).started = true;
        }
    }

    @Override
    public void update(double time) {
        if (current >= commands.size()) {
            finished = true;
            return;
        }

        Command cmd = commands.get(current);
        cmd.update(time);

        if (cmd.isFinished()) {
            cmd.reset();
            current++;
            if (current < commands.size()) {
                commands.get(current).start();
                commands.get(current).started = true;
            } else {
                finished = true;
            }
        }
    }

    @Override
    public void reset() {
        current = 0;
        finished = false;
    }
}
