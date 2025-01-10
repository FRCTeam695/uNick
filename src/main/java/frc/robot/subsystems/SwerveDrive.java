package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

public class SwerveDrive extends SubsystemBase {

    DoubleSupplier FWD;
    DoubleSupplier STR;
    DoubleSupplier RCW;

    double width;
    double length;
    double hypotenuse;
    double angle;

    TalonFX speedMotor;
    TalonFX directionMotor;

    PIDController pid;

    CANcoder encoder;

    public SwerveDrive() {
        width = 100 / 2; // need to get the real numbers
        length = 100 / 2;
        hypotenuse = Math.sqrt(Math.pow(width, 2) + Math.pow(length, 2));
        angle = Math.atan(width / length);

        speedMotor = new TalonFX(23);
        directionMotor = new TalonFX(22);

        pid = new PIDController(0.005, 0, 0);

        encoder = new CANcoder(21);
        encoder.setPosition(0);

        pid.enableContinuousInput(-180.0, 180.0);
    }

    public double realFWD(DoubleSupplier FWD, DoubleSupplier RCW) {

        double RCW_FWD = RCW.getAsDouble() * (length / hypotenuse);

        SmartDashboard.putNumber("RCW_FWD", RCW_FWD);
        SmartDashboard.putNumber("finalFWD", FWD.getAsDouble() + RCW_FWD);
        return FWD.getAsDouble() + RCW_FWD;
    }

    public double realSTR(DoubleSupplier STR, DoubleSupplier RCW) {

        double RCW_STR = RCW.getAsDouble() * (width / hypotenuse);

        SmartDashboard.putNumber("finalSTR", STR.getAsDouble() - RCW_STR);
        SmartDashboard.putNumber("RCW_STR", RCW_STR);
        return STR.getAsDouble() - RCW_STR;
    }

    public double speed(DoubleSupplier FWD, DoubleSupplier STR) {
        return Math.sqrt((Math.pow(FWD.getAsDouble(), 2) + Math.pow(STR.getAsDouble(), 2)));
    }

    public double speedAngle(DoubleSupplier FWD, DoubleSupplier STR, DoubleSupplier RCW) {
        return Math.atan2(realSTR(STR, RCW), realFWD(FWD, RCW)) * 180 / Math.PI;
    }

    public Command test(DoubleSupplier FWD, DoubleSupplier STR, DoubleSupplier RCW) {
        return new FunctionalCommand(() -> {
        }, () -> {
            realFWD(FWD, RCW);
            realSTR(STR, RCW);
        }, interrupted -> {
        }, () -> false, this);
    }

    public Command swerveCommand(DoubleSupplier FWD, DoubleSupplier STR, DoubleSupplier RCW) {
        return new FunctionalCommand(
            () -> {},

            () -> {

                if ((-0.05 < RCW.getAsDouble() && RCW.getAsDouble() < 0.05) && (-0.1 < FWD.getAsDouble() && FWD.getAsDouble() < 0.1) && (-0.1 < STR.getAsDouble() && STR.getAsDouble() < 0.1)) {
                    speedMotor.set(0);
                    directionMotor.set(0);
                }

                if (-0.1 < speed(FWD, STR) && speed(FWD, STR) < 0.1) {
                    speedMotor.set(0);
                } else {
                    if (speed(FWD, STR) < 0) {
                        speedMotor.set(speed(FWD, STR) * -1);
                    } else {
                        speedMotor.set(speed(FWD, STR));
                    }
                }

                if (-0.1 < pid.calculate((encoder.getAbsolutePosition().getValueAsDouble() * 360), speedAngle(FWD, STR, RCW)) && pid.calculate((encoder.getAbsolutePosition().getValueAsDouble() * 360), speedAngle(FWD, STR, RCW)) < 0.1) {
                    directionMotor.set(0);
                } else {
                    directionMotor.set(pid.calculate((encoder.getAbsolutePosition().getValueAsDouble() * 360),
                    speedAngle(FWD, STR, RCW)));
                }

            },

            interrupted -> {},

            () -> false,

        this);
    }
}
