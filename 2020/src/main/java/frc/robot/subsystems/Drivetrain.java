/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import frc.robot.Constants.DriveConstants;

public class DriveTrain extends SubsystemBase {
  private CANSparkMax m_leftMotor1 = new CANSparkMax(DriveConstants.kLeftDriveMotor1Port, MotorType.kBrushless);
  private CANSparkMax m_leftMotor2 = new CANSparkMax(DriveConstants.kLeftDriveMotor2Port, MotorType.kBrushless);;
  private CANSparkMax m_rightMotor1 = new CANSparkMax(DriveConstants.kRightDriveMotor1Port, MotorType.kBrushless);;
  private CANSparkMax m_rightMotor2 = new CANSparkMax(DriveConstants.kRightDriveMotor2Port, MotorType.kBrushless);;

  private final SpeedController m_leftMotor = new SpeedControllerGroup(m_leftMotor1, m_leftMotor2);
  private final SpeedController m_rightMotor = new SpeedControllerGroup(m_rightMotor1, m_rightMotor2);

  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotor, m_rightMotor);

  private final CANEncoder m_leftEncoder = m_leftMotor1.getEncoder();
  private final CANEncoder m_rightEncoder = m_rightMotor1.getEncoder();

  private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

  private NetworkTable table;

  private final DifferentialDriveOdometry m_odometry;

  public DriveTrain() {
    resetEncoders();

    m_leftEncoder.setPositionConversionFactor(DriveConstants.kDistancePerTick);
    m_rightEncoder.setPositionConversionFactor(DriveConstants.kDistancePerTick);

    m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

    m_leftMotor.setInverted(true);
    m_rightMotor.setInverted(false);

    table = NetworkTableInstance.getDefault().getTable("limelight");
  }

  public void drive(double left, double right) {
    m_leftMotor.set(left);
    m_rightMotor.set(right);
  }

  @Override
  public void periodic() {
    m_odometry.update(Rotation2d.fromDegrees(getHeading()), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());
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
    m_drive.arcadeDrive(fwd, rot);
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    m_leftMotor.setVoltage(leftVolts);
    m_rightMotor.setVoltage(-rightVolts);    
  }

  public void resetEncoders() {
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);
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

  public double getLimelight() {
    return table.getEntry("tx").getDouble(0.0) / 27;
  }
}
