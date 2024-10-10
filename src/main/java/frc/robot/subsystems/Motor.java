// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;



import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.controller.PIDController;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Motor extends SubsystemBase {
  // use paramter in commands
  CANSparkFlex motor1 = new CANSparkFlex(51, MotorType.kBrushless);

  PIDController pid = new PIDController(0.00005, 0, 0);

  public DoublePublisher leftXStick;

  public Motor() 
  {

  }

  public Command motorOn(DoubleSupplier axis) {
    return new FunctionalCommand(

        () -> {

        },

        () -> {

          System.out.println(axis.getAsDouble());
          motor1.set(axis.getAsDouble());

        }, 
        
        interrupted -> {
        },

        () -> false, this);
  }

}
