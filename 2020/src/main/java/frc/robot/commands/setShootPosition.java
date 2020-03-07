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
        m_shooter.setSetPoint(ShooterConstants.kNearTrenchShotRPM);
        m_hood.setPosition(HoodConstants.kNearTrenchPos);

        SmartDashboard.putString("Shoot setpoint", "TrenchShot (" + ShooterConstants.kNearTrenchShotRPM + ")");

        break;

      case ShooterConstants.kBackBumpers:
        m_shooter.setSetPoint(ShooterConstants.k10ftBackBumperShotRPM);
        m_hood.setPosition(HoodConstants.k10ftBackPos);

        SmartDashboard.putString("Shoot setpoint", "10FtBackShot (4250)");

        break;

      case ShooterConstants.kFrontBumpers:
        m_shooter.setSetPoint(ShooterConstants.k10ftFrontBumperShotRPM);
        m_hood.setPosition(HoodConstants.k10ftFrontPos);

        SmartDashboard.putString("Shoot setpoint", "10FtFrontShot (4600)");

        break;

      case ShooterConstants.kMidBumpers:
        m_shooter.setSetPoint(ShooterConstants.k10ftMidBumperShotRPM);
        m_hood.setPosition(HoodConstants.k10ftMidPos);

        SmartDashboard.putString("Shoot setpoint", "10FtFrontShot (4425)");

        break;

      case ShooterConstants.kFarTrench:
        m_shooter.setSetPoint(ShooterConstants.kFarTrenchShotRPM);
        m_hood.setPosition(HoodConstants.kFarTrenchPos);

        SmartDashboard.putString("Shoot setpoint", "10FtFrontShot (5500)");

        break;

      case ShooterConstants.kFrontBumperFar:
        m_shooter.setSetPoint(ShooterConstants.kFrontBumperFarRPM);
        m_hood.setPosition(HoodConstants.kFarTrenchPos);

        SmartDashboard.putString("Shoot setpoint", "10FtFrontShot (" + ShooterConstants.kFrontBumperFarRPM + ")");

      case ShooterConstants.kTrenchAutoFar:
        m_shooter.setSetPoint(ShooterConstants.kTrenchAutoRPM);
        m_hood.setPosition(HoodConstants.kFarTrenchPos);

        SmartDashboard.putString("Shoot setpoint", "10FtFrontShot (" + ShooterConstants.kFrontBumperFarRPM + ")");
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
        