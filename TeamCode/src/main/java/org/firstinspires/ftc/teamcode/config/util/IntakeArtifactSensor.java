package org.firstinspires.ftc.teamcode.config.util;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.config.Robot;

public class IntakeArtifactSensor {




    DigitalChannel distanceSensor;
    ElapsedTime ballTimer = new ElapsedTime();

    CyclingList distanceList = new CyclingList(10);

    public double distance = 0;
    boolean ballTimerStarted = false;
    boolean justHadBall = false;
    public IntakeArtifactSensor(DigitalChannel distanceSensor) {
        this.distanceSensor = distanceSensor;


        // Set the channel as an input
        this.distanceSensor.setMode(DigitalChannel.Mode.INPUT);
    }
    public void update() {

        distanceList.add(distanceSensor.getState() ? 1 : 0, 0);

        if (!distanceSensor.getState()) {

        }

    }

    public boolean getBall() {
        return distanceSensor.getState();


    }

    public boolean hasBall() {
        if (distanceList.mode() == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public CyclingList getDistanceList() {
        return distanceList;
    }
    public boolean getState() {
        return distanceSensor.getState();
    }
}
