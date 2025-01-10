// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.subsystems.CanSparkFlex;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.LimitSwitches;
import frc.robot.subsystems.NetworkTables;
import frc.robot.subsystems.Servos;
import frc.robot.subsystems.SwerveDrive;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {

  //declaring controllers/joysticks
  private final CommandXboxController controller = new CommandXboxController(0);
  public final static CommandJoystick leftJoystick = new CommandJoystick(0);
  public final static CommandJoystick rightJoystick = new CommandJoystick(1);

  //public final TankDrive m_driveCommand = new TankDrive(DriveTrain);


  //declaring controller buttons
  private final Trigger xButton = controller.x();
  private final Trigger aButton = controller.a();
  private final Trigger bButton = controller.b();
  private final Trigger yButton = controller.y();


  //declaring controller triggers
  private final Trigger rBumper = controller.rightBumper();
  private final Trigger lBumper = controller.leftBumper();


  //declaring controller axes
  private final DoubleSupplier leftXAxis = () -> controller.getRawAxis(0);
  private final DoubleSupplier leftYAxis = () -> controller.getRawAxis(1);
  private final DoubleSupplier leftTrigger = () -> controller.getRawAxis(2);
  private final DoubleSupplier rightTrigger = () -> controller.getRawAxis(3);
  private final DoubleSupplier rightXAxis = () -> controller.getRawAxis(4); 
  private final DoubleSupplier rightYAxis = () -> controller.getRawAxis(5);

  //_____________________________________________________________________________________________

  //declaring leftJoystick buttons
  private final Trigger leftJoystickButton1 = leftJoystick.button(1);
  private final Trigger leftJoystickButton2 = leftJoystick.button(2);
  private final Trigger leftJoystickButton3 = leftJoystick.button(3);
  private final Trigger leftJoystickButton4 = leftJoystick.button(4);
  private final Trigger leftJoystickButton5 = leftJoystick.button(5);
  private final Trigger leftJoystickButton6 = leftJoystick.button(6);
  private final Trigger leftJoystickButton7 = leftJoystick.button(7);
  private final Trigger leftJoystickButton8 = leftJoystick.button(8);
  private final Trigger leftJoystickButton9 = leftJoystick.button(9);
  private final Trigger leftJoystickButton10 = leftJoystick.button(10);
  private final Trigger leftJoystickButton11 = leftJoystick.button(11);
  private final Trigger leftJoystickButton12 = leftJoystick.button(12);


  //declaring leftjoystick axes
  private final DoubleSupplier leftJoystickXAxis = () -> leftJoystick.getRawAxis(0);
  private final DoubleSupplier leftJoystickYAxis = () -> leftJoystick.getRawAxis(1);
  private final DoubleSupplier leftJoystickZAxis = () -> leftJoystick.getRawAxis(2);

  //__________________________________________________________________________________________


  //declaring rightJoystick buttons
  private final Trigger rightJoystickButton1 = rightJoystick.button(1);
  private final Trigger rightJoystickButton2 = rightJoystick.button(2);
  private final Trigger rightJoystickButton3 = rightJoystick.button(3);
  private final Trigger rightJoystickButton4 = rightJoystick.button(4);
  private final Trigger rightJoystickButton5 = rightJoystick.button(5);
  private final Trigger rightJoystickButton6 = rightJoystick.button(6);
  private final Trigger rightJoystickButton7 = rightJoystick.button(7);
  private final Trigger rightJoystickButton8 = rightJoystick.button(8);
  private final Trigger rightJoystickButton9 = rightJoystick.button(9);
  private final Trigger rightJoystickButton10 = rightJoystick.button(10);
  private final Trigger rightJoystickButton11 = rightJoystick.button(11);
  private final Trigger rightJoystickButton12 = rightJoystick.button(12);


  //declaring rightJoystick axes
  private final DoubleSupplier rightJoystickXAxis = () -> rightJoystick.getRawAxis(0);
  private final DoubleSupplier rightJoystickYAxis = () -> rightJoystick.getRawAxis(1);
  private final DoubleSupplier rightJoystickZAxis = () -> rightJoystick.getRawAxis(2);

  //___________________________________________________________________________________________

  //linking subsystems
  private final CanSparkFlex m_canSparkFlex = new CanSparkFlex();
  private final LED LED = new LED();
  private final Servos m_Servo = new Servos();
  //private final DriveTrain driveTrain = new DriveTrain();
  private final LimitSwitches limitSwitch = new LimitSwitches();
  private final NetworkTables networkTable = new NetworkTables();
  //private final SwerveDrive swerve = new SwerveDrive();

  //_________________________________________________________________________________________

  public RobotContainer() {
    configureBindings(); 
  }
  


  private void configureBindings() {

    //m_canSparkFlex.setDefaultCommand(m_canSparkFlex.moveMotor(leftXAxis));
    //xButton.whileTrue(m_canSparkFlex.closedLoopControlVelocity());

    //LED.setDefaultCommand(LED.controllerDirectionRGBCommand(leftXAxis, 0, 1).ignoringDisable(true));
    //LED.setDefaultCommand(LED.controllerDirectionRGBCommand(leftYAxis, 2, 3).ignoringDisable(true));
    //LED.setDefaultCommand(LED.controllerDirectionRGBCommand(rightXAxis, 4, 5).ignoringDisable(true));
    //LED.setDefaultCommand(LED.controllerDirectionRGBCommand(rightYAxis, 6, 7).ignoringDisable(true));

    //LED.setDefaultCommand(LED.LEDShiftCommand(leftXAxis, 0, 1).ignoringDisable(true));
    //LED.setDefaultCommand(LED.LEDShiftCommand(leftYAxis, 2, 3).ignoringDisable(true));
    //LED.setDefaultCommand(LED.LEDShiftCommand(rightXAxis, 4, 5).ignoringDisable(true));
    //LED.setDefaultCommand(LED.LEDShiftCommand(rightYAxis, 6, 7).ignoringDisable(true));

    // xButton.whileTrue(LED.setColor(0).ignoringDisable(true));
    // aButton.whileTrue(LED.setColor(1).ignoringDisable(true));
    // bButton.whileTrue(LED.setColor(2).ignoringDisable(true));
    // yButton.whileTrue(LED.setColor(3).ignoringDisable(true));
    // rBumper.whileTrue(LED.setColor(4).ignoringDisable(true));
    // lBumper.whileTrue(LED.setColor(5).ignoringDisable(true));

    //m_Servo.setDefaultCommand(m_Servo.servoStick(rightXAxis));
    //rightJoystickButton1.whileTrue(m_Servo.servoTurnLeft());

    //driveTrain.setDefaultCommand(driveTrain.tankDriveCommand(leftYAxis, rightYAxis));

    //limitSwitch.setDefaultCommand(limitSwitch.limitSwitch(leftXAxis));

    //swerve.setDefaultCommand(swerve.test(leftYAxis, leftXAxis, rightXAxis));
    //swerve.setDefaultCommand(swerve.swerveCommand(leftYAxis, leftXAxis, rightXAxis));

  }
}
