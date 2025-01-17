package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CanSparkFlex;
import frc.robot.subsystems.DriveTrain;

public class moveMotor extends Command {

    private final CanSparkFlex sparkFlex;

  public moveMotor(CanSparkFlex sparkFlex) {
    this.sparkFlex = sparkFlex;
    addRequirements(sparkFlex);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {

    double a = RobotContainer.leftXAxis.getAsDouble();

    if ((a > 0.1) || (a < -0.1)) {
        sparkFlex.setSpeed(a);
      } else {
        sparkFlex.setSpeed(0);
      }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}