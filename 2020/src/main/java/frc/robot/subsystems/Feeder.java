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
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FeederConstants;

public class Feeder extends SubsystemBase {
  private CANSparkMax m_feederMotor;
  private DigitalInput m_BreakBeam1;
  private static DigitalInput m_BreakBeam2;
  private Timer m_timer;
  private boolean m_timerStarted = false;
  private boolean m_finishedArraging = false;

  public Feeder() {
    m_feederMotor = new CANSparkMax(FeederConstants.kFeederMotorPort, MotorType.kBrushless);

    m_feederMotor.setInverted(FeederConstants.kFeederInversion);

    m_feederMotor.setOpenLoopRampRate(FeederConstants.kCurrentLimit);

    m_BreakBeam1 = new DigitalInput(FeederConstants.kBreakBeamPort1);
    m_BreakBeam2 = new DigitalInput(FeederConstants.kBreakBeamPort2);
    
    m_timer = new Timer();
  }

  @Override
  public void periodic() {
    printFeederValues();
  }

  public void feed() {
    if(!getBreakBeam() && !m_timerStarted){
      m_timerStarted = true;
      m_timer.reset();
      m_timer.start();
    } else if(!m_timerStarted){
      System.out.println("In! " + !getBreakBeam() + " " + m_timerStarted + " " + m_timer.get());
      m_feederMotor.set(FeederConstants.kFeederPercent);
    } else if(m_timerStarted && m_timer.get() < 0.25){
      System.out.println("Out! " + !getBreakBeam() + " " + m_timerStarted + " " + m_timer.get());
      m_feederMotor.set(-FeederConstants.kFeederPercent);
    } else if(m_timerStarted && m_timer.get() > 0.25){
      System.out.println("Reset timer!");
      m_timerStarted = false;
      m_finishedArraging = true;
      m_timer.stop();
      m_timer.reset();
    }
  }

  
  public void constantFeed() {
    System.out.println("Constant! " + !getBreakBeam() + " " + m_timerStarted + " " + m_timer.get());
    m_feederMotor.set(FeederConstants.kFeederPercent);
  }

  public void stop() {
    m_feederMotor.set(0);
    m_timer.stop();
    m_timer.reset();
    m_timerStarted = false;
  }

  public boolean getBreakBeam() {
    return m_BreakBeam1.get() || m_BreakBeam2.get();
  }

  public static boolean getBottomBeamBreak(){
    return m_BreakBeam2.get();
  }

  public boolean finishedArraging(){
    System.out.println("Finished arranging is "+m_finishedArraging+"!");
    if(m_finishedArraging){
      System.out.println("Done arranging!");
      m_finishedArraging = false;
      return true;
    } else return false;
  }


  public void printFeederValues() {
    SmartDashboard.putBoolean("Top Break Beam Status", m_BreakBeam1.get());
    SmartDashboard.putBoolean("Bottom Break Beam Status", m_BreakBeam2.get());
    SmartDashboard.putNumber("Feeder timer", m_timer.get());
    SmartDashboard.putBoolean("Feeder timer started", m_timerStarted);
  }
}
