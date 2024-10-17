// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Preferences;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.controller.PIDController;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class CanSparkFlex extends SubsystemBase {
  // use parameter in commands
  public CANSparkFlex motor1;
  public RelativeEncoder myEncoder;
  public SparkPIDController myPIDController;
  // public final LEDSubsystem motorLED = new LEDSubsystem();

  public DigitalInput mechanicalSwitch;
  public DigitalInput magnetSwitch;

  double kP = 0.0003;
  double kI = 0.000001;
  double kD = 0.00001;
  double kIz = 0;

  double kMinOutput = -1;
  double kMaxOutput = 1;

  double kFF = 0.0001;

  double setPointRPM = 1427;

  public CanSparkFlex() {
    motor1 = new CANSparkFlex(51, CANSparkLowLevel.MotorType.kBrushless);
    myEncoder = motor1.getEncoder(); // getEncoder returns a relative encoder

    motor1.restoreFactoryDefaults();
    myEncoder.setPosition(0);

    myPIDController = motor1.getPIDController();

    // motorLED = new LEDSubsystem();

    mechanicalSwitch = new DigitalInput(9);
    magnetSwitch = new DigitalInput(8);

    Preferences.initDouble(Constants.PIDConstants.pKey, kP);
    Preferences.initDouble(Constants.PIDConstants.iKey, kI);
    Preferences.initDouble(Constants.PIDConstants.dKey, kD);
    Preferences.initDouble(Constants.PIDConstants.ffKey, kFF);
    Preferences.initDouble(Constants.PIDConstants.spKey, setPointRPM);

    // myPIDController = new PIDController(Constants.PIDConstants.kP,
    // Constants.PIDConstants.kI, Constants.PIDConstants.kD);

    // myPIDController.setReference(Constants.PIDConstants.setPoint,
    // CANSparkBase.ControlType.kVelocity);

    myPIDController.setP(kP);
    myPIDController.setI(kI);
    myPIDController.setD(kD);
    myPIDController.setIZone(kIz);

    // Set the minimum and maximum outputs of the motor [-1, 1]
    myPIDController.setOutputRange(kMinOutput, kMaxOutput);

    // Set kFF
    myPIDController.setFF(kFF);
  }

  public boolean smallDeadBand(double axis) {
    return (axis < 0 && axis > -0.1);
  }

  public boolean largeDeadBand(double axis) {
    return (axis > 0 && axis < 0.1);
  }

  public Command moveMotor(DoubleSupplier axis) {
    return new FunctionalCommand(

        () -> {

        },

        () -> {

          double a = axis.getAsDouble();

          SmartDashboard.putNumber("Motor Speed", a);

          if ((a > 0.1) || (a < -0.1)) {
            motor1.set(a);
          } else {
            motor1.set(0);
          }
        },

        interrupted -> {
          motor1.set(0);

          System.out.println(axis.getAsDouble());
        },

        () -> false,

        this);
  }

}
