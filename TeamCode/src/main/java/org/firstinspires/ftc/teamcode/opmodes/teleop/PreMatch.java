/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorGoBildaPinpoint;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;


@TeleOp(name="PREMATCH", group="Linear OpMode")

public class PreMatch extends LinearOpMode {

    DcMotor frontRightDrive;
    DcMotor frontLeftDrive;
    DcMotor backRightDrive;
    DcMotor backLeftDrive;

    DcMotor intake;
    DcMotor transfer;
    DcMotor sml;
    DcMotor smr;

    Servo lifter;
    Servo transferBlocker;
    Servo shooterBlocker;
    Servo ll;
    Servo rl;
    Servo leftAscent;
    Servo rightAscent;

    RevColorSensorV3 colorSensorV3;
    DigitalChannel distance;

    @Override
    public void runOpMode() {

        frontRightDrive = hardwareMap.get(DcMotor.class, ConfigConstants.FRONT_RIGHT);
        frontLeftDrive = hardwareMap.get(DcMotor.class, ConfigConstants.FRONT_LEFT);
        backRightDrive = hardwareMap.get(DcMotor.class, ConfigConstants.BACK_RIGHT);
        backLeftDrive = hardwareMap.get(DcMotor.class, ConfigConstants.BACK_LEFT);


        intake = hardwareMap.get(DcMotor.class, ConfigConstants.INTAKE);
        transfer = hardwareMap.get(DcMotor.class, ConfigConstants.GREEN_WHEEL);

        sml = hardwareMap.get(DcMotor.class, ConfigConstants.SHOOTER_LEFT);
        smr = hardwareMap.get(DcMotor.class, ConfigConstants.SHOOTER_RIGHT);

        lifter = hardwareMap.get(Servo.class, ConfigConstants.INTAKE_LIFTER);
        transferBlocker = hardwareMap.get(Servo.class, ConfigConstants.TRANSFER_BLOCKER);
        shooterBlocker = hardwareMap.get(Servo.class, ConfigConstants.SHOOTER_BLOCKER);
        ll = hardwareMap.get(Servo.class, ConfigConstants.LEFT_LINDEXER);
        rl = hardwareMap.get(Servo.class, ConfigConstants.RIGHT_LINDEXER);
        leftAscent = hardwareMap.get(Servo.class, ConfigConstants.ASCENT_LEFT);
        rightAscent = hardwareMap.get(Servo.class, ConfigConstants.ASCENT_RIGHT);

        colorSensorV3 = hardwareMap.get(RevColorSensorV3.class, ConfigConstants.LINDEX_COLOR_LEFT);
        distance = hardwareMap.get(DigitalChannel.class, ConfigConstants.DIGITAL_DISTANCE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();

        frontRightDrive.setPower(1);
        sleep(1000);
        frontRightDrive.setPower(0);
        frontLeftDrive.setPower(1);
        sleep(1000);
        frontLeftDrive.setPower(0);
        backLeftDrive.setPower(1);
        sleep(1000);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(1);
        sleep(1000);
        backRightDrive.setPower(0);

        while (!gamepad1.ps);

        intake.setPower(0.5);
        sleep(1000);
        intake.setPower(0);
        transfer.setPower(0.5);
        sleep(1000);
        transfer.setPower(0);

        while (!gamepad1.ps);

        sml.setPower(0.5);
        sleep(1000);
        sml.setPower(0);
        sleep(2000);
        smr.setPower(0.5);
        sleep(1000);
        smr.setPower(0);

        while (!gamepad1.ps);

        lifter.setPosition(ConfigConstants.INTAKE_LIFT);
        sleep(500);
        lifter.setPosition(ConfigConstants.INTAKE_DROP);
        sleep(500);
        transferBlocker.setPosition(ConfigConstants.TRANSFER_BLOCK);
        sleep(500);
        transferBlocker.setPosition(ConfigConstants.TRANSFER_UNBLOCK);
        sleep(500);
        shooterBlocker.setPosition(ConfigConstants.SHOOTER_BLOCK);
        sleep(500);
        shooterBlocker.setPosition(ConfigConstants.SHOOTER_UNBLOCK);
        sleep(500);
        ll.setPosition(ConfigConstants.LEFT_LIN_IN);
        sleep(500);
        ll.setPosition(ConfigConstants.LEFT_LIN_OUT);
        sleep(500);
        rl.setPosition(ConfigConstants.RIGHT_LIN_IN);
        sleep(500);
        rl.setPosition(ConfigConstants.RIGHT_LIN_OUT);
        sleep(500);

        while (!gamepad1.ps);

        leftAscent.setPosition(ConfigConstants.ASCEND_LEFT);
        sleep(500);
        leftAscent.setPosition(ConfigConstants.DESCEND_LEFT);
        sleep(500);
        rightAscent.setPosition(ConfigConstants.ASCEND_RIGHT);
        sleep(500);
        rightAscent.setPosition(ConfigConstants.DESCEND_RIGHT);
        sleep(500);

        while (!gamepad1.ps);

        sleep(1000);

        while(!gamepad1.ps) {
            telemetry.addData("distance", colorSensorV3.getDistance(DistanceUnit.CM));
            telemetry.update();
        }
        sleep(1000);

        while(!gamepad1.ps) {
            telemetry.addData("ball in?", distance.getState());
            telemetry.update();
        }

    }
}
