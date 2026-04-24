package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.util.CachedMotor;
import org.firstinspires.ftc.teamcode.config.util.IntakeArtifactSensor;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

public class Indicator {



    HardwareMap hardwareMap;
    Robot robot;
    Servo indicator;



    public Indicator(HardwareMap hardwareMap, Robot robot) {
        this.hardwareMap = hardwareMap;
        this.robot = robot;


        indicator = hardwareMap.get(Servo.class, ConfigConstants.RGB_INDICATOR);


    }

    public void update() {
        boolean ball = robot.intake.artifactSensor.getState();
        if (ball) {
            if (!robot.turret.passedBounds() && robot.chassis.inchesAwayPinpoint(robot.follower.getPose()) > 57) {
                green();
            }
            else {
                red();
            }
        }
        else {
            off();
        }
    }
    public void red() {
        indicator.setPosition(0.28);
    }
    public void off() {
        indicator.setPosition(0);
    }
    public void green() {
        indicator.setPosition(0.5);
    }

}
