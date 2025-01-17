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

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;

import edu.wpi.first.math.controller.PIDController;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class CanSparkFlex extends SubsystemBase {
  // use parameter in commands
  private SparkFlex motor1;
  private SparkClosedLoopController pid;
  private SparkMaxConfig config;

  private double kP = Constants.PIDConstants.kP;
  private double kI = Constants.PIDConstants.kI;
  private double kD = Constants.PIDConstants.kD;
  private double kFF = Constants.PIDConstants.kFF;

  private int dir = 0;

  private double speedSetPoint = 1000;

  public CanSparkFlex() {

    motor1 = new SparkFlex(Constants.CANID.testMotor, MotorType.kBrushless);
    config = new SparkMaxConfig();

    pid = motor1.getClosedLoopController();

    config
        .inverted(true)
        .idleMode(IdleMode.kBrake);
    config.encoder
        .positionConversionFactor(1000)
        .velocityConversionFactor(1000);
    config.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .pid(kP, kI, kD);

    motor1.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    Preferences.initDouble(Constants.PIDConstants.kPKey, kP);
    Preferences.initDouble(Constants.PIDConstants.kIKey, kI);
    Preferences.initDouble(Constants.PIDConstants.kDKey, kD);
    Preferences.initDouble(Constants.PIDConstants.kFFKey, kFF);
    Preferences.initInt("Direction", dir);
  }

  public void setSpeed(double speed) {
    motor1.set(speed);
  }

  // public Command closedLoopControlVelocity() {
  // return new FunctionalCommand(
  // () -> {

  // // kP = SmartDashboard.getNumber("kP", kP);
  // // kI = SmartDashboard.getNumber("kI", kI);
  // // kD = SmartDashboard.getNumber("kD", kD);
  // // kFF = SmartDashboard.getNumber("kFF", kFF);

  // dir = (int) Preferences.getInt("Direction", dir);

  // // pid.setP(kP);
  // // pid.setI(kI);
  // // pid.setD(kD);
  // // pid.setD(kFF);
  // },

  // () -> {
  // if (dir == 0) {
  // pid.setReference(speedSetPoint, CANSparkMax.ControlType.kVelocity);
  // SmartDashboard.putNumber("Motor Speed", myEncoder.getVelocity());
  // } else {
  // pid.setReference(speedSetPoint * -1, CANSparkMax.ControlType.kVelocity);
  // SmartDashboard.putNumber("Motor Speed", myEncoder.getVelocity());
  // }
  // },

  // interrupted -> {
  // pid.setReference(0, CANSparkMax.ControlType.kVelocity);
  // SmartDashboard.putNumber("Motor Speed", 0);
  // },

  // () -> false,

  // this);
  // }

  @Override
  public void periodic() {

  }

}
