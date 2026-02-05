package org.firstinspires.ftc.teamcode.config.subsystems.field;

public class Classifier {

    double ballsOnClassifier = 0;
    public Classifier() {

    }
    public void reset() {
        ballsOnClassifier = 0;
    }
    public void addBall() {
        ballsOnClassifier += 1;
    }
    public void subtractBall() {
        if (ballsOnClassifier > 0) {
            ballsOnClassifier -= 1;
        }
    }

    public double getBallsOnClassifier() {
        return ballsOnClassifier;
    }
}
