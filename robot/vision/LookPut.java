/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LookPut extends Command {

  private final LimeLight limeLight;
  private long time;

  public LookPut() {
    this.limeLight = Robot.limeLight;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    limeLight.ledOFF();
    time = System.currentTimeMillis();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // limeLight.printImage();
    // limeLight.xOffset(6.5, 0, -5.375);
  }

  @Override
  protected void end() {
    limeLight.ledON();
  }
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (System.currentTimeMillis() - time > 1000 );
  }
}
