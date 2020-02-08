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
  private CANSparkMax m_stirrerMotor1;
  private CANSparkMax m_stirrerMotor2;
  
  public Stirrer() {
    m_stirrerMotor1 = new CANSparkMax(StirrerConstants.kStirrerMotor1Port, MotorType.kBrushless);
    m_stirrerMotor2 = new CANSparkMax(StirrerConstants.kStirrerMotor2Port, MotorType.kBrushless);
    m_stirrerMotor1.setSmartCurrentLimit(StirrerConstants.kStirrerCurrentLimit);
    m_stirrerMotor2.setSmartCurrentLimit(StirrerConstants.kStirrerCurrentLimit);

    m_stirrerMotor1.setInverted(false);
    m_stirrerMotor2.setInverted(true);
  }

  public void stir(double speed) {
    m_stirrerMotor1.set(speed);
    m_stirrerMotor2.set(speed);
  }

  public void stop() {
    m_stirrerMotor1.set(0);
    m_stirrerMotor2.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
