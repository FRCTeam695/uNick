package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
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

    CANSparkMax leftFront = new CANSparkMax(10, CANSparkLowLevel.MotorType.kBrushless);
    CANSparkMax leftBack = new CANSparkMax(12, CANSparkLowLevel.MotorType.kBrushless);
    CANSparkMax rightFront = new CANSparkMax(11, CANSparkLowLevel.MotorType.kBrushless);
    CANSparkMax rightBack = new CANSparkMax(13, CANSparkLowLevel.MotorType.kBrushless);

    public DriveTrain() {
        leftFront.restoreFactoryDefaults();
        leftBack.restoreFactoryDefaults();
        rightFront.restoreFactoryDefaults();
        rightBack.restoreFactoryDefaults();


        leftBack.follow(leftFront);
        rightBack.follow(rightFront);

        //leftFront.setInverted(true);
        // rightFront.setInverted(true);
    }

    // public void tankDrive(double leftSpeed, double rightSpeed) {
    //     differentialDrive.tankDrive(leftSpeed, rightSpeed);
    // }

    public Command tankDriveCommand(DoubleSupplier leftSpeed, DoubleSupplier rightSpeed) {

        return new FunctionalCommand(
                () -> {
                },

                () -> {
                    leftFront.set(leftSpeed.getAsDouble());
                    rightFront.set(rightSpeed.getAsDouble());
                },

                interrupted -> {
                    leftFront.set(0);
                    rightFront.set(0);
                },

                () -> false,

                this);
    }
}