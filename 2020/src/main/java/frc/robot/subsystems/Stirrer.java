/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.GeneralConstants;
import frc.robot.Constants.StirrerConstants;

public class Stirrer extends SubsystemBase {
  private CANSparkMax m_stirrerMotor1;
  private CANSparkMax m_stirrerMotor2;
  private Timer m_timer = new Timer();
  private Boolean m_isAlternating = true;
  
  public Stirrer() {
    m_stirrerMotor1 = new CANSparkMax(StirrerConstants.kStirrerMotor1Port, MotorType.kBrushless);
    m_stirrerMotor2 = new CANSparkMax(StirrerConstants.kStirrerMotor2Port, MotorType.kBrushless);

    m_stirrerMotor1.restoreFactoryDefaults();
    m_stirrerMotor2.restoreFactoryDefaults();

    m_stirrerMotor1.setSmartCurrentLimit(StirrerConstants.kStirrerCurrentLimit);
    m_stirrerMotor2.setSmartCurrentLimit(StirrerConstants.kStirrerCurrentLimit);

    m_stirrerMotor1.setInverted(StirrerConstants.kStirrerInversion1);
    m_stirrerMotor2.setInverted(StirrerConstants.kStirrerInversion2);

    if (GeneralConstants.realMatch) {
      m_stirrerMotor1.burnFlash();
      m_stirrerMotor2.burnFlash();
    }
  }

  public void setIdleState() {
    m_timer.stop();
    m_timer.reset();
  }

  public void setShootState() {
    m_timer.reset();
    m_timer.start();
  }

  public void stir() {
    m_stirrerMotor1.set(StirrerConstants.kStirrerPercent1);
    // m_stirrerMotor2.set(StirrerConstants.kStirrerPercent2);
  }

  public void altStir() {
    if (m_isAlternating) {
      m_stirrerMotor1.set(StirrerConstants.kStirrerPercent1);
      m_stirrerMotor2.set(0);
    } else {
      m_stirrerMotor1.set(0);
      m_stirrerMotor2.set(StirrerConstants.kStirrerPercent2);
    }
  }

  public void reverseAltStir() {
    if (m_isAlternating) {
      m_stirrerMotor1.set(-StirrerConstants.kStirrerPercent1);
      m_stirrerMotor2.set(0);
    } else {
      m_stirrerMotor1.set(0);
      m_stirrerMotor2.set(-StirrerConstants.kStirrerPercent2);
    }
  }
  
  

  public void stop() {
    m_stirrerMotor1.set(0);
    m_stirrerMotor2.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (m_timer.get() / StirrerConstants.kAlternatingTime > 1) {
      m_timer.reset();
      m_isAlternating = !m_isAlternating;
    }
  }
}
