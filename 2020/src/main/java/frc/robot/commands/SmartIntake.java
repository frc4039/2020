package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallManager;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html

public class SmartIntake extends CommandBase {
  public final BallManager m_ballManager;
  
  public SmartIntake(BallManager ballManager) {
    m_ballManager = ballManager;

    addRequirements(m_ballManager);
  }

  @Override
  public void initialize() {
    m_ballManager.startTimer();
  }

  @Override
  public void execute() {
    m_ballManager.intake();
    m_ballManager.stirFeed();
  }

  @Override
  public void end(boolean interrupted) {
    m_ballManager.stopIntake();
    m_ballManager.stopFeeder();
    m_ballManager.stopStirrer();
    m_ballManager.stopTimer();
  }
}