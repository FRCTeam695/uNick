// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Autos;
import frc.robot.commands.arcadeDriveCommand;
import frc.robot.commands.moveMotor;
import frc.robot.commands.tankDriveCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.CanSparkFlex;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.LimitSwitches;
import frc.robot.subsystems.NetworkTables;
import frc.robot.subsystems.Servos;
import frc.robot.subsystems.SwerveDrive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {

  //declaring controllers/joysticks
  public final static CommandXboxController controller = new CommandXboxController(Constants.controllerConstants.controllerPort);
  public final static CommandJoystick leftJoystick = new CommandJoystick(Constants.controllerConstants.leftJoystickPort);
  public final static CommandJoystick rightJoystick = new CommandJoystick(Constants.controllerConstants.rightJoystickPort);

  //declaring controller buttons

  private static final Trigger xButton = controller.x();
  private static final Trigger aButton = controller.a();
  private static final Trigger bButton = controller.b();
  private static final Trigger yButton = controller.y();

  //declaring controller triggers
  private static final Trigger rBumper = controller.rightBumper();
  private static final Trigger lBumper = controller.leftBumper();

  //declaring controller axes
  public static final DoubleSupplier leftXAxis = () -> controller.getRawAxis(0);
  private static final DoubleSupplier leftYAxis = () -> controller.getRawAxis(1);
  private static final DoubleSupplier leftTrigger = () -> controller.getRawAxis(2);
  private static final DoubleSupplier rightTrigger = () -> controller.getRawAxis(3);
  private static final DoubleSupplier rightXAxis = () -> controller.getRawAxis(4); 
  private static final DoubleSupplier rightYAxis = () -> controller.getRawAxis(5);

  //_____________________________________________________________________________________________

  //declaring leftJoystick buttons
  private static final Trigger leftJoystickButton1 = leftJoystick.button(1);
  private static final Trigger leftJoystickButton2 = leftJoystick.button(2);
  private static final Trigger leftJoystickButton3 = leftJoystick.button(3);
  private static final Trigger leftJoystickButton4 = leftJoystick.button(4);
  private static final Trigger leftJoystickButton5 = leftJoystick.button(5);
  private static final Trigger leftJoystickButton6 = leftJoystick.button(6);
  private static final Trigger leftJoystickButton7 = leftJoystick.button(7);
  private static final Trigger leftJoystickButton8 = leftJoystick.button(8);
  private static final Trigger leftJoystickButton9 = leftJoystick.button(9);
  private static final Trigger leftJoystickButton10 = leftJoystick.button(10);
  private static final Trigger leftJoystickButton11 = leftJoystick.button(11);
  private static final Trigger leftJoystickButton12 = leftJoystick.button(12);

  //declaring leftjoystick axes
  private static final DoubleSupplier leftJoystickXAxis = () -> leftJoystick.getRawAxis(0);
  private static final DoubleSupplier leftJoystickYAxis = () -> leftJoystick.getRawAxis(1);
  private static final DoubleSupplier leftJoystickZAxis = () -> leftJoystick.getRawAxis(2);

  //__________________________________________________________________________________________

  //declaring rightJoystick buttons
  private static final Trigger rightJoystickButton1 = rightJoystick.button(1);
  private static final Trigger rightJoystickButton2 = rightJoystick.button(2);
  private static final Trigger rightJoystickButton3 = rightJoystick.button(3);
  private static final Trigger rightJoystickButton4 = rightJoystick.button(4);
  private static final Trigger rightJoystickButton5 = rightJoystick.button(5);
  private static final Trigger rightJoystickButton6 = rightJoystick.button(6);
  private static final Trigger rightJoystickButton7 = rightJoystick.button(7);
  private static final Trigger rightJoystickButton8 = rightJoystick.button(8);
  private static final Trigger rightJoystickButton9 = rightJoystick.button(9);
  private static final Trigger rightJoystickButton10 = rightJoystick.button(10);
  private static final Trigger rightJoystickButton11 = rightJoystick.button(11);
  private static final Trigger rightJoystickButton12 = rightJoystick.button(12);

  //declaring rightJoystick axes
  private static final DoubleSupplier rightJoystickXAxis = () -> rightJoystick.getRawAxis(0);
  private static final DoubleSupplier rightJoystickYAxis = () -> rightJoystick.getRawAxis(1);
  private static final DoubleSupplier rightJoystickZAxis = () -> rightJoystick.getRawAxis(2);
  private static final DoubleSupplier rightJoystickSlider = () -> rightJoystick.getRawAxis(3);

  //___________________________________________________________________________________________

  private static final CanSparkFlex m_canSparkFlex = new CanSparkFlex();
  private static final LED LED = new LED();
  private static final Servos m_Servo = new Servos();
  private static final DriveTrain driveTrain = new DriveTrain();
  private static final LimitSwitches limitSwitch = new LimitSwitches();
  private static final NetworkTables networkTable = new NetworkTables();
  private static final SwerveDrive swerve = new SwerveDrive();
  private static final Intake intake = new Intake();

  private static final arcadeDriveCommand arcadeDriveCommand = new arcadeDriveCommand(driveTrain);
  private static final tankDriveCommand tankDriveCommand = new tankDriveCommand(driveTrain);
  private static final moveMotor motorMove = new moveMotor(m_canSparkFlex);
  //_________________________________________________________________________________________

  public RobotContainer() {
    configureBindings(); 
  }
  
  private void configureBindings() {
    // intake.setDefaultCommand(new ParallelCommandGroup(
    //   intake.moveMotorCommand(leftTrigger, rightTrigger),
    //   intake.collectMotorCommand(lBumper, rBumper)
    // ));

    //driveTrain.setDefaultCommand(arcadeDriveCommand);

    bButton.whileTrue(intake.setIntakeHeight(() -> 30));
    //intake.setDefaultCommand(intake.collectMotorCommand(lBumper, rBumper));
  }
}
