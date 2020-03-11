/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Stirrer;

public class AltStir extends CommandBase {
  private final Stirrer m_stirrer;
  private final Feeder m_feeder;

  public AltStir(Stirrer stirrer, Feeder feeder) {
    m_stirrer = stirrer;
    m_feeder = feeder;

    addRequirements(m_stirrer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_stirrer.setShootState();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_stirrer.altStir();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_stirrer.stop();
    m_stirrer.setIdleState();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !m_feeder.getOrBreakBeams();
  }
}
