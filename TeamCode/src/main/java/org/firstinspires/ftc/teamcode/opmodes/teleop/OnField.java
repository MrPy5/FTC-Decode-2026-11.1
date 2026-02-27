package org.firstinspires.ftc.teamcode.opmodes.teleop;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.config.Robot;

@TeleOp(name = "ON FIELD")
public class OnField extends OpMode {

    Robot robot;
    @Override
    public void init() {
        robot = new Robot(hardwareMap, gamepad1, gamepad2);
    }

    @Override
    public void init_loop() {

        robot.follower.update();

    }

    @Override
    public void start() {
        robot.follower.update();
    }

    @Override
    public void loop() {

        robot.follower.update();

        telemetry.addData("x:", robot.follower.getPose().getX());
        telemetry.addData("y:", robot.follower.getPose().getY());
        telemetry.addData("heading:", robot.follower.getPose().getHeading());
        telemetry.addData("tag fps", robot.tagCamera.getFPS());
        telemetry.update();

    }
}