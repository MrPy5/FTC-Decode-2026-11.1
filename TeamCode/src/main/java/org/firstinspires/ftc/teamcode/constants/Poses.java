package org.firstinspires.ftc.teamcode.constants;

import com.pedropathing.geometry.Pose;

public class Poses {
    public static final Pose startPoseBlueFar = new Pose(1.2, 94.5, 0);
    public static final Pose startPoseRedFar = new Pose(1.2, 62.5, 0);
    public static final Pose startPoseBlueClose = new Pose(122.14, 120.7, (0.896));
    public static final Pose startPoseRedClose = new Pose(121, 34.5, Math.toRadians(309));

    public static final Pose detectPoseBlue = new Pose(80, 86.5, Math.toRadians(-15));
    public static final Pose detectPoseRed = new Pose(78, 65.5, Math.toRadians(15));

    public static final Pose shootPoseRedFar = new Pose(10.1, 64.5, Math.toRadians(-23));
    public static final Pose shootPoseBlueFar = new Pose(10.1, 92.5, Math.toRadians(22));
    public static final Pose shootPoseBlueClose = new Pose(80, 83.5, Math.toRadians(90));
    public static final Pose parkPoseBlueClose = new Pose(95, 90.5, Math.toRadians(0));
    public static final Pose parkPoseRedClose = new Pose(95, 66.5, Math.toRadians(0));
    public static final Pose shootPoseRedClose = new Pose(83.5, 65.5, Math.toRadians(312.5));

    public static final Pose endShootPoseBlue = new Pose(80, 81.05, 0.92);
    public static final Pose endShootPoseRed = new Pose(25, -2.5, Math.toRadians(-54));

    public static final Pose gateTurn1Red = new Pose(68, 28.5, Math.toRadians(0));
    public static final Pose gatePush1Red = new Pose(68, 21.5, Math.toRadians(0));

    public static final Pose gateTurn1Blue = new Pose(68, 124.5, Math.toRadians(0));
    public static final Pose gatePush1Blue = new Pose(68, 136.5, Math.toRadians(0));

    public static final Pose gateTurn2Red = new Pose(-2, -38.5, Math.toRadians(180));
    public static final Pose gatePush2Red = new Pose(-2, -50.5, Math.toRadians(180));

    public static final Pose gateTurn2Blue = new Pose(-2, 51.5, Math.toRadians(180));
    public static final Pose gatePush2Blue = new Pose(-2, 63.5, Math.toRadians(180));

    // close
    public static final Pose spike1PoseBlue = new Pose(79.2, 101.5, 1.56);
    public static final Pose spike1EndPoseBlue = new Pose(79.2, 128.5, 1.56);

    public static final Pose spike2PoseBlue = new Pose(56.5, 101.5, 1.56);
    public static final Pose spike2EndPoseBlue = new Pose(56.5, 135.5, 1.56);
    public static final Pose spike2BezierPoseBlue = new Pose(45, 86.5, Math.toRadians(90));
    public static final Pose spike2EndPoseFastBlue = new Pose(55, 26.5, Math.toRadians(-90));

    public static final Pose spike3PoseBlue = new Pose(31.5, 101.5, 1.56);
    public static final Pose spike3EndPoseBlue = new Pose(31.5, 135.5, 1.56);

    public static final Pose tunnelPoseBlue = new Pose(-22, 67.5, Math.toRadians(90));
    public static final Pose tunnelPoseRed = new Pose(-22, -54.5, Math.toRadians(90));

    public static final Pose spike1PoseRed = new Pose(79.2, 55.5, Math.toRadians(-90));
    public static final Pose spike1EndPoseRed = new Pose(79.2, 27.5, Math.toRadians(-90));

    public static final Pose spike2PoseRed = new Pose(55, 55.5, Math.toRadians(-90));
    public static final Pose spike2EndPoseRed = new Pose(55, 19.5, Math.toRadians(-90));
    public static final Pose spike2BezierPoseRed = new Pose(45, 70.5, Math.toRadians(-90));
    public static final Pose spike2EndPoseFastRed = new Pose(55, 35.5, Math.toRadians(-90));

    public static final Pose spike3PoseRed = new Pose(31, 55.5, Math.toRadians(-90));
    public static final Pose spike3EndPoseRed = new Pose(31, 19.5, Math.toRadians(-90));

    public static final Pose gatePushAngleBlue = new Pose(-11.5, 66.5, Math.toRadians(62));
    public static final Pose gatePushAngleBezierBlue = new Pose(-20, 16.5, Math.toRadians(62));

    public static final Pose gateBackupAngleBlue = new Pose(-11.5, 68.5, Math.toRadians(58));

    public static final Pose gatePushAngleRed = new Pose(55, 21.5, -1);
    public static final Pose gatePushAngleBezierRed = new Pose(53, 56.5, Math.toRadians(0));

    public static final Pose gateBackupAngleRed = new Pose(45, 19.5, -0.5);

    public static final Pose gatePoseSpikeBlue = new Pose(-17, 61.0, Math.toRadians(58));
    public static final Pose gatePoseSpikeRed = new Pose(63, 27.5, Math.toRadians(-90));

    // far
    public static final Pose spike2PoseBlueFar = new Pose(-16.5, 26.5, Math.toRadians(90));
    public static final Pose spike2EndPoseBlueFar = new Pose(-16.5, 56.5, Math.toRadians(90));

    public static final Pose spike3PoseBlueFar = new Pose(31, 101.5, 1.56);
    public static final Pose spike3EndPoseBlueFar = new Pose(31, 133.5, 1.56);

    public static final Pose spike2PoseRedFar = new Pose(-16, -13.5, Math.toRadians(-90));
    public static final Pose spike2EndPoseRedFar = new Pose(-16, -45.5, Math.toRadians(-90));

    public static final Pose spike3PoseRedFar = new Pose(29.5, 55.5, Math.toRadians(-90));
    public static final Pose spike3EndPoseRedFar = new Pose(29.5, 23.5, Math.toRadians(-90));

    public static final Pose parkPoseBlueFar = new Pose(30, 92.5, Math.toRadians(90));
    public static final Pose parkPoseRedFar = new Pose(30, 64.5, Math.toRadians(-90));

    public static final Pose hpPoseRed = new Pose(5, 20.5, Math.toRadians(-90));
    public static final Pose hpPoseBlue = new Pose(5, 136.5, Math.toRadians(90));

    public static final Pose hpBezierPoseRed = new Pose(0, 60.5, Math.toRadians(-90));
    public static final Pose hpBezierPoseBlue = new Pose(0, 96.5, Math.toRadians(90));

    public static final Pose backupHPPoseRed = new Pose(5, 25.5, Math.toRadians(-90));
    public static final Pose backupHPPoseBlue = new Pose(5, 131.5, Math.toRadians(90));

    public static final Pose gateOverFlowPoseBlueFar = new Pose(3, 136.5, Math.toRadians(35));
    public static final Pose gateOverFlowPoseRedFar = new Pose(3, 20.5, Math.toRadians(-35));

    public static final Pose gateOverFlowEndPoseBlueFar = new Pose(30, 136.5, Math.toRadians(35));
    public static final Pose gateOverFlowEndPoseRedFar = new Pose(30, 20.5, Math.toRadians(-35));

    // Test
    public static final Pose startPose = new Pose(0, 6.5, Math.toRadians(0));
    public static final Pose endPose = new Pose(10, 36.5, Math.toRadians(90));



}
