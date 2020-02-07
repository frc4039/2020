package frc.robot.commands;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ClimberConstants;
import frc.robot.subsystems.Climber;

public class Climb extends CommandBase {
    private DoubleSupplier m_leftTrigger;
    private DoubleSupplier m_rightTrigger;
    private Climber m_Climber;

    public Climb(DoubleSupplier leftTrigger, DoubleSupplier rightTrigger, Climber m_Climb){
        m_leftTrigger = leftTrigger;
        m_rightTrigger = rightTrigger;
        m_Climber = m_Climb;

        addRequirements(m_Climber);
    }

    @Override
    public void execute() {
        if (m_leftTrigger.getAsDouble() > ClimberConstants.kTriggerDeadzone) {
            m_Climber.lower(m_leftTrigger.getAsDouble());
        }
        else if (m_rightTrigger.getAsDouble() > ClimberConstants.kTriggerDeadzone) {
            m_Climber.raise(m_rightTrigger.getAsDouble());
        }
    }

    
}