/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.wpilibj.SpeedController;
// import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.Constants.DriveConstants;

public class DriveTrain extends SubsystemBase {


  // private final SpeedController m_leftMotor = new SpeedControllerGroup(new CANSparkMax(DriveConstants.kLeftDriveMotor1Port, MotorType.kBrushless),
  //     new CANSparkMax(DriveConstants.kLeftDriveMotor2Port, MotorType.kBrushless));
  // private final SpeedController m_rightMotor = new SpeedControllerGroup(new CANSparkMax(DriveConstants.kRightDriveMotor1Port, MotorType.kBrushless),
  //     new CANSparkMax(DriveConstants.kRightDriveMotor2Port, MotorType.kBrushless));

  private CANSparkMax m_leftMotor1;
  private CANSparkMax m_leftMotor2;
  private CANSparkMax m_rightMotor1;
  private CANSparkMax m_rightMotor2;

  public DriveTrain() {
    m_leftMotor1 = new CANSparkMax(DriveConstants.kLeftDriveMotor1Port, MotorType.kBrushless);
    m_leftMotor2 = new CANSparkMax(DriveConstants.kLeftDriveMotor2Port, MotorType.kBrushless);
    m_rightMotor1 = new CANSparkMax(DriveConstants.kRightDriveMotor1Port, MotorType.kBrushless);
    m_rightMotor2 = new CANSparkMax(DriveConstants.kRightDriveMotor2Port, MotorType.kBrushless);
    
    m_leftMotor2.follow(m_leftMotor1);
    m_rightMotor2.follow(m_rightMotor1);
  }

  public void drive(double left, double right) {
    m_leftMotor1.set(left);
    m_rightMotor1.set(right);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
