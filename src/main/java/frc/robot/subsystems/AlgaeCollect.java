package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;

public class AlgaeCollect extends SubsystemBase {
    private SparkMax collectMotor;

    private SparkMaxConfig collectConfig;

    public AlgaeCollect() {
        collectMotor = new SparkMax(Constants.CANID.collectMotorID, MotorType.kBrushless);
        collectConfig = new SparkMaxConfig();

        configMotors();
    }

    public void configMotors() {
        collectConfig
            .smartCurrentLimit(10)
            .idleMode(IdleMode.kBrake);

        collectMotor.configure(collectConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public Command collectMotorCommand(Trigger left, Trigger right) {
        return new FunctionalCommand(

            () -> {},

            () -> {
                Boolean leftBool = left.getAsBoolean();
                Boolean rightBool = right.getAsBoolean();
                if (leftBool && rightBool) {
                    collectMotor.set(0);
                } else if (rightBool) {
                    collectMotor.set(-0.5);
                } else if (leftBool) {
                    collectMotor.set(0.5);
                } else {
                    collectMotor.set(0);
                }
            },

            interrupted -> {
                collectMotor.set(0);
            },

            () -> false,

        this);
    }
}
