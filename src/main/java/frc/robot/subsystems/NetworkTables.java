package frc.robot.subsystems;

import javax.swing.text.StyleContext.SmallAttributeSet;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.IntegerPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class NetworkTables extends SubsystemBase {
    private int count = 0;
    private int maxCount = 50;
    private double xInc = 0.5;
    private double yInc = 1;
    private double x = 0;
    private double y = 0;

    private NetworkTableInstance inst = NetworkTableInstance.getDefault();
    private NetworkTable table = inst.getTable("frc695_test_table");

    private DoublePublisher xPub = table.getDoubleTopic("x").publish();

    private DoublePublisher yPub = table.getDoubleTopic("y").publish();

    

    public NetworkTables() {
        xPub.set(0);
        yPub.set(0);

        SmartDashboard.putNumber("x", x);
        SmartDashboard.putNumber("y", y);
    }

    @Override
    public void periodic() {

        x = x + xInc;
        if (x == 40) {
            xInc = xInc * -1;
        }
        if (x == 0) {
            xInc = xInc * -1;
        }
        y = y + yInc;
        if (y == 30) {
            yInc = yInc * -1;
        }
        if (y == 0) {
            yInc = yInc * -1;
        }
        xPub.set(x);
        yPub.set(y);
        SmartDashboard.putNumber("x", x);
        SmartDashboard.putNumber("y", y);
    }

}
