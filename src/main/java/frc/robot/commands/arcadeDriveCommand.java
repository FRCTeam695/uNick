package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class arcadeDriveCommand extends Command {

  private final DriveTrain driveTrain;

  public arcadeDriveCommand(DriveTrain driveTrain) {
    this.driveTrain = driveTrain;
    addRequirements(driveTrain);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double forwardSpeed = RobotContainer.controller.getRawAxis(Constants.driveConstants.leftAxis);
    double turningSpeed = RobotContainer.controller.getRawAxis(Constants.driveConstants.rightAxis);
    driveTrain.arcadeDrive(forwardSpeed, turningSpeed);
  }


  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
