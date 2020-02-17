/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intaker;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stirrer;
import frc.robot.commands.Shoot;
import frc.robot.commands.SmartIntake;
import frc.robot.commands.SmartShoot;
import frc.robot.commands.TrenchAuto;
import frc.robot.commands.TurnToLimelight;
import frc.robot.commands.setShootPosition;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.GeneralConstants;
import frc.robot.Constants.IntakeConstants;
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

  SendableChooser<Command> autoSelector = new SendableChooser<Command>();
  
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    //m_drivetrain.setDefaultCommand(new ArcadeDrive(() -> m_driverController.getY(Hand.kLeft),
    //() -> m_driverController.getX(Hand.kRight), m_drivetrain));
    autoSelector.addOption("10ftshot", new TrenchAuto(m_shooter, m_feeder, m_stirrer, m_drivetrain));
    autoSelector.setDefaultOption("dsd", new PrintCommand("hello"));
    autoSelector.addOption("trenchshot", new PrintCommand("hola"));
    autoSelector.addOption("wallshot", new PrintCommand("bonjour"));
    SmartDashboard.putData("Auto Selector", autoSelector);


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
    return (Command) autoSelector.getSelected();
  }

  public void zeroDriveTrain() {
    m_drivetrain.resetOdometry(new Pose2d(0, 0, new Rotation2d(0)));
    m_drivetrain.zeroHeading();
  }
}
