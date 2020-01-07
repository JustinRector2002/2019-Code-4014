package frc.robot.autonomous;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.vision.LimeLight;

public class AlignByVision extends Command {

  private double p , i , d = 1;
  private double angleIntegral , xIntegral , previousAngleError, previousXError = 0;
  private boolean acceptableAngle =  false;
  private boolean acceptableX = false;
  private boolean acceptabley = false;
  private double angleTolerance = 1; //Placeholder
  private double xTolerance = 2.5; //Placeholder
  private final LimeLight limeLight;
  private final AHRS navX;
  private double correctionAngleSign = 1;
  private double targetAngle;
  private double heightDifference;
  private double cameraAngle = -15;
  private double xDifference;
  private long timeCheck;
  private double prevX;

  public AlignByVision() {
    this.limeLight = Robot.limeLight;
    this.navX = RobotMap.NAVX;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.targetAngle = -1* Robot.driveTrain.getAngle();
    this.heightDifference = Robot.driveTrain.getHeight();
    this.xDifference = Robot.driveTrain.getX();
    System.out.println("target angle is " + targetAngle);
    System.out.println("height difference is " + heightDifference);
    p = 1;
    i = .6;
    d = 0;
    for (double j = targetAngle; Math.abs(j - navX.getAngle()) >= 180; ){
      j = j + (360 * Math.signum(navX.getAngle()));
      targetAngle = j;
    }
    previousAngleError = targetAngle - navX.getAngle();
    timeCheck = System.currentTimeMillis();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double correctionAngle = targetAngle - navX.getAngle();
    double correctionX = limeLight.xOffset(heightDifference,cameraAngle,xDifference);
    double correctiony = 35 - limeLight.yOffset(heightDifference,cameraAngle);
    if (limeLight.yOffset(heightDifference,cameraAngle) == 0){
      correctiony = 0;
    }
    acceptableAngle = Math.abs(correctionAngle) < angleTolerance;
    acceptableX = Math.abs(correctionX) < xTolerance;
    // acceptabley = Math.abs(correctiony) < 5;
    acceptabley = true;
    double angleRCW = 0;
    double xRCW = 0;
    double yRCW = 0;

    if (!acceptableAngle){
      angleIntegral = (angleIntegral*.6) + correctionAngle; // .02 is the typical timing for IterativeRobot
      double derivative = (correctionAngle - previousAngleError) / .02; // last years code uses division, but this maybe should be multiplication?
      angleRCW = (p * correctionAngle * correctionAngleSign) + (i * angleIntegral) + (d * derivative);
      angleRCW = angleRCW / 45;
      double modAngleRCW = Math.max(.1, Math.min(Math.abs(angleRCW), 1));
      angleRCW = angleRCW < 0 ? -modAngleRCW : modAngleRCW;
      timeCheck = System.currentTimeMillis();
    }

    if (!acceptableX){
      xIntegral = (xIntegral * .6) + correctionX;
      double derivative = (correctionX - previousXError) / .02;
      xRCW = (p * correctionX) + (i * xIntegral) + (d * derivative);
      xRCW = xRCW / -1;
      double modxRCW = Math.max(.1, Math.min(Math.abs(xRCW), 1));
      xRCW = xRCW < 0 ? -modxRCW : modxRCW;
      timeCheck = System.currentTimeMillis();
    }

    if (!acceptabley){
      yRCW = p * correctiony;
      double modyRCW = Math.max(.3, Math.min(Math.abs(yRCW), 1));
      yRCW = yRCW / 20;
      yRCW = yRCW < 0 ? -modyRCW : modyRCW;
      timeCheck = System.currentTimeMillis();
    }
    System.out.println("Correction Angle = " + correctionAngle + " Correction X = " + correctionX
     + " Angle RCW = " + angleRCW + " x RCW = " + xRCW + " distance = " + correctiony + " yRCW = " + yRCW);
    RobotMap.DRIVE_TRAIN_MECANUM.driveCartesian(xRCW, yRCW, -angleRCW);

    previousAngleError = correctionAngle;
    previousXError = correctionX;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return ((acceptableAngle && acceptableX && acceptabley && System.currentTimeMillis() - timeCheck > 500) || Robot.oi.limeLightButton.get());
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    RobotMap.DRIVE_TRAIN_MECANUM.driveCartesian(0, 0, 0);
  }


}
