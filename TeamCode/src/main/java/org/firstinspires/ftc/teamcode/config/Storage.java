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
        if (storedFollower != null) {
            return storedFollower;
        }
        else {
            return null;
        }
    }

    public static void setStoredAlliance(Alliance storedAlliance) {
        Storage.storedAlliance = storedAlliance;
    }

    public static Alliance getStoredAlliance() {
        if (storedAlliance != null) {
            return storedAlliance;
        }
        else {
            return null;
        }
    }

    public static void setStoredMotif(Motif storedMotif) {

        Storage.storedMotif = storedMotif;


    }

    public static Motif getStoredMotif() {
        if (storedMotif != null) {
            return storedMotif;
        }
        else {
            return null;
        }
    }

    public static void cleanup(Alliance storedAlliance, Motif storedMotif, Follower storedFollower) {
        setStoredAlliance(storedAlliance);
        setStoredMotif(storedMotif);
        setStoredFollower(storedFollower);
    }
}
