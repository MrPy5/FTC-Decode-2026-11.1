package org.firstinspires.ftc.teamcode.pedroPathing;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
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
@Configurable
@Config
public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(10.9)
            .forwardZeroPowerAcceleration(-25.74251)
            .lateralZeroPowerAcceleration(-59.60297)

            .translationalPIDFCoefficients(new PIDFCoefficients(0.13, 0, 0.01, 0.01))
           // .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.09, 0, 0.005, 0.05))
           // .useSecondaryTranslationalPIDF(true)

            .headingPIDFCoefficients(new PIDFCoefficients(1.3, 0, 0.08, 0.01))
           // .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(1.8, 0, 0.025, 0.07))
           // .useSecondaryHeadingPIDF(true)

            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.015, 0, 0.00001, 0.6, 0.01))

            .automaticHoldEnd(true)
            .turnHeadingErrorThreshold(0.02)
            .centripetalScaling(0.0000000005);

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 0.9, 1);

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
            .xVelocity(86.60169)

            .yVelocity(68.28731);



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