package org.firstinspires.ftc.teamcode.config.subsystems;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.util.CachedMotor;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Chassis {



    HardwareMap hardwareMap;

    VoltageSensor voltageSensor;

    public Chassis(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");

    }
    //Util functions


    public void updateState() {

    }

    public double getVoltage() {
        return voltageSensor.getVoltage();
    }

    public static double[] calculateDrivePowers(Gamepad c1, double driveDampeneing, double strafeDampening, double turnDampening) {
        double drivePower;
        double strafePower;
        double turnPower;

        drivePower = -c1.left_stick_y * driveDampeneing;
        strafePower = -c1.left_stick_x * strafeDampening;
        turnPower = -c1.right_stick_x * turnDampening;

        return new double[]{drivePower, strafePower, turnPower};
    }
}
