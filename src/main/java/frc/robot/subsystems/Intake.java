package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

    private SparkMax moveMotor;
    private SparkMaxConfig moveConfig;

    private SparkMax collectMotor;
    private SparkMaxConfig collectConfig;

    private RelativeEncoder encoder;

    public Intake() {

        moveMotor = new SparkMax(Constants.CANID.moveMotorID, MotorType.kBrushless);
        moveConfig = new SparkMaxConfig();

        collectMotor = new SparkMax(Constants.CANID.collectMotorID, MotorType.kBrushless);
        collectConfig = new SparkMaxConfig();

        encoder = moveMotor.getEncoder();
        encoder.setPosition(0);

        configMotors();
    }

    public void configMotors() {

        moveConfig
        .smartCurrentLimit(10)
        .idleMode(IdleMode.kBrake);

        moveMotor.configure(moveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        collectConfig
            .smartCurrentLimit(40)
            .idleMode(IdleMode.kBrake);

        collectMotor.configure(collectConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public Command getEncoder() {
        return new FunctionalCommand(
            () -> {}, 
            
            () -> {
                System.out.println(encoder.getPosition());
            }, 
            
            interrupted -> {}, 
            
            () -> false, 
            
        this);
    }

    public Command setIntakeHeight() {
        return new FunctionalCommand(null, null, null, null, null);
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
