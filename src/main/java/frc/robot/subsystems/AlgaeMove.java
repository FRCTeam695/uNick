package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AlgaeMove extends SubsystemBase {
    private SparkMax moveMotor;
    private SparkMaxConfig moveConfig;
    public AlgaeMove() {
        moveMotor = new SparkMax(Constants.CANID.moveMotorID, MotorType.kBrushless);
        moveConfig = new SparkMaxConfig();

        configMotors();
    }
    
    public void configMotors() {
        moveConfig
            .inverted(false)
            .smartCurrentLimit(10)
            .idleMode(IdleMode.kBrake);

        moveMotor.configure(moveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public Command moveMotorCommand(DoubleSupplier speed) {

        return new FunctionalCommand(

                () -> {
                },

                () -> {
                    if (speed.getAsDouble() > 0.1) {
                        moveMotor.set(speed.getAsDouble() / 3.0);
                    } else if (speed.getAsDouble() < -0.1) {
                        moveMotor.set(speed.getAsDouble() / 3.0);
                    } else {
                        moveMotor.set(0);
                    }
                },

                interrupted -> {
                    moveMotor.set(0);
                },

                () -> false,

                this);
    }

    public Command moveMotorCommand(DoubleSupplier left, DoubleSupplier right) {

        return new FunctionalCommand(

                () -> {
                },

                () -> {
                    double l = left.getAsDouble();
                    double r = right.getAsDouble();
                    if (l > 0.1) {
                        moveMotor.set(l / 3.0);
                    } else if (r > 0.1) {
                        moveMotor.set((r * -1.0) / 3.0);
                    } else {
                        moveMotor.set(0);
                    }
                },

                interrupted -> {
                    moveMotor.set(0);
                },

                () -> false,

                this);
    }
}
