/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {
  /**
   * Creates a new Climber.
   */
  private final TalonFX m_climberMotorLeft;
  private final TalonFX m_climberMotorRight;

  //private final DigitalInput m_leftLimitSwitch;
  //private final DigitalInput m_rightLimitSwitch;

  public Climber() {
    m_climberMotorLeft = new TalonFX(ClimberConstants.kClimberMotorLeftPort);
    m_climberMotorRight = new TalonFX(ClimberConstants.kClimberMotorRightPort);
    //m_leftLimitSwitch = new DigitalInput(ClimberConstants.kLeftLimitSwitchPort);
    //m_rightLimitSwitch = new DigitalInput(ClimberConstants.kRightLimitSwitchPort);
    

    m_climberMotorLeft.setInverted(true);
    m_climberMotorRight.setInverted(false);

    m_climberMotorLeft.setSensorPhase(false);

    m_climberMotorRight.follow(m_climberMotorLeft);

    m_climberMotorLeft.configFactoryDefault();

    m_climberMotorLeft.setNeutralMode(NeutralMode.Brake);
    m_climberMotorRight.setNeutralMode(NeutralMode.Brake);

    m_climberMotorLeft.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    m_climberMotorRight.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    m_climberMotorLeft.config_kF(ClimberConstants.kPIDLoopIdx, ClimberConstants.kF, ClimberConstants.kTimeoutMs);
    m_climberMotorLeft.config_kP(ClimberConstants.kPIDLoopIdx, ClimberConstants.kP, ClimberConstants.kTimeoutMs);
    m_climberMotorLeft.config_kI(ClimberConstants.kPIDLoopIdx, ClimberConstants.kI, ClimberConstants.kTimeoutMs);
    m_climberMotorLeft.config_kD(ClimberConstants.kPIDLoopIdx, ClimberConstants.kD, ClimberConstants.kTimeoutMs);
  }

  public void setClimberPosition(double inches) {
    m_climberMotorLeft.set(ControlMode.Position, inchesToTicks(inches));
    m_climberMotorRight.follow(m_climberMotorLeft);
  }

  /*
  public boolean isSwitchToggled() {
    return m_leftLimitSwitch.get() || m_rightLimitSwitch.get();
  }
  */

  public void stop() {
    m_climberMotorLeft.set(ControlMode.PercentOutput, 0);
  }

  public void zeroClimber() {
    m_climberMotorLeft.setSelectedSensorPosition(0);
    m_climberMotorRight.setSelectedSensorPosition(0);
  }

  public void printClimberValues() {
    SmartDashboard.putNumber("Left Climber Position Ticks", m_climberMotorLeft.getSelectedSensorPosition());
    SmartDashboard.putNumber("Right Climber Position Ticks", m_climberMotorRight.getSelectedSensorPosition());
    SmartDashboard.putNumber("Left Climber Position Inches",  ticksToInches(m_climberMotorLeft.getSelectedSensorPosition()));
    SmartDashboard.putNumber("Right Climber Position Inches",  ticksToInches(m_climberMotorRight.getSelectedSensorPosition()));
  }

  @Override
  public void periodic() {
  }

  public double inchesToTicks(double inches) {
    return inches * 4096.0 * ClimberConstants.kGearRatio / ClimberConstants.kShaftDiameter / Math.PI;
  }

  public double ticksToInches(double ticks){
    return ticks * ClimberConstants.kShaftDiameter * Math.PI / 4096.0 / ClimberConstants.kGearRatio;
  }
}
