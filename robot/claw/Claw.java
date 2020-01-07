/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.claw;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Claw extends Subsystem {

  public boolean inHatchPosition;

  public Claw(){
    
  }
  @Override
  public void initDefaultCommand() {
    // Robot.oi.intakeButton.whenPressed(new SmartIntakeOutput(true));
    // Robot.oi.outpuButton.whenPressed(new SmartIntakeOutput(false));
    Robot.oi.intakeButton.whenPressed(new CargoIntake());
    Robot.oi.outpuButton.whenPressed(new CargoOutput());
    Robot.oi.hatchInButton.whenPressed(new HatchInput());
    Robot.oi.hatchOutButton.whenPressed(new HatchOutput());
  }
  public void cargoIntake(){
    RobotMap.CLAW_MOTOR.set(ControlMode.PercentOutput,.7);
    // RobotMap.CLAW_MOTOR.set(.7);
  }
  public void cargoOutput(){
    RobotMap.CLAW_MOTOR.set(ControlMode.PercentOutput,-1);
    // RobotMap.CLAW_MOTOR.set(-1);
  }
  public void cargoHold(){
    RobotMap.CLAW_MOTOR.set(ControlMode.PercentOutput,0);
    // RobotMap.CLAW_MOTOR.set(0);
  }
  public void hatchIntake(){
    RobotMap.CLAW_SOLENOID.set(DoubleSolenoid.Value.kForward);
  }
  public void hatchOutput(){
    RobotMap.CLAW_SOLENOID.set(DoubleSolenoid.Value.kReverse);
  }
  public void setHatchPosition(boolean hatch){
    inHatchPosition = hatch;
  }
  public boolean getHatchPosition(){
    return inHatchPosition;
  }
}
