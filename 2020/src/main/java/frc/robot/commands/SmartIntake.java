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
  
  public SmartIntake(Intaker intaker, Feeder feeder, Stirrer stirrer) {
    m_feeder = feeder;
    m_intaker = intaker;
    m_stirrer = stirrer;

    addCommands(
      new Intake(m_intaker),
      new ParallelCommandGroup(
        new Stir(m_stirrer, m_feeder),
        new Feed(m_feeder)
      ),
      new FeedOnce(m_feeder)
    );
  }
}