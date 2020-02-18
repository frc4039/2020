/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {
  private TalonFX m_Motor1 = new TalonFX(ClimberConstants.kClimberMotorLeftPort);
  private TalonFX m_Motor2 = new TalonFX(ClimberConstants.kClimberMotorRightPort);

  private double offset = 0;

  public Climber() {
    m_Motor1.configFactoryDefault();
    m_Motor2.configFactoryDefault();

    m_Motor1.setNeutralMode(NeutralMode.Brake);
    m_Motor2.setNeutralMode(NeutralMode.Brake);

    m_Motor1.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 0, 1));
    m_Motor2.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 0, 1));

    m_Motor1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    m_Motor2.configRemoteFeedbackFilter(m_Motor1.getDeviceID(), RemoteSensorSource.TalonFX_SelectedSensor, 1);
    
    m_Motor2.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor1, ClimberConstants.kTimeoutMs);				// Feedback Device of Remote Talon
    m_Motor2.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.IntegratedSensor, ClimberConstants.kTimeoutMs);	// Quadrature Encoder of current Talon

		m_Motor2.configSensorTerm(SensorTerm.Diff1, FeedbackDevice.RemoteSensor1, ClimberConstants.kTimeoutMs);
		m_Motor2.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.IntegratedSensor, ClimberConstants.kTimeoutMs);
    
    m_Motor2.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, ClimberConstants.PID_PRIMARY, ClimberConstants.kTimeoutMs);

    m_Motor2.configSelectedFeedbackCoefficient(	0.5, 0,	ClimberConstants.kTimeoutMs);	

    m_Motor2.configSelectedFeedbackSensor(FeedbackDevice.SensorDifference, ClimberConstants.PID_TURN, ClimberConstants.kTimeoutMs);

    m_Motor2.configSelectedFeedbackCoefficient(	1, 1,	ClimberConstants.kTimeoutMs);	

    m_Motor1.setInverted(true);
    m_Motor2.setInverted(false);

    m_Motor2.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, ClimberConstants.kTimeoutMs);

    m_Motor2.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, ClimberConstants.kTimeoutMs);

    m_Motor2.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, ClimberConstants.kTimeoutMs);

    m_Motor1.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20, ClimberConstants.kTimeoutMs);

    m_Motor2.configNeutralDeadband(ClimberConstants.kNeutralDeadband, ClimberConstants.kTimeoutMs);

    m_Motor1.configNeutralDeadband(ClimberConstants.kNeutralDeadband, ClimberConstants.kTimeoutMs);

    m_Motor1.configPeakOutputForward(0, ClimberConstants.kTimeoutMs);
    m_Motor1.configPeakOutputReverse(-1.0, ClimberConstants.kTimeoutMs);
    m_Motor2.configPeakOutputForward(0, ClimberConstants.kTimeoutMs);
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

    m_Motor2.selectProfileSlot(ClimberConstants.kSlotDistance, ClimberConstants.PID_PRIMARY);
    m_Motor2.selectProfileSlot(ClimberConstants.kSlotTurning, ClimberConstants.PID_TURN);

    m_Motor1.configReverseSoftLimitThreshold((int) inchesToTicks(ClimberConstants.kMotor1SoftLimit));
    m_Motor1.configForwardSoftLimitThreshold(0);
    m_Motor2.configReverseSoftLimitThreshold((int) inchesToTicks(ClimberConstants.kMotor2SoftLimit));
    m_Motor2.configForwardSoftLimitThreshold(0);
  
    m_Motor1.configReverseSoftLimitEnable(true);
    m_Motor1.configForwardSoftLimitEnable(true);
    m_Motor2.configReverseSoftLimitEnable(true);
    m_Motor2.configForwardSoftLimitEnable(true);
    
  }

  public void setClimberPosition(double inches){
    if(inches > ClimberConstants.kSetpointExtended) {
      offset = ClimberConstants.kOffset;
    } else {
      offset = 0;
    }
    m_Motor2.set(ControlMode.Position, -inchesToTicks(inches), DemandType.AuxPID, inchesToTicks(offset));
    m_Motor1.follow(m_Motor2, FollowerType.AuxOutput1);
  }
  
  public void stop() {
    m_Motor1.set(ControlMode.PercentOutput, 0);
    m_Motor2.set(ControlMode.PercentOutput, 0);
  }

  public double getClimberPosition() {
    return -ticksToInches(m_Motor2.getSelectedSensorPosition(ClimberConstants.kSlotDistance));
  }

  public void zeroClimber() {
    m_Motor1.getSensorCollection().setIntegratedSensorPosition(0, ClimberConstants.kTimeoutMs);
    m_Motor2.getSensorCollection().setIntegratedSensorPosition(0, ClimberConstants.kTimeoutMs);
  }

  public void printClimberValues() {
    SmartDashboard.putNumber("Loop 0 position Ticks motor 2", ticksToInches(m_Motor2.getSelectedSensorPosition(0)));
    SmartDashboard.putNumber("Loop 1 Position Ticks motor 2", ticksToInches(m_Motor2.getSelectedSensorPosition(1)));
    SmartDashboard.putNumber("Left Climber Position Inches empty",  ticksToInches(m_Motor1.getSelectedSensorPosition()));
    SmartDashboard.putNumber("Right Climber Position Inches empty",  ticksToInches(m_Motor2.getSelectedSensorPosition()));
    SmartDashboard.putNumber("motor 1 integrated", ticksToInches(m_Motor1.getSensorCollection().getIntegratedSensorPosition()));
    SmartDashboard.putNumber("motor 2 integrated", ticksToInches(m_Motor2.getSensorCollection().getIntegratedSensorPosition()));
  }

  @Override
  public void periodic() {
  }

  public double inchesToTicks(double inches) {
    return inches * 2048.0 * ClimberConstants.kGearRatio / ClimberConstants.kShaftDiameter / Math.PI;
  }

  public double ticksToInches(double ticks){
    return ticks * ClimberConstants.kShaftDiameter * Math.PI / 2048.0 / ClimberConstants.kGearRatio;
  }
}
