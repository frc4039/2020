/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intaker;

/**
 * An example command that uses an example subsystem.
 */
public class ReverseIntake extends CommandBase {
  private final Intaker m_intaker;
  // private final Stirrer m_stirrer;

  /**
   * Creates a new ArcadeDrive Command.
   *
   * @param subsystem 
   */
  public ReverseIntake(Intaker intaker) {
    m_intaker = intaker;
    // m_stirrer = stirrer;

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
    m_intaker.outtake();
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
