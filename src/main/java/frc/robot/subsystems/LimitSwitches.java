package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimitSwitches extends SubsystemBase {
    DigitalInput limitSwitch;

    public LimitSwitches() {
        limitSwitch = new DigitalInput(0);
    }

    public Command limitSwitch(DoubleSupplier control) {
        return new FunctionalCommand(
            () -> {},

            () -> {
                if (control.getAsDouble() != 0) {
                    if (limitSwitch.get()) {
                        System.out.println("THE LIMIT SWITCH WORKS XDXD");
                    } else {
                        System.out.println("NOT WORKING :(((");
                    }
                }
            },

            interrupted -> {},

            () -> false,

            this);
    }
}
