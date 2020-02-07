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

public class Feeder extends SubsystemBase {
  private CANSparkMax m_feederMotor;
  private DigitalInput m_BreakBeam;

  public Feeder() {
    m_feederMotor = new CANSparkMax(FeederConstants.kFeederMotorPort, MotorType.kBrushless);
    m_feederMotor.setInverted(true);

    m_BreakBeam = new DigitalInput(FeederConstants.kBreakBeamPort);
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

  public boolean getBreakBeam() {
    return m_BreakBeam.get();
  }

  public void printFeederValues() {
    SmartDashboard.putBoolean("Break Beam Status", getBreakBeam());
  }
}
