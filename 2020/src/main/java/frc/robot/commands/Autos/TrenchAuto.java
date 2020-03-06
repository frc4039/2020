/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Autos;

import java.util.List;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.BallManager;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Hood;

public class TrenchAuto extends SequentialCommandGroup {
  static final Trajectory trenchTrajectory1 = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(
            new Translation2d(0, -Units.inchesToMeters(65))
        ),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(-Units.inchesToMeters(16 * 12 + 6), -Units.inchesToMeters(65), new Rotation2d(Units.degreesToRadians(180))),
        AutoConstants.MediumConfig.setReversed(false)
    );

    static final Trajectory trenchTrajectory2 = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, -Units.inchesToMeters(65), new Rotation2d(Units.degreesToRadians(180))),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(
            new Translation2d(-Units.inchesToMeters(8 * 12), -Units.inchesToMeters(65))
        ),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(-Units.inchesToMeters(16 * 12 + 6), -Units.inchesToMeters(65), new Rotation2d(Units.degreesToRadians(180))),
        AutoConstants.MediumConfig.setReversed(false)
    );

    static final Trajectory trenchTrajectory3 = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(-Units.inchesToMeters(16 * 12 + 6), -Units.inchesToMeters(65), new Rotation2d(Units.degreesToRadians(180))),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(
            new Translation2d(-Units.inchesToMeters((6 * 12 + 6)-40), -Units.inchesToMeters(65)),
            new Translation2d(-Units.inchesToMeters((6 * 12 + 6)-40), -Units.inchesToMeters(65)-40)
        ),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(-Units.inchesToMeters((16 * 12 + 6) - 40) , -Units.inchesToMeters(65), new Rotation2d(Units.degreesToRadians(0))),
        AutoConstants.slowConfig.setReversed(true)
    );

  /**
   * Creates a new AutoRoutine.
   */
  public TrenchAuto(BallManager ballManager, DriveTrain drivetrain, Hood hood) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
          new setShootPosition(ShooterConstants.kBackBumpers, ballManager, hood),
          new SmartShoot(ballManager).withTimeout(3), 
        //   new AutoCommand(drivetrain, trenchTrajectory1),
          new ParallelRaceGroup(
                                   new AutoCommand(drivetrain, trenchTrajectory1),
                                   new SmartIntake(ballManager)
                                   )
        //   new setShootPosition(ShooterConstants.kFarTrench, shooter, hood)
          // new AutoCommand(drivetrain, trenchTrajectory3),
          // new LimelightShoot(drivetrain, feeder, shooter, stirrer, intaker)
          );
  }
}
