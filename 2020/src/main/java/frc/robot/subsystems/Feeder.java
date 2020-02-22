/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.GeneralConstants;

public class Feeder extends SubsystemBase {
  private CANSparkMax m_feederMotor;
  private static DigitalInput m_BreakBeam1;
  private static DigitalInput m_BreakBeam2;

  public Feeder() {
    m_feederMotor = new CANSparkMax(FeederConstants.kFeederMotorPort, MotorType.kBrushless);

    m_feederMotor.restoreFactoryDefaults();
    
    m_feederMotor.setSmartCurrentLimit(FeederConstants.kCurrentLimit);
    
    m_feederMotor.setInverted(FeederConstants.kFeederInversion);

    if (GeneralConstants.realMatch) {
      m_feederMotor.burnFlash();
    }

    m_BreakBeam1 = new DigitalInput(FeederConstants.kBreakBeamPort1);
    m_BreakBeam2 = new DigitalInput(FeederConstants.kBreakBeamPort2);
  }

  @Override
  public void periodic() {

  }

  public void feed() {
    m_feederMotor.set(FeederConstants.kFeederPercent);
  }

  public void stop() {
    m_feederMotor.set(0);
  }

  public boolean getBottomBreakBeam(){
    return m_BreakBeam2.get();
  }

  public boolean getTopBreakBeam(){
    return m_BreakBeam1.get();
  }

  public boolean getOrBreakBeams() {
    return m_BreakBeam1.get() || m_BreakBeam2.get();
  }

  public boolean getAndBreakBeams() {
    return m_BreakBeam1.get() && m_BreakBeam2.get();
  }

  public boolean isStalled(){
    if(m_feederMotor.getAppliedOutput() != 0 && m_feederMotor.getEncoder().getVelocity() == 0){
      return true;
    } else {
      return false;
    }
  }

  public void printFeederValues() {
    SmartDashboard.putBoolean("Top Break Beam Status", m_BreakBeam1.get());
    SmartDashboard.putBoolean("Bottom Break Beam Status", m_BreakBeam2.get());
  }
}
