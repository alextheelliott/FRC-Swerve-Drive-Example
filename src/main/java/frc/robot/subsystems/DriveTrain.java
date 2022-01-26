package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.CalibrationMode;

import static com.ctre.phoenix.motorcontrol.ControlMode.*;
import static frc.robot.constants.Constants.DriveTrain.*;

public class DriveTrain extends SubsystemBase {
  public static DriveTrain instance;

  private TalonFX leftFrontSpeed, leftBackSpeed, rightFrontSpeed, rightBackSpeed;
  private TalonSRX leftFrontAngle, leftBackAngle, rightFrontAngle, rightBackAngle;
  private PigeonIMU pigeon;

  public static DriveTrain getInstance() {
    if(instance == null)
      instance = new DriveTrain();
    return instance;
  }

  public double getGyroAngle() {
    return pigeon.getFusedHeading();
  }

  //Order: Front Right, Front Left, Back Left, Back Right
  public void setSwerveAngle(double a1, double a2, double a3, double a4) {
    double s1, s2, s3, s4;
    s1 = (a1-rightFrontAngle.getSelectedSensorPosition());
    s2 = (a2-leftFrontAngle.getSelectedSensorPosition());
    s3 = (a3-leftBackAngle.getSelectedSensorPosition());
    s4 = (a4-rightBackAngle.getSelectedSensorPosition());

    rightFrontAngle.set(PercentOutput, s1);
    leftFrontAngle.set(PercentOutput, s2);
    leftBackAngle.set(PercentOutput, s3);
    rightBackAngle.set(PercentOutput, s4);
  }

  //Order: Front Right, Front Left, Back Left, Back Right
  public void setSwerveSpeed(double... s) {
    double max = -1; for(int i=0; i<s.length; i++) max=Math.max(max, s[i]);
    if(max>1) for(int i=0; i<s.length; i++) s[i]/=max;

    rightFrontSpeed.set(PercentOutput, s[0]);
    leftFrontSpeed.set(PercentOutput, s[1]);
    leftBackSpeed.set(PercentOutput, s[2]);
    rightBackSpeed.set(PercentOutput, s[3]);
  }

  public DriveTrain() {
    rightFrontSpeed = new TalonFX(RIGHT_FRONT_SPEED);
    leftFrontSpeed = new TalonFX(LEFT_FRONT_SPEED);
    leftBackSpeed = new TalonFX(LEFT_BACK_SPEED);
    rightBackSpeed = new TalonFX(RIGHT_BACK_SPEED);

    rightFrontAngle = new TalonSRX(RIGHT_FRONT_ANGLE);
    leftFrontAngle = new TalonSRX(LEFT_FRONT_ANGLE);
    leftBackAngle = new TalonSRX(LEFT_BACK_ANGLE);
    rightBackAngle = new TalonSRX(RIGHT_BACK_ANGLE);

    pigeon = new PigeonIMU(00);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}