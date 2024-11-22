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
  public CANSparkFlex motor1;
  public RelativeEncoder myEncoder;
  public SparkPIDController pid;
  // public final LEDSubsystem motorLED = new LEDSubsystem();

  double kP = 0.00005;
  double kI = 0.000001;
  double kD = 0.002;
  double kFF = 0.0001;

  int dir = 0;

  double kMinOutput = -1;
  double kMaxOutput = 1;

  double setPoint = 1000;

  public CanSparkFlex() {

    motor1 = new CANSparkFlex(51, CANSparkLowLevel.MotorType.kBrushless);
    myEncoder = motor1.getEncoder(); // getEncoder returns a relative encoder

    motor1.restoreFactoryDefaults();
    myEncoder.setPosition(0);

    pid = motor1.getPIDController();

    pid.setP(kP);
    pid.setI(kI);
    pid.setD(kD);
    pid.setFF(kFF);

    // Set the minimum and maximum outputs of the motor [-1, 1]
    pid.setOutputRange(kMinOutput, kMaxOutput);

    SmartDashboard.putNumber("kP", kP);
    SmartDashboard.putNumber("kI", kI);
    SmartDashboard.putNumber("kD", kD);
    SmartDashboard.putNumber("kFF", kFF);
    SmartDashboard.putNumber("direction", dir);

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

  public Command closedLoopControl() {
    return new FunctionalCommand(
        () -> {

          kP = SmartDashboard.getNumber("kP", kP);
          kI = SmartDashboard.getNumber("kI", kI);
          kD = SmartDashboard.getNumber("kD", kD);
          kFF = SmartDashboard.getNumber("kFF", kFF);
          dir = (int) SmartDashboard.getNumber("direction", dir);

          pid.setP(kP);
          pid.setI(kI);
          pid.setD(kD);
          pid.setD(kFF);
        }, 
        
        () -> {
          if (dir == 0) {
            pid.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
            SmartDashboard.putNumber("Motor Speed", myEncoder.getVelocity());
          } else {
            pid.setReference(setPoint * -1, CANSparkMax.ControlType.kVelocity);
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

  @Override
  public void periodic() {
    
  }

}
