/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.GeneralConstants;
// import edu.wpi.first.wpilibj.SpeedController;
// import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {


  // private final SpeedController m_leftMotor = new SpeedControllerGroup(new CANSparkMax(DriveConstants.kLeftDriveMotor1Port, MotorType.kBrushless),
  //     new CANSparkMax(DriveConstants.kLeftDriveMotor2Port, MotorType.kBrushless));
  // private final SpeedController m_rightMotor = new SpeedControllerGroup(new CANSparkMax(DriveConstants.kRightDriveMotor1Port, MotorType.kBrushless),
  //     new CANSparkMax(DriveConstants.kRightDriveMotor2Port, MotorType.kBrushless));

  private TalonSRX m_shooterMotor1;
  private TalonSRX m_shooterMotor2;

  public Shooter() {
    m_shooterMotor1 = new TalonSRX(ShooterConstants.kShooterMotor1Port);
    m_shooterMotor2 = new TalonSRX(ShooterConstants.kShooterMotor2Port);
        
    m_shooterMotor2.follow(m_shooterMotor1);

    m_shooterMotor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
  }

  public void shoot(double rpm) {
    m_shooterMotor1.set(ControlMode.Velocity, RPMtoTicks(rpm));
  }

  public void stop() {
    m_shooterMotor1.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public double RPMtoTicks(double rpm) {
    return rpm*GeneralConstants.TicksPerRev;
  }
}
