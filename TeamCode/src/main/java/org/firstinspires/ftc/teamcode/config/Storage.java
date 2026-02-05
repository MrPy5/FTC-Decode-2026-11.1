package org.firstinspires.ftc.teamcode.config;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.config.util.Alliance;
import org.firstinspires.ftc.teamcode.config.util.Motif;

public class Storage {
    public static Follower storedFollower;
    public static Motif storedMotif;
    public static Alliance storedAlliance;

    public static void setStoredFollower(Follower storedFollower) {
        Storage.storedFollower = storedFollower;
    }

    public static Follower getStoredFollower() {
        return storedFollower;
    }

    public static void setStoredAlliance(Alliance storedAlliance) {
        Storage.storedAlliance = storedAlliance;
    }

    public static Alliance getStoredAlliance() {
        return storedAlliance;
    }

    public static void setStoredMotif(Motif storedMotif) {
        Storage.storedMotif = storedMotif;
    }

    public static Motif getStoredMotif() {
        return storedMotif;
    }

    public static void cleanup(Alliance storedAlliance, Motif storedMotif, Follower storedFollower) {
        setStoredAlliance(storedAlliance);
        setStoredMotif(storedMotif);
        setStoredFollower(storedFollower);
    }
}
