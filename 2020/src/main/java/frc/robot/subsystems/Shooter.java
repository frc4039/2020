/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.GeneralConstants;
import frc.robot.Constants.HoodConstants;
// import edu.wpi.first.wpilibj.SpeedController;
// import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {


  // private final SpeedController m_leftMotor = new SpeedControllerGroup(new CANSparkMax(DriveConstants.kLeftDriveMotor1Port, MotorType.kBrushless),
  //     new CANSparkMax(DriveConstants.kLeftDriveMotor2Port, MotorType.kBrushless));
  // private final SpeedController m_rightMotor = new SpeedControllerGroup(new CANSparkMax(DriveConstants.kRightDriveMotor1Port, MotorType.kBrushless),
  //     new CANSparkMax(DriveConstants.kRightDriveMotor2Port, MotorType.kBrushless));

  private TalonSRX m_shooterMotor1;
  private TalonSRX m_shooterMotor2;
  private double m_rpmSetPoint;
  private double hoodSetPoint;

  public Shooter() {
    m_shooterMotor1 = new TalonSRX(ShooterConstants.kShooterMotor1Port);
    m_shooterMotor2 = new TalonSRX(ShooterConstants.kShooterMotor2Port);

    m_shooterMotor1.setInverted(ShooterConstants.kShooterInversion1);
    m_shooterMotor2.setInverted(ShooterConstants.kShooterInversion2);
    // m_shooterMotor1.setInverted(true);
    // m_shooterMotor2.setInverted(false);
    
    m_shooterMotor1.setSensorPhase(false);
        
    m_shooterMotor2.follow(m_shooterMotor1);

    m_shooterMotor1.configFactoryDefault();
    m_shooterMotor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, ShooterConstants.kPIDLoopIdx, ShooterConstants.kTimeoutMs);

    m_shooterMotor1.config_kF(ShooterConstants.kPIDLoopIdx, ShooterConstants.kF, ShooterConstants.kTimeoutMs);
    m_shooterMotor1.config_kP(ShooterConstants.kPIDLoopIdx, ShooterConstants.kP, ShooterConstants.kTimeoutMs);

    m_rpmSetPoint = ShooterConstants.kWallShotRPM;
    hoodSetPoint = HoodConstants.kPos3; // CHECK THESE POSITIONS IF THEY ARE RIGHT
  }

  public void shoot() {
    m_shooterMotor1.set(ControlMode.Velocity, RPMtoTicks(m_rpmSetPoint));
  }

  public void stop() {
    m_shooterMotor1.set(ControlMode.PercentOutput, 0);
  }

  public void setSetPoint(double rpm, double hood) {
    m_rpmSetPoint = rpm;
    hoodSetPoint = hood;
  }

  @Override
  public void periodic() {
    printShooterValues();
  }

  public double RPMtoTicks(double rpm) {
    /**
     * Units that ControlMode.velocity expects to be in ticks per 100ms, NOT per minute.
     * Reference:
     * https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/VelocityClosedLoop/src/main/java/frc/robot/Robot.java#L123
     */
    return rpm * ShooterConstants.kGearRatio * GeneralConstants.TicksPerRev / 600.0;
  }

  public double TicksToRPM(double ticks) {
    return ticks * 600 / GeneralConstants.TicksPerRev / ShooterConstants.kGearRatio;
  }

  public void printShooterValues() {
    SmartDashboard.putNumber("Shooter RPM", returnCurrentRPM());
    SmartDashboard.putNumber("RPM Set Point", m_rpmSetPoint);
    SmartDashboard.putNumber("Hood Set Point", hoodSetPoint);

    if (m_rpmSetPoint == ShooterConstants.kWallShotRPM) {
        SmartDashboard.putString("RPM Setpoint", "Wall shot (3475)");
    }
        
    
    else if (m_rpmSetPoint == ShooterConstants.k10FtShotRPM) {
        SmartDashboard.putString("RPM Set Point", "10 ft shot (4250)");
    }

    else {
        SmartDashboard.putString("RPM Set Point", "Trench shot (4500)");
    }
  }

  public double returnCurrentRPM() {
    return TicksToRPM(m_shooterMotor1.getSelectedSensorVelocity());
  }

  public double getSetpoint() {
    return m_rpmSetPoint;
  }
}
