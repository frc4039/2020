/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.TurningConstants;
import frc.robot.subsystems.DriveTrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TurnToAngle extends PIDCommand {
  /**
   * Creates a new TurnToAngle.
   */
  public TurnToAngle(double angle, DriveTrain m_drivetrain) {
    super(
        // The controller that the command will use
        new PIDController(TurningConstants.kP, TurningConstants.kI, TurningConstants.kD),
        // This should return the measurement
        m_drivetrain::getHeading,
        // This should return the setpoint (can also be a constant)
        () -> angle,
        // This uses the output
        output -> {
          m_drivetrain.arcadeDrive(0, output + Math.signum(output)*TurningConstants.kFF);
        }, 
        m_drivetrain
        );
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().enableContinuousInput(-180, 180);
    getController().setTolerance(TurningConstants.kTolerance, TurningConstants.kRateTolerance);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
