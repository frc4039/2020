/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain extends SubsystemBase {
  private final SpeedController m_leftMotor =
      new SpeedControllerGroup(new CANSparkMax(0, MotorType.kBrushless), new CANSparkMax(1, MotorType.kBrushless));
  private final SpeedController m_rightMotor =
      new SpeedControllerGroup(new CANSparkMax(2, MotorType.kBrushless), new CANSparkMax(3, MotorType.kBrushless));
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotor, m_rightMotor);

  public Drivetrain() {
    super();

    // Let's name the sensors on the LiveWindow
    addChild("Drive", m_drive);
  }

  public void drive(double left, double right) {
    m_drive.arcadeDrive(left, right);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
