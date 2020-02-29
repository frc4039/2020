/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.FeederConstants;
import frc.robot.subsystems.Feeder;

public class AdjustBallTwo extends CommandBase {
  public final Feeder m_feeder;
  /**
   * Creates a new AdjustBallTwo.
   */
  public AdjustBallTwo(Feeder feeder) {
    m_feeder = feeder;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_feeder.zeroEncoder();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_feeder.unjam();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_feeder.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_feeder.getPosition() < FeederConstants.kAdjustBallTwoPos;
  }
}
