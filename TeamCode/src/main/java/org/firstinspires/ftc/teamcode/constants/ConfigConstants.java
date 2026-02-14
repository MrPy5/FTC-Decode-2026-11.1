package org.firstinspires.ftc.teamcode.constants;

import com.acmerobotics.dashboard.config.Config;
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
    public static final boolean LOGGING = false;
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
    public static final String INTAKE = "intake";
    public static final String INTAKE_LIFTER = "intake lifter";
    public static final String GREEN_WHEEL = "green wheel";
    public static final String ARDUCAM = "ardu cam";
    public static final String INTAKE_BLOCKER = "intake blocker";
    public static final String TRANSFER_BLOCKER = "transfer blocker";

    public static final String LINDEX_COLOR_LEFT = "lindex color left";
    public static final String LINDEX_COLOR_RIGHT = "lindex color right";

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
    public static final double STICK_AT_ZERO_DISTANCE = 0.01;  // Anything less than this number is considered 0
    public static final double IGNORE_LEFT_STICK_X = 0.15;   // Dead zone between .1 and 0

    //Subsystems
        //Driving
    public static final double DRIVE_DAMPENING = 1; //slows the robot down for motors
    public static final double STRAFE_DAMPENING = 1; //slows the robot down for motors
    public static final double TURN_DAMPENING = 0.8; //slows the robot down for motors
    public static final boolean USE_BRAKE_MODE = true;

    public static double TURN_kP = 1;   // 0.01 – 0.025
    public static double TURN_kD = 0.12;   // 0.001 – 0.006
    public static final double BOOST_MULTIPLIER = 0;   // 0.001 – 0.006

        //Intake
    public static final double INTAKE_POWER = 1;
    public static final double OUTTAKE_POWER = -1;
    public static final double INTAKE_LIFT = 0.1;
    public static final double INTAKE_DROP = 0.3;

        //Intake Blocker
    public static final double INTAKE_BLOCK = 0.4;
    public static final double INTAKE_UNBLOCK = 0.22;

        //Lindexer
    public static final double LEFT_LIN_IN = 0.37;
    public static final double LEFT_LIN_OUT = 0.625;
    public static final double RIGHT_LIN_IN = 0.695;
    public static final double RIGHT_LIN_OUT = 0.46;
    public static final double MOVE_MILLISECONDS = 300;


        //Transfer
    public static final double TRANSFER_INTAKE_RPM = 1;
    public static final double TRANSFER_INTAKE_SLOW_RPM = 1;
    public static final double TRANSFER_OUTTAKE_RPM = -1;

        //Transfer Blocker
    public static final double TRANSFER_CPR = 103.8;
    public static final double TRANSFER_BLOCK = 0.515;
    public static final double TRANSFER_UNBLOCK = 0.315;


    //Shooter
    public static final PIDFCoefficients SHOOTER_PID = new PIDFCoefficients(100, 0, 1, 14);
    public static double kP = 0.007;
    public static double kS = 0;
    public static double kV = 0.00035;
    public static final double RPM_ROC_BOUND = 0.9; //0.8 rpm per millisecond  if average is less than this = ready
    public static final double RPM_DISTANCE_BOUND = 100; //

    public static final double NEAR_VS_FAR = 100; //

    public static final double FURTHEST_DIST = 124;
    public static final double FURTHEST_RPM = 3200;

    public static final double BACK_TRIANGLE_DIST = 100;
    public static final double BACK_TRIANGLE_RPM = 3100;

    public static final double FAR_FRONT_TRAINGLE_DIST = 85;
    public static final double FAR_FRONT_TRIANGLE_RPM = 2700;

    public static final double MID_FRONT_TRIANGLE_DIST = 70;
    public static final double MID_FRONT_TRIANGLE_RPM = 2650;

    public static final double FRONT_TRIANGLE_DIST = 50;
    public static final double FRONT_TRIANGLE_RPM = 2500;

    public static final double CLOSEST_DIST = 0;
    public static final double CLOSEST_RPM = 2400;

    public static final double DEFAULT_RPM = 3000;

    public static final double RPM_ADJUST_AMOUNT = 50;
    public static final double SHOOTER_CPR = 28.0; //ticks per revolution

        //Shooter Blocker
    public static final double SHOOTER_BLOCK = 0.39;
    public static final double SHOOTER_UNBLOCK = 0.61;


    //Vision
        //Tag Camera
    public static final double TAG_DEGREE_TOLERANCE = 0.7; //aligns to april tag within .7 degrees

    public static final Map<Double, Double> CLOSE_OFFSET_MAP_BLUE = new HashMap<Double, Double>() {{
        put(-7.0, -1.0); //-3
        put(0.0, 0.0); //-3
        put(17.0, 0.5); // 0
        put(28.0, 2.0); // 0
        put(36.0, 4.0); // 2
    }};
    public static final Map<Double, Double> CLOSE_OFFSET_MAP_RED = new HashMap<Double, Double>() {{
        put(7.0, 3.0); //-3
        put(0.0, 0.0); //-3
        put(-17.0, 1.0); // 0
        put(-28.0, -2.0); // 0
        put(-36.0, -3.0); // 2
    }};

    public static final Map<Double, Double> FAR_OFFSET_MAP_BLUE = new HashMap<Double, Double>() {{
        put(-19.0, -2.5); //-2
        put(-23.0, -2.5); //-2
        put(-28.0, -2.5); // -1
        put(-34.0, -2.5); // -1
        put(-38.0, -3.0); // -1.2
    }};
    public static final Map<Double, Double> FAR_OFFSET_MAP_RED = new HashMap<Double, Double>() {{
        put(23.0, -0.5); //-2
        put(28.0, 1.0); // -1
        put(34.0, 1.0); // -1
        put(38.0, 1.0); // -1.2
    }};


    //Auto
    public static final double AUTO_CLOSE_RPM_RED = 2600;
    public static final double AUTO_CLOSE_RPM_BLUE = 2800;
    public static final double AUTO_CLOSE_RPM = 2800;
    public static final double AUTO_FAR_RPM = 3300;


}
