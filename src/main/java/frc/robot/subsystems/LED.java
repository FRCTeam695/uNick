package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

import java.util.function.DoubleSupplier;

public class LED extends SubsystemBase {

    int[] LEDShift = {1, 1, 1, 0, 0 };
    long counter = 0;

    AddressableLED realLED;

    AddressableLEDBuffer realLEDBuffer;

    public LED() {

        realLED = new AddressableLED(1);
        realLEDBuffer = new AddressableLEDBuffer(5);

        realLED.setLength(realLEDBuffer.getLength());
        realLED.setData(realLEDBuffer);
        realLED.start();

    }

    public void setRed(long redBrightness) {
        for (var i = 0; i < realLEDBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red

            realLEDBuffer.setHSV(i, 0, 255, (int) ((redBrightness) - 5) * 2);

            realLED.setData(realLEDBuffer);
        }
    }

    public void setGreen(long greenBrightness) {
        for (var i = 0; i < realLEDBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for green

            realLEDBuffer.setHSV(i, 60, 255, (int) ((greenBrightness) - 5) * 2);

            realLED.setData(realLEDBuffer);
        }
    }

    public double controllerXDirection(double xaxis) {
        return (1 - ((xaxis + 1) / 2));
    }

    public boolean controllerXDirectionBoolRed(double xaxis) {
        return controllerXDirection(xaxis) > 0.5;
    }

    public boolean controllerXDirectionBoolGreen(double xaxis) {
        return controllerXDirection(xaxis) < 0.5;
    }

    public void setLEDOff() {
        for (var i = 0; i < realLEDBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red

            realLEDBuffer.setHSV(i, 0, 255, 0);

            realLED.setData(realLEDBuffer);

        }
    }

    public void controllerDirectionRGB(double xaxis) {

        long redBrightness = Math.round(((128 * controllerXDirection(xaxis))));
        long greenBrightness = Math.round(128 * (0.6 - controllerXDirection(xaxis)));

        // System.out.println(redBrightness);
        // System.out.println(greenBrightness);
        if (Math.round(xaxis * 10) != 0) {

            // System.out.println((1 - ((controller.getLeftX() + 1) / 2)) * 10);

            if (controllerXDirectionBoolGreen(xaxis)) {
                // System.out.println(System.out.format("Green Brightness: %d",
                // greenBrightness));
                setGreen(greenBrightness);

            }

            if (controllerXDirectionBoolRed(xaxis)) {
                // System.out.println(System.out.format("Red Brightness: %d", redBrightness));
                setRed(redBrightness);

            }

            // moving leds that move in the direction of the stick, while moving the servo,
            // while moving the servo and controlling brightness at variable speed

            // clean up code

        } else {

            setLEDOff();

        }
    }

    public Command controllerDirectionRGBCommand(DoubleSupplier xaxis) {
        return new FunctionalCommand(

                () -> {

                },

                () -> {

                    controllerDirectionRGB(xaxis.getAsDouble());

                },

                interrupted -> {
                },

                () -> false,

                this);
    }

    public void servoRGB(double axis) {

        long redBrightness = Math.round(((128 * controllerXDirection(axis))));
        long greenBrightness = Math.round(128 * (0.6 - controllerXDirection(axis)));

        if (Math.round(axis * 10) != 0) { //might just be able to remove this but idk

            if (controllerXDirectionBoolGreen(axis)) {
                // System.out.println(System.out.format("Green Brightness: %d",
                // greenBrightness));
                LEDShiftIterateRight(greenBrightness);

            }

            if (controllerXDirectionBoolRed(axis)) {
                // System.out.println(System.out.format("Red Brightness: %d", redBrightness));
                LEDShiftIterateLeft(redBrightness);

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
    public void LEDShiftIterateRight(long brightness) {

        if ((counter % 10) == 0) {
            LEDShift = LEDShiftRight(LEDShift);

            for (int i = 0; i < LEDShift.length; i++) {
                if (LEDShift[i] == 1) {
                    realLEDBuffer.setHSV(i, 60, 255, (int) ((brightness) - 5) * 2);
                } else {
                    realLEDBuffer.setHSV(i, 0, 255, 0);
                }

                // System.out.println(counter);
                if (counter > 1000) {
                    counter = 0;
                }
            }
        }

        counter += 1;

        realLED.setData(realLEDBuffer);
    }

    public void LEDShiftIterateLeft(long brightness) {

        if ((counter % 10) == 0) {
            LEDShift = LEDShiftLeft(LEDShift);

            for (int i = 0; i < LEDShift.length; i++) {
                if (LEDShift[i] == 1) {
                    realLEDBuffer.setHSV(i, 0, 255, (int) ((brightness) - 5) * 2);
                } else {
                    realLEDBuffer.setHSV(i, 0, 255, 0);
                }

                // System.out.println(counter);
                if (counter > 1000) {
                    counter = 0;
                }
            }
        }

        counter += 1;

        realLED.setData(realLEDBuffer);
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
