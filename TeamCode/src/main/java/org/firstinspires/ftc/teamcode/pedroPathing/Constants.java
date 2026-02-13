package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.constants.ConfigConstants;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(8.754)
            .forwardZeroPowerAcceleration(-58.26890)
            .lateralZeroPowerAcceleration(-34.39833)

            .translationalPIDFCoefficients(new PIDFCoefficients(0.06, 0, 0.01, 0.05))
            .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.09, 0, 0.005, 0.05))
            .useSecondaryTranslationalPIDF(true)

            .headingPIDFCoefficients(new PIDFCoefficients(0.7, 0, 0.005, 0.08))
            .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(1.8, 0, 0.025, 0.07))
            .useSecondaryHeadingPIDF(true)

            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.025, 0, 0.00001, 0.6, 0.01))

            .automaticHoldEnd(true)
            .turnHeadingErrorThreshold(0.02)
            .centripetalScaling(0.0007);

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public static MecanumConstants driveConstants = new MecanumConstants()
            .useBrakeModeInTeleOp(ConfigConstants.USE_BRAKE_MODE)
            .maxPower(1)
            .rightFrontMotorName(ConfigConstants.FRONT_RIGHT)
            .rightRearMotorName(ConfigConstants.BACK_RIGHT)
            .leftRearMotorName(ConfigConstants.BACK_LEFT)
            .leftFrontMotorName(ConfigConstants.FRONT_LEFT)
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(71.18228)
            .yVelocity(84.98774);


    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY((0.0))
            .strafePodX(1 + (1.0/4.0))
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName(ConfigConstants.PINPOINT)
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .pinpointLocalizer(localizerConstants)
                .build();
    }
}