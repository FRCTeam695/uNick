package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class tankDriveCommand extends Command {

  private final DriveTrain driveTrain;

  public tankDriveCommand(DriveTrain driveTrain) {
    this.driveTrain = driveTrain;
    addRequirements(driveTrain);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double leftGroup = RobotContainer.controller.getRawAxis(Constants.driveConstants.leftAxis);
    double rightGroup = RobotContainer.controller.getRawAxis(Constants.driveConstants.rightAxis);
    driveTrain.tankDrive(leftGroup, rightGroup);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
