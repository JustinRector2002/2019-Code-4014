package frc.robot.lift;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Lift extends Subsystem {

  public Lift(){

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  
  // we probably need to add max and min speeds, especially the arm motor
  public void moveVertical(double movement){
    RobotMap.LIFT_VERTICAL_MOTOR.set(ControlMode.PercentOutput,movement);
    // RobotMap.LIFT_VERTICAL_MOTOR.set(movement);
  }
  public void moveArm(double movement){
    RobotMap.LIFT_ARM_MOTOR.set(movement);
  }
  public void moveWrist(double movement){
    RobotMap.LIFT_WRIST_MOTOR.set(movement);
  }
  public void stopMoving(){
    RobotMap.LIFT_ARM_MOTOR.set(0);
    RobotMap.LIFT_VERTICAL_MOTOR.set(ControlMode.PercentOutput, .2);
    // RobotMap.LIFT_VERTICAL_MOTOR.set(.2);
    RobotMap.LIFT_WRIST_MOTOR.set(0);
  }
}
