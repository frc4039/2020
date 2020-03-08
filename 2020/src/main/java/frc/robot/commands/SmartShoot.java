/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intaker;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stirrer;

public class SmartShoot extends CommandBase {
  Feeder m_feeder;
  double m_rpm;
  Shooter m_shooter;
  Stirrer m_stirrer;
  Intaker m_intaker;

  public SmartShoot(Feeder feeder, Shooter shooter, Stirrer stirrer, Intaker intaker) {
    m_feeder = feeder;
    m_shooter = shooter;
    m_stirrer = stirrer;
    m_intaker = intaker;

    addRequirements(m_feeder, m_shooter, m_stirrer, m_intaker);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_stirrer.setShootState();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // only feeds if shooter RPM is +/- 500 of the current RPM
    m_shooter.shoot();
    // m_stirrer.stir(m_stirSpeed);

    if (Math.abs(m_shooter.returnCurrentRPM() - m_shooter.getSetpoint()) < ShooterConstants.kShooterThreshold) {
      m_intaker.intake();
      m_feeder.feed();
      m_stirrer.altStir();
      m_shooter.feedShooter();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_stirrer.setIdleState();
    m_feeder.stop();
    m_stirrer.stop();
    m_shooter.stop();
    m_intaker.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}