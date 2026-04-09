package org.firstinspires.ftc.teamcode.config;

import android.util.Log;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.lynx.LynxI2cDeviceSynch;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.subsystems.Ascent;
import org.firstinspires.ftc.teamcode.config.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.config.subsystems.CrTurret;
import org.firstinspires.ftc.teamcode.config.subsystems.Intake;
import org.firstinspires.ftc.teamcode.config.subsystems.Lindexer;
import org.firstinspires.ftc.teamcode.config.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.config.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.config.subsystems.Turret;
import org.firstinspires.ftc.teamcode.config.subsystems.field.Classifier;
import org.firstinspires.ftc.teamcode.config.subsystems.vision.LimelightCamera;
import org.firstinspires.ftc.teamcode.config.util.Alliance;
import org.firstinspires.ftc.teamcode.config.util.Motif;
import org.firstinspires.ftc.teamcode.config.util.OpMode;
import org.firstinspires.ftc.teamcode.config.util.scheduler.CommandScheduler;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.vision.VisionPortal;

import java.util.List;
import java.util.function.Consumer;

//http://192.168.43.1:8080/dash  -- ftc dash
//http://192.168.43.1:8001/ -- panels


public class Robot {

    public enum RobotState {
        INTAKE,
        SHOOT,
        PARK
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
    public Ascent ascent;
    public Turret turret;
    public Follower follower;


    public LimelightCamera limelightCamera;


    private Alliance alliance;
    private OpMode opMode;
    private Motif motif;

    public Commands commands;

    public Classifier classifier;

    private RobotState robotState;
    private ElapsedTime gameTimer = new ElapsedTime();
    public boolean usePinpoint = false;

    public Robot(HardwareMap hardwareMap, Gamepad g1, Gamepad g2, Telemetry telemetry) {
        this(hardwareMap, OpMode.TELEOP, null, null, null, g1, g2, telemetry, null);
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

        shooter = new Shooter(hardwareMap, this);
        intake = new Intake(hardwareMap);
        transfer = new Transfer(hardwareMap, this);
        lindexer = new Lindexer(hardwareMap, this);
        chassis = new Chassis(hardwareMap, this);
        ascent = new Ascent(hardwareMap);
        turret = new Turret(hardwareMap, this);
        limelightCamera = new LimelightCamera(hardwareMap);


        commands = new Commands(this);

        classifier = new Classifier();

        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        ((LynxI2cDeviceSynch) lindexer.getLindexerColor().getColorSensor().getDeviceClient()).setBusSpeed(LynxI2cDeviceSynch.BusSpeed.FAST_400K);

    }


    public void initAutoPositions() {

        shooter.unblock();
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


        }
    }
    public void initLoopTelemetry() {

        if (getMotif() != null) {
            telemetry.addData("motif", getMotif().toString());
        }

        telemetry.addData("Alliance: ", getAlliance());

        telemetry.addData("pinpoint", follower.getHeading());
        telemetry.addData("position", follower.getPose().getX());
        telemetry.addData("position", follower.getPose().getY());


    }
    public void startAuto(Consumer<Robot> buildPaths, Pose startPose) {

        follower.setStartingPose(startPose);

        buildPaths.accept(this);

        gameTimer.reset();
    }

    public void initTeleop() {
        breakFollowing();
        follower.update();
        turret.setAngle(0);

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

        if (c1.psWasPressed()) {
            follower.setPose(new Pose(77.5,71,0));
            follower.update();
        }
    }

    public void startTeleopPositions() {

        robotState = RobotState.INTAKE;
        scheduler.schedule(commands.startIntaking, getMilliseconds());
        transfer.unblock();
        ascent.descend();
        lindexer.leftCenter();
        turret.setAngle(0);
    }

    public void startTeleop() {
        gameTimer.reset();
        follower.startTeleopDrive(ConfigConstants.USE_BRAKE_MODE);
        startTeleopPositions();
        intake.reset(); // for turret reset
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
        shooter.update();
        lindexer.update();
        intake.update();
        follower.update();
        transfer.update();
        turret.update();

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
        packet.put("max", 4000);
        packet.put("min", 1000);
       // packet.put("bang", shooter.droppedActivateBangBang ? 3000 : 200);

        packet.put("left", shooter.getShooterMotorLeft().getRPM());
        packet.put("right", shooter.getShooterMotorRight().getRPM());

        packet.put("voltL", shooter.getShooterMotorLeft().getCurrent());
        packet.put("voltR", shooter.getShooterMotorRight().getCurrent());


      //  packet.put("hue", lindexer.getLindexerColor().getDistance());
       // packet.put("ball", intake.getArtifactSensor().getDistanceList().mode());
      //  packet.put("dist", lindexer.getLindexerColor().getDistance());
        //packet.put("hue", lindexer.getLindexerColor().getHue());
       // packet.put("hue", lindexer.getLindexerColor().getHue() > 240 ? 1 : 0);
        //packet.put("dist", lindexer.getLindexerColor().getDistance() < 5 ? 1 : 0);
        /*if (lindexer.getLindexerColor().distance < 3.4) {
            packet.put("dist", 1);
        }
        else {
           packet.put("dist", 0);
        }
        if (lindexer.getLindexerColor().hue > 180) {
            packet.put("hue", 1);
        }
        else {
            packet.put("hue", 0);
        }*/
       // packet.put("volt", transfer.getTransferMotor().getCurrent());

    }
    public void log(String tag, String message) {
        if (ConfigConstants.LOGGING) {
            Log.d(tag, message);
        }
    }

    public void stop() {
        Storage.cleanup(alliance, motif, follower);
    }









}