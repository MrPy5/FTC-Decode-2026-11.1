package org.firstinspires.ftc.teamcode.config.util.scheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommandScheduler {
    private final List<Command> commands = new ArrayList<>();
    public void schedule(Command cmd, double time) {
        cmd.start();
        cmd.started = true;
        commands.add(cmd);
    }


    public void update(double time) {
        if (!commands.isEmpty()) {
            Iterator<Command> it = commands.iterator();
            while (it.hasNext()) {
                Command cmd = it.next();
                if (!cmd.started) {
                    cmd.start();
                    cmd.started = true;
                }
                cmd.update(time);
                if (cmd.isFinished()) {
                    cmd.reset();
                    it.remove();

                }
            }
        }
    }

    public boolean isIdle() {
        return commands.isEmpty();
    }

    public void clear() {
        Iterator<Command> it = commands.iterator();
        while (it.hasNext()) {
            Command cmd = it.next();
            cmd.reset();
        }
        commands.clear();
    }
    public double length() {
        return commands.size();
    }

    public String latestCommand() {
        if (!commands.isEmpty()) {
            return commands.get(0).toString();
        }
        return null;
    }

}