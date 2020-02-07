/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.StirrerConstants;

public class Stirrer extends SubsystemBase {
  private CANSparkMax m_stirrerMotor;
  
  public Stirrer() {
    m_stirrerMotor = new CANSparkMax(StirrerConstants.kStirrerMotorPort, MotorType.kBrushless);
    m_stirrerMotor.setSmartCurrentLimit(StirrerConstants.kStirrerCurrentLimit);
  }

  public void stir(double speed) {
    m_stirrerMotor.set(speed);
  }

  public void stop() {
    m_stirrerMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
