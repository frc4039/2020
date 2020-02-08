/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stirrer;

public class SmartShoot extends CommandBase {
  Feeder m_feeder;
  double m_rpm;
  double m_stirSpeed;
  Shooter m_shooter;
  Stirrer m_stirrer;

  public SmartShoot(double shooterRPM, double stirSpeed, Feeder feeder, Shooter shooter, Stirrer stirrer) {
    m_rpm = shooterRPM;
    m_feeder = feeder;
    m_shooter = shooter;
    m_stirrer = stirrer;
    m_stirSpeed = stirSpeed;

    addRequirements(m_feeder, m_shooter, m_stirrer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // only feeds if shooter RPM is +/- 500 of the current RPM
    m_shooter.shoot(m_rpm);
    // m_stirrer.stir(m_stirSpeed);

    if (Math.abs(m_shooter.returnCurrentRPM() - m_rpm) < 500) {
      m_feeder.feed();
      m_stirrer.stir(m_stirSpeed);
      m_shooter.shoot(m_rpm);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_feeder.stop();
    m_stirrer.stop();
    m_shooter.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}