package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

import java.util.function.DoubleSupplier;

public class LEDShift extends SubsystemBase {

    int[] LEDShift = {1, 1, 1, 0, 0 };
    long counter = 0;


    AddressableLED ledShifter;

    AddressableLEDBuffer ledShifterBuffer;

    public LEDShift() {

        ledShifter = new AddressableLED(1);

        ledShifterBuffer = new AddressableLEDBuffer(5);
    }

    public double controllerXDirection(double axis) {
        return (1 - ((axis + 1) / 2));
    }

    public boolean controllerXDirectionBoolRed(double axis) {
        return controllerXDirection(axis) > 0.5;
    }

    public boolean controllerXDirectionBoolGreen(double axis) {
        return controllerXDirection(axis) < 0.5;
    }

    public void setLEDOff() {
        for (var i = 0; i < ledShifterBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red

            ledShifterBuffer.setHSV(i, 0, 255, 0);

            ledShifter.setData(ledShifterBuffer);
        }
    }

    public void servoRGB(double axis) {

        long redBrightness = Math.round(((128 * controllerXDirection(axis))));
        long greenBrightness = Math.round(128 * (0.6 - controllerXDirection(axis)));

        if (Math.round(axis * 10) != 0) { //might just be able to remove this but idk

            if (controllerXDirectionBoolGreen(axis)) {
                // System.out.println(System.out.format("Green Brightness: %d",
                // greenBrightness));
                LEDShiftIterateRight();

            }

            if (controllerXDirectionBoolRed(axis)) {
                // System.out.println(System.out.format("Red Brightness: %d", redBrightness));
                LEDShiftIterateLeft();

            }

            // moving leds that move in the direction of the stick, while moving the servo,
            // while moving the servo and controlling brightness at variable speed

            // clean up code

        } else {

            setLEDOff();

        }

    }

    public int[] LEDShiftRight(int[] array) {

        int[] newNumbers = { 0, 0, 0, 0, 0 };

        int first = array[0];

        for (int i = 1; i < array.length; i++) {
            newNumbers[i - 1] = array[i];
        }
        // {1, 1, 1, 0, 0}
        // {0, 1, 1, 1, 0}
        newNumbers[array.length - 1] = first;

        return newNumbers;
    }

    public int[] LEDShiftLeft(int[] array) {
        int[] newNumbers = { 0, 0, 0, 0, 0 };

        int last = array[array.length - 1];

        for (int i = 0; i < array.length - 1; i++) {
            newNumbers[i + 1] = array[i];
        }

        // {0, 1, 1, 1, 0}
        // {0, 0, 1, 1, 1}
        // {1, 0, 0, 1, 1}
        newNumbers[0] = last;

        return newNumbers;
    }

    // MAIN ENTRY
    public void LEDShiftIterateRight() {

        if ((counter % 10) == 0) {
            LEDShift = LEDShiftRight(LEDShift);

            for (int i = 0; i < LEDShift.length; i++) {
                if (LEDShift[i] == 1) {
                    ledShifterBuffer.setHSV(i, 0, 255, 255);
                } else {
                    ledShifterBuffer.setHSV(i, 0, 255, 0);
                }

                // System.out.println(counter);
                if (counter > 1000) {
                    counter = 0;
                }
            }
        }

        counter += 1;

        ledShifter.setData(ledShifterBuffer);
    }

    public void LEDShiftIterateLeft() {

        if ((counter % 10) == 0) {
            LEDShift = LEDShiftLeft(LEDShift);

            for (int i = 0; i < LEDShift.length; i++) {
                if (LEDShift[i] == 1) {
                    ledShifterBuffer.setHSV(i, 0, 255, 255);
                } else {
                    ledShifterBuffer.setHSV(i, 0, 255, 0);
                }

                // System.out.println(counter);
                if (counter > 1000) {
                    counter = 0;
                }
            }
        }

        counter += 1;

        ledShifter.setData(ledShifterBuffer);
    }

    public Command LEDShiftCommand(DoubleSupplier axis) {
        return new FunctionalCommand(

                () -> {
                },

                () -> {
                    servoRGB(axis.getAsDouble());
                },

                interrupted -> {
                },

                () -> false,

                this);
    }
}
