package org.firstinspires.ftc.teamcode.constants;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.config.util.Alliance;
import org.firstinspires.ftc.teamcode.config.util.Color;
import org.firstinspires.ftc.teamcode.config.util.Motif;

import java.util.HashMap;
import java.util.Map;

@Config
public class ConfigConstants {
    //DEBUG
    public static final boolean LOGGING = true;
    public static final boolean DRAWING = true;
    public static final boolean DASHBOARD = true;

    //Names
    public static final String BACK_LEFT = "BL";
    public static final String BACK_RIGHT = "BR";
    public static final String FRONT_LEFT = "FL";
    public static final String FRONT_RIGHT = "FR";

    public static final String PINPOINT = "pinpoint";
    public static final String SHOOTER_LEFT = "shooter left";
    public static final String SHOOTER_RIGHT = "shooter right";
    public static final String SHOOTER_BLOCKER = "shooter blocker";
    public static final String LEFT_LINDEXER = "left lindexer";
    public static final String RIGHT_LINDEXER = "right lindexer";
    public static final String INTAKE = "intake motor";
    public static final String INTAKE_LIFTER = "intake lifter";
    public static final String GREEN_WHEEL = "transfer motor";
    public static final String ARDUCAM = "ardu cam";
    public static final String LIMELIGHT = "limelight";
    public static final String TRANSFER_BLOCKER = "transfer blocker";
    public static final String ASCENT_LEFT = "ascent left";
    public static final String ASCENT_RIGHT = "ascent right";
    public static final String DIGITAL_DISTANCE = "digital distance";
    public static final String TURRET_ENCODER = INTAKE;

    public static final String LINDEX_COLOR_LEFT = "lindex color left";
    public static final String LINDEX_COLOR_RIGHT = "lindex color right";
    public static final String LINDEX_COLOR_CENTER = "lindex color center";

    public static final String RGB_INDICATOR = "rgb";

    public static final String TURRET_LEFT = "ts left";
    public static final String TURRET_RIGHT = "ts right";

    //Game
        //Defaults
    public static final Alliance DEFAULT_ALLIANCE = Alliance.BLUE;
    public static final Motif DEFAULT_MOTIF = Motif.GPP;
    public static final int DEFAULT_MOTIF_TAG_NUMBER = 21;
        //Alliance, Motif, and Tag Assignments
    public static final Map<Alliance, Integer> GOAL_TAG_MAP = new HashMap<Alliance, Integer>() {{
        put(Alliance.BLUE, 20);
        put(Alliance.RED, 24);

        // Number of the April Tag on the blue goal and red goal

    }};
    public static final Map<Integer, Motif> MOTIF_TAG_MAP = new HashMap<Integer, Motif>(){{
        put(21, Motif.GPP);
        put(22, Motif.PGP);
        put(23, Motif.PPG);
        // Number of the April Tag on each side of the obelisk

    }};
    public static final Map<Motif, Color[]> MOFIT_COLOR_MAP = new HashMap<Motif, Color[]>() {{
        put(Motif.GPP, new Color[]{Color.GREEN, Color.PURPLE, Color.PURPLE});
        put(Motif.PGP, new Color[]{Color.PURPLE, Color.GREEN, Color.PURPLE});
        put(Motif.PPG, new Color[]{Color.PURPLE, Color.PURPLE, Color.GREEN});

    }};


    //Controller
    public static final double TRIGGER_SENSITIVITY = 0.01;   // Anything less than this number is considered 0


    //Subsystems
        //Driving
    public static final double DRIVE_DAMPENING = 1; //slows the robot down for motors
    public static final double STRAFE_DAMPENING = 1; //slows the robot down for motors
    public static final double TURN_DAMPENING = 0.8; //slows the robot down for motors
    public static final boolean USE_BRAKE_MODE = true;

        //Intake
    public static final double INTAKE_POWER = 1;
    public static final double OUTTAKE_POWER = -1;
    public static final double INTAKE_LIFT = 0.2;
    public static final double INTAKE_DROP = 0.38;


        //Lindexer
    public static final double LEFT_LIN_IN = 0.37;
    public static final double LEFT_LIN_OUT = 0.625;
    public static final double RIGHT_LIN_IN = 0.65;
    public static final double RIGHT_LIN_OUT = 0.355;
    public static final double MOVE_MILLISECONDS = 300;

        //Transfer
    public static final double TRANSFER_INTAKE_RPM = 1;

        //Transfer Blocker
    public static final double TRANSFER_CPR = 103.8;
    public static final double TRANSFER_BLOCK = 0.4;
    public static final double TRANSFER_UNBLOCK = 0.63;

        //Turret
    public static final double TURRET_ZERO = 0.516; // left ticks
    public static final double TURRET_MAX = 0.85; // left ticks
    public static final double TURRET_MIN = 0.18; // left ticks
    public static final double TURRET_TICK_OFFSET_FOR_RIGHT = 0; //0.5
    public static final double TICKS_PER_ENCODER_REVOLUTION = 8192;
    public static final double TURRET_TEETH = 204;
    public static final double ENCODER_TEETH = 34;
    public static final PIDFCoefficients TRANSFER_PID = new PIDFCoefficients(100, 0, 1, 5);

    public static final Map<Double, Double> TICKS_PER_TURRET_DEGREE_MAP = new HashMap<Double, Double>() {{
        put(-90.0, 0.0037);
        put(-67.5, 0.0038);
        put(-43.0, 0.004);
        put(-20.0, 0.0043);
        put(-12.0, 0.0045);
        put(-5.0, 0.0055);
        put(5.0, 0.0055);
        put(12.0, 0.0045);
        put(20.0, 0.0043);
        put(43.0, 0.004);
        put(67.5, 0.0038);
        put(90.0, 0.0037);
    }};


        //Shooter
    public static final PIDFCoefficients SHOOTER_PID = new PIDFCoefficients(200, 0, 1, 14);

    public static final double RPM_ROC_BOUND = 1.3; //0.8 rpm per millisecond  if average is less than this = ready
    public static final double RPM_DISTANCE_BOUND = 150; //

    public static final double NEAR_VS_FAR = 130;

    public static final double DEFAULT_RPM = 3000;

    public static final double RPM_ADJUST_AMOUNT = 30;
    public static final double SHOOTER_CPR = 28.0; //ticks per revolution

        //Shooter Blocker
    public static final double SHOOTER_BLOCK = 0.715;
    public static final double SHOOTER_UNBLOCK = 0.475;

        //Ascent
    public static final double ASCEND_RIGHT = 0.5; // 53
    public static final double ASCEND_LEFT = 0.565; // 54
    public static final double DESCEND_RIGHT = 0.83;
    public static final double DESCEND_LEFT = 0.25;
    public static final double DOWN_RIGHT = 0.695;
    public static final double DOWN_LEFT = 0.385;

    public static final Map<Double, Double> RPM_MAP = new HashMap<Double, Double>() {{
        put(0.0, 2300.0);
        put(50.0, 2250.0);
        put(70.0, 2350.0);
        put(85.0, 2400.0);
        put(95.0, 2450.0);
        put(113.0, 2600.0);
        put(140.0, 2900.0);
        put(170.0, 3200.0);
    }};

    //Vision


    public static final Pose BLUE_FIELD_RESET = new Pose(1.5, 11.5, 0);
    public static final Pose RED_FIELD_RESET = new Pose(1.5, 136, 0);


    //Positions
    public static final Pose GOAL_BLUE = new Pose(144,144);
    public static final Pose GOAL_RED = new Pose(144,0);

    public static final Pose targetPointFarBlue = new Pose(136,134);
    public static final Pose targetPointCloseBlue = new Pose(133,133);

    public static final Pose targetPointFarRed = new Pose(136,8);
    public static final Pose targetPointCloseRed = new Pose(133,9);


}
