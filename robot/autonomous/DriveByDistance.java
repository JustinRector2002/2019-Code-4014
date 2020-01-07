package frc.robot.autonomous;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.drivetrain.DriveTrain;

public class DriveByDistance extends Command {
  private final DriveTrain driveTrain;

  private double speed;
  private double distance;

  private double p, i, d, dp = 0;
  private double integral, previousError;
//   private final double setPoint = 0;
  private boolean isInsideTolerance = false;

  private final AHRS navX;
  private double initAngle;

  private double maxSpeed;
  private double minSpeed;
  private double tolerance;
  private long initTimestamp;
  private double maxDSpeed;
  
  private int collisions;

  public DriveByDistance(double speed, double distance) {
      this.navX = RobotMap.NAVX;
      this.driveTrain = Robot.driveTrain;
      this.speed = speed;
      this.distance = distance - 5;
      this.maxDSpeed = speed;
      requires(driveTrain);
  }

  @Override
  protected void initialize() {
      System.out.println("DriveByDistance.initialize(): distance = " + distance);
      driveTrain.resetEncoders();

//        ahrs.resetDisplacement();
      initPIDControl();
  }

  private void initPIDControl() {
      initTimestamp = System.currentTimeMillis();
    //   navX.reset();
      initAngle = navX.getAngle();
      p = 0.8;//guesses
      i = 0;
      d = 0;
      dp = .6; //getting the right distance doesn't need anything fancy, so this is just a "p" on a position pid with no i or d
      maxSpeed = 1;
      minSpeed = 0;
      tolerance = 1;
      integral = previousError = 0;
      isInsideTolerance = false;
      System.out.println("p: " + p + " | i: " + i + " | d: " + d + " | setPoint: ");
  }

  @Override
  protected void execute() {
      final double angle = navX.getAngle() - initAngle;
      double error = angle;
    //   error = first ? setPoint : error; // in case ahrs.reset() isn't isInsideTolerance (only observed first time, so kludging it)
      double rcw = 0;
      double rotation = 0;
      speed = dp * (distance - RobotMap.DRIVE_TRAIN_ENCODER.getDistance());
      speed = speed / 30;
      speed = Math.max(0, Math.min(speed, maxDSpeed));
      isInsideTolerance = Math.abs(error) < tolerance;
      if (!isInsideTolerance) {
          integral += error * 0.02; // 0.02 because it's normal timing for IterativeRobot.
          double derivative = (error - previousError) / 0.02;
          rcw = (p * error) + (i * integral) + (d * derivative);

          double modRcw = Math.abs(rcw) /45 ;/* / (setPoint * .25)*/ //setpoint was 0, maybe dividing by 0 causes problems?
          rotation = Math.max(minSpeed, Math.min(modRcw, maxSpeed));
          rotation = rcw < 0 ? -rotation : rotation;
          System.out.println("speed is " + speed + " rotation is " + rotation);
          RobotMap.DRIVE_TRAIN_MECANUM.driveCartesian(0, -speed, rotation);
      } else {
          RobotMap.DRIVE_TRAIN_MECANUM.driveCartesian(0, -speed, 0);
      }
//		System.out.println("isInsideTolerance: " + isInsideTolerance + " | angle: " + angle + " | error: " + error + " | raw rcw: " + rcw
//				+ " | rotation: " + rotation +" | speed: " + speed);
  }

  protected void end() {
      RobotMap.DRIVE_TRAIN_MECANUM.driveCartesian(0, 0, 0);
  }

  @Override
  protected boolean isFinished() {
      return probableCollision() ||
              achievedDistance();
  }

  private boolean probableCollision() {
      boolean collision = (System.currentTimeMillis() - initTimestamp > 300) &&
              (Math.abs(RobotMap.DRIVE_TRAIN_ENCODER.getRate()) < 0.0);
      if (collision) {
          System.out.println("DriveByDistance: collision");
          collisions++;
      }
      return (collisions > 3);
  }

  private boolean achievedDistance() {
      double leftDistance = RobotMap.DRIVE_TRAIN_ENCODER.getDistance();
    // double leftDistance = 1;
      boolean finished = (leftDistance >= distance);
    //   System.out.println("DriveByDistance.isFinished(): ENCODER distance = " + leftDistance + " |speed = " + speed + " |is finished = " + finished);
      if (finished) {
          System.out.println("DriveByDistance: drove " + distance + " inches");
      }
      return finished;
  }

}
