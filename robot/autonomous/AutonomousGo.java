/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.lift.GoToPosition;
import frc.robot.lift.LiftPositionByPotentiometer;

public class AutonomousGo extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutonomousGo() {
    addSequential(new LiftPositionByPotentiometer(22 , 1 , 179, true,false,false),1);
    addSequential(new LiftPositionByPotentiometer(22, 1, 179, false,true,false),1);
    addSequential(new AlignByVision());
    addSequential(new LiftPositionByPotentiometer(22, 1, 179, false,true,true),1);
    addSequential(new LiftPositionByPotentiometer(22, 1, 179, false,false,true));

    //TODO add deploy code
  }
}
