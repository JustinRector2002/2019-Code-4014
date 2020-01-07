/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.claw;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class SmartIntakeOutput extends CommandGroup {
  /**
   * Add your docs here.
   */
  public SmartIntakeOutput(boolean isIntake) {
    System.out.println("running smart intake");
    if (isIntake){
      System.out.println("running intake");
      if (Robot.claw.getHatchPosition()){
        System.out.println("in hatch position");
        addSequential(new HatchInput());
      } 
      else 
      {
        addSequential(new CargoIntake());
        System.out.println("cargo position");
      }
    } 
    else
    {
      if (Robot.claw.getHatchPosition()){
        addSequential(new HatchOutput());
      } 
      else 
      {
        addSequential(new CargoOutput());
      }
    }
    
  }
}
