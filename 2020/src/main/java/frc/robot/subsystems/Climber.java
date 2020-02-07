package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {
  private CANSparkMax m_Motor1 = new CANSparkMax(ClimberConstants.kClimberMotor1Port, MotorType.kBrushless);
  private CANSparkMax m_Motor2 = new CANSparkMax(ClimberConstants.kClimberMotor2Port, MotorType.kBrushless);

  private final SpeedController m_leftMotor = new SpeedControllerGroup(m_Motor1);
  private final SpeedController m_rightMotor = new SpeedControllerGroup(m_Motor2);

  public Climber() {

  }
  
  @Override
  public void periodic() {

  }

  public void raise(Double speed){
    m_leftMotor.set(-speed);
    m_rightMotor.set(-speed);
  }

  public void lower(Double speed){
    m_leftMotor.set(speed);
    m_rightMotor.set(speed);
  }

  public void ticksToInches(double ticks) {
    ticks * 
  }
}