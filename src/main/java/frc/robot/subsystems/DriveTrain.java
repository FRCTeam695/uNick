package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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

    private SparkMax leftFront = new SparkMax(Constants.CANID.leftFrontID, MotorType.kBrushless);
    private SparkMax leftBack = new SparkMax(Constants.CANID.leftBackID, MotorType.kBrushless);
    private SparkMax rightFront = new SparkMax(Constants.CANID.rightFrontID, MotorType.kBrushless);
    private SparkMax rightBack = new SparkMax(Constants.CANID.rightBackID, MotorType.kBrushless);

    private RelativeEncoder leftEncoder = leftFront.getEncoder();
    private RelativeEncoder rightEncoder = rightFront.getEncoder();

    private DifferentialDrive differentialDrive;

    public DriveTrain() {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);

        differentialDrive = new DifferentialDrive(leftFront, rightFront);

        SparkMaxConfig rightFrontSparkConfig = new SparkMaxConfig();
        SparkMaxConfig leftFrontSparkConfig = new SparkMaxConfig();
        SparkMaxConfig rightBackSparkConfig = new SparkMaxConfig();
        SparkMaxConfig leftBackSparkConfig = new SparkMaxConfig();

        rightFrontSparkConfig.idleMode(IdleMode.kBrake);
        rightFrontSparkConfig.inverted(false);

        rightBackSparkConfig.follow(Constants.CANID.rightFrontID);

        leftFrontSparkConfig.idleMode(IdleMode.kBrake);
        leftFrontSparkConfig.inverted(true);

        leftBackSparkConfig.follow(Constants.CANID.leftFrontID);

        rightFront.configure(rightFrontSparkConfig, SparkBase.ResetMode.kResetSafeParameters,
                SparkBase.PersistMode.kPersistParameters);
        rightBack.configure(rightBackSparkConfig, SparkBase.ResetMode.kResetSafeParameters,
                SparkBase.PersistMode.kPersistParameters);
        leftFront.configure(leftFrontSparkConfig, SparkBase.ResetMode.kResetSafeParameters,
                SparkBase.PersistMode.kPersistParameters);
        leftBack.configure(leftBackSparkConfig, SparkBase.ResetMode.kResetSafeParameters,
                SparkBase.PersistMode.kPersistParameters);

    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        differentialDrive.tankDrive(leftSpeed, rightSpeed);
    }

    public void arcadeDrive(double forwardSpeed, double rotationSpeed) {
        differentialDrive.arcadeDrive(forwardSpeed, rotationSpeed);
    }

    

}