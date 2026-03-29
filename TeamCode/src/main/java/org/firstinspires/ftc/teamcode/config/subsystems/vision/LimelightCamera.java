package org.firstinspires.ftc.teamcode.config.subsystems.vision;

import android.util.Size;

import com.pedropathing.geometry.Pose;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.util.Alliance;
import org.firstinspires.ftc.teamcode.config.util.CyclingList;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class LimelightCamera {
    public enum TagMode {
        MOTIF,
        GOAL
    }

    Limelight3A limelight;
    HardwareMap hardwareMap;

    TagMode currentMode = TagMode.MOTIF;

    CyclingList motifList = new CyclingList(15);

    public LimelightCamera(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        limelight = hardwareMap.get(Limelight3A.class, "limelight");


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

    public Pose getPedroPose() {
        LLResult result = getResult();
        return new Pose(getBotPose().getPosition().x * 39.701 + 72,getBotPose().getPosition().y * -39.701 + 72,getBotPose().getOrientation().getYaw(AngleUnit.RADIANS));

    }
    public int getMostPopularMotifTag() {
        if (motifList.getSize() == 0) {
            return ConfigConstants.DEFAULT_MOTIF_TAG_NUMBER;
        }
        else {
            return (int) motifList.mode();
        }
    }

}
