package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  public Joystick driverJoystick = new Joystick(0);
  public Button intakeButton = new JoystickButton(driverJoystick, 2);
  public Button outpuButton = new JoystickButton(driverJoystick, 1);
  public Button DriveOffPositionButton = new JoystickButton(driverJoystick, 3);
  public Button limeLightButton = new JoystickButton(driverJoystick, 4);
  public Button hatchInButton = new JoystickButton(driverJoystick, 5);
  public Button hatchOutButton = new JoystickButton(driverJoystick, 6);
  public Button justForwardButton = new JoystickButton(driverJoystick, 11);
  public Button printPositionButton = new JoystickButton(driverJoystick, 12);
  public Button raiseLowButton = new JoystickButton(driverJoystick, 7);
  public Button raiseHighButton = new JoystickButton(driverJoystick, 8);

  // public Button frontleftButton = new JoystickButton(driverJoystick, 7);
  // public Button frontrightButton = new JoystickButton(driverJoystick, 8);
  // public Button backleftButton = new JoystickButton(driverJoystick, 11);
  // public Button backrightButton = new JoystickButton(driverJoystick, 12);
 
  public Joystick launchpad1 = new Joystick(1); 
  public Joystick launchpad2 = new Joystick(2);

  //rocket angle is 28.75 degrees
  public Button gButton = new JoystickButton(launchpad1, 16);
  public Button lowHatch0Button = new JoystickButton(launchpad1, 4);
  public Button LowHatch2875Button = new JoystickButton(launchpad2, 2);
  public Button LowHatch90Button = new JoystickButton(launchpad1, 12);
  public Button LowHatch15125Button = new JoystickButton(launchpad2, 12);
  public Button LowHatch180Button = new JoystickButton(launchpad2, 1);//3 buttons go here
  public Button LowHatch20875Button = new JoystickButton(launchpad1, 9);
  public Button LowHatch270Button = new JoystickButton(launchpad2, 13);
  public Button LowHatch33125Button = new JoystickButton(launchpad1, 3);
  public Button MidHatch2875Button = new JoystickButton(launchpad2, 3);
  public Button MidHatch15125Button = new JoystickButton(launchpad2, 11);
  public Button MidHatch20875Button = new JoystickButton(launchpad1, 10);
  public Button MidHatch33125Button = new JoystickButton(launchpad1, 2);
  public Button HighHatch2875Button = new JoystickButton(launchpad2, 4);
  public Button HighHatch15125Button = new JoystickButton(launchpad2, 10);
  public Button HighHatch20875Button = new JoystickButton(launchpad1, 11);
  public Button HighHatch33125Button = new JoystickButton(launchpad1, 1);
  public Button GrabCargoButton = new JoystickButton(launchpad2, 5);
  public Button LowCargo0Button = new JoystickButton(launchpad1, 5);
  public Button LowCargo90ButtonRocket = new JoystickButton(launchpad2, 8);
  public Button LowCargo90Button = new JoystickButton(launchpad1, 13);
  public Button LowCargo270ButtonRocket = new JoystickButton(launchpad1, 14);
  public Button LowCargo270Button = new JoystickButton(launchpad2, 14);
  public Button MidCargo90Button = new JoystickButton(launchpad2, 6);
  public Button MidCargo270Button = new JoystickButton(launchpad1, 15);
  public Button HighCargo90Button = new JoystickButton(launchpad2, 7);
  public Button HighCargo270Button = new JoystickButton(launchpad1, 8);

  public Button drivePositionButton = new JoystickButton(launchpad2, 9);

  // public Button DoneButton = new JoystickButton(launchpad2, 1);







}
