/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.FeederConstants;
import frc.robot.subsystems.BallManager;

public class AdjustBallTwo extends CommandBase {
  public final BallManager m_ballManager;
  public double m_initialPos = 0;
  /**
   * Creates a new AdjustBallTwo.
   */
  public AdjustBallTwo(BallManager ballManager) {
    m_ballManager = ballManager;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_ballManager);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_initialPos = m_ballManager.getFeederPosition();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_ballManager.slowFeed();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_ballManager.stopFeeder();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_ballManager.getFeederPosition() > m_initialPos + FeederConstants.kAdjustBallTwoPos;
  }
}
