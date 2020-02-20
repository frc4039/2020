/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.IntakeConstants;

public class Intaker extends SubsystemBase {

  private final VictorSPX m_intakeMotor;

  public Intaker() {
    m_intakeMotor = new VictorSPX(IntakeConstants.kIntakeMotorPort); 

    m_intakeMotor.configFactoryDefault(); 
    
    m_intakeMotor.setInverted(IntakeConstants.kIntakeInversion);
  }

  public void intake() {
    m_intakeMotor.set(ControlMode.PercentOutput, IntakeConstants.kIntakePercent);
  }

  public void outtake() {
    m_intakeMotor.set(ControlMode.PercentOutput, -IntakeConstants.kIntakePercent);
  }

  public void stop() {
    m_intakeMotor.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    
  }
}
