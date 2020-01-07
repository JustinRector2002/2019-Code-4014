package frc.robot.lift;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;

public class GoToPosition extends CommandGroup {
  /**
   * Add your docs here.
   */
  public GoToPosition(double vertical, double arm, double wrist) {
    // if ((arm > 180 && RobotMap.LIFT_ARM_POTENTIOMETER.get() < 180) || (arm < 180 && RobotMap.LIFT_ARM_POTENTIOMETER.get() > 180)){
    //   // the arm needs to flip arounds, so we should raise the lift beacause I guess we have to?

    // }
    System.out.println("position target is: " + vertical + " " + arm + " " + wrist);
    addSequential(new LiftPositionByPotentiometer(22 , arm , wrist, true,false,false),1);
    addSequential(new LiftPositionByPotentiometer(22, arm, wrist, false,true,false),1);
    addSequential(new LiftPositionByPotentiometer(vertical , arm , wrist, false,false,false));
  }
}
