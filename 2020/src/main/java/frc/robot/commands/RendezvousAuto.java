/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Intaker;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stirrer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class RendezvousAuto extends SequentialCommandGroup {
  static final Trajectory trenchTrajectory1 = TrajectoryGenerator.generateTrajectory(
      // Start at the origin facing the +X direction
      new Pose2d(0, 0, new Rotation2d(0)),
      // Pass through these two interior waypoints, making an 's' curve path
      List.of(new Translation2d(-Units.inchesToMeters(65) / 2, -Units.inchesToMeters(65) / 2)),
      // End 3 meters straight ahead of where we started, facing forward
      new Pose2d(0, -Units.inchesToMeters(65), new Rotation2d(Units.degreesToRadians(180))),
      // Pass config
      AutoConstants.config.setReversed(true));

  static final Trajectory trenchTrajectory2 = TrajectoryGenerator.generateTrajectory(
      // Start at the origin facing the +X direction
      new Pose2d(0, -Units.inchesToMeters(65), new Rotation2d(Units.degreesToRadians(180))),
      // Pass through these two interior waypoints, making an 's' curve path
      List.of(

      ),
      // End 3 meters straight ahead of where we started, facing forward
      new Pose2d(-Units.inchesToMeters(35 + 12 * 12), -Units.inchesToMeters(65),
          new Rotation2d(Units.degreesToRadians(180))),
      // Pass config
      AutoConstants.config.setReversed(false));

  /**
   * Creates a new AutoRoutine.
   */
  public RendezvousAuto(Shooter shooter, Feeder feeder, Stirrer stirrer, DriveTrain drivetrain, Intaker intaker,
      Hood hood) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new setShootPosition(ShooterConstants.kInitiationLine, shooter, hood).withTimeout(2),
        new SmartShoot(feeder, shooter, stirrer).withTimeout(3),
        new SequentialCommandGroup(new AutoCommand(drivetrain, trenchTrajectory1), new ParallelCommandGroup(
            new AutoCommand(drivetrain, trenchTrajectory2), new SmartIntake(intaker, feeder, stirrer))));
  }
}
