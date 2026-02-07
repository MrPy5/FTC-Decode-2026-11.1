package org.firstinspires.ftc.teamcode.config.subsystems.field;

import org.firstinspires.ftc.teamcode.config.util.Color;
import org.firstinspires.ftc.teamcode.config.util.Motif;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

public class Classifier {

    int ballsInClassifier = 0;
    public Classifier() {

    }
    public void reset() {
        ballsInClassifier = 0;
    }
    public void addBall() {
        ballsInClassifier += 1;
    }
    public void subtractBall() {
        if (ballsInClassifier > 0) {
            ballsInClassifier -= 1;
        }
    }

    public double getBallsOnClassifier() {
        return ballsInClassifier;
    }

    public Color getNextColor(Motif motif) {
        return ConfigConstants.MOFIT_COLOR_MAP.get(motif)[ballsInClassifier % 3];
    }
}
