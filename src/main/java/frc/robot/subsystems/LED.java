package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import java.util.function.DoubleSupplier;

public class LED extends SubsystemBase {

    private static NetworkTables networkTable;
    
        double xaxis;
        int counter;
        int[] LEDShift;
        private AddressableLED realLED;
        private AddressableLEDBuffer realLEDBuffer;
    
        StringPublisher ledStatus;
        
        public LED() {
    
            counter = 0;
            LEDShift = new int[] { 0, 1, 1, 1, 0 };
    
            realLED = new AddressableLED(1);
            realLEDBuffer = new AddressableLEDBuffer(5);
    
            realLED.setLength(realLEDBuffer.getLength());
            realLED.setData(realLEDBuffer);
            realLED.start();
    
            networkTable = new NetworkTables();

            ledStatus = networkTable.getStringPublisher("LED_Status");
            

            DataLogManager.start();
            DriverStation.startDataLog(DataLogManager.getLog());

            ledStatus.set("off");
            SmartDashboard.putNumber("pi", 3.14);
        }
    //___________________________________________________________________________________________
    //Rainbow Pattern
    public void rainbow() {
        LEDPattern rainbow = LEDPattern.rainbow(255, 255); //The first value is saturation, second value is brightness. Max of 255
        /*
         * Calculate this by finding the length of your LED Strip. 
         * Divide 100 by that number and then multiply by the number of leds on your strip that you measured. 
         * Then divide that number by one. 
         * That number represents the amount of LEDs per meter of strip, 
         * which in this case is rounded to 58.8. 
         * Dividing one by this number is to create a ratio that says "for every one meter, there are 58.8 LEDs"
         */
        Distance kLedSpacing = Meters.of(1 / 58.8); 
        LEDPattern scrollingRainbow = rainbow.scrollAtAbsoluteSpeed(MetersPerSecond.of(0.1), kLedSpacing); //creates scrolling rainbow effect. Change the MetersPerSecond.of() variable to control speed.

        scrollingRainbow.applyTo(realLEDBuffer);
        realLED.setData(realLEDBuffer);
        ledStatus.set("rainbow");
    }
    //Turns LED Off
    public void setLEDOff() {
        for (int i = 0; i < realLEDBuffer.getLength(); i++) {

            realLEDBuffer.setRGB(i, 0, 0, 0);

        }

        realLED.setData(realLEDBuffer);
    }
    
    //Select a number that will correspond to a color. The LED will be set to that color.
    public LEDPattern colorBase(int colorNumber) {
        switch (colorNumber) {
            case 0:
                ledStatus.set("red");
                return LEDPattern.solid(Color.kRed);
            case 1:
                ledStatus.set("orange");
                return LEDPattern.solid(Color.kOrange);
            case 2:
                ledStatus.set("yellow");
                return LEDPattern.solid(Color.kYellow);
            case 3:
                ledStatus.set("green");
                return LEDPattern.solid(Color.kGreen);
            case 4:
                ledStatus.set("blue");
                return LEDPattern.solid(Color.kBlue);
            case 5:
                ledStatus.set("purple");
                return LEDPattern.solid(Color.kPurple);
            case 6:
                ledStatus.set("grey");
                return LEDPattern.solid(Color.kGray);
            case 7:
                ledStatus.set("off");
                return LEDPattern.solid(Color.kBlack);
            case 8:
                ledStatus.set("white");
                return LEDPattern.solid(Color.kWhite);
            default:
                ledStatus.set("off");
                return LEDPattern.solid(Color.kBlack);
        }
    }
    public void setColor(int colorNumber) {
        LEDPattern color = colorBase(colorNumber);

        color.applyTo(realLEDBuffer);

        realLED.setData(realLEDBuffer);
    }
    public void breathingEffect(int colorNumber) {
        LEDPattern base = colorBase(colorNumber);
        LEDPattern pattern = base.breathe(Seconds.of(2));

        pattern.applyTo(realLEDBuffer);
        realLED.setData(realLEDBuffer);
    }
    
    //___________________________________________________________________________________________
    //Commands

    //solid color command
    public Command solidColor(int colorNumber) {
        return new FunctionalCommand( 
            () -> {
            }, 
            
            () -> {
                setColor(colorNumber);
            }, 
            
            interrupted -> {
                setLEDOff();
                ledStatus.set("off");
            }, 
            
            () -> false, 
            
            this);
    }
    //breatheEffect command
    public Command breatheEffect(int colorNumber) {
        return new FunctionalCommand(

                () -> {

                },

                () -> {
                    breathingEffect(colorNumber);
                },

                interrupted -> {
                    setLEDOff();
                    ledStatus.set("off");
                },

                () -> false,

                this);
    }    //rainbowLED command
    public Command rainbowLED() {
        return new FunctionalCommand(
            () -> {}, 
            
            () -> {
                rainbow();
            }, 
        
            interrupted -> {
                setLEDOff();
                ledStatus.set("off");
            }, 
            
            () -> false, 
        
        this);
    }

    // Ignore this stuff - It's not important------------------------------------------------------------------------------------

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
}
