# uNick
Currently there are 7 subsystems. Code has only been edited in these subsystems and in RobotContainer.java to run and test these subsystems and their commands.

###CanSparkFlex.java
This is the subsystem that controls the can spark flex motor. moveMotor() will only spin the motor in one direction at the press of a button. closedLoopControl() is the PIDControl method that involves a properly tuned motor to reach a certain setPoint velocity.

###DriveTrain.java
This is the tank drive subsystem. tankDriveCommand is the only command that is complete and allows the user to control the tank drive chassis

###LED.java
This subsystem controls the LEDS. There are multiple commands and methods which control leds, more details in comments

###LimitSwitches.java
There is only a method that allows the implementation of limitSwitches.

###NetworkTables.java
Allows for the use of network tables

###Servos.java
Servo subsystem that allows full control of the servo motor

###SwerveDrive.java
Allows the implementation of a swerve drive subsystem.
