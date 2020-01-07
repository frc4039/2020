/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.Constants.DriveConstants;

public class DriveTrain extends SubsystemBase {
  private final SpeedController m_leftMotor = new SpeedControllerGroup(new CANSparkMax(DriveConstants.kLeftDriveMotor1Port, MotorType.kBrushless),
      new CANSparkMax(DriveConstants.kLeftDriveMotor2Port, MotorType.kBrushless));
  private final SpeedController m_rightMotor = new SpeedControllerGroup(new CANSparkMax(DriveConstants.kRightDriveMotor1Port, MotorType.kBrushless),
      new CANSparkMax(DriveConstants.kRightDriveMotor2Port, MotorType.kBrushless));

  public DriveTrain() {

  }

  public void drive(double left, double right) {
    m_leftMotor.set(left);
    m_rightMotor.set(right);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
