/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.HoodConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class setShootPosition extends CommandBase {
  /**
   * Creates a new setSetPoint.
   */

  private int m_shootingPosition;
  private Shooter m_shooter;
  
  public setShootPosition(int shootingPosition, Shooter shooter) {
    m_shootingPosition = shootingPosition;
    m_shooter = shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (m_shootingPosition == ShooterConstants.kTargetZone) {
      m_shooter.setSetPoint(ShooterConstants.kShooterRPM4, HoodConstants.kPos3);
    } else if (m_shootingPosition == ShooterConstants.kShooterRPM3) {
      m_shooter.setSetPoint(ShooterConstants.kShooterRPM3, HoodConstants.kPos3);
    } else if (m_shootingPosition == ShooterConstants.kInitiationLine) {
      m_shooter.setSetPoint(ShooterConstants.kShooterRPM2, HoodConstants.kPos3);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
