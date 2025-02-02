package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
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

    private SparkClosedLoopController pidControl;

    public Intake() {

        moveMotor = new SparkMax(Constants.CANID.moveMotorID, MotorType.kBrushless);
        moveConfig = new SparkMaxConfig();

        collectMotor = new SparkMax(Constants.CANID.collectMotorID, MotorType.kBrushless);
        collectConfig = new SparkMaxConfig();

        encoder = moveMotor.getEncoder();
        encoder.setPosition(0);

        pidControl = moveMotor.getClosedLoopController();

        configMotors();
    }

    public void configMotors() {

        moveConfig
                .smartCurrentLimit(20)
                .idleMode(IdleMode.kBrake);
        moveConfig
            .closedLoop.pid(0.015, 0, 0);

        moveMotor.configure(moveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        collectConfig
                .smartCurrentLimit(5)
                .idleMode(IdleMode.kBrake);

        collectMotor.configure(collectConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public Command setIntakeHeight(DoubleSupplier ref) {
        return new FunctionalCommand(
            () -> {
                
            },

            () -> {
                
                pidControl.setReference(ref.getAsDouble(), ControlType.kPosition);
                collectMotor.set(-0.5);
                
                // if (encoder.getPosition() < ref.getAsDouble()) {
                //     moveMotor.set(0.1);
                // }
            },

            interrupted -> {
                pidControl.setReference(0, ControlType.kPosition);
                collectMotor.set(0);
                //moveMotor.set(0);
            },

            () -> false,

        this);
    }

    public Command collectMotorCommand(Trigger left, Trigger right) {
        return new FunctionalCommand(

                () -> {
                },

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
