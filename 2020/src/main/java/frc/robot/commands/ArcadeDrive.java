/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

/**
 * An example command that uses an example subsystem.
 */
public class ArcadeDrive extends CommandBase {
  private final DriveTrain m_drivetrain;
  private DoubleSupplier left;
  private DoubleSupplier right;

  /**
   * Creates a new ArcadeDrive Command.
   *
   * @param subsystem 
   */
  public ArcadeDrive(DoubleSupplier left, DoubleSupplier right, DriveTrain drivetrain) {
    m_drivetrain = drivetrain;
    this.left = left;
    this.right = right;
    
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double m_left= left.getAsDouble()*left.getAsDouble()*left.getAsDouble();
    double m_right = right.getAsDouble()*right.getAsDouble()*right.getAsDouble();

    double saturatedInput;
    double greaterInput = Math.max(Math.abs(m_left), Math.abs(m_right));
    double lesserInput = Math.abs(m_left) + Math.abs(m_right) - greaterInput;
    if (greaterInput > 0.0){
        saturatedInput = (lesserInput / greaterInput) + 1.0;
    } else{
        saturatedInput = 1.0;
    }

    m_left = m_left / saturatedInput;
    m_right = m_right / saturatedInput;

    m_drivetrain.drive(m_left + m_right, m_left - m_right);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.drive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
