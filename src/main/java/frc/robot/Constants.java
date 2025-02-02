package frc.robot;

public final class Constants {

  public static final class PIDConstants {
    public static final double kP = 0.00005;
    public static final double kI = 0.000001;
    public static final double kD = 0.002;
    public static final double kFF = 0.0001;

    public static final String kPKey = "kP";
    public static final String kIKey = "kI";
    public static final String kDKey = "kD";
    public static final String kFFKey = "kFF";
  }

  public static final class CANID {
    public static final int testMotor = 51;

    public static final int leftFrontID = 10;
    public static final int leftBackID = 12;
    public static final int rightFrontID = 11;
    public static final int rightBackID = 13;

    public static final int moveMotorID = 53;
    public static final int collectMotorID = 54;
  }

  public static final class driveConstants {
    public static final int leftAxis = 1;
    public static final int rightAxis = 0;
  }

  public static final class controllerConstants {
    public static final int controllerPort = 0;
    public static final int leftJoystickPort = 0;
    public static final int rightJoystickPort = 0;
  }
}
