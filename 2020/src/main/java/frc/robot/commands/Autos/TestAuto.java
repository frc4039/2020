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
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.BallManager;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Hood;

public class TestAuto extends SequentialCommandGroup {
  static final Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(
            new Translation2d(-Units.inchesToMeters(18), 0)
        ),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(-Units.inchesToMeters(36), 0, new Rotation2d(0)),
        // Pass config
        AutoConstants.slowConfig.setReversed(true)
    );
  /**
   * Creates a new AutoRoutine.
   */
  public TestAuto(BallManager ballManager, DriveTrain drivetrain, Hood hood) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
          new setShootPosition(ShooterConstants.kMidBumpers, ballManager, hood),
          new SmartShoot(ballManager).withTimeout(3), 
          new AutoCommand(drivetrain, exampleTrajectory));
  }
}
