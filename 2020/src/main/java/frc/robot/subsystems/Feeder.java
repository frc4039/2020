/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FeederConstants;

public class Feeder extends SubsystemBase {
  private VictorSPX m_feederMotor;
  private DigitalInput m_BreakBeam;

  public Feeder() {
    m_feederMotor = new VictorSPX(FeederConstants.kFeederMotorPort);
    m_feederMotor.setInverted(true);

    m_BreakBeam = new DigitalInput(FeederConstants.kBreakBeamPort);
  }

  @Override
  public void periodic() {
    
  }

  public void feed() {
    m_feederMotor.set(ControlMode.PercentOutput, FeederConstants.kPercentFeed);
  }

  public void stop() {
    m_feederMotor.set(ControlMode.PercentOutput, 0);
  }

  public void getBreakBeam() {
    m_BreakBeam.get();
  }
}
