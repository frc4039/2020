package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {
  private TalonFX m_Motor1 = new TalonFX(ClimberConstants.kClimberMotor1Port);
  private TalonFX m_Motor2 = new TalonFX(ClimberConstants.kClimberMotor2Port);


  public Climber() {
    m_Motor1.setInverted(true);
    m_Motor1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

    m_Motor2.configRemoteFeedbackFilter(m_Motor1.getDeviceID(), RemoteSensorSource.TalonFX_SelectedSensor, 1);
    
    m_Motor2.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor1, ClimberConstants.kTimeoutMs);				// Feedback Device of Remote Talon
    m_Motor2.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.CTRE_MagEncoder_Relative, ClimberConstants.kTimeoutMs);	// Quadrature Encoder of current Talon

		m_Motor2.configSensorTerm(SensorTerm.Diff1, FeedbackDevice.RemoteSensor1, ClimberConstants.kTimeoutMs);
		m_Motor2.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.CTRE_MagEncoder_Relative, ClimberConstants.kTimeoutMs);
    
    m_Motor2.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, 0, ClimberConstants.kTimeoutMs);

    m_Motor2.configSelectedFeedbackCoefficient(	0.5, 0,	ClimberConstants.kTimeoutMs);	

    m_Motor2.configSelectedFeedbackSensor(FeedbackDevice.SensorDifference, 1, ClimberConstants.kTimeoutMs);

    m_Motor2.configSelectedFeedbackCoefficient(	1, 1,	ClimberConstants.kTimeoutMs);	

    m_Motor1.setSensorPhase(true);
    m_Motor2.setSensorPhase(true);

    m_Motor1.setInverted(true);
    m_Motor2.setInverted(false);

    m_Motor2.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, ClimberConstants.kTimeoutMs);

    m_Motor2.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, ClimberConstants.kTimeoutMs);

    m_Motor2.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, ClimberConstants.kTimeoutMs);

    m_Motor1.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, ClimberConstants.kTimeoutMs);

    m_Motor2.configNeutralDeadband(ClimberConstants.kNeutralDeadband, ClimberConstants.kTimeoutMs);

    m_Motor1.configNeutralDeadband(ClimberConstants.kNeutralDeadband, ClimberConstants.kTimeoutMs);

    m_Motor1.configPeakOutputForward(1.0, ClimberConstants.kTimeoutMs);
    m_Motor1.configPeakOutputReverse(-1.0, ClimberConstants.kTimeoutMs);
    m_Motor2.configPeakOutputForward(1.0, ClimberConstants.kTimeoutMs);
    m_Motor2.configPeakOutputReverse(-1.0, ClimberConstants.kTimeoutMs);  
    
    m_Motor2.config_kF(ClimberConstants.kSlotDistance, ClimberConstants.kDistanceF, ClimberConstants.kTimeoutMs);
    m_Motor2.config_kP(ClimberConstants.kSlotDistance, ClimberConstants.kDistanceP, ClimberConstants.kTimeoutMs);
    m_Motor2.config_kI(ClimberConstants.kSlotDistance, ClimberConstants.kDistanceI, ClimberConstants.kTimeoutMs);
    m_Motor2.config_kD(ClimberConstants.kSlotDistance, ClimberConstants.kDistanceD, ClimberConstants.kTimeoutMs);

    m_Motor2.config_IntegralZone(ClimberConstants.kSlotDistance, ClimberConstants.kDistanceIZone, ClimberConstants.kTimeoutMs);
    m_Motor2.configClosedLoopPeakOutput(ClimberConstants.kSlotDistance, ClimberConstants.kDistancePeakOutput, ClimberConstants.kTimeoutMs);

    m_Motor2.config_kF(ClimberConstants.kSlotTurning, ClimberConstants.kTurnF, ClimberConstants.kTimeoutMs);
    m_Motor2.config_kP(ClimberConstants.kSlotTurning, ClimberConstants.kTurnP, ClimberConstants.kTimeoutMs);
    m_Motor2.config_kI(ClimberConstants.kSlotTurning, ClimberConstants.kTurnI, ClimberConstants.kTimeoutMs);
    m_Motor2.config_kD(ClimberConstants.kSlotTurning, ClimberConstants.kTurnD, ClimberConstants.kTimeoutMs);

    m_Motor2.config_IntegralZone(ClimberConstants.kSlotTurning, ClimberConstants.kTurnIZone, ClimberConstants.kTimeoutMs);
    m_Motor2.configClosedLoopPeakOutput(ClimberConstants.kSlotTurning, ClimberConstants.kTurnPeakOutput, ClimberConstants.kTimeoutMs);

    m_Motor2.configClosedLoopPeriod(0, 1, ClimberConstants.kTimeoutMs);
    m_Motor2.configClosedLoopPeriod(1, 1, ClimberConstants.kTimeoutMs);

    m_Motor2.configAuxPIDPolarity(false, ClimberConstants.kTimeoutMs);

    m_Motor1.getSensorCollection().setIntegratedSensorPosition(0, ClimberConstants.kTimeoutMs);
    m_Motor2.getSensorCollection().setIntegratedSensorPosition(0, ClimberConstants.kTimeoutMs);
  }
  
  @Override
  public void periodic() {

  }

  public void raise(double inches) {
    m_Motor1.set(ControlMode.Position, inchesToTicks(inches));
    m_Motor2.set(ControlMode.Position, inchesToTicks(inches));
  }

  public void lower(double inches){
    m_Motor1.set(ControlMode.Position, inchesToTicks(inches));
    m_Motor2.set(ControlMode.Position, inchesToTicks(inches));
  }

  public double inchesToTicks(double inches) {
    return 4096.0 * Math.PI * (1.0 / 2.0) * inches * 36.0;
  }
}