package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {

    // To connect to the DriveTrain:
    // 1.) Change team number in driver station to 9995
    // 2.) Disconnect from WireGuard
    // 3.) Activate the drivetrain
    // 4.) Connect to 9995 wireless network

    // To connect to software Roborio:
    // 1.) Change team number in driver station to 695
    // 2.) Connect to WireGuard
    // 3.) Connect to NCS wireless network
    // 4.) Hopefully turn off drivetrain

    private CANSparkMax leftFront = new CANSparkMax(Constants.CANID.leftFrontID, CANSparkLowLevel.MotorType.kBrushless);
    private CANSparkMax leftBack = new CANSparkMax(Constants.CANID.leftBackID, CANSparkLowLevel.MotorType.kBrushless);
    private CANSparkMax rightFront = new CANSparkMax(Constants.CANID.rightFrontID, CANSparkLowLevel.MotorType.kBrushless);
    private CANSparkMax rightBack = new CANSparkMax(Constants.CANID.rightBackID, CANSparkLowLevel.MotorType.kBrushless);

    private RelativeEncoder leftEncoder = leftFront.getEncoder();
    private RelativeEncoder rightEncoder = rightFront.getEncoder();

    private DifferentialDrive differentialDrive;

    public DriveTrain() {
        leftFront.restoreFactoryDefaults();
        leftBack.restoreFactoryDefaults();
        rightFront.restoreFactoryDefaults();
        rightBack.restoreFactoryDefaults();

        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);

        leftBack.follow(leftFront);
        rightBack.follow(rightFront);

        differentialDrive = new DifferentialDrive(leftFront, rightFront);

        rightFront.setInverted(false);
        leftFront.setInverted(true);
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        differentialDrive.tankDrive(leftSpeed, rightSpeed);
    }

    public void arcadeDrive(double forwardSpeed, double rotationSpeed) {
        differentialDrive.arcadeDrive(forwardSpeed, rotationSpeed);
    }
    
}