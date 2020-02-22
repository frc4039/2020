/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.HoodConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Shooter;

public class setShootPosition extends CommandBase {
  /**
   * Creates a new setSetPoint.
   */

  private int m_shootingPosition;
  private Shooter m_shooter;
  private Hood m_hood;
  
  
  public setShootPosition(int shootingPosition, Shooter shooter, Hood hood) {
    m_shootingPosition = shootingPosition;
    m_shooter = shooter;
    m_hood = hood;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch (m_shootingPosition) {
      case ShooterConstants.kTargetZone:
        m_shooter.setSetPoint(ShooterConstants.kWallShotRPM);
        m_hood.setPosition(HoodConstants.kWallPos);

        SmartDashboard.putString("Shoot setpoint", "WallShot (3475)");

        break;

      case ShooterConstants.kNearTrench:
        m_shooter.setSetPoint(ShooterConstants.kTrenchShotRPM);
        m_hood.setPosition(HoodConstants.kTrenchPos);

        SmartDashboard.putString("Shoot setpoint", "TrenchShot (4500)");

        break;

      case ShooterConstants.kInitiationLine:
        m_shooter.setSetPoint(ShooterConstants.k10FtShotRPM);
        m_hood.setPosition(HoodConstants.k10FtPos);

        SmartDashboard.putString("Shoot setpoint", "10FtShot (4250)");

        break;
    }
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
