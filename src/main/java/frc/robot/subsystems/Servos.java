package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.function.DoubleSupplier;

public class Servos extends SubsystemBase {

    Servo exampleServo = new Servo(0);

    public Servos() {

    }

    public double controllerXDirection(double xaxis) {
        return (1 - ((xaxis + 1) / 2));
    }

    public Command servoStick(DoubleSupplier xaxis) {
        return new FunctionalCommand(

                () -> {
                },

                () -> {
                    double axis = controllerXDirection(xaxis.getAsDouble());

                    SmartDashboard.putNumber("Servo Speed", axis);

                    if ((axis > 0.6) || (axis < 0.4)) {
                        exampleServo.set(axis);
                    } else {
                        exampleServo.set(0.475);
                    }
                },

                interrupted -> {
                    exampleServo.set(0.475);
                },

                () -> false,

                this);
    }

    public Command servoTurnRight() {
        return new FunctionalCommand(
                () -> {
                },

                () -> {
                    servoRight();
                },

                interrupted -> {
                    servoOff();
                },

                () -> false,

                this);

    }

    public Command servoTurnLeft() {
        return new FunctionalCommand(
                () -> {
                },

                () -> {
                    servoLeft();
                },

                interrupted -> {
                    servoOff();
                },

                () -> false,

                this);

    }

    public void servoRight() {
        exampleServo.set(1);
    }

    public void servoLeft() {
        exampleServo.set(0);
    }

    public void servoOff() {
        exampleServo.set(0.475);
    }
}
