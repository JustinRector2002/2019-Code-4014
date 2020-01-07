package frc.robot.lift;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class LiftPositionByPotentiometer extends Command {

  private double vp;
  private double vi;
  private double verticalintegral;
  private double ap;
  private double ai;
  private double armIntegral;
  private double wp;

  private Potentiometer vertical;
  // private Potentiometer vertical;
  private Potentiometer arm;
  // private Potentiometer wrist;
  private Potentiometer wrist;

  private double toleranceVertical;
  private double toleranceArm;
  private double toleranceWrist;
  private boolean acceptableVertical;
  private boolean acceptableArm;
  private boolean acceptableWrist;
  private double setPointArm;
  private double setPointVertical;
  private double setPointWrist;
  private boolean justVertical;
  private boolean notVertical;
  private boolean getFromDT;
  double vRcw, aRcw, wRcw;


  public LiftPositionByPotentiometer(double setPointVertical, double setPointArm, double setPointWrist, boolean justVertical,boolean notVertical, boolean getFromDT) {
    this.vertical = RobotMap.LIFT_VERTICAL_POTENTIOMETER;
    this.arm = RobotMap.LIFT_ARM_POTENTIOMETER;
    this.wrist = RobotMap.LIFT_WRIST_POTENTIOMETER; //should be wrist, this is a bodge
    this.setPointArm = setPointArm;
    this.setPointVertical = setPointVertical + 1;
    this.setPointWrist = setPointWrist;
    this.justVertical = justVertical;
    this.notVertical = notVertical;
    this.getFromDT = getFromDT;
    requires(Robot.lift);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    wp = .3;
    ap = 1.2;
    ai = .015;
    vp = .25;
    vi = .02;
    armIntegral = 0;
    verticalintegral = 0;
    toleranceArm = .5;
    toleranceWrist = 2;
    toleranceVertical = .1;
    acceptableArm = acceptableWrist = acceptableVertical = false;
    if (getFromDT){
      setPointArm = Robot.driveTrain.getArm();
      setPointVertical = Robot.driveTrain.getVertical();
      setPointWrist = Robot.driveTrain.getWrist();
    }
    if (justVertical){
      setPointArm = arm.get();
      setPointWrist = wrist.get();
    }
    if (notVertical){
      setPointVertical = vertical.get();
    }
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    wRcw = 0;
    double minA,maxA;
    minA = .00;
    maxA = .4;
    double errorVertical = setPointVertical - vertical.get();
    verticalintegral = (verticalintegral * .6) + errorVertical;
    double errorArm = setPointArm - arm.get() + (2 * Math.signum(180 - setPointArm));
    armIntegral = (armIntegral * .4) + errorArm;
    double errorWrist = setPointWrist - wrist.get();
    acceptableArm = Math.abs(errorArm) < toleranceArm;
    acceptableVertical = Math.abs(errorVertical) < toleranceVertical;
    acceptableWrist = Math.abs(errorWrist) < toleranceWrist;
    if (!acceptableArm){
      // aRcw = (ap * errorArm)/-5;
      if (errorArm < 0){
        maxA = .2;
      } else { maxA = .5;}
      aRcw = ((((maxA-minA)/315)*errorArm) * ap) + (armIntegral * ai);
      aRcw = Math.signum(aRcw) * Math.min(maxA, Math.max(minA, Math.abs(aRcw) + minA));
    }
    if (!acceptableVertical){
      if (errorVertical < 0) {
        vp = .1;
      } else {
        vp = .2;
      }
      vRcw = ((vp * errorVertical)/7) + (verticalintegral * vi);
      vRcw = Math.signum(vRcw) * Math.min(.7, Math.abs(vRcw));
    }
    if (!acceptableWrist){
      wRcw = (wp * errorWrist)/110 ;
    }
    Robot.lift.moveArm(aRcw);
    Robot.lift.moveVertical(vRcw);
    Robot.lift.moveWrist(wRcw);
    System.out.println("arm error: " + errorArm + " aRcw= " + aRcw);
    System.out.println("arm integral= " + (armIntegral * ai));
    System.out.println("vertical error: " + errorVertical + " vRcw= " + vRcw);
    System.out.println("vertical integral= " + (verticalintegral * vi));
    System.out.println("wrist error: " + errorWrist + " wRcw= " + wRcw);
    System.out.println("arm amps: " + RobotMap.PDP.getCurrent(4));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return ((acceptableVertical && justVertical) || (acceptableArm && acceptableWrist && notVertical));
    // return (Robot.oi.DoneButton.get());
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.lift.stopMoving();
  }

}
