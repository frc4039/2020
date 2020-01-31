/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    m_shooterMotor1.setInverted(false);
    m_shooterMotor2.setInverted(true);
    
    m_shooterMotor1.setSensorPhase(false);
        
    m_shooterMotor2.follow(m_shooterMotor1);

    m_shooterMotor1.configFactoryDefault();
    m_shooterMotor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, ShooterConstants.kPIDLoopIdx, ShooterConstants.kTimeoutMs);

    m_shooterMotor1.config_kF(ShooterConstants.kPIDLoopIdx, ShooterConstants.kF, ShooterConstants.kTimeoutMs);
    m_shooterMotor1.config_kP(ShooterConstants.kPIDLoopIdx, ShooterConstants.kP, ShooterConstants.kTimeoutMs);
  }

  public void shoot(double rpm) {
    m_shooterMotor1.set(ControlMode.Velocity, RPMtoTicks(rpm));
  }

  public void stop() {
    m_shooterMotor1.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    printShooterValues();
  }

  public double RPMtoTicks(double rpm) {
    /**
     * Units that ControlMode.velocity expects to be in ticks per 100ms, NOT per minute.
     * Reference:
     * https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/VelocityClosedLoop/src/main/java/frc/robot/Robot.java#L123
     */
    return rpm * ShooterConstants.kGearRatio * GeneralConstants.TicksPerRev / 600.0;
  }

  public double TicksToRPM(double ticks) {
    return ticks * 600 / GeneralConstants.TicksPerRev / ShooterConstants.kGearRatio;
  }

  public void printShooterValues() {
    SmartDashboard.putNumber("Shooter RPM", TicksToRPM(m_shooterMotor1.getSelectedSensorVelocity()));
  }

  public double returnCurrentRPM() {
    return TicksToRPM(m_shooterMotor1.getSelectedSensorVelocity());
  }
}
