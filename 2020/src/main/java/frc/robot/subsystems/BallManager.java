/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.GeneralConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.StirrerConstants;

public class BallManager extends SubsystemBase {
  // Feeder variables + hardware
  private CANSparkMax m_feederMotor;
  private static DigitalInput m_BreakBeam1;
  private static DigitalInput m_BreakBeam2;

  // Intake variables + hardware
  private VictorSPX m_intakeMotor;

  // Stirrer variables + hardware
  private CANSparkMax m_stirrerMotor1;
  private CANSparkMax m_stirrerMotor2;
  private Timer m_timer = new Timer();
  private Boolean m_isAlternating = true;

  // Shooter variables + hardware
  private TalonSRX m_shooterMotor1;
  private TalonSRX m_shooterMotor2;
  private CANSparkMax m_shooterFeederMotor;
  private double m_rpmSetPoint;

  public BallManager() {
    intakerInit();
    feederInit();
    shooterInit();
  }

  @Override
  public void periodic() {
    if (m_timer.get() / StirrerConstants.kAlternatingTime > 1) {
      m_timer.reset();
      m_isAlternating = !m_isAlternating;
    }
  }

  // Intaker subsystem

  public void intakerInit() {
    m_intakeMotor = new VictorSPX(IntakeConstants.kIntakeMotorPort); 

    m_intakeMotor.configFactoryDefault(); 
    
    m_intakeMotor.setInverted(IntakeConstants.kIntakeInversion);
  }

  public void intake() {
    m_intakeMotor.set(ControlMode.PercentOutput, IntakeConstants.kIntakePercent);
  }

  public void outtake() {
    m_intakeMotor.set(ControlMode.PercentOutput, -IntakeConstants.kIntakePercent);
  }

  public void stopIntake() {
    m_intakeMotor.set(ControlMode.PercentOutput, 0);
  }

  // Stirrer Subsystem

  public void stirrerInit() {
    m_stirrerMotor1 = new CANSparkMax(StirrerConstants.kStirrerMotor1Port, MotorType.kBrushless);
    m_stirrerMotor2 = new CANSparkMax(StirrerConstants.kStirrerMotor2Port, MotorType.kBrushless);

    m_stirrerMotor1.restoreFactoryDefaults();
    m_stirrerMotor2.restoreFactoryDefaults();

    m_stirrerMotor1.setSmartCurrentLimit(StirrerConstants.kStirrerCurrentLimit);
    m_stirrerMotor2.setSmartCurrentLimit(StirrerConstants.kStirrerCurrentLimit);

    m_stirrerMotor1.setInverted(StirrerConstants.kStirrerInversion1);
    m_stirrerMotor2.setInverted(StirrerConstants.kStirrerInversion2);

    if (GeneralConstants.realMatch) {
      m_stirrerMotor1.burnFlash();
      m_stirrerMotor2.burnFlash();
    }
  }

  public void setIntakeState() {
    m_timer.stop();
    m_timer.reset();
  }

  public void setShootState() {
    m_timer.reset();
    m_timer.start();
  }

  public void stir() {
    m_stirrerMotor1.set(StirrerConstants.kStirrerPercent1);
    // m_stirrerMotor2.set(StirrerConstants.kStirrerPercent2);
  }

  public void altStir() {
    if (m_isAlternating) {
      m_stirrerMotor1.set(StirrerConstants.kStirrerPercent1);
      m_stirrerMotor2.set(0);
    } else {
      m_stirrerMotor1.set(0);
      m_stirrerMotor2.set(StirrerConstants.kStirrerPercent2);
    }
  }

  public void stopStirrer() {
    m_stirrerMotor1.set(0);
    m_stirrerMotor2.set(0);
  }

  // Feeder Subsystem

  public void feederInit() {
    m_feederMotor = new CANSparkMax(FeederConstants.kFeederMotorPort, MotorType.kBrushless);

    m_feederMotor.restoreFactoryDefaults();
    
    m_feederMotor.setSmartCurrentLimit(FeederConstants.kCurrentLimit);
    
    m_feederMotor.setInverted(FeederConstants.kFeederInversion);

    if (GeneralConstants.realMatch) {
      m_feederMotor.burnFlash();
    }

    m_BreakBeam1 = new DigitalInput(FeederConstants.kBreakBeamPort1);
    m_BreakBeam2 = new DigitalInput(FeederConstants.kBreakBeamPort2);
  }

  public void feed() {
    if (getOrBreakBeams()) {
      m_feederMotor.set(FeederConstants.kFeederPercent);
    }
  }

  public void unjam() {
    m_feederMotor.set(-FeederConstants.kFeederPercent);
  }

  public void slowFeed() {
    m_feederMotor.set(FeederConstants.kFeederSlow);
  }

  public void stopFeeder() {
    m_feederMotor.set(0);
  }

  public double getFeederPosition() {
    return m_feederMotor.getEncoder().getPosition();
  }

  public void zeroEncoder(){
    m_feederMotor.getEncoder().setPosition(0);
  }

  public boolean getBottomBreakBeam(){
    return m_BreakBeam2.get();
  }

  public boolean getTopBreakBeam(){
    return m_BreakBeam1.get();
  }

  public boolean getOrBreakBeams() {
    return m_BreakBeam1.get() || m_BreakBeam2.get();
  }

  public boolean getAndBreakBeams() {
    return m_BreakBeam1.get() && m_BreakBeam2.get();
  }

  public boolean isStalled(){
    if(m_feederMotor.getAppliedOutput() != 0 && m_feederMotor.getEncoder().getVelocity() == 0){
      return true;
    } else {
      return false;
    }
  }

  public void printFeederValues() {
    SmartDashboard.putBoolean("Top Break Beam Status", m_BreakBeam1.get());
    SmartDashboard.putBoolean("Bottom Break Beam Status", m_BreakBeam2.get());
    SmartDashboard.putNumber("Feeder Encoder Value", getFeederPosition());
  }

  // Shooter subsystem

  public void shooterInit() {
    m_shooterMotor1 = new TalonSRX(ShooterConstants.kShooterMotor1Port);
    m_shooterMotor2 = new TalonSRX(ShooterConstants.kShooterMotor2Port);
    
    m_shooterMotor1.configFactoryDefault();
    m_shooterMotor2.configFactoryDefault();

    m_shooterMotor1.setInverted(ShooterConstants.kShooterInversion1);
    m_shooterMotor2.setInverted(ShooterConstants.kShooterInversion2);
    
    m_shooterMotor1.setSensorPhase(false);
        
    m_shooterMotor2.follow(m_shooterMotor1);

    m_shooterMotor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, ShooterConstants.kPIDLoopIdx, ShooterConstants.kTimeoutMs);

    m_shooterMotor1.config_kF(ShooterConstants.kPIDLoopIdx, ShooterConstants.kF, ShooterConstants.kTimeoutMs);
    m_shooterMotor1.config_kP(ShooterConstants.kPIDLoopIdx, ShooterConstants.kP, ShooterConstants.kTimeoutMs);


    m_shooterFeederMotor = new CANSparkMax(ShooterConstants.kShooterFeederMotorPort, MotorType.kBrushless);

    m_shooterFeederMotor.restoreFactoryDefaults();

    m_shooterFeederMotor.setInverted(ShooterConstants.kShooterFeederInversion);

    m_shooterFeederMotor.setSmartCurrentLimit(ShooterConstants.kShooterFeederMotorCurrentLimit);

    if (GeneralConstants.realMatch) {
      m_shooterFeederMotor.burnFlash();
    }

    m_rpmSetPoint = ShooterConstants.k10ftBackBumperShotRPM;
  }

  public void shoot() {
    m_shooterMotor1.set(ControlMode.Velocity, RPMtoTicks(m_rpmSetPoint));
  }

  public void feedShooter() {
    m_shooterFeederMotor.set(ShooterConstants.kShooterFeederSpeed);
  }

  public void stopShooter() {
    m_shooterMotor1.set(ControlMode.PercentOutput, 0);
    m_shooterFeederMotor.set(0);
  }

  public void setSetPoint(double rpm) {
    m_rpmSetPoint = rpm;
  }

  public double RPMtoTicks(double rpm) {
    return rpm * ShooterConstants.kGearRatio * 4096 / 600.0;
  }

  public double TicksToRPM(double ticks) {
    return ticks * 600 / 4096 / ShooterConstants.kGearRatio;
  }

  public void printShooterValues() {
    SmartDashboard.putNumber("Shooter RPM", returnCurrentRPM());
  }

  public double returnCurrentRPM() {
    return TicksToRPM(m_shooterMotor1.getSelectedSensorVelocity());
  }

  public double getSetpoint() {
    return m_rpmSetPoint;
  }

  // Ball Manager methods

  public void stopEverything() {
    stopFeeder();
    stopIntake();
    stopShooter();
    stopStirrer();
  }

  public void stirFeed() {
    if (getOrBreakBeams()) {
      stir();
      feed();
    }
  }
}
