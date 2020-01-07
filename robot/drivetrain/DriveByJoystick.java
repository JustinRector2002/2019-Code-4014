package frc.robot.drivetrain;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveByJoystick extends Command {

  private final AHRS navX;

  public DriveByJoystick() {
    this.navX = RobotMap.NAVX;
    requires(Robot.driveTrain);
  }

  protected void initialize(){
    navX.reset();
    Robot.driveTrain.resetEncoders();
    Robot.lowRider.retractLowBack();
    Robot.lowRider.retractLowFront();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.oi.justForwardButton.get()){
      Robot.driveTrain.driveStraight(-.4);
    }
     else {
      Robot.driveTrain.drive(Robot.oi.driverJoystick,navX.getAngle());
    }
    // if(Robot.oi.frontleftButton.get()){
    //   System.out.println("front left");
    //   Robot.driveTrain.fl();
    // }
    // else if(Robot.oi.frontrightButton.get()){
    //   System.out.println("front right");
    //   Robot.driveTrain.fr();
    // }
    // else if(Robot.oi.backleftButton.get()){
    //   System.out.println("back left");
    //   Robot.driveTrain.bl();
    // }
    // else if(Robot.oi.backrightButton.get()){
    //   System.out.println("back right");
    //   Robot.driveTrain.br();
    // } else {
    //   Robot.driveTrain.stop();
    // }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false; //never gonna stop driving
  }

}
