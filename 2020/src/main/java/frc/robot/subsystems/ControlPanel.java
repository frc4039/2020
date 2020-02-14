/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ControlPanelConstants;

public class ControlPanel extends SubsystemBase {
  private double kSpinRatio;

  private CANSparkMax m_controlPanelMotor;
  private CANPIDController m_PIDController;

  public ControlPanel() {
    m_controlPanelMotor = new CANSparkMax(ControlPanelConstants.kControlPanelMotorPort, MotorType.kBrushless);

    m_PIDController = m_controlPanelMotor.getPIDController();
  }

  public void number(int numRotations) {
    kSpinRatio = ControlPanelConstants.kControlPanelCircumference / ControlPanelConstants.kWheelCircumferance;
  }

  public void setPosition(double position) {
    m_PIDController.setReference(3, ControlMode.Position);
  };

  @Override
  public void periodic() {

  }
}
