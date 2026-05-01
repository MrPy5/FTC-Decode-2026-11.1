package org.firstinspires.ftc.teamcode.config.subsystems.vision;

import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.util.Alliance;
import org.firstinspires.ftc.teamcode.config.util.CyclingList;
import org.firstinspires.ftc.teamcode.config.util.Motif;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;
import org.firstinspires.ftc.teamcode.opmodes.auto.paths.far.FarPaths;
import org.firstinspires.ftc.teamcode.opmodes.testing.BallTest;

import java.util.List;

public class LimelightCamera {
    public enum TagMode {
        MOTIF,
        BALL,
        OFF
    }

    Limelight3A limelight;
    Robot robot;
    HardwareMap hardwareMap;
    public Pose ballPose = new Pose(0,0);

    public CyclingList xPos = new CyclingList(20);
    public CyclingList yPos = new CyclingList(20);
    public CyclingList headingPos = new CyclingList(20);

    public CyclingList ball = new CyclingList(5);
    public CyclingList left = new CyclingList(5);
    public CyclingList right = new CyclingList(5);


    TagMode currentMode = TagMode.MOTIF;

    CyclingList motifList = new CyclingList(15);

    public LimelightCamera(HardwareMap hardwareMap, Robot robot) {
        this.hardwareMap = hardwareMap;
        this.robot = robot;

        limelight = hardwareMap.get(Limelight3A.class, ConfigConstants.LIMELIGHT);


        limelight.pipelineSwitch(0);

        limelight.start();
    }



    public double getFPS() {
        return limelight.getStatus().getFps();
    }

    public TagMode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(TagMode currentMode) {
        this.currentMode = currentMode;
    }

    public LLResult getResult() {
        return limelight.getLatestResult();
    }

    public double[] getPython() {
        return limelight.getLatestResult().getPythonOutput();
    }



    public List<LLResultTypes.FiducialResult> getDetections() {

        LLResult result = getResult();

        if (result.isValid()) {
            // Access fiducial results
            return result.getFiducialResults();
        }
        else {
            return null;
        }


    }
    public Pose3D getBotPose() {

        LLResult result = getResult();


        return result.getBotpose();
    }
    public int getTagCount() {
        LLResult result = getResult();

        return result.getBotposeTagCount();
    }

    public Pose getPedroPose() {
        double rawX = getBotPose().getPosition().x;
        double rawY =  getBotPose().getPosition().y;

        double pedroX = (rawY / DistanceUnit.mPerInch);
        double pedroY = (rawX / DistanceUnit.mPerInch);


        Pose asPedro = FTCCoordinates.INSTANCE.convertToPedro(
                new Pose(pedroX, pedroY, getBotPose().getOrientation().getYaw(AngleUnit.RADIANS), PedroCoordinates.INSTANCE));
        asPedro = new Pose(142 - asPedro.getX(), asPedro.getY());
        return new Pose(asPedro.getX(),asPedro.getY(), getBotPose().getOrientation().getYaw(AngleUnit.RADIANS));

    }
    public int getMostPopularMotifTag() {
        if (motifList.getSize() == 0) {
            return ConfigConstants.DEFAULT_MOTIF_TAG_NUMBER;
        }
        else {
            return (int) motifList.mode();
        }
    }

    public void update() {
        if (currentMode == TagMode.MOTIF) {
            List<LLResultTypes.FiducialResult> detections = getDetections();
            if (detections != null) {
                motifList.add(detections.get(0).getFiducialId(), 0);
            }
        }
        else if (currentMode == TagMode.BALL) {
            double[] llpython = robot.limelightCamera.getPython();
            ball.add(llpython[0], 0);
            left.add(llpython[3], 0);
            right.add(llpython[2], 0);
        }

    }

    public Motif getMotif() {
        if (motifList.getSize() != 0) {
            double mode = motifList.mode();
            Motif motif = ConfigConstants.MOTIF_TAG_MAP.get((int) mode);
            if (motif != null) {
                return motif;
            }
            else {
                return Motif.GPP;
            }

        }
        else {
            return Motif.GPP;
        }
    }

    public double getSize() {
        return motifList.getSize();
    }
    public double getMode() {
        return motifList.mode();
    }

    public Limelight3A getLimelight() {
        return limelight;
    }

    public void recordPosition() {
        Pose botPose = getPedroPose();

        xPos.add(botPose.getX(), 0);
        yPos.add(botPose.getY(), 0);
        headingPos.add(botPose.getHeading(), 0);
    }

    public Pose avgPose() {
        return new Pose(xPos.average(), yPos.average(), robot.follower.getHeading());
    }
    public double length() {
        return xPos.getSize();
    }

    public void switchToBallDetection() {

        limelight.pipelineSwitch(1);
    }
    public void switchToTagDetection() {
        limelight.pipelineSwitch(0);
    }

    public PathChain getBallPath() {
        double angle = robot.getAlliance() == Alliance.RED ? right.average() : left.average();
        double x = robot.follower.getPose().getX() + ((Math.sin(Math.toRadians(angle)) * 45) * (robot.getAlliance() == Alliance.RED ? -1 : 1));
        x = Math.max(x, 9.5);
        x = Math.min(x, 32.85);
        ballPose = new Pose(x, robot.getAlliance() == Alliance.RED ? 12 : 131);
        if (ball.mode() == 1) {
            if (x == 9.5) {
                PathChain pathChain = robot.follower.pathBuilder()
                        .addPath(new BezierCurve(robot.follower.getPose(), new Pose(8, robot.getAlliance() == Alliance.RED ? 40 : 103), ballPose))
                        .setConstantHeadingInterpolation(robot.getAlliance() == Alliance.RED ? -1.56 : 1.56)
                        .build();
                return pathChain;
            }
            else {
                PathChain pathChain = robot.follower.pathBuilder()
                        .addPath(new BezierLine(robot.follower.getPose(), ballPose))
                        .setConstantHeadingInterpolation(robot.getAlliance() == Alliance.RED ? -1.56 : 1.56)
                        .build();
                return pathChain;
            }
        }
        else {
            return FarPaths.driveToGateOverflow;
        }

    }
}
