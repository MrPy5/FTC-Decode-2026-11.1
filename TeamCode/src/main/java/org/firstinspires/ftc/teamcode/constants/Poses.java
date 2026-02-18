package org.firstinspires.ftc.teamcode.constants;

import com.pedropathing.geometry.Pose;

public class Poses {
    public static final Pose startPoseBlueFar = new Pose(-67, 14.5, 0); // Start Pose of our robot.
    public static final Pose startPoseRedFar = new Pose(-66.6, -15.7, 0); // Start Pose of our robot.
    public static final Pose startPoseBlueClose = new Pose(122.14, 114.2, (0.896)); // Start Pose of our robot.
    public static final Pose startPoseRedClose = new Pose(39.5, -54, Math.toRadians(0)); // Start Pose of our robot.

    public static final Pose detectPoseBlue = new Pose(80, 80, Math.toRadians(-15)); // Start Pose of our robot.
    public static final Pose detectPoseRed = new Pose(23, -25.6, Math.toRadians(23)); // Start Pose of our robot.

    public static final Pose shootPoseRedFar = new Pose(-60, -16, Math.toRadians(-20)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose shootPoseBlueFar = new Pose(-60, 16, Math.toRadians(20)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose shootPoseBlueClose = new Pose(80, 77, 0.9);
    public static final Pose parkPoseBlueClose = new Pose(95, 83, Math.toRadians(0)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose shootPoseRedClose = new Pose(16, -15.5, Math.toRadians(-46));

    public static final Pose endShootPoseBlue = new Pose(80, 74.55, 0.92);
    public static final Pose endShootPoseRed = new Pose(25, -9, Math.toRadians(-54));


    public static final Pose gateTurn1Red = new Pose(-2, -45, Math.toRadians(0));
    public static final Pose gatePush1Red = new Pose(-2, -57, Math.toRadians(0));

    public static final Pose gateTurn1Blue = new Pose(69, 118, Math.toRadians(0));
    public static final Pose gatePush1Blue = new Pose(69, 127, Math.toRadians(0));

    public static final Pose gateTurn2Red = new Pose(-2, -45, Math.toRadians(180));
    public static final Pose gatePush2Red = new Pose(-2, -57, Math.toRadians(180));

    public static final Pose gateTurn2Blue = new Pose(-2, 45, Math.toRadians(180));
    public static final Pose gatePush2Blue = new Pose(-2, 57, Math.toRadians(180));

    //close
    public static final Pose spike1PoseBlue = new Pose(78.78, 95, 1.56); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike1EndPoseBlue = new Pose(78.78, 120, 1.56); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose spike2PoseBlue = new Pose(55, 95, 1.56); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike2EndPoseBlue = new Pose(55, 127, 1.56); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike2BezierPoseBlue = new Pose(-25, 0, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose spike3PoseBlue = new Pose(31, 95, 1.56); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike3EndPoseBlue = new Pose(31, 127, 1.56); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose tunnelPoseBlue = new Pose(-22, 61, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose tunnelPoseRed = new Pose(-22, -61, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.


    public static final Pose spike1PoseRed = new Pose(9.5, -20, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike1EndPoseRed = new Pose(9.5, -52, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose spike2PoseRed = new Pose(-14.5, -23, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike2EndPoseRed = new Pose(-14.5, -52, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike2BezierPoseRed = new Pose(-25, 0, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose spike3PoseRed = new Pose(-39, -23, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike3EndPoseRed = new Pose(-39, -52, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose gatePushAngleBlue = new Pose(-11.5, 60, Math.toRadians(62)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose gatePushAngleBezierBlue = new Pose(-20, 10, Math.toRadians(62)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose gateBackupAngleBlue = new Pose(-11.5, 62, Math.toRadians(58)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose gatePushAngleRed = new Pose(-16, 55, Math.toRadians(62)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose gatePushAngleBezierRed = new Pose(-30, 25, Math.toRadians(62)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose gateBackupAngleRed = new Pose(-17, 54.5, Math.toRadians(58)); // Highest (First Set) of Artifacts from the Spike Mark.


    //far
    public static final Pose spike2PoseBlueFar = new Pose(-16.5, 20, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike2EndPoseBlueFar = new Pose(-16.5, 50, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose spike3PoseBlueFar = new Pose(-40, 20, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike3EndPoseBlueFar = new Pose(-40, 50, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose spike2PoseRedFar = new Pose(-16, -20, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike2EndPoseRedFar = new Pose(-16, -52, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose spike3PoseRedFar = new Pose(-40, -20, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike3EndPoseRedFar = new Pose(-40, -51, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose parkPoseBlueFar = new Pose(-52, 22, Math.toRadians(90)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose parkPoseRedFar = new Pose(-50, -23, Math.toRadians(-90)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose parkPoseRedClose = new Pose(30, -12, Math.toRadians(-54)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.

    public static final Pose hpPoseRed = new Pose(-66.8, -60, Math.toRadians(-90)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose hpPoseBlue = new Pose(-66.5, 59.5, Math.toRadians(90)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.

    public static final Pose hpBezierPoseRed = new Pose(-70, -10, Math.toRadians(-90)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose hpBezierPoseBlue = new Pose(-70, 10, Math.toRadians(90)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.

    public static final Pose backupHPPoseRed = new Pose(-65.5, -57.5, Math.toRadians(-100)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose backupHPPoseBlue = new Pose(-66, 58.5, Math.toRadians(100)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.

    public static final Pose spike4PoseBlueFar = new Pose(-40, 20, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike4EndPoseBlueFar = new Pose(-40, 50, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose spike4PoseRedFar = new Pose(-40, -20, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike4EndPoseRedFar = new Pose(-40, -65, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.

    //Test
    public static final Pose startPose = new Pose(0, 0, Math.toRadians(0)); // Start Pose of our robot.
    public static final Pose endPose = new Pose(10, 30, Math.toRadians(90));



}
