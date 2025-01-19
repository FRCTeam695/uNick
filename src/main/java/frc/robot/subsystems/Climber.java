package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase{
    private SparkMax winchMotor;
    
    public Climber() {
       winchMotor = new SparkMax(Constants.CANID.winchMotorID, MotorType.kBrushless);
       SparkMaxConfig winchMotorConfig = new SparkMaxConfig();
    }

    public Command winchMotorCommand(DoubleSupplier speed) {

        return new FunctionalCommand(

            () -> {}, 

            () -> {
                winchMotor.set(speed.getAsDouble());
            },

            interrupted -> {
                winchMotor.set(0);
            }, 
            
            () -> false,

            this);
    }
}
