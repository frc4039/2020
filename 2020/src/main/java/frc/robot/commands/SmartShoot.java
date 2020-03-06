/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.BallManager;

public class SmartShoot extends CommandBase {
  public final BallManager m_ballManager;

  public SmartShoot(BallManager ballManager) {
    m_ballManager = ballManager;

    addRequirements(m_ballManager);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_ballManager.setShootState();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // only feeds if shooter RPM is +/- 500 of the current RPM
    m_ballManager.shoot();
    // m_stirrer.stir(m_stirSpeed);

    if (Math.abs(m_ballManager.returnCurrentRPM() - m_ballManager.getSetpoint()) < ShooterConstants.kShooterThreshold) {
      m_ballManager.intake();
      m_ballManager.feed();
      m_ballManager.altStir();
      m_ballManager.feedShooter();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_ballManager.setIntakeState();
    m_ballManager.stopEverything();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}