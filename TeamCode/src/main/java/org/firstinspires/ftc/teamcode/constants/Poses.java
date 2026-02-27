package org.firstinspires.ftc.teamcode.constants;

import com.pedropathing.geometry.Pose;

public class Poses {
    public static final Pose startPoseBlueFar = new Pose(1.2, 88, 0); // Start Pose of our robot.
    public static final Pose startPoseRedFar = new Pose(1.2, 56, 0); // Start Pose of our robot.
    public static final Pose startPoseBlueClose = new Pose(122.14, 114.2, (0.896)); // Start Pose of our robot.
    public static final Pose startPoseRedClose = new Pose(121, 28, Math.toRadians(309)); // Start Pose of our robot.

    public static final Pose detectPoseBlue = new Pose(80, 80, Math.toRadians(-15)); // Start Pose of our robot.
    public static final Pose detectPoseRed = new Pose(78, 59, Math.toRadians(15)); // Start Pose of our robot.

    public static final Pose shootPoseRedFar = new Pose(10.1, 58, Math.toRadians(-23)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose shootPoseBlueFar = new Pose(10.1, 86, Math.toRadians(21)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose shootPoseBlueClose = new Pose(80, 77, Math.toRadians(49));
    public static final Pose parkPoseBlueClose = new Pose(95, 84, Math.toRadians(0)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose parkPoseRedClose = new Pose(95, 60, Math.toRadians(0)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose shootPoseRedClose = new Pose(83, 59.6, Math.toRadians(311));

    public static final Pose endShootPoseBlue = new Pose(80, 74.55, 0.92);
    public static final Pose endShootPoseRed = new Pose(25, -9, Math.toRadians(-54));


    public static final Pose gateTurn1Red = new Pose(68, 30, Math.toRadians(0));
    public static final Pose gatePush1Red = new Pose(68, 15, Math.toRadians(0));

    public static final Pose gateTurn1Blue = new Pose(68, 118, Math.toRadians(0));
    public static final Pose gatePush1Blue = new Pose(68, 130, Math.toRadians(0));

    public static final Pose gateTurn2Red = new Pose(-2, -45, Math.toRadians(180));
    public static final Pose gatePush2Red = new Pose(-2, -57, Math.toRadians(180));

    public static final Pose gateTurn2Blue = new Pose(-2, 45, Math.toRadians(180));
    public static final Pose gatePush2Blue = new Pose(-2, 57, Math.toRadians(180));

    //close
    public static final Pose spike1PoseBlue = new Pose(78.78, 95, 1.56); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike1EndPoseBlue = new Pose(78.78, 122, 1.56); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose spike2PoseBlue = new Pose(56, 95, 1.56); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike2EndPoseBlue = new Pose(56, 129, 1.56); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike2BezierPoseBlue = new Pose(45, 80, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose spike3PoseBlue = new Pose(31, 95, 1.56); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike3EndPoseBlue = new Pose(31, 129, 1.56); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose tunnelPoseBlue = new Pose(-22, 61, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose tunnelPoseRed = new Pose(-22, -61, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.


    public static final Pose spike1PoseRed = new Pose(79.2, 49, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike1EndPoseRed = new Pose(79.2, 21, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose spike2PoseRed = new Pose(55, 49, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike2EndPoseRed = new Pose(55, 13, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike2BezierPoseRed = new Pose(45, 64, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose spike3PoseRed = new Pose(31, 49, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike3EndPoseRed = new Pose(31, 13, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose gatePushAngleBlue = new Pose(-11.5, 60, Math.toRadians(62)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose gatePushAngleBezierBlue = new Pose(-20, 10, Math.toRadians(62)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose gateBackupAngleBlue = new Pose(-11.5, 62, Math.toRadians(58)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose gatePushAngleRed = new Pose(-16, 55, Math.toRadians(62)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose gatePushAngleBezierRed = new Pose(-30, 25, Math.toRadians(62)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose gateBackupAngleRed = new Pose(-17, 54.5, Math.toRadians(58)); // Highest (First Set) of Artifacts from the Spike Mark.


    //far
    public static final Pose spike2PoseBlueFar = new Pose(-16.5, 20, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike2EndPoseBlueFar = new Pose(-16.5, 50, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose spike3PoseBlueFar = new Pose(31, 95, 1.56); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike3EndPoseBlueFar = new Pose(31, 127, 1.56); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose spike2PoseRedFar = new Pose(-16, -20, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike2EndPoseRedFar = new Pose(-16, -52, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose spike3PoseRedFar = new Pose(31, 49, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose spike3EndPoseRedFar = new Pose(31, 17, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose parkPoseBlueFar = new Pose(30, 86, Math.toRadians(90)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose parkPoseRedFar = new Pose(30, 58, Math.toRadians(-90)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.

    public static final Pose hpPoseRed = new Pose(5, 14, Math.toRadians(-90)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose hpPoseBlue = new Pose(5, 130, Math.toRadians(90)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.

    public static final Pose hpBezierPoseRed = new Pose(0, 54, Math.toRadians(-90)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose hpBezierPoseBlue = new Pose(0, 90, Math.toRadians(90)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.

    public static final Pose backupHPPoseRed = new Pose(-65.5, -57.5, Math.toRadians(-100)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public static final Pose backupHPPoseBlue = new Pose(5, 125, Math.toRadians(90)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.

   /* public static final Pose gateOverFlowPoseBlueFar = new Pose(10, 134, Math.toRadians(10)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose gateOverFlowPoseRedFar = new Pose(-40, 50, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose gateOverFlowEndPoseBlueFar = new Pose(40, 135, Math.toRadians(10)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose gateOverFlowEndPoseRedFar = new Pose(-40, -65, Math.toRadians(-90)); // Highest (First Set) of Artifacts from the Spike Mark.
*/

    public static final Pose gateOverFlowPoseBlueFar = new Pose(2, 130, Math.toRadians(35)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose gateOverFlowPoseRedFar = new Pose(2, 14, Math.toRadians(-35)); // Highest (First Set) of Artifacts from the Spike Mark.

    public static final Pose gateOverFlowEndPoseBlueFar = new Pose(30, 130, Math.toRadians(35)); // Highest (First Set) of Artifacts from the Spike Mark.
    public static final Pose gateOverFlowEndPoseRedFar = new Pose(30, 14, Math.toRadians(-35)); // Highest (First Set) of Artifacts from the Spike Mark.

    //Test
    public static final Pose startPose = new Pose(0, 0, Math.toRadians(0)); // Start Pose of our robot.
    public static final Pose endPose = new Pose(10, 30, Math.toRadians(90));



}
