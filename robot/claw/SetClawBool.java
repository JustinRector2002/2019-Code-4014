/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.claw;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class SetClawBool extends Command {

  private boolean isHatchPosition;

  public SetClawBool(boolean isHatchPosition) {
    this.isHatchPosition = isHatchPosition;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (isHatchPosition){
      Robot.claw.setHatchPosition(true);
    } else {
      Robot.claw.setHatchPosition(false);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

}
