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
import com.revrobotics.CANSparkMax;
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
  private CANSparkFlex motor1;
  private RelativeEncoder myEncoder;
  private SparkPIDController pid;

  private double kP = Constants.PIDConstants.kP;
  private double kI = Constants.PIDConstants.kI;
  private double kD = Constants.PIDConstants.kD;
  private double kFF = Constants.PIDConstants.kFF;

  private int dir = 0;

  private double kMinOutput = -1;
  private double kMaxOutput = 1;

  private double speedSetPoint = 1000;
  private double positionSetPoint = 1000;

  public CanSparkFlex() {

    motor1 = new CANSparkFlex(Constants.CANID.testMotor, CANSparkLowLevel.MotorType.kBrushless);
    myEncoder = motor1.getEncoder(); // getEncoder returns a relative encoder

    motor1.restoreFactoryDefaults();
    myEncoder.setPosition(0);

    pid = motor1.getPIDController();

    pid.setP(kP);
    pid.setI(kI);
    pid.setD(kD);
    pid.setFF(kFF);

    // Preferences.initDouble(Constants.PIDConstants.kPKey, kP);
    // Preferences.initDouble(Constants.PIDConstants.kIKey, kI);
    // Preferences.initDouble(Constants.PIDConstants.kDKey, kD);
    // Preferences.initDouble(Constants.PIDConstants.kFFKey, kFF);
    // Preferences.initInt("Direction", dir);

    // SmartDashboard.putNumber("kP", kP);
    // SmartDashboard.putNumber("kI", kI);
    // SmartDashboard.putNumber("kD", kD);
    // SmartDashboard.putNumber("kFF", kFF);
    //SmartDashboard.putNumber("direction", dir);

    // SmartDashboard.putNumber("kIz", kIz);
    // SmartDashboard.putNumber("kFF", kFF);
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
        },

        () -> false,

        this);
  }

  public Command closedLoopControlVelocity() {
    return new FunctionalCommand(
        () -> {

          // kP = SmartDashboard.getNumber("kP", kP);
          // kI = SmartDashboard.getNumber("kI", kI);
          // kD = SmartDashboard.getNumber("kD", kD);
          // kFF = SmartDashboard.getNumber("kFF", kFF);

          dir = (int) Preferences.getInt("Direction", dir);

          // pid.setP(kP);
          // pid.setI(kI);
          // pid.setD(kD);
          // pid.setD(kFF);
        },

        () -> {
          if (dir == 0) {
            pid.setReference(speedSetPoint, CANSparkMax.ControlType.kVelocity);
            SmartDashboard.putNumber("Motor Speed", myEncoder.getVelocity());
          } else {
            pid.setReference(speedSetPoint * -1, CANSparkMax.ControlType.kVelocity);
            SmartDashboard.putNumber("Motor Speed", myEncoder.getVelocity());
          }
        },

        interrupted -> {
          pid.setReference(0, CANSparkMax.ControlType.kVelocity);
          SmartDashboard.putNumber("Motor Speed", 0);
        },

        () -> false,

        this);
  }

  public Command closedLoopControlPosition() {
    return new FunctionalCommand(
        () -> {
          kP = Preferences.getDouble("kP", kP);
          kI = Preferences.getDouble("kI", kI);
          kD = Preferences.getDouble("kD", kD);
          kFF = Preferences.getDouble("kFF", kFF);
          dir = (int) Preferences.getInt("Direction", dir);

          pid.setP(kP);
          pid.setI(kI);
          pid.setD(kD);
          pid.setD(kFF);
        },

        () -> {
          if (dir == 0) {
            pid.setReference(positionSetPoint, CANSparkMax.ControlType.kPosition);
            SmartDashboard.putNumber("Motor Position", myEncoder.getPosition());
          } else {
            pid.setReference(positionSetPoint * -1, CANSparkMax.ControlType.kPosition);
            SmartDashboard.putNumber("Motor Position", myEncoder.getPosition());
          }
        },

        interrupted -> {
          pid.setReference(0, CANSparkMax.ControlType.kPosition);
          SmartDashboard.putNumber("Motor Position", 0);
        },

        () -> false,

        this);
  }

  @Override
  public void periodic() {

  }

}
