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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {
  /**
   * Creates a new Climber.
   */
  private final TalonFX m_climberMotorLeft;
  private final TalonFX m_climberMotorRight;

  public Climber() {
    m_climberMotorLeft = new TalonFX(ClimberConstants.kClimberMotorLeftPort);
    m_climberMotorRight = new TalonFX(ClimberConstants.kClimberMotorRightPort);

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

  public void extend() {
    m_climberMotorLeft.set(ControlMode.Position, ClimberConstants.kExtended);
  }

  public void retract() {
    m_climberMotorLeft.set(ControlMode.Position, ClimberConstants.kRetracted);
  }

  public void stop() {
    m_climberMotorLeft.set(ControlMode.PercentOutput, 0);
  }

  public void printClimberValues() {
    SmartDashboard.putNumber("Climber Position", (m_climberMotorLeft.getSelectedSensorPosition()));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
