package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

import java.util.function.DoubleSupplier;

public class LED extends SubsystemBase {

    private int[] LEDShift = { 0, 1, 1, 1, 0 };
    private long counter = 0;

    private AddressableLED realLED;

    private AddressableLEDBuffer realLEDBuffer;

    public LED() {

        realLED = new AddressableLED(1);
        realLEDBuffer = new AddressableLEDBuffer(5);

        realLED.setLength(realLEDBuffer.getLength());
        realLED.setData(realLEDBuffer);
        realLED.start();

    }

    public void setRed(double redBrightness) {

        for (int i = 0; i < realLEDBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red

            realLEDBuffer.setRGB(i, (int) ((255) * (double) (Math.abs(redBrightness))), 0, 0);

        }

        realLED.setData(realLEDBuffer);
    }

    public void setOrange(double orangeBrightness) {
        for (int i = 0; i < realLEDBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for green

            realLEDBuffer.setRGB(i, (int) ((255) * (double) (Math.abs(orangeBrightness))),
                    (int) ((100) * (double) (Math.abs(orangeBrightness))), 0);

        }

        realLED.setData(realLEDBuffer);
    }

    public void setYellow(double yellowBrightness) {
        for (int i = 0; i < realLEDBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for green

            realLEDBuffer.setRGB(i, (int) ((255) * (double) (Math.abs(yellowBrightness))),
                    (int) ((255) * (double) (Math.abs(yellowBrightness))), 0);

        }

        realLED.setData(realLEDBuffer);
    }

    public void setGreen(double greenBrightness) {
        for (int i = 0; i < realLEDBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for green

            realLEDBuffer.setRGB(i, 0, (int) ((255) * (double) (Math.abs(greenBrightness))), 0);

        }

        realLED.setData(realLEDBuffer);
    }

    public void setBlue(double blueBrightness) {
        for (int i = 0; i < realLEDBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for green

            realLEDBuffer.setRGB(i, 0, 0, (int) ((255) * (double) (Math.abs(blueBrightness))));

        }

        realLED.setData(realLEDBuffer);
    }

    public void setPurple(double purpleBrightness) {
        for (int i = 0; i < realLEDBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for green

            realLEDBuffer.setRGB(i, (int) ((160) * (double) (Math.abs(purpleBrightness))),
                    (int) ((32) * (double) (Math.abs(purpleBrightness))),
                    (int) ((240) * (double) (Math.abs(purpleBrightness))));

        }

        realLED.setData(realLEDBuffer);
    }

    public void setWhite(double whiteBrightness) {
        for (int i = 0; i < realLEDBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for green

            realLEDBuffer.setRGB(i, (int) ((255) * (double) (Math.abs(whiteBrightness))),
                    (int) ((255) * (double) (Math.abs(whiteBrightness))),
                    (int) ((255) * (double) (Math.abs(whiteBrightness))));

        }

        realLED.setData(realLEDBuffer);
    }

    public void setLEDOff() {
        for (int i = 0; i < realLEDBuffer.getLength(); i++) {

            realLEDBuffer.setRGB(i, 0, 0, 0);

        }

        realLED.setData(realLEDBuffer);
    }

    // -----------------------------------------------------------------------------------------------

    public double controllerXDirection(double xaxis) {
        return (1 - ((xaxis + 1) / 2));
    }

    public boolean controllerXDirectionBoolLeft(double xaxis) {
        return controllerXDirection(xaxis) > 0.5;
    }

    public boolean controllerXDirectionBoolRight(double xaxis) {
        return controllerXDirection(xaxis) < 0.5;
    }

    public void controllerDirectionRGB(double xaxis, int leftColor, int rightColor) {

        double leftBrightness = xaxis;
        double rightBrightness = xaxis;

        if (Math.round(xaxis * 10) != 0) {

            if (controllerXDirectionBoolRight(xaxis)) {

                if (rightColor == 0) {
                    setRed(rightBrightness);
                }

                if (rightColor == 1) {
                    setOrange(rightBrightness);
                }

                if (rightColor == 2) {
                    setYellow(rightBrightness);
                }

                if (rightColor == 3) {
                    setGreen(rightBrightness);
                }

                if (rightColor == 4) {
                    setBlue(rightBrightness);
                }

                if (rightColor == 5) {
                    setPurple(rightBrightness);
                }

                if (rightColor == 6) {
                    setWhite(rightBrightness);
                }

            }

            if (controllerXDirectionBoolLeft(xaxis)) {

                if (leftColor == 0) {
                    setRed(leftBrightness);
                }

                if (leftColor == 1) {
                    setOrange(leftBrightness);
                }

                if (leftColor == 2) {
                    setYellow(leftBrightness);
                }

                if (leftColor == 3) {
                    setGreen(leftBrightness);
                }

                if (leftColor == 4) {
                    setBlue(leftBrightness);
                }

                if (leftColor == 5) {
                    setPurple(leftBrightness);
                }

                if (leftColor == 6) {
                    setWhite(leftBrightness);
                }

            }

        } else {

            setLEDOff();

        }
    }

    public Command controllerDirectionRGBCommand(DoubleSupplier xaxis, int leftColor, int rightColor) {
        return new FunctionalCommand(

                () -> {

                },

                () -> {

                    controllerDirectionRGB(xaxis.getAsDouble(), leftColor, rightColor);

                },

                interrupted -> {
                },

                () -> false,

                this);
    }

    // __________________________________________________________________________________________

    public void servoRGB(double axis, int leftColor, int rightColor) {

        double leftBrightness = axis;
        double rightBrightness = axis;

        if (Math.round(axis * 10) != 0) {

            if (controllerXDirectionBoolRight(axis)) {

                LEDShiftIterateRight(leftBrightness, rightColor);

            }

            if (controllerXDirectionBoolLeft(axis)) {

                LEDShiftIterateLeft(rightBrightness, leftColor);

            }

        } else {

            setLEDOff();

        }

    }

    public int[] LEDShiftRight(int[] array) {

        int[] newNumbers = { 0, 0, 0, 0, 0 };

        int first = array[0];

        for (int i = 1; i < 5; i++) {
            newNumbers[i - 1] = array[i];
        }

        newNumbers[4] = first;

        return newNumbers;
    }

    public int[] LEDShiftLeft(int[] array) {
        int[] newNumbers = { 0, 0, 0, 0, 0 };

        int last = array[4];

        for (int i = 0; i < 4; i++) {
            newNumbers[i + 1] = array[i];
        }

        newNumbers[0] = last;

        return newNumbers;
    }

    public void LEDShiftIterateRight(double brightness, int rightColor) {

        if ((counter % 10) == 0) {
            LEDShift = LEDShiftRight(LEDShift);

            for (int i = 0; i < 5; i++) {
                if (LEDShift[i] == 1) {
                    if (rightColor == 0) {
                        realLEDBuffer.setRGB(i, (int) ((255) * (double) (Math.abs(brightness))), 0, 0);
                    }

                    if (rightColor == 1) {
                        realLEDBuffer.setRGB(i, (int) ((255) * (double) (Math.abs(brightness))),
                                (int) ((100) * (double) (Math.abs(brightness))), 0);
                    }

                    if (rightColor == 2) {
                        realLEDBuffer.setRGB(i, (int) ((255) * (double) (Math.abs(brightness))),
                                (int) ((255) * (double) (Math.abs(brightness))), 0);
                    }

                    if (rightColor == 3) {
                        realLEDBuffer.setRGB(i, 0, (int) ((255) * (double) (Math.abs(brightness))), 0);
                    }

                    if (rightColor == 4) {
                        realLEDBuffer.setRGB(i, 0, 0, (int) ((255) * (double) (Math.abs(brightness))));
                    }

                    if (rightColor == 5) {
                        realLEDBuffer.setRGB(i, (int) ((160) * (double) (Math.abs(brightness))),
                                (int) ((32) * (double) (Math.abs(brightness))),
                                (int) ((240) * (double) (Math.abs(brightness))));
                    }

                    if (rightColor == 6) {
                        realLEDBuffer.setRGB(i, (int) ((255) * (double) (Math.abs(brightness))),
                                (int) ((255) * (double) (Math.abs(brightness))),
                                (int) ((255) * (double) (Math.abs(brightness))));
                    }

                } else {
                    realLEDBuffer.setRGB(i, 0, 0, 0);
                }

                if (counter > 1000) {
                    counter = 0;
                }

            }

            realLED.setData(realLEDBuffer);
        }

        counter += 1;

    }

    public void LEDShiftIterateLeft(double brightness, int leftColor) {

        if ((counter % 10) == 0) {
            LEDShift = LEDShiftLeft(LEDShift);

            for (int i = 0; i < 5; i++) {
                if (LEDShift[i] == 1) {
                    if (leftColor == 0) {
                        realLEDBuffer.setRGB(i, (int) ((255) * (double) (Math.abs(brightness))), 0, 0);
                    }

                    if (leftColor == 1) {
                        realLEDBuffer.setRGB(i, (int) ((255) * (double) (Math.abs(brightness))),
                                (int) ((100) * (double) (Math.abs(brightness))), 0);
                    }

                    if (leftColor == 2) {
                        realLEDBuffer.setRGB(i, (int) ((255) * (double) (Math.abs(brightness))),
                                (int) ((255) * (double) (Math.abs(brightness))), 0);
                    }

                    if (leftColor == 3) {
                        realLEDBuffer.setRGB(i, 0, (int) ((255) * (double) (Math.abs(brightness))), 0);
                    }

                    if (leftColor == 4) {
                        realLEDBuffer.setRGB(i, 0, 0, (int) ((255) * (double) (Math.abs(brightness))));
                    }

                    if (leftColor == 5) {
                        realLEDBuffer.setRGB(i, (int) ((160) * (double) (Math.abs(brightness))),
                                (int) ((32) * (double) (Math.abs(brightness))),
                                (int) ((240) * (double) (Math.abs(brightness))));
                    }

                    if (leftColor == 6) {
                        realLEDBuffer.setRGB(i, (int) ((255) * (double) (Math.abs(brightness))),
                                (int) ((255) * (double) (Math.abs(brightness))),
                                (int) ((255) * (double) (Math.abs(brightness))));
                    }

                } else {
                    realLEDBuffer.setRGB(i, 0, 0, 0);
                }

                if (counter > 1000) {
                    counter = 0;
                }

            }

            realLED.setData(realLEDBuffer);
        }

        counter += 1;

    }

    public Command LEDShiftCommand(DoubleSupplier axis, int leftColor, int rightColor) {
        return new FunctionalCommand(

                () -> {
                },

                () -> {
                    servoRGB(axis.getAsDouble(), leftColor, rightColor);
                },

                interrupted -> {
                },

                () -> false,

                this);
    }

    // ------------------------------------------------------------------------------------

    public Command setColor(int colorNumber) {
        return new FunctionalCommand(

                () -> {

                },

                () -> {

                    if (colorNumber == 0) {
                        setRed(1);
                    }

                    if (colorNumber == 1) {
                        setOrange(1);
                    }

                    if (colorNumber == 2) {
                        setYellow(1);
                    }

                    if (colorNumber == 3) {
                        setGreen(1);
                    }

                    if (colorNumber == 4) {
                        setBlue(1);
                    }

                    if (colorNumber == 5) {
                        setPurple(1);
                    }

                    if (colorNumber == 6) {
                        setWhite(1);
                    }

                },

                interrupted -> {
                    setLEDOff();
                },

                () -> false,

                this);
    }

    // _________________________________________________

    public void rainbow() {

    }
}
