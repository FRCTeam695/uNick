package frc.robot.subsystems;

import org.littletonrobotics.junction.wpilog.WPILOGWriter.AdvantageScopeOpenBehavior;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class NetworkTables extends SubsystemBase {
    private int minXCount = 0;
    private int maxXCount = 40;

    private int minYCount = 0;
    private int maxYCount = 30;

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
    }

    public NetworkTable getTable(String key) {
        return inst.getTable(key);
    }
    
    public StringPublisher getStringPublisher(String key) {
        return getTable(key).getStringTopic(key).publish();
    }

    @Override
    public void periodic() {

        x = x + xInc;
        if (x == maxXCount) {
            xInc = xInc * -1;
        }
        if (x == minXCount) {
            xInc = xInc * -1;
        }
        y = y + yInc;
        if (y == maxYCount) {
            yInc = yInc * -1;
        }
        if (y == minYCount) {
            yInc = yInc * -1;
        }
        xPub.set(x);
        yPub.set(y); 
    }

}
