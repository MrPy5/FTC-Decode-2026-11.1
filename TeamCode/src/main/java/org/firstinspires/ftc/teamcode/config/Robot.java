package org.firstinspires.ftc.teamcode.config;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.config.subsystems.Intake;
import org.firstinspires.ftc.teamcode.config.subsystems.Lindexer;
import org.firstinspires.ftc.teamcode.config.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.config.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.config.subsystems.field.Classifier;
import org.firstinspires.ftc.teamcode.config.subsystems.vision.TagCamera;
import org.firstinspires.ftc.teamcode.config.util.Alliance;
import org.firstinspires.ftc.teamcode.config.util.Motif;
import org.firstinspires.ftc.teamcode.config.util.OpMode;
import org.firstinspires.ftc.teamcode.config.util.scheduler.CommandScheduler;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.List;
import java.util.function.Consumer;

//http://192.168.43.1:8080/dash  -- ftc dash
//http://192.168.43.1:8001/ -- panels


public class Robot {

    public enum RobotState {
        INTAKE,
        SHOOT
    }

    public CommandScheduler scheduler = new CommandScheduler();

    private Gamepad g1;
    private Gamepad g2;

    private Gamepad c1 = new Gamepad(); //take snapshots of the controllers
    private Gamepad c2 = new Gamepad();

    private Telemetry telemetry;
    private FtcDashboard dashboard;


    public Shooter shooter;
    public Lindexer lindexer;
    public Transfer transfer;
    public Intake intake;
    public Chassis chassis;
    public Follower follower;

    public TagCamera tagCamera;


    private Alliance alliance;
    private OpMode opMode;
    private Motif motif;

    public Commands commands;

    public Classifier classifier;

    private RobotState robotState;
    private ElapsedTime gameTimer = new ElapsedTime();

    public Robot(HardwareMap hardwareMap, Gamepad g1, Gamepad g2) {
        this(hardwareMap, OpMode.TELEOP, null, null, null, g1, g2, null, null);
    }
    public Robot(HardwareMap hardwareMap, Gamepad g1, Gamepad g2, Follower follower) {
        this(hardwareMap, OpMode.TELEOP, null, null, follower, g1, g2, null, null);
    }
    public Robot(HardwareMap hardwareMap, OpMode opMode, Alliance alliance, Motif motif, Follower follower, Gamepad g1, Gamepad g2, Telemetry telemetry, FtcDashboard dashboard) {

        this.opMode = opMode;

        //Alliance
        if (alliance == null) {
            alliance = ConfigConstants.DEFAULT_ALLIANCE;
        }
        this.alliance = alliance;

        //Motif
        if (motif == null) {
            motif = ConfigConstants.DEFAULT_MOTIF;
        }
        this.motif = motif;

        //Follower
        if (follower == null) {
            follower = Constants.createFollower(hardwareMap);
        }

        this.follower = follower;

        this.g1 = g1;
        this.g2 = g2;
        this.telemetry = telemetry;
        this.dashboard = dashboard;

        shooter = new Shooter(hardwareMap);
        intake = new Intake(hardwareMap);
        transfer = new Transfer(hardwareMap);
        lindexer = new Lindexer(hardwareMap);
        chassis = new Chassis(hardwareMap);

        tagCamera = new TagCamera(hardwareMap);

        commands = new Commands(this);

        classifier = new Classifier();

        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
    }

    public void initAutoPositions() {

        shooter.unblock();
        tagCamera.setCurrentMode(TagCamera.TagMode.MOTIF);
    }
    public void initLoopAuto(Runnable useBluePaths, Runnable useRedPaths, boolean scanMotif) {
        updateGamepads();

        if (c1.triangleWasPressed()) {
            setAlliance(Alliance.BLUE);
            useBluePaths.run();
        }
        if (c1.crossWasPressed()) {
            setAlliance(Alliance.RED);
            useRedPaths.run();
        }

        if (scanMotif) {
           tagCamera.processDetections(tagCamera.getDetections(), this);

           setMotifByTag(tagCamera.getMostPopularMotifTag());
        }
    }
    public void initLoopTelemetry() {
        if (getMotif() != null) {
            telemetry.addData("motif", getMotif().toString());
        }

        telemetry.addData("Alliance: ", getAlliance());

        telemetry.addData("Tag FPS: ", tagCamera.getFPS());


    }
    public void startAuto(Consumer<Robot> buildPaths, Pose startPose) {
       tagCamera.setCurrentMode(TagCamera.TagMode.MOTIF);
        follower.setStartingPose(startPose);

        buildPaths.accept(this);

        gameTimer.reset();
    }

    public void initTeleop() {
        breakFollowing();
        follower.update();

    }
    public void breakFollowing() {
        follower.breakFollowing();
        follower.setMaxPower(1);
    }
    public void initLoopTeleop() {
        updateGamepads();

        if (c1.triangleWasPressed()) {
            setAlliance(Alliance.BLUE);
        }
        if (c1.crossWasPressed()) {
            setAlliance(Alliance.RED);
        }

        if (c1.dpadUpWasPressed()) {
            setMotif(Motif.PPG);
        }
        if (c1.dpadLeftWasPressed()) {
            setMotif(Motif.PGP);
        }
        if (c1.dpadDownWasPressed()) {
            setMotif(Motif.GPP);
        }
    }

    public void startTeleopPositions() {

        robotState = RobotState.INTAKE;
        scheduler.schedule(commands.startIntaking, getMilliseconds());
    }

    public void startTeleop() {
        gameTimer.reset();
        follower.startTeleopDrive(ConfigConstants.USE_BRAKE_MODE);
        tagCamera.setCurrentMode(TagCamera.TagMode.GOAL);

        startTeleopPositions();
    }

    public void updateGamepads() {
        c1.copy(g1);
        c2.copy(g2);
    }

    public Gamepad getC1() {
        return c1;
    }

    public Gamepad getC2() {
        return c2;
    }

    public void update() {
        shooter.update(this);
        lindexer.update(this);
        follower.update();
        transfer.update(getRobotState());
        tagCamera.processDetections(tagCamera.getDetections(), this);

        scheduler.update(getMilliseconds());
    }

    public void updateHardware() {
        shooter.updatePower();
        intake.updatePower();
        transfer.updatePower();
    }


    //Debug
    public void doDashboard() {
        if (ConfigConstants.DASHBOARD) {
            if (dashboard == null) return;
            TelemetryPacket packet = new TelemetryPacket();
            doDashboard(packet);
            dashboard.sendTelemetryPacket(packet);
        }
    }

    public void setAlliance(Alliance alliance) {
        this.alliance = alliance;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public void setMotif(Motif motif) {
        this.motif = motif;
    }
    public void setMotifByTag(int tag) {
        this.motif = ConfigConstants.MOTIF_TAG_MAP.get(tag);
    }
    public Motif getMotif() {
        return motif;
    }

    public Follower getFollower() {
        return follower;
    }
    public RobotState getRobotState() {
        return robotState;
    }

    public void setRobotState(RobotState robotState) {
        this.robotState = robotState;
    }
    public void setDashboard(FtcDashboard dashboard) {
        this.dashboard = dashboard;
    }
    public double getMilliseconds() {
        return gameTimer.milliseconds();
    }

    public void doDashboard(TelemetryPacket packet) {
        packet.put("currentRPM", shooter.getShooterMotor().getRPM());
        packet.put("targetRPM", shooter.getTargetShooterRPM());
        packet.put("max", 6000);
        packet.put("min", 0);
        if (shooter.droppedActivateBangBang) {
            packet.put("bang", 4000);
        }
        else {
            packet.put("bang", 0);
        }

     /*   packet.put("dist", lindexer.getLindexerColor().getDistance());
        packet.put("hue", lindexer.getLindexerColor().getHue());*/
       // packet.put("volt", transfer.getTransferMotor().getCurrent());

    }



}