package org.firstinspires.ftc.teamcode.opmodes.teleop;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.config.Robot;

@TeleOp(name = "ON FIELD")
public class OnField extends OpMode {

    Robot robot;
    @Override
    public void init() {
        robot = new Robot(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    @Override
    public void init_loop() {

        robot.follower.update();

    }

    @Override
    public void start() {
        robot.follower.update();
        robot.follower.setPose(new Pose(77.5,71,0));
        //robot.follower.setPose(new Pose(8.5,54,Math.toRadians(-90)));
    }

    @Override
    public void loop() {

        robot.follower.update();

        telemetry.addData("x:", robot.follower.getPose().getX());
        telemetry.addData("y:", robot.follower.getPose().getY());
        telemetry.addData("heading:", robot.follower.getPose().getHeading());
        telemetry.update();

    }
}