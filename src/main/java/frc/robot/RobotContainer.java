// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;

import frc.robot.subsystems.LED;
import frc.robot.subsystems.LEDShift;
import frc.robot.subsystems.CanSparkFlex;
import frc.robot.subsystems.Servos;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  private final CommandXboxController controller = new CommandXboxController(0);

  
  private final DoubleSupplier leftXAxis = () -> controller.getRawAxis(0);
  private final DoubleSupplier leftYAxis = () -> controller.getRawAxis(1);
  private final DoubleSupplier leftTrigger = () -> controller.getRawAxis(2);
  private final DoubleSupplier rightTrigger = () -> controller.getRawAxis(3);
  private final DoubleSupplier rightXAxis = () -> controller.getRawAxis(4); 
  private final DoubleSupplier rightYAxis = () -> controller.getRawAxis(5);

  private final CanSparkFlex m_canSparkFlex = new CanSparkFlex();
  private final LED m_LED = new LED();
  private final Servos m_Servo = new Servos();

  //private final LEDShift m_LEDShift = new LEDShift();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureBindings(); 
  }

  private void configureBindings() {

    //need comments

    //m_canSparkFlex.setDefaultCommand(m_canSparkFlex.moveMotor(leftXAxis));
    //m_LED.setDefaultCommand(m_LED.controllerDirectionRGBCommand(leftXAxis).ignoringDisable(true));
    m_LED.setDefaultCommand(m_LED.LEDShiftCommand(rightXAxis).ignoringDisable(true));
    //m_Servo.setDefaultCommand(m_Servo.servoStick(rightXAxis));

  }
}
