package frc.robot.autonomous;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;

public class Pivot extends Command {

  private double p, i, d, integral, previousError, setPoint = 0;
  private boolean acceptable = false;

  private final AHRS navX;
  private double maxSpeed, minSpeed;
  private double tolerance;
  private long checkTime;

  public Pivot(double setPoint) {
    this.navX = RobotMap.NAVX;
    this.setPoint = setPoint;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    maxSpeed = .8; // just guessing here, based on last year's code
    minSpeed = .3;
    tolerance = 1;
    p = 1;
    i = 0;
    d = 0;
    integral = previousError = 0;
    for (double j = setPoint; Math.abs(j - navX.getAngle()) >= 180; ){
      j = j + (360 * Math.signum(navX.getAngle()));
      setPoint = j;
    }
    System.out.println("Begin Pivot, setpoint is: " + setPoint);
    acceptable = false;
    checkTime = System.currentTimeMillis();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    final double angle = navX.getAngle();
    double error = setPoint - angle;
    double rcw = 0;
    double rotation = 0;
    System.out.println("Pivot Error is " + Math.abs(error));
    acceptable = Math.abs(error) < tolerance;
    if (!acceptable){
      integral += error * .02;
      double derivative = (error - previousError) / .02;
      rcw = (p * error) + (i * integral) + (d * derivative);
      double modRcw = Math.abs(rcw) / 45;
      rotation = Math.max(minSpeed, Math.min(modRcw, maxSpeed));
      rotation = rcw < 0 ? -rotation : rotation;
      // System.out.println(" rotation speed is " + rotation);
      RobotMap.DRIVE_TRAIN_MECANUM.driveCartesian(0, 0, -rotation);
      checkTime = System.currentTimeMillis();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return acceptable && System.currentTimeMillis() - checkTime > 500;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    RobotMap.DRIVE_TRAIN_MECANUM.driveCartesian(0, 0, 0);
  }

}
