/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intaker;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stirrer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class LimelightShoot extends SequentialCommandGroup {
  /**
   * Creates a new LimelightShoot.
   */
  public LimelightShoot(DriveTrain drivetrain, Feeder feeder, Shooter shooter, Stirrer stirrer, Intaker intaker) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new SequentialCommandGroup(
        new ParallelRaceGroup(
          new Shoot(shooter),
          new SequentialCommandGroup(
            new InstantCommand(drivetrain::setPipelineOne),
            new WaitCommand(0.2),
            new TurnToLimelight(drivetrain)
          )
        ),
        new ParallelCommandGroup(
          new TurnToLimelight(drivetrain).perpetually(),
          new SmartShoot(feeder, shooter, stirrer, intaker)
        )
      )
    );
  }
}
