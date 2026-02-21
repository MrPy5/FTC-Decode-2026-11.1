package org.firstinspires.ftc.teamcode.opmodes.auto.paths.close;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.constants.Poses;

public class ClosePaths {



    public static PathChain spike2Reverse, gateTurnToGatePush2, gateTurnToGatePush1, gatePushToShoot1, gatePushToShoot2, tunnelToShoot, shootToTunnel, spike2ToGateTurn, shootToGate,scanMotif, straightShootPreload, shootPreload, driveToSpike1, spike1, spike1ToShoot, driveToSpike2, spike2, spike2ToShoot, driveToSpike3, spike3, spike3ToShoot, parkPath, spike1ToGateTurn, gateTurnToGatePush, gatePushToShoot, driveToGate, backupFronGate, gateAngleToShoot;

    public static Pose startPose = Poses.startPoseBlueClose;
    static Pose shootPose = Poses.shootPoseBlueClose;
    static Pose spike2Pose = Poses.spike2PoseBlue;
    static Pose spike2EndPose = Poses.spike2EndPoseBlue;
    static Pose spike2BezierPose = Poses.spike2BezierPoseBlue;
    static Pose spike1Pose = Poses.spike1PoseBlue;
    static Pose spike1EndPose = Poses.spike1EndPoseBlue;
    static Pose spike3Pose = Poses.spike3PoseBlue;
    static Pose spike3EndPose = Poses.spike3EndPoseBlue;
    static Pose parkPose = Poses.parkPoseBlueClose;
    static Pose detectPose = Poses.detectPoseBlue;
    static Pose gateTurn1Pose = Poses.gateTurn1Blue;
    static Pose gatePush1Pose = Poses.gatePush1Blue;
    static Pose gateTurn2Pose = Poses.gateTurn2Blue;
    static Pose gatePush2Pose = Poses.gatePush2Blue;
    static Pose endShootPose = Poses.endShootPoseBlue;
    static Pose gatePushAngle = Poses.gatePushAngleBlue;
    static Pose gatePushAngleBezier = Poses.gatePushAngleBezierBlue;
    static Pose gateBackupAngle = Poses.gateBackupAngleBlue;
    static Pose tunnelPose = Poses.tunnelPoseBlue;

    public static boolean pathsBuilt = false;
    public static void useBluePaths() {
        startPose = Poses.startPoseBlueClose;
        shootPose = Poses.shootPoseBlueClose;
        spike2Pose = Poses.spike2PoseBlue;
        spike2EndPose = Poses.spike2EndPoseBlue;
        spike2BezierPose = Poses.spike2BezierPoseBlue;
        spike1Pose = Poses.spike1PoseBlue;
        spike1EndPose = Poses.spike1EndPoseBlue;
        spike3Pose = Poses.spike3PoseBlue;
        spike3EndPose = Poses.spike3EndPoseBlue;
        parkPose = Poses.parkPoseBlueClose;
        detectPose = Poses.detectPoseBlue;
        gateTurn1Pose = Poses.gateTurn1Blue;
        gatePush1Pose = Poses.gatePush1Blue;
        gateTurn2Pose = Poses.gateTurn2Blue;
        gatePush2Pose = Poses.gatePush2Blue;
        endShootPose = Poses.endShootPoseBlue;

        gatePushAngle = Poses.gatePushAngleBlue;
        gatePushAngleBezier = Poses.gatePushAngleBezierBlue;
        gateBackupAngle = Poses.gateBackupAngleBlue;
        tunnelPose = Poses.tunnelPoseBlue;

    }

    public static void useRedPaths() {
        startPose = Poses.startPoseRedClose;
        shootPose = Poses.shootPoseRedClose;
        spike2Pose = Poses.spike2PoseRed;
        spike2EndPose = Poses.spike2EndPoseRed;
        spike2BezierPose = Poses.spike2BezierPoseRed;
        spike1Pose = Poses.spike1PoseRed;
        spike1EndPose = Poses.spike1EndPoseRed;
        spike3Pose = Poses.spike3PoseRed;
        spike3EndPose = Poses.spike3EndPoseRed;
        parkPose = Poses.parkPoseRedClose;
        detectPose = Poses.detectPoseRed;
        gateTurn1Pose = Poses.gateTurn1Red;
        gatePush1Pose = Poses.gatePush1Red;
        gateTurn2Pose = Poses.gateTurn2Red;
        gatePush2Pose = Poses.gatePush2Red;
        endShootPose = Poses.endShootPoseRed;

        gatePushAngle = Poses.gatePushAngleRed;
        gatePushAngleBezier = Poses.gatePushAngleBezierRed;
        gateBackupAngle = Poses.gateBackupAngleRed;
        tunnelPose = Poses.tunnelPoseRed;

    }
    public static void buildPaths(Robot robot) {
        pathsBuilt = true;

        shootPreload = robot.follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setConstantHeadingInterpolation(startPose.getHeading())
                .build();
        driveToSpike1 = robot.follower.pathBuilder()
                .addPath(new BezierLine(shootPose, spike1Pose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), spike1Pose.getHeading())
                .setTValueConstraint(0.8)
                .build();

        spike1 = robot.follower.pathBuilder()
                .addPath(new BezierLine(spike1Pose, spike1EndPose))
                .setLinearHeadingInterpolation(spike1Pose.getHeading(), spike1EndPose.getHeading())
                .build();
        spike1ToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(spike1EndPose, shootPose))
                .setConstantHeadingInterpolation(shootPose.getHeading())
                .build();

        driveToSpike2 = robot.follower.pathBuilder()
                .addPath(new BezierLine(shootPose, spike2Pose))
                .setConstantHeadingInterpolation(spike2Pose.getHeading())
               /* .setHeadingInterpolation(HeadingInterpolator.piecewise(
                        new HeadingInterpolator.PiecewiseNode(0, 0.5, HeadingInterpolator.tangent),
                        new HeadingInterpolator.PiecewiseNode(0.5, 1, HeadingInterpolator.constant(spike2Pose.getHeading()))
                ))
                              */
                .setTValueConstraint(0.8)
                .build();

        spike2 = robot.follower.pathBuilder()
                .addPath(new BezierLine(spike2Pose, spike2EndPose))
                .setLinearHeadingInterpolation(spike2Pose.getHeading(), spike2EndPose.getHeading())
                .build();
        spike2Reverse = robot.follower.pathBuilder()
                .addPath(new BezierLine(spike2EndPose, spike2Pose))
                .setLinearHeadingInterpolation(spike2EndPose.getHeading(), shootPose.getHeading())
                .setTValueConstraint(0.7)
                .build();

        spike2ToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(spike2Pose, shootPose))
                .setConstantHeadingInterpolation(shootPose.getHeading())
                .build();

        driveToSpike3 = robot.follower.pathBuilder()
                .addPath(new BezierLine(shootPose, spike3Pose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), spike3Pose.getHeading())
                .setTValueConstraint(0.8)
                .build();

        spike3 = robot.follower.pathBuilder()
                .addPath(new BezierLine(spike3Pose, spike3EndPose))
                .setLinearHeadingInterpolation(spike3Pose.getHeading(), spike3EndPose.getHeading())
                .build();

        spike3ToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(spike3EndPose, shootPose))
                .setConstantHeadingInterpolation(shootPose.getHeading())
                .build();

        spike1ToGateTurn = robot.follower.pathBuilder()
                .addPath(new BezierLine(spike1EndPose, gateTurn1Pose))
                .setLinearHeadingInterpolation(spike1EndPose.getHeading(), gateTurn1Pose.getHeading())
                .setTValueConstraint(0.8)
                .build();
        gateTurnToGatePush1 = robot.follower.pathBuilder()
                .addPath(new BezierLine(gateTurn1Pose, gatePush1Pose))
                .setLinearHeadingInterpolation(gateTurn1Pose.getHeading(), gatePush1Pose.getHeading())
                .build();
        gatePushToShoot1 = robot.follower.pathBuilder()
                .addPath(new BezierLine(gatePush1Pose, shootPose))
                .setConstantHeadingInterpolation(shootPose.getHeading())
                .build();
        scanMotif = robot.follower.pathBuilder()
                .addPath(new BezierLine(shootPose, detectPose))
                .setConstantHeadingInterpolation(detectPose.getHeading())
                .build();
        parkPath = robot.follower.pathBuilder()
                .addPath(new BezierLine(shootPose, parkPose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), parkPose.getHeading())
                .build();

        //--------//



        straightShootPreload = robot.follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setConstantHeadingInterpolation(shootPose.getHeading())
                .build();










        spike2ToGateTurn = robot.follower.pathBuilder()
                .addPath(new BezierLine(spike2EndPose, gateTurn2Pose))
                .setLinearHeadingInterpolation(spike2EndPose.getHeading(), gateTurn2Pose.getHeading())
                .build();

        gateTurnToGatePush2 = robot.follower.pathBuilder()
                .addPath(new BezierLine(gateTurn2Pose, gatePush2Pose))
                .setLinearHeadingInterpolation(gateTurn2Pose.getHeading(), gatePush2Pose.getHeading())
                .build();
        gatePushToShoot2 = robot.follower.pathBuilder()
                .addPath(new BezierLine(gatePush2Pose, shootPose))
                .setConstantHeadingInterpolation(shootPose.getHeading())
                .build();
        driveToGate = robot.follower.pathBuilder()
                .addPath(new BezierCurve(shootPose, gatePushAngleBezier, gatePushAngle))
                .setConstantHeadingInterpolation(gatePushAngle.getHeading())
                .build();
        backupFronGate = robot.follower.pathBuilder()
                .addPath(new BezierLine(gatePushAngle, gateBackupAngle))
                .setConstantHeadingInterpolation(gateBackupAngle.getHeading())
                .build();
        gateAngleToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(gateBackupAngle, shootPose))
                .setConstantHeadingInterpolation(shootPose.getHeading())
                .build();
        shootToGate = robot.follower.pathBuilder()
                .addPath(new BezierLine(shootPose, gateTurn1Pose))
                .setConstantHeadingInterpolation(gateTurn1Pose.getHeading())
                .build();
        shootToTunnel = robot.follower.pathBuilder()
                .addPath(new BezierLine(shootPose, tunnelPose))
                .setConstantHeadingInterpolation(tunnelPose.getHeading())
                .build();
        tunnelToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(tunnelPose, shootPose))
                .setConstantHeadingInterpolation(shootPose.getHeading())
                .build();

    }

}
