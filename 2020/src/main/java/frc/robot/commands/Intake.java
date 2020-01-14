/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intaker;
import frc.robot.subsystems.Shooter;

/**
 * An example command that uses an example subsystem.
 */
public class Intake extends CommandBase {
  private final Intaker m_intaker;
  private double m_speed;



  /**
   * Creates a new ArcadeDrive Command.
   *
   * @param subsystem 
   */
  public Intake(double speed, Intaker intaker) {
    m_speed = speed;
    m_intaker = intaker;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_intaker);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_intaker.intake(m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intaker.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
