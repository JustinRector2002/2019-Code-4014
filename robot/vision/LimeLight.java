/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class LimeLight extends Subsystem {
  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry tx = table.getEntry("tx");
  private NetworkTableEntry ty = table.getEntry("ty");
  private NetworkTableEntry twidth = table.getEntry("tlong");
  private NetworkTableEntry tv = table.getEntry("tv");
  private double prevX;
  // private NetworkTableEntry theight = table.getEntry("tvert");
  // private NetworkTableEntry tarea = table.getEntry("ta");


  @Override
  public void initDefaultCommand() { // we probably won't want a default command for this by the end of build season, this is just for testing
    // setDefaultCommand(new LookPut(this));
  }
  public void printImage(){
    double x = Math.toRadians(tx.getDouble(0.0));
    double y = Math.toRadians(ty.getDouble(0.0));
    double width = twidth.getDouble(0.0);
    // double height = theight.getDouble(0.0);
    // double area = tarea.getDouble(0.0);

    double distance = 12.125 / Math.tan(y);
    double beta = (41 * width/320);
    double angleDistance = distance / Math.cos(Math.abs(x - Math.abs(beta/2)));
    double angle = 90 - Math.asin((angleDistance * Math.sin(beta))/14.627);
    double idealBeta = Math.toDegrees(2 * Math.atan(7.336/distance));
    double offBeta = Math.abs(idealBeta - beta);
    
    System.out.print("angle= " + angle);
    System.out.print(" Distance= " + distance);
    System.out.print(" beta=" + beta);
    System.out.print(" ideal Beta= " + idealBeta);
    System.out.println(" off Beta= " + offBeta);

  }
  public double angle(double heightDifference, double cameraAngle, double xDifference){
    double distance = heightDifference / Math.tan(cameraAngle + ty.getDouble(0.0));
    double beta = (41 * twidth.getDouble(0.0)/320);
    double idealBeta = Math.toDegrees(2 * Math.atan(7.336/distance));
    double signGuess = tx.getDouble(0.0)/Math.abs(xOffset(heightDifference, cameraAngle, xDifference));
    double offBeta = 0;
      if (beta > idealBeta){
        offBeta = 0;
      } else {
        offBeta = idealBeta - beta;
      }
    double offBetaGuess = offBeta * signGuess;
    return offBetaGuess;
  }
  public double xOffset(double heightDifference, double cameraAngle, double xDifference){
    double distancex = heightDifference / Math.tan(Math.toRadians(cameraAngle + ty.getDouble(0.0)));
    double xOff = (distancex * Math.tan(Math.toRadians(tx.getDouble(0.0)))) - xDifference;
    if (tv.getDouble(0) == 0){
      xOff = prevX;
    }
    // double xOff = tx.getDouble(0.0);
    // System.out.println( "distance is: " + distancex + "x off is: " + xOff + " tv is: " + tv.getDouble(0));
    prevX = xOff;
    return xOff;
  }
  public double yOffset(double heightDifference, double cameraAngle){
    double yOff = heightDifference / Math.tan(Math.toRadians(cameraAngle + ty.getDouble(0.0)));
    if (tv.getDouble(0) == 0){
      yOff = 0;
    }
    return yOff;
  }
  public void ledON(){
    table.getEntry("ledMode").setNumber(3);
  }
  public void ledOFF(){
    table.getEntry("ledMode").setNumber(1);
  }
  public void servoForward(){
    // RobotMap.CAMERA_SERVO.setAngle(180); 
    table.getEntry("pipeline").setNumber(0);
  }
  public void servoReverse(){
    // RobotMap.CAMERA_SERVO.setAngle(0);
    table.getEntry("pipeline").setNumber(0);
  }
  public void regCamera(){
    table.getEntry("pipeline").setNumber(2);
  }
}
