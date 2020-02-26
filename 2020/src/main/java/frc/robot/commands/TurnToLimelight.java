/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.VisionConstants;
import frc.robot.subsystems.DriveTrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TurnToLimelight extends PIDCommand {
  DriveTrain m_drivetrain;
  /**
   * Creates a new Limelight.
   */

  public TurnToLimelight(DriveTrain drivetrain) {
    super(
        // The controller that the command will use
        new PIDController(VisionConstants.kP, VisionConstants.kI, VisionConstants.kD),
        // This should return the measurement
        drivetrain::getLimelight,
        // This should return the setpoint (can also be a constant)
         () -> VisionConstants.kLimelightOffset,
        // This uses the output
        output -> drivetrain.arcadeDrive(0, (output + Math.signum(output)*VisionConstants.kFF)), drivetrain);
    getController().setTolerance(VisionConstants.kTolerance, VisionConstants.kRateTolerance);
    getController().setIntegratorRange(-VisionConstants.kMaxI, VisionConstants.kMaxI);

    m_drivetrain = drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  @Override
  public void initialize() {
    super.initialize();
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    SmartDashboard.putBoolean("Targeted", false);
  }
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    SmartDashboard.putNumber("Position error", getController().getPositionError());
    SmartDashboard.putNumber("Velocity error", getController().getVelocityError());
    if ((Math.abs(getController().getPositionError()) <= VisionConstants.kTolerance) && (Math.abs(getController().getVelocityError()) <= VisionConstants.kRateTolerance)){
      SmartDashboard.putBoolean("Targeted", true);
      return true;
    } else {
      SmartDashboard.putBoolean("Targeted", false);
      return false;
    }
    //return getController().atSetpoint();
  }
}
