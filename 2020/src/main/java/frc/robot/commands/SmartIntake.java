package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intaker;
import frc.robot.subsystems.Stirrer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html

public class SmartIntake extends ParallelCommandGroup {
  public final Feeder m_feeder;
  public final Intaker m_intaker;
  public final Stirrer m_stirrer;
  public final double m_intakeSpeed;
  public final double m_feedSpeed;
  public final double m_stirSpeed;
  
  public SmartIntake(double intakeSpeed, double feedSpeed, double stirSpeed, Intaker intaker, Feeder feeder, Stirrer stirrer) {
    m_feeder = feeder;
    m_intaker = intaker;
    m_stirrer = stirrer;
    m_intakeSpeed = intakeSpeed;
    m_stirSpeed = stirSpeed;
    m_feedSpeed = feedSpeed;

    addCommands(
      new Stir(m_stirSpeed, m_stirrer),
      new Intake(m_intakeSpeed, m_intaker),
      new Feed(m_feedSpeed, m_feeder)
    );
  }
}