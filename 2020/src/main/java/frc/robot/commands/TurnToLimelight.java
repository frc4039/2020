/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.VisionConstants;
import frc.robot.subsystems.DriveTrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TurnToLimelight extends PIDCommand {
  /**
   * Creates a new Limelight.
   */
  public TurnToLimelight(double targetLimelight, DriveTrain drivebase) {
    super(
        // The controller that the command will use
        new PIDController(VisionConstants.kP, VisionConstants.kI, VisionConstants.kD),
        // This should return the measurement
        () -> 0,
        // This should return the setpoint (can also be a constant)
        drivebase::getLimelight,
        // This uses the output
        output -> drivebase.arcadeDrive(0, output), drivebase);

    getController().setTolerance(VisionConstants.kTurnToleranceDeg, VisionConstants.kTurnRateToleranceDegPerS);
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
