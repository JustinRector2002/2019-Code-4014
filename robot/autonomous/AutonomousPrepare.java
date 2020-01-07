/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.claw.SetClawBool;
import frc.robot.lift.GoToPosition;
import frc.robot.vision.TurnCameraForward;
import frc.robot.vision.TurnCameraReverse;
import frc.robot.drivetrain.SetThings;

public class AutonomousPrepare extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutonomousPrepare(double verticalPosition, double armPosition, double wristPosition, double approachAngle, boolean isHatchPosition,boolean isTargetLow) {
    // these commands are seperate for now, but it would be faster if bits could be done at the same time
    // we should check how stable the robot is when changing lift positions before we try anything though
    // if (armPosition < 180){
    //   addSequential(new TurnCameraForward());
    // } 
    // else
    // {
    //   addSequential(new TurnCameraReverse());
    //   approachAngle = approachAngle + 180; 
    // }
    addSequential(new SetClawBool(isHatchPosition));
    // System.out.println("approach angle set to" + approachAngle);
    // Robot.driveTrain.targetAngle = approachAngle;
    if (isTargetLow){
      // Robot.driveTrain.targetHeightDifference = -17.75;
      if (isHatchPosition){
        addSequential(new SetThings(-17.75, approachAngle,verticalPosition,armPosition,wristPosition, -3.875));
      } else {
        addSequential(new SetThings(-17.75, approachAngle,verticalPosition,armPosition,wristPosition, -7.875));
      }
      
    } else {
      // Robot.driveTrain.targetHeightDifference = -9.75;
      if (isHatchPosition){
        addSequential(new SetThings(-9.75, approachAngle, verticalPosition,armPosition,wristPosition, -3.875));
      } else {
        addSequential(new SetThings(-9.75, approachAngle, verticalPosition,armPosition,wristPosition, -7.875));
      }
    }
    addSequential(new GoToPosition(verticalPosition, armPosition, wristPosition));
  }
}
