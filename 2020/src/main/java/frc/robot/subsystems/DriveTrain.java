/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Constants.DriveConstants;

public class DriveTrain extends SubsystemBase {
  private CANSparkMax m_leftMotor1 = new CANSparkMax(DriveConstants.kLeftDriveMotor1Port, MotorType.kBrushless);
  private CANSparkMax m_leftMotor2 = new CANSparkMax(DriveConstants.kLeftDriveMotor2Port, MotorType.kBrushless);
  private CANSparkMax m_rightMotor1 = new CANSparkMax(DriveConstants.kRightDriveMotor1Port, MotorType.kBrushless);
  private CANSparkMax m_rightMotor2 = new CANSparkMax(DriveConstants.kRightDriveMotor2Port, MotorType.kBrushless);

  private final CANEncoder m_leftEncoder = m_leftMotor1.getEncoder();
  private final CANEncoder m_rightEncoder = m_rightMotor1.getEncoder();

  private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

  private final DifferentialDriveOdometry m_odometry;

  private final DifferentialDrive m_drive;

  private NetworkTable table;

  public DriveTrain() {

    m_leftMotor1.restoreFactoryDefaults();
    m_leftMotor2.restoreFactoryDefaults();
    m_rightMotor1.restoreFactoryDefaults();
    m_rightMotor2.restoreFactoryDefaults();

    m_leftMotor1.setSmartCurrentLimit(60);
    m_leftMotor2.setSmartCurrentLimit(60);
    m_rightMotor1.setSmartCurrentLimit(60);
    m_rightMotor2.setSmartCurrentLimit(60);

    m_leftMotor1.setIdleMode(IdleMode.kBrake);
    m_leftMotor2.setIdleMode(IdleMode.kBrake);
    m_rightMotor1.setIdleMode(IdleMode.kBrake);
    m_rightMotor2.setIdleMode(IdleMode.kBrake);  

    m_leftMotor1.setInverted(DriveConstants.kLeftInversion);
    m_leftMotor2.setInverted(DriveConstants.kLeftInversion);
    m_rightMotor1.setInverted(DriveConstants.kRightInversion);
    m_rightMotor2.setInverted(DriveConstants.kRightInversion);

    m_leftMotor2.follow(m_leftMotor1);
    m_rightMotor2.follow(m_rightMotor1);

    m_gyro.zeroYaw();
    resetEncoders();

    m_leftEncoder.setPositionConversionFactor(DriveConstants.kEncoderConstant);
    m_rightEncoder.setPositionConversionFactor(DriveConstants.kEncoderConstant);
    m_leftEncoder.setVelocityConversionFactor(DriveConstants.kEncoderConstant/60);
    m_rightEncoder.setVelocityConversionFactor(DriveConstants.kEncoderConstant/60);

    m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

    m_drive = new DifferentialDrive(m_leftMotor1, m_rightMotor1);

    m_drive.setRightSideInverted(false);

    table = NetworkTableInstance.getDefault().getTable("limelight");
    table.getEntry("pipeline").setNumber(6);

  }

  @Override
  public void periodic() {
    m_odometry.update(Rotation2d.fromDegrees(getHeading()), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());
    printDriveValues();
  }

  public void drive(double left, double right) {
    m_drive.feed();
    m_leftMotor1.set(left);
    m_rightMotor1.set(right);
  }

  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(m_leftEncoder.getVelocity(), m_rightEncoder.getVelocity());
  }

  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
  }

  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(-fwd, rot); 
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    m_leftMotor1.setVoltage(leftVolts);
    m_rightMotor1.setVoltage(rightVolts);    
    m_drive.feed();
  }

  public void resetEncoders() {
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);
  }

  public void setRampRate() {
    m_leftMotor1.setOpenLoopRampRate(DriveConstants.kVoltageRampRate);
    m_leftMotor2.setOpenLoopRampRate(DriveConstants.kVoltageRampRate);

    m_rightMotor1.setOpenLoopRampRate(DriveConstants.kVoltageRampRate);
    m_rightMotor2.setOpenLoopRampRate(DriveConstants.kVoltageRampRate);
  }

  public double getAverageEncoderDistance() {
    return (m_leftEncoder.getPosition() + m_rightEncoder.getPosition()) / 2.0;
  }
  
  public CANEncoder getLeftEncoder() {
    return m_leftEncoder;
  }

  public CANEncoder getRightEncoder() {
    return m_rightEncoder;
  }

  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  public void zeroHeading() {
    m_gyro.reset();
  }

  public double getHeading() {
    return Math.IEEEremainder(m_gyro.getAngle(), 360) * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }

  public double getTurnRate() {
    return m_gyro.getRate() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }

  public double normalizeJoystickWithDeadband(double val, double deadband) {
		val = (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;

		if (val != 0)
			val = Math.signum(val) * ((Math.abs(val) - deadband) / (1.0 - deadband));

		return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
	}

  public double getLimelight() {
    return -table.getEntry("tx").getDouble(0.0) / 27;
  }

  public void printDriveValues() {
    SmartDashboard.putNumber("limelightx", getLimelight());
    SmartDashboard.putNumber("Gyro", m_gyro.getAngle());
    SmartDashboard.putNumber("Left Encoder", m_leftEncoder.getPosition());
    SmartDashboard.putNumber("Right Encoder", m_rightEncoder.getPosition());
  }
}
