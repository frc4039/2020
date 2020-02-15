/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Intaker;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stirrer;
import frc.robot.commands.AdjustHood;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.Shoot;
import frc.robot.commands.SmartIntake;
import frc.robot.commands.SmartShoot;
import frc.robot.commands.TurnToLimelight;
import frc.robot.commands.setShootPosition;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.GeneralConstants;
import frc.robot.Constants.HoodConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.StirrerConstants;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrain m_drivetrain = new DriveTrain();
  private final Shooter m_shooter = new Shooter();
  private final Intaker m_intaker = new Intaker();
  private final Stirrer m_stirrer = new Stirrer();
  private final Feeder m_feeder = new Feeder();
  //private final Hood m_hood = new Hood();
  
  XboxController m_driverController = new XboxController(GeneralConstants.kDriverController);
  XboxController m_operatorController = new XboxController(GeneralConstants.kOperatorController);
  
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    //m_drivetrain.setDefaultCommand(new ArcadeDrive(() -> m_driverController.getY(Hand.kLeft),
    //() -> m_driverController.getX(Hand.kRight), m_drivetrain));

    m_drivetrain.setDefaultCommand(new RunCommand(() -> m_drivetrain.arcadeDrive(m_driverController.getY(Hand.kLeft),
      m_driverController.getX(Hand.kRight)), m_drivetrain));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    

    // Operator Controls---------------------------------------------

    // Shoots
    new JoystickButton(m_operatorController, Button.kA.value)
      .whenHeld(new Shoot(m_shooter));

    // // Intakes
    // new JoystickButton(m_operatorController, Button.kB.value)
    //   .whenHeld(new Intake(IntakeConstants.kIntakePercent, m_intaker));

    // Smart Intake
    new JoystickButton(m_operatorController, Button.kB.value)
      .whenHeld(new SmartIntake(IntakeConstants.kIntakePercent, FeederConstants.kFeederPercent, StirrerConstants.kStirrerPercent, m_intaker, m_feeder, m_stirrer));

    // Revv the shooter for SmartShoot
    new JoystickButton(m_operatorController, Button.kX.value)
      .toggleWhenPressed(new Shoot(m_shooter));

    // SmartShoot
    new JoystickButton(m_operatorController, Button.kBumperLeft.value)
      .whileHeld(new SmartShoot(StirrerConstants.kStirrerPercent, m_feeder, m_shooter, m_stirrer));


    // Set Shoot RPM
      new POVButton(m_operatorController, 0)
      .toggleWhenPressed(new setShootPosition(1, m_shooter));

      new POVButton(m_operatorController, 270)
      .toggleWhenPressed(new setShootPosition(2, m_shooter));

      new POVButton(m_operatorController, 180)
      .toggleWhenPressed(new setShootPosition(3, m_shooter));

    // Driver Controls-------------------------------------------------

    // Shoots 
    new JoystickButton(m_driverController, Button.kA.value)
      .whileHeld(new Shoot(m_shooter));

    // Limelight
    new JoystickButton(m_driverController, Button.kBumperLeft.value)
      .whileHeld(new TurnToLimelight(m_drivetrain));

    //Move Servo
    /*
    new POVButton(m_operatorController, 0)
      .toggleWhenPressed(new AdjustHood(HoodConstants.kPos1, m_hood));

    new POVButton(m_operatorController, 90)
      .toggleWhenPressed(new AdjustHood(HoodConstants.kPos2, m_hood));

    new POVButton(m_operatorController, 180)
      .toggleWhenPressed(new AdjustHood(HoodConstants.kPos3, m_hood));

    new POVButton(m_operatorController, 270)
      .toggleWhenPressed(new AdjustHood(HoodConstants.kFullExtend, m_hood));
      */
  }

  public Command getAutonomousCommand() {

    // Create a voltage constraint to ensure we don't accelerate too fast
    var autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(DriveConstants.ksVolts,
                                       DriveConstants.kvVoltSecondsPerMeter,
                                       DriveConstants.kaVoltSecondsSquaredPerMeter),
            DriveConstants.kDriveKinematics,
            10);

    // Create config for trajectory
    TrajectoryConfig config =
        new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecond,
                             AutoConstants.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(DriveConstants.kDriveKinematics)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);

    // An example trajectory to follow.  All units in meters.
    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(
            new Translation2d(1, 1),
            new Translation2d(2, -1)
        ),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(3, 0, new Rotation2d(0)),
        // Pass config
        config
    );

    RamseteCommand ramseteCommand = new RamseteCommand(
        exampleTrajectory,
        m_drivetrain::getPose,
        new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
        new SimpleMotorFeedforward(DriveConstants.ksVolts,
                                   DriveConstants.kvVoltSecondsPerMeter,
                                   DriveConstants.kaVoltSecondsSquaredPerMeter),
        DriveConstants.kDriveKinematics,
        m_drivetrain::getWheelSpeeds,
        new PIDController(DriveConstants.kPDriveVel, 0, 0),
        new PIDController(DriveConstants.kPDriveVel, 0, 0),
        // RamseteCommand passes volts to the callback
        m_drivetrain::tankDriveVolts,
        m_drivetrain
    );

    // Run path following command, then stop at the end.
    return ramseteCommand.andThen(() -> m_drivetrain.tankDriveVolts(0, 0));
  }
}
