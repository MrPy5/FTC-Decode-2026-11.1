package org.firstinspires.ftc.teamcode.config.subsystems.vision;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
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

public class TagCamera {
    public enum TagMode {
        MOTIF,
        GOAL
    }

    AprilTagProcessor aprilTag;
    VisionPortal visionPortal;
    HardwareMap hardwareMap;

    TagMode currentMode = TagMode.MOTIF;
    AprilTagDetection tag;

    CyclingList motifList = new CyclingList(15);

    public TagCamera(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        Position cameraPosition = new Position(DistanceUnit.INCH,
                0, 0, 0, 0);
        YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES,
                0, -73.3, 0, 0);
        aprilTag = new AprilTagProcessor.Builder()
                .setCameraPose(cameraPosition, cameraOrientation)
                .setTagLibrary(AprilTagGameDatabase.getDecodeTagLibrary())
                .setLensIntrinsics(902.235, 902.235, 633.041, 378.356)
                .setDrawTagID(ConfigConstants.DRAWING)
                .setDrawTagOutline(ConfigConstants.DRAWING)
                .setDrawAxes(ConfigConstants.DRAWING)
                .setDrawCubeProjection(ConfigConstants.DRAWING)

                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, ConfigConstants.ARDUCAM))
                .setCameraResolution(new Size(1280, 720))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .addProcessor(aprilTag)
                .enableLiveView(false)
                .setShowStatsOverlay(false)
                .setAutoStopLiveView(true)
                .build();
    }
    public void reinstall() {
        Position cameraPosition = new Position(DistanceUnit.INCH,
                0, 0, 0, 0);
        YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES,
                0, -73.3, 0, 0);
        aprilTag = new AprilTagProcessor.Builder()
                .setCameraPose(cameraPosition, cameraOrientation)
                .setTagLibrary(AprilTagGameDatabase.getDecodeTagLibrary())
                .setLensIntrinsics(902.235, 902.235, 633.041, 378.356)
                .setDrawTagID(ConfigConstants.DRAWING)
                .setDrawTagOutline(ConfigConstants.DRAWING)
                .setDrawAxes(ConfigConstants.DRAWING)
                .setDrawCubeProjection(ConfigConstants.DRAWING)

                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, ConfigConstants.ARDUCAM))
                .setCameraResolution(new Size(1280, 720))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .addProcessor(aprilTag)
                .enableLiveView(false)
                .setShowStatsOverlay(false)
                .setAutoStopLiveView(true)
                .build();
    }

    public VisionPortal getVisionPortal() {
        return visionPortal;
    }

    public double getFPS() {
        return visionPortal.getFps();
    }

    public VisionPortal.CameraState getState() {
        return visionPortal.getCameraState();
    }

    public TagMode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(TagMode currentMode) {
        this.currentMode = currentMode;
    }

    public List<AprilTagDetection> getDetections() {

        return aprilTag.getDetections();

    }
    public void processDetections(List<AprilTagDetection> currentDetections, Robot robot) {
        tag = null;
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if (getCurrentMode() == TagCamera.TagMode.GOAL) {
                    if (detection.id == ConfigConstants.GOAL_TAG_MAP.get(robot.getAlliance())) {
                        tag = detection;
                    }
                }
                else {
                    if (detection.id == 21 || detection.id == 22 || detection.id == 23) {
                        motifList.add(detection.id, robot.getMilliseconds());
                    }
                }
            }
        }
    }

    public int getMostPopularMotifTag() {
        if (motifList.getSize() == 0) {
            return ConfigConstants.DEFAULT_MOTIF_TAG_NUMBER;
        }
        else {
            return (int) motifList.mode();
        }
    }

    public boolean hasTag() {
        if (tag != null && tag.metadata != null) {
            return true;
        }
        else {
            return false;
        }
    }

    public double range() {
        if (hasTag()) {
            return tag.ftcPose.y;
        }
        else {
            return -1;
        }
    }

    public  double faceAngle() {
        if (hasTag()) {
            return tag.ftcPose.yaw;
        }
        else {
            return 0;
        }
    }

    public double degreesAway(double offset) {

        if (hasTag()) {
            return tag.ftcPose.bearing - offset;
        }
        else {
            return 0;
        }
    }
    public double combined() {
        return degreesAway(0) - faceAngle();
    }

    public AprilTagProcessor getAprilTag() {
        return aprilTag;
    }
}
