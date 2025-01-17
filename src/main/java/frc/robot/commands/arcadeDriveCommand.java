// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class arcadeDriveCommand extends Command {

  private final DriveTrain driveTrain;

  public arcadeDriveCommand(DriveTrain driveTrain) {
    this.driveTrain = driveTrain;
    addRequirements(driveTrain);
  }

  @Override
  public void initialize() {
    System.out.println("Starting arcadeDriveCommand");
  }

  @Override
  public void execute() {
    double forwardSpeed = RobotContainer.controller.getRawAxis(1);
    double turningSpeed = RobotContainer.controller.getRawAxis(0);
    driveTrain.arcadeDrive(forwardSpeed, turningSpeed);
  }


  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
