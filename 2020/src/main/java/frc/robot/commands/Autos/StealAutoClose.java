/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Autos;

import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
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
public class StealAutoClose extends SequentialCommandGroup {
  static final Trajectory farTrajectory1 = TrajectoryGenerator.generateTrajectory(
      // Start at the origin facing the +X direction
      new Pose2d(0, 0, new Rotation2d(0)),
      // Pass through these two interior waypoints, making an 's' curve path
      List.of(
        new Translation2d(Units.inchesToMeters(77.5), Units.inchesToMeters(0))
      ),
      // End 3 meters straight ahead of where we started, facing forward
      new Pose2d(Units.inchesToMeters(105), -Units.inchesToMeters(61), new Rotation2d(-Units.degreesToRadians(60))),
      // Pass config
      AutoConstants.slowConfig.setReversed(false));

  static final Trajectory farTrajectory2 = TrajectoryGenerator.generateTrajectory(
      // Start at the origin facing the +X direction
      new Pose2d(Units.inchesToMeters(103), -Units.inchesToMeters(61), new Rotation2d(-Units.degreesToRadians(70))),
      // Pass through these two interior waypoints, making an 's' curve path
      List.of(
        new Translation2d(-Units.inchesToMeters(5 * 12), Units.inchesToMeters(4 * 12))
      ),
      // End 3 meters straight ahead of where we started, facing forward
      new Pose2d(-Units.inchesToMeters(2.5 * 12), Units.inchesToMeters(12 * 12), new Rotation2d(Units.degreesToRadians(180))),
      // Pass config
      AutoConstants.fastConfig.setReversed(true));

  /**
   * Creates a new AutoRoutine.
   */
  public StealAutoClose(Shooter shooter, Feeder feeder, Stirrer stirrer, DriveTrain drivetrain, Intaker intaker, Hood hood) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
        new setShootPosition(ShooterConstants.kBackBumpers, shooter, hood),
        new ParallelRaceGroup(new AutoCommand(drivetrain, farTrajectory1), new Intake(intaker)),
        new ParallelRaceGroup(
            new AutoCommand(drivetrain, farTrajectory2),
            new Intake(intaker)),
        new LimelightShoot(drivetrain, feeder, shooter, stirrer, intaker).withTimeout(10)
        );
    }
}
