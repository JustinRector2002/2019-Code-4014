package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

  public static final PowerDistributionPanel PDP = new PowerDistributionPanel();

  public static final Encoder DRIVE_TRAIN_ENCODER = 
                    new Encoder(DPIO.DRIVE_TRAIN_ENCODER_A_CHANNEL, 
                                DPIO.DRIVE_TRAIN_ENCODER_B_CHANNEL, 
                                false,
                                Encoder.EncodingType.k4X);

  public static final WPI_TalonSRX DRIVE_TRAIN_FRONT_RIGHT_MOTOR = new WPI_TalonSRX(CAN.DRIVE_TRAIN_MOTOR_FRONT_RIGHT);
  public static final WPI_TalonSRX DRIVE_TRAIN_BACK_RIGHT_MOTOR = new WPI_TalonSRX(CAN.DRIVE_TRAIN_MOTOR_BACK_RIGHT);
  public static final WPI_TalonSRX DRIVE_TRAIN_FRONT_LEFT_MOTOR = new WPI_TalonSRX(CAN.DRIVE_TRAIN_MOTOR_FRONT_LEFT);
  public static final WPI_TalonSRX DRIVE_TRAIN_BACK_LEFT_MOTOR = new WPI_TalonSRX(CAN.DRIVE_TRAIN_MOTOR_BACK_LEFT);

  public static final MecanumDrive DRIVE_TRAIN_MECANUM = new MecanumDrive(DRIVE_TRAIN_FRONT_LEFT_MOTOR,
   DRIVE_TRAIN_BACK_LEFT_MOTOR, DRIVE_TRAIN_FRONT_RIGHT_MOTOR, DRIVE_TRAIN_BACK_RIGHT_MOTOR);

  // the following stuff is for a standard arcade drive for use when testing on
  // // 2018 bot
  // private static final SpeedControllerGroup DRIVE_TRAIN_LEFT_MOTOR_GROUP = new SpeedControllerGroup(
  //     DRIVE_TRAIN_FRONT_LEFT_MOTOR, DRIVE_TRAIN_BACK_LEFT_MOTOR);
  // private static final SpeedControllerGroup DRIVE_TRAIN_RIGHT_MOTOR_GROUP = new SpeedControllerGroup(
  //     DRIVE_TRAIN_FRONT_RIGHT_MOTOR, DRIVE_TRAIN_BACK_RIGHT_MOTOR);
  // public static final DifferentialDrive DRIVE_TRAIN_DIFFERENTIAL_DRIVE = new DifferentialDrive(
  //     DRIVE_TRAIN_LEFT_MOTOR_GROUP, DRIVE_TRAIN_RIGHT_MOTOR_GROUP);

  public static final AHRS NAVX = new AHRS(SPI.Port.kMXP);
  
  public static final Compressor COMPRESSOR = new Compressor(1);
  public static final DoubleSolenoid FRONT_HIGH_SOLENOID = new DoubleSolenoid(4, 3);//4,3
  public static final DoubleSolenoid BACK_HIGH_SOLENOID = new DoubleSolenoid(7, 0);//7,0
  public static final DoubleSolenoid FRONT_LOW_SOLENOID = new DoubleSolenoid(2, 5);//2,5
  public static final DoubleSolenoid BACK_LOW_SOLENOID = new DoubleSolenoid(1, 6);//1,6

//   public static final Ultrasonic FRONT_RIGHT_ULTRASONIC = new Ultrasonic(7, 6);
//   public static final Ultrasonic FRONT_LEFT_ULTRASONIC = new Ultrasonic(1, 2);
//   public static final Ultrasonic BACK_RIGHT_ULTRASONIC = new Ultrasonic(3, 4);
//   public static final Ultrasonic BACK_LEFT_ULTRASONIC = new Ultrasonic(5, 8);

  public static final Potentiometer LIFT_VERTICAL_POTENTIOMETER = new AnalogPotentiometer(0, 46, -5);
  public static final Potentiometer LIFT_ARM_POTENTIOMETER = new AnalogPotentiometer(1, -1200, 694);//-1233
  public static final Potentiometer LIFT_WRIST_POTENTIOMETER = new AnalogPotentiometer(2, 3600, -1250);
  public static final VictorSPX LIFT_VERTICAL_MOTOR = new VictorSPX(CAN.LIFT_VERTICAL_MOTOR);
  // public static final WPI_TalonSRX LIFT_VERTICAL_MOTOR = new WPI_TalonSRX(CAN.LIFT_VERTICAL_MOTOR);
  public static final WPI_TalonSRX LIFT_ARM_MOTOR = new WPI_TalonSRX(CAN.LIFT_ARM_MOTOR);
  public static final WPI_TalonSRX LIFT_WRIST_MOTOR = new WPI_TalonSRX(CAN.LIFT_WRIST_MOTOR);

  public static DoubleSolenoid CLAW_SOLENOID = new DoubleSolenoid(1, 0, 7); //that first 1 should make this look for a pcm with an id of 1 (default id is 0)
  public static VictorSPX CLAW_MOTOR = new VictorSPX(CAN.CLAW_MOTOR);
  // public static WPI_TalonSRX CLAW_MOTOR = new WPI_TalonSRX(CAN.CLAW_MOTOR);

// public static Servo CAMERA_SERVO = new Servo(0);

  public static void init() {
    COMPRESSOR.setClosedLoopControl(true);
    // CAMERA_SERVO.setAngle(180); // TODO figure out what the servo angles need to be
    FRONT_HIGH_SOLENOID.set(DoubleSolenoid.Value.kReverse);
    FRONT_LOW_SOLENOID.set(DoubleSolenoid.Value.kReverse);
    BACK_HIGH_SOLENOID.set(DoubleSolenoid.Value.kReverse);
    BACK_LOW_SOLENOID.set(DoubleSolenoid.Value.kReverse);

    NAVX.reset();
    // NAVX.setAngleAdjustment(180);
    DRIVE_TRAIN_ENCODER.reset();
    DRIVE_TRAIN_ENCODER.setDistancePerPulse(0.00894454);
    // DRIVE_TRAIN_ENCODER.setDistancePerPulse(1);
    // LIFT_WRIST_ENCODER.setDistancePerPulse(ANGLE_PER_PULSE);
    // FRONT_RIGHT_ULTRASONIC.setAutomaticMode(true);
    // FRONT_LEFT_ULTRASONIC.setAutomaticMode(true);
    // BACK_RIGHT_ULTRASONIC.setAutomaticMode(true);
    // BACK_LEFT_ULTRASONIC.setAutomaticMode(true);
  }
}
