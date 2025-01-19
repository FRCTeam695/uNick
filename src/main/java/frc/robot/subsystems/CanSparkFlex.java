// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;

public class CanSparkFlex extends SubsystemBase {
  private SparkFlex motor1;
  private SparkClosedLoopController pid;
  private SparkMaxConfig config;

  private double kP = Constants.PIDConstants.kP;
  private double kI = Constants.PIDConstants.kI;
  private double kD = Constants.PIDConstants.kD;

  private String dir = "Positive";

  private double speedSetPoint = 1000;
  private double positionSetPoint = 50;

  public CanSparkFlex() {

    motor1 = new SparkFlex(Constants.CANID.testMotor, MotorType.kBrushless);
    config = new SparkMaxConfig();

    pid = motor1.getClosedLoopController();

    config
        .inverted(false);
    config.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .pid(kP, kI, kD);

    motor1.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    SmartDashboard.putNumber(Constants.PIDConstants.kPKey, kP);
    SmartDashboard.putNumber(Constants.PIDConstants.kIKey, kI);
    SmartDashboard.putNumber(Constants.PIDConstants.kDKey, kD);
    SmartDashboard.putString("Direction", "Positive");
    SmartDashboard.putNumber("Rotations", 0);
  }

  public void setSpeed(double speed) {
    motor1.set(speed);
  }

  public Command closedLoopControlVelocity(DoubleSupplier speedSupplier) {
    return new FunctionalCommand(
        () -> {
          kP = SmartDashboard.getNumber("kP", kP);
          kI = SmartDashboard.getNumber("kI", kI);
          kD = SmartDashboard.getNumber("kD", kD);

          dir = SmartDashboard.getString("Direction", dir);

          config.closedLoop
              .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
              .pid(kP, kI, kD);

          motor1.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        },

        () -> {

          pid.setReference(speedSetPoint * speedSupplier.getAsDouble(), SparkMax.ControlType.kVelocity);
          SmartDashboard.putNumber("Motor Speed", motor1.getEncoder().getVelocity());
        },

        interrupted -> {
          pid.setReference(positionSetPoint, SparkMax.ControlType.kVelocity);
          SmartDashboard.putNumber("Motor Speed", 0);
        },

        () -> false,

        this);
  }

  public Command closedLoopControlPosition(DoubleSupplier speedSupplier) {
    return new FunctionalCommand(
        () -> {
          
          kP = SmartDashboard.getNumber("kP", kP);
          kI = SmartDashboard.getNumber("kI", kI);
          kD = SmartDashboard.getNumber("kD", kD);

          dir = SmartDashboard.getString("Direction", dir);

          config.closedLoop
              .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
              .pid(kP, kI, kD);

          motor1.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        },

        () -> {

          pid.setReference(1, SparkMax.ControlType.kPosition);
          SmartDashboard.putNumber("Motor Position", motor1.getEncoder().getPosition());
        },

        interrupted -> {
          pid.setReference(0, SparkMax.ControlType.kPosition);
          SmartDashboard.putNumber("Motor Position", 0);
        },

        () -> false,

        this);
  }

  @Override
  public void periodic() {

  }

}
