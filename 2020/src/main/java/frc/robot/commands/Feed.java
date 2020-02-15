/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;

public class Feed extends CommandBase {
  public double m_speed;
  public final Feeder m_feeder;
  private static Timer m_timer;
  private static boolean m_timerStarted = false;

  public Feed(double speed, Feeder feeder) {
    m_speed = speed;
    m_feeder = feeder;
    m_timer = new Timer();

    addRequirements(m_feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_feeder.printFeederValues();
    SmartDashboard.putNumber("Feeder timer", m_timer.get());

    if (m_feeder.getBreakBeam() && !m_timerStarted) {
      m_feeder.feed();

      return;
    }

    if (!m_timerStarted) {
      m_timer.start();
      m_timerStarted = true;
    }

    if (m_timerStarted && m_timer.get() < 3.0) {
      m_feeder.spinBackwards();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_feeder.stop();
    m_timerStarted = false;
    m_timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return !m_feeder.getBreakBeam();
  }
}
