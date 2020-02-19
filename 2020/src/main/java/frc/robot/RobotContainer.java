/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Intaker;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stirrer;
import frc.robot.commands.TurnToAngle;
import frc.robot.commands.AdjustClimb;
import frc.robot.commands.AdjustHood;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.Climb;
import frc.robot.commands.Feed;
import frc.robot.commands.Intake;
import frc.robot.commands.ReverseIntake;
import frc.robot.commands.Shoot;
import frc.robot.commands.SmartIntake;
import frc.robot.commands.SmartShoot;
import frc.robot.commands.TestAuto;
import frc.robot.commands.TrenchAuto;
import frc.robot.commands.TurnToLimelight;
import frc.robot.Constants.ClimberConstants;
import frc.robot.commands.setShootPosition;
import frc.robot.Constants.GeneralConstants;
import frc.robot.Constants.HoodConstants;
import frc.robot.Constants.ShooterConstants;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrain m_drivetrain = new DriveTrain();
  private final Shooter m_shooter = new Shooter();
  private final Intaker m_intaker = new Intaker();
  private final Climber m_climber = new Climber();
  private final Stirrer m_stirrer = new Stirrer();
  private final Feeder m_feeder = new Feeder();
  private final Hood m_hood = new Hood();
  
  XboxController m_driverController = new XboxController(GeneralConstants.kDriverController);
  XboxController m_operatorController = new XboxController(GeneralConstants.kOperatorController);

  SendableChooser<Command> autoSelector = new SendableChooser<Command>();
  
  /**
   * The container for the robot.  Contains subsy
   * 
   * stems, OI devices, and commands.
   */
  public RobotContainer() {
    autoSelector.addOption("10ftshot", new TrenchAuto(m_shooter, m_feeder, m_stirrer, m_drivetrain));
    autoSelector.setDefaultOption("dsd", new TestAuto(m_shooter, m_feeder, m_stirrer, m_drivetrain, m_hood));
    autoSelector.addOption("trenchshot", new PrintCommand("hola"));
    autoSelector.addOption("wallshot", new PrintCommand("bonjour"));
    SmartDashboard.putData("Auto Selector", autoSelector);


    m_drivetrain.setDefaultCommand(new RunCommand(() -> m_drivetrain.arcadeDrive(m_driverController.getY(Hand.kLeft),
    m_driverController.getX(Hand.kRight)), m_drivetrain));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    

    // Operator Controls---------------------------------------------

    // Smart Intake
    new JoystickButton(m_operatorController, Button.kB.value)
      .whenHeld(new SmartIntake(m_intaker, m_feeder, m_stirrer));

    // Revv the shooter for SmartShoot
    new JoystickButton(m_operatorController, Button.kX.value)
      .toggleWhenPressed(new Shoot(m_shooter));

    // SmartShoot
    new JoystickButton(m_operatorController, Button.kBumperLeft.value)
      .whileHeld(new SmartShoot(m_feeder, m_shooter, m_stirrer));

    // Set Shoot RPM
      new POVButton(m_operatorController, 0)
        .whenPressed(new setShootPosition(ShooterConstants.kTargetZone, m_shooter, m_hood));

      new POVButton(m_operatorController, 270)
        .whenPressed(new setShootPosition(ShooterConstants.kInitiationLine, m_shooter, m_hood));

      new POVButton(m_operatorController, 180)
        .whenPressed(new setShootPosition(ShooterConstants.kNearTrench, m_shooter, m_hood));

    // Driver Controls-------------------------------------------------

    // Shoots 
    //new JoystickButton(m_driverController, Button.kA.value)
      //.whileHeld(new Shoot(ShooterConstants.kShooterRPM4, m_shooter));

    //cancel climber
    new JoystickButton(m_driverController, Button.kA.value)
      .whenPressed(new InstantCommand(m_climber::stop, m_climber));

    //new JoystickButton(m_driverController, Button.kY.value)
    //  .whenPressed(new InstantCommand(m_climber::zeroClimber));

    // Fully unspooled
    new JoystickButton(m_driverController, Button.kB.value)
      .whenPressed(new Climb(ClimberConstants.kSetpointExtended, m_climber));

    new JoystickButton(m_driverController, Button.kX.value)
      .whenPressed(new Climb(ClimberConstants.kSetpointClimbed, m_climber));

    new Trigger(() -> m_operatorController.getTriggerAxis(Hand.kRight) > 0.05)
      .whileActiveContinuous(new AdjustClimb(() -> m_operatorController.getTriggerAxis(Hand.kRight), m_climber));

    // Limelight
    new JoystickButton(m_driverController, Button.kBumperLeft.value)
      .whileHeld(new TurnToLimelight(m_drivetrain));

    new JoystickButton(m_driverController, Button.kY.value)
      .whenPressed(new InstantCommand(m_drivetrain::zeroHeading, m_drivetrain));

    //Reverse the intake
    new JoystickButton(m_driverController, Button.kBumperRight.value)
      .toggleWhenPressed(new ReverseIntake(m_intaker));

    // new POVButton(m_driverController, 0)
    //   .whenPressed(new TurnToAngle(0, m_drivetrain));

    // new POVButton(m_driverController, 90)
    //   .whenPressed(new TurnToAngle(90, m_drivetrain));

    // new POVButton(m_driverController, 180)
    //   .whenPressed(new TurnToAngle(180, m_drivetrain));

    // new POVButton(m_driverController, 270)
    //   .whenPressed(new TurnToAngle(-90, m_drivetrain));
  }

  public void init(){
    m_hood.stop();
  }

  public Command getAutonomousCommand() {    
    return (Command) autoSelector.getSelected();
  }

  public void zeroDriveTrain() {
    m_drivetrain.resetOdometry(new Pose2d(0, 0, new Rotation2d(0)));
    m_drivetrain.zeroHeading();
  }

  public void setCoastMode() {
    m_drivetrain.setCoastMode();
  }

public void setTeleSettings() {
  m_drivetrain.setRampRate();
}

}
