package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

public class Turret {
    public enum TurretState {
        TRACK,
        HOLD
    }


    HardwareMap hardwareMap;
    Servo turretLeft;
    Servo turretRight;

    TurretState turretState = TurretState.HOLD;

    double angle = 0;

    public Turret(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        turretLeft = hardwareMap.get(Servo.class, ConfigConstants.TURRET_LEFT);
        turretRight = hardwareMap.get(Servo.class, ConfigConstants.TURRET_RIGHT);

    }


    public TurretState getTurretState() {
        return turretState;
    }

    public void update(Robot robot) {
        if (turretState == TurretState.TRACK) {
            setAngle(-Math.toDegrees(robot.follower.getHeading()) + robot.chassis.degreesAwayPinpoint(robot));
        }
    }
    public void setAngle(double angle) {
        double ticks = angleToTicks(wrapAngle(angle));

        turretLeft.setPosition(ticks);
        turretRight.setPosition(ticks + ConfigConstants.TURRET_TICK_OFFSET_FOR_RIGHT);
    }

    public double angleToTicks(double angle) {
        double ticks = ConfigConstants.TURRET_ZERO + (angle * ConfigConstants.TICKS_PER_TURRET_DEGREE);
        ticks = Math.min(ConfigConstants.TURRET_MAX, ticks);
        ticks = Math.max(ConfigConstants.TURRET_MIN, ticks);
        return ticks;
    }

    public double wrapAngle(double angle) {
        angle = angle % 360;

        if (angle > 180) {
            angle = -(360 - angle);
        }
        return angle;
    }
    public void setState(TurretState state) {
        turretState = state;
    }

    public double getAngle() {
        return angle;
    }
    public void incrementAngle(double increment) {
        this.angle += increment;
    }
}
