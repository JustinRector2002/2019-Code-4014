/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.drivetrain.SetThings;
import frc.robot.lift.LiftPositionByPotentiometer;

public class Sandstorm extends CommandGroup {
  /**
   * Add your docs here.
   */
  public Sandstorm(int position) {
 if (position == 2){
      addSequential(new DriveByDistance(.8,-50));
      addSequential(new SetThings(-17.75, 0, 24, 90, 40, -3.875));
    } else {
      addSequential(new LiftPositionByPotentiometer(22 , 81.5 , 38, true,false,false),1);
      addSequential(new LiftPositionByPotentiometer(22, 81.5, 38, false,true,false),1);
      addSequential(new LiftPositionByPotentiometer(0, 81.5, 38, false,false,false),1); 
      addParallel(new LiftPositionByPotentiometer(0, 81.5, 38, false,false,false));   
      addSequential(new DriveByDistance(.8, -192));
      if (position == 1){
        addSequential(new SetThings(-17.75, -90, 24, 90, 40, -3.875));
        addSequential(new Pivot(-90));
      } else if (position == 3){
        addSequential(new SetThings(-17.75, 90, 24, 90, 40, -3.875));
        addSequential(new Pivot(90));
      }
    }
    addSequential(new AutonomousGo());
  }
}
