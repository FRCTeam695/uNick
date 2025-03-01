package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import java.util.function.DoubleSupplier;

public class LED extends SubsystemBase {

    private int[] LEDShift = { 0, 1, 1, 1, 0 };
    private long counter = 0;

    private AddressableLED realLED;
    private AddressableLEDBuffer realLEDBuffer;

    private double xaxis;

    public LED() {

        realLED = new AddressableLED(Constants.LEDConstants.ledConstant);
        realLEDBuffer = new AddressableLEDBuffer(5);

        realLED.setLength(realLEDBuffer.getLength());
        realLED.setData(realLEDBuffer);
        realLED.start();

    }

    public void setYellow() {
        LEDPattern red = LEDPattern.solid(Color.kYellow);

        // Apply the LED pattern to the data buffer
        red.applyTo(realLEDBuffer);

        // Write the data to the LED strip
        realLED.setData(realLEDBuffer);
    }

    public void breathingYellow() {
        LEDPattern base = LEDPattern.solid(Color.kYellow);
        LEDPattern pattern = base.breathe(Seconds.of(2));

        pattern.applyTo(realLEDBuffer);
        realLED.setData(realLEDBuffer);
    }

    public void rainbow() {
        LEDPattern rainbow = LEDPattern.rainbow(255, 255);
        Distance kLedSpacing = Meters.of(1 / 58.8);
        LEDPattern scrollingRainbow = rainbow.scrollAtAbsoluteSpeed(MetersPerSecond.of(0.1), kLedSpacing);

        scrollingRainbow.applyTo(realLEDBuffer);
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

    public Command controllerDirectionRGBCommand(DoubleSupplier xaxis, int leftColor, int rightColor) {
        return new FunctionalCommand(

                () -> {

                },

                () -> {

                    this.xaxis = xaxis.getAsDouble();

                    if (controllerXDirectionBoolRight(this.xaxis)) {

                        setColor(leftColor);

                    }

                    if (controllerXDirectionBoolLeft(this.xaxis)) {

                        setColor(rightColor);

                    }

                },

                interrupted -> {
                    setLEDOff();
                },

                () -> false,

                this);
    }

    // __________________________________________________________________________________________

    public void shiftRGB(double axis, int leftColor, int rightColor) {

        if (Math.round(axis * 10) != 0) {

            if (controllerXDirectionBoolRight(axis)) {

                LEDShiftIterateRight(axis, rightColor);

            }

            if (controllerXDirectionBoolLeft(axis)) {

                LEDShiftIterateLeft(axis, leftColor);

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
                    shiftRGB(axis.getAsDouble(), leftColor, rightColor);
                },

                interrupted -> {
                },

                () -> false,

                this);
    }

    // ------------------------------------------------------------------------------------

    public void setColor(int colorNumber) {

        if (colorNumber == 0) {
            setYellow();
        }
        if (colorNumber == 1) {
            rainbow();
        }
    }

    public Command breatheYellow() {
        return new FunctionalCommand(

                () -> {

                },

                () -> {
                    breathingYellow();
                },

                interrupted -> {
                    setLEDOff();
                },

                () -> false,

                this);
    }

    public Command rainbowLED() {
        return new FunctionalCommand(
            () -> {}, 
            
            () -> {
                rainbow();
            }, 
        
            interrupted -> {
                setLEDOff();
            }, 
            
            () -> false, 
        
        this);
    }
}
