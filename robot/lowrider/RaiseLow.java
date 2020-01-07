package frc.robot.lowrider;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RaiseLow extends Command {

  private boolean botup;
  private boolean backup;

  private long initTimeStamp;
  public RaiseLow() {
    // requires(Robot.lowRider);
  }

  @Override
  protected void initialize() {
    System.out.println("begin raise low");
    Robot.lowRider.extendLow();
    initTimeStamp = System.currentTimeMillis();
    botup = false;
    backup = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // System.out.println("Running raise low");
    if (Robot.oi.raiseLowButton.get() && System.currentTimeMillis() - initTimeStamp > 2000 && Robot.lowRider.isFrontNearFloor()) {
      if (botup == false ){
        System.out.println("raise low front");
        Robot.lowRider.retractLowFront();
        initTimeStamp = System.currentTimeMillis();
        botup = true;
      } else if (Robot.lowRider.isBackNearFloor()){
        Robot.lowRider.retractLowBack();
        initTimeStamp = System.currentTimeMillis();
        backup = true;
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (backup == true && System.currentTimeMillis() - initTimeStamp > 100);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("finished raise low");
  }
}
