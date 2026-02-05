package org.firstinspires.ftc.teamcode.opmodes.auto.paths.far;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.constants.Poses;

public class FarPaths {

    public static PathChain shootPreload, driveToSpike1, spike1, spike1ToShoot, driveToSpike2, spike2, spike2ToShoot, driveToSpike3, spike4, driveToSpike4, spike4ToShoot, spike3, spike3ToShoot, parkPath, driveToHP, backupHP, returnHP, hpToShoot;

    public static Pose startPose = Poses.startPoseBlueFar;
    static Pose shootPose = Poses.shootPoseBlueFar;
    static Pose spike2Pose = Poses.spike2PoseBlueFar;
    static Pose spike2EndPose = Poses.spike2EndPoseBlueFar;
    static Pose spike3Pose = Poses.spike3PoseBlueFar;
    static Pose spike3EndPose = Poses.spike3EndPoseBlueFar;
    static Pose spike4Pose = Poses.spike3PoseBlueFar;
    static Pose spike4EndPose = Poses.spike3EndPoseBlueFar;
    static Pose parkPose = Poses.parkPoseBlueFar;
    static Pose hpPose = Poses.hpPoseBlue;
    static Pose hpBezierPose = Poses.hpBezierPoseBlue;
    static Pose backupHPPose = Poses.backupHPPoseBlue;


    public static void useBluePaths() {
        startPose = Poses.startPoseBlueFar;
        shootPose = Poses.shootPoseBlueFar;

        spike3Pose = Poses.spike3PoseBlueFar;
        spike3EndPose = Poses.spike3EndPoseBlueFar;

        spike2Pose = Poses.spike2PoseBlueFar;
        spike2EndPose = Poses.spike2EndPoseBlueFar;

        parkPose = Poses.parkPoseBlueFar;

        hpPose = Poses.hpPoseBlue;
        hpBezierPose = Poses.hpBezierPoseBlue;
        backupHPPose = Poses.backupHPPoseBlue;

        spike4Pose = Poses.spike4PoseBlueFar;
        spike4EndPose = Poses.spike4EndPoseBlueFar;
    }

    public static void useRedPaths() {
        startPose = Poses.startPoseRedFar;
        shootPose = Poses.shootPoseRedFar;

        spike3Pose = Poses.spike3PoseRedFar;
        spike3EndPose = Poses.spike3EndPoseRedFar;

        spike2Pose = Poses.spike2PoseRedFar;
        spike2EndPose = Poses.spike2EndPoseRedFar;

        parkPose = Poses.parkPoseRedFar;

        hpPose = Poses.hpPoseRed;
        hpBezierPose = Poses.hpBezierPoseRed;
        backupHPPose = Poses.backupHPPoseRed;

        spike4Pose = Poses.spike4PoseRedFar;
        spike4EndPose = Poses.spike4EndPoseRedFar;
    }

    public static void buildPaths(Robot robot) {

        shootPreload = robot.follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setConstantHeadingInterpolation(shootPose.getHeading())
                .build();

        driveToSpike2 = robot.follower.pathBuilder()
                .addPath(new BezierLine(shootPose, spike2Pose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), spike2Pose.getHeading())
                .build();

        spike2 = robot.follower.pathBuilder()
                .addPath(new BezierLine(spike2Pose, spike2EndPose))
                .setLinearHeadingInterpolation(spike2Pose.getHeading(), spike2EndPose.getHeading())
                .build();

        spike2ToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(spike2EndPose, shootPose))
                .setLinearHeadingInterpolation(spike2EndPose.getHeading(), shootPose.getHeading())
                .build();
        driveToSpike3 = robot.follower.pathBuilder()
                .addPath(new BezierLine(shootPose, spike3Pose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), spike2Pose.getHeading())
                .build();

        spike3 = robot.follower.pathBuilder()
                .addPath(new BezierLine(spike3Pose, spike3EndPose))
                .setLinearHeadingInterpolation(spike2Pose.getHeading(), spike3EndPose.getHeading())
                .build();

        spike3ToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(spike3EndPose, shootPose))
                .setConstantHeadingInterpolation(shootPose.getHeading())
                .build();

        parkPath = robot.follower.pathBuilder()
                .addPath(new BezierLine(shootPose, parkPose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), parkPose.getHeading())
                .build();

        driveToHP = robot.follower.pathBuilder()
                .addPath(new BezierCurve(shootPose, hpBezierPose, hpPose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), hpPose.getHeading(), 0.3)
                .build();

        backupHP = robot.follower.pathBuilder()
                .addPath(new BezierLine(hpPose, backupHPPose))
                .setLinearHeadingInterpolation(hpPose.getHeading(), backupHPPose.getHeading())
                .build();

        returnHP = robot.follower.pathBuilder()
                .addPath(new BezierLine(backupHPPose, hpPose))
                .setLinearHeadingInterpolation(backupHPPose.getHeading(), hpPose.getHeading())
                .build();
        hpToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(hpPose, shootPose))
                .setLinearHeadingInterpolation(hpPose.getHeading(), shootPose.getHeading(), 0.5)
                .build();

        driveToSpike4 = robot.follower.pathBuilder()
                .addPath(new BezierLine(shootPose, spike3Pose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), spike4Pose.getHeading())
                .build();

        spike4 = robot.follower.pathBuilder()
                .addPath(new BezierLine(spike4Pose, spike4EndPose))
                .setLinearHeadingInterpolation(spike4Pose.getHeading(), spike4EndPose.getHeading())
                .build();

        spike4ToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(spike4EndPose, shootPose))
                .setConstantHeadingInterpolation(shootPose.getHeading())
                .build();

    }
}