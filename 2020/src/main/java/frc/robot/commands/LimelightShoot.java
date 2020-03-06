/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.BallManager;
import frc.robot.subsystems.DriveTrain;

public class LimelightShoot extends SequentialCommandGroup {  
  public LimelightShoot(DriveTrain drivetrain, BallManager ballManager) {
    super(
      new ParallelCommandGroup(
        new ParallelRaceGroup(
          new SmartShoot(ballManager),
          new SequentialCommandGroup(
            new InstantCommand(drivetrain::setPipelineOne),
            new WaitCommand(0.2),
            new TurnToLimelight(drivetrain)
          ),
        new AdjustBallTwo(ballManager)
        )
      ),
      new ParallelCommandGroup(
        new TurnToLimelight(drivetrain).perpetually(),
        new SmartShoot(ballManager)
      )
    );
  }
}
