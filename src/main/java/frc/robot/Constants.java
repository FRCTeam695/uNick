// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
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
  }

}
