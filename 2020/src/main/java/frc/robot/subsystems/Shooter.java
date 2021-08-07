/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.GeneralConstants;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {

  private TalonFX m_shooterMotor1;
  private TalonFX m_shooterMotor2;
  private CANSparkMax m_shooterFeederMotor;
  private double m_rpmSetPoint;

  public Shooter() {
    m_shooterMotor1 = new TalonFX(ShooterConstants.kShooterMotor1Port);
    m_shooterMotor2 = new TalonFX(ShooterConstants.kShooterMotor2Port);
    
    m_shooterMotor1.configFactoryDefault();
    m_shooterMotor2.configFactoryDefault();

    m_shooterMotor1.setInverted(ShooterConstants.kShooterInversion1);
    m_shooterMotor2.setInverted(ShooterConstants.kShooterInversion2);
    
    m_shooterMotor1.setSensorPhase(true);
        
    m_shooterMotor2.follow(m_shooterMotor1);

    m_shooterMotor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, ShooterConstants.kPIDLoopIdx, ShooterConstants.kTimeoutMs);

    m_shooterMotor1.config_kF(ShooterConstants.kPIDLoopIdx, ShooterConstants.kF, ShooterConstants.kTimeoutMs);
    m_shooterMotor1.config_kP(ShooterConstants.kPIDLoopIdx, ShooterConstants.kP, ShooterConstants.kTimeoutMs);


    m_shooterFeederMotor = new CANSparkMax(ShooterConstants.kShooterFeederMotorPort, MotorType.kBrushless);

    m_shooterFeederMotor.restoreFactoryDefaults();

    m_shooterFeederMotor.setInverted(ShooterConstants.kShooterFeederInversion);

    m_shooterFeederMotor.setSmartCurrentLimit(ShooterConstants.kShooterFeederMotorCurrentLimit);

    if (GeneralConstants.realMatch) {
      m_shooterFeederMotor.burnFlash();
    }

    m_rpmSetPoint = ShooterConstants.k10ftBackBumperShotRPM;
  }

  public void shoot() {
    m_shooterMotor1.set(ControlMode.Velocity, RPMtoTicks(m_rpmSetPoint));
  }

  public void feedShooter() {
    m_shooterFeederMotor.set(ShooterConstants.kShooterFeederSpeed);
  }

  public void stop() {
    m_shooterMotor1.set(ControlMode.PercentOutput, 0);
    m_shooterFeederMotor.set(0);
  }

  public void setSetPoint(double rpm) {
    m_rpmSetPoint = rpm;
  }

  @Override
  public void periodic() {

  }

  public double RPMtoTicks(double rpm) {
    return rpm * ShooterConstants.kGearRatio * 1024 / 600.0;
  }

  public double TicksToRPM(double ticks) {
    return ticks * 600 / 1024 / ShooterConstants.kGearRatio;
  }

  public void printShooterValues() {
    SmartDashboard.putNumber("Shooter RPM", returnCurrentRPM());
  }

  public double returnCurrentRPM() {
    // Ben A. - m_shooterMotor1 always gives zero for the sensor value. Not sure why.
    return TicksToRPM(m_shooterMotor2.getSelectedSensorVelocity());
  }

  public double getSetpoint() {
    return m_rpmSetPoint;
  }
}
