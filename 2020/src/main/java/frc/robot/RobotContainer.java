/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.GeneralConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.AdjustClimb;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.Climb;
import frc.robot.commands.EnableClimber;
import frc.robot.commands.ReverseIntake;
import frc.robot.commands.Shoot;
import frc.robot.commands.SmartIntake;
import frc.robot.commands.SmartShoot;
import frc.robot.commands.TestAuto;
import frc.robot.commands.ThirtyInchReverse;
import frc.robot.commands.TrenchAuto;
import frc.robot.commands.TurnToAngle;
import frc.robot.commands.TurnToLimelight;
import frc.robot.commands.setShootPosition;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Intaker;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stirrer;

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


    m_drivetrain.setDefaultCommand(new ArcadeDrive(
      () -> m_driverController.getY(Hand.kLeft), 
      () -> m_driverController.getX(Hand.kRight), 
      m_drivetrain));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    

    // Operator Controls---------------------------------------------

    // Smart Intake
    new Trigger(() -> m_operatorController.getY(Hand.kLeft) > 0.25)
      .whileActiveContinuous(new SmartIntake(m_intaker, m_feeder, m_stirrer));

    //Reverse the intake
    new Trigger(() -> m_operatorController.getY(Hand.kLeft) < -0.25)
      .whileActiveContinuous(new ReverseIntake(m_intaker));

    // Rev the shooter for SmartShoot
    new JoystickButton(m_operatorController, Button.kX.value)
      .toggleWhenPressed(new Shoot(m_shooter).withTimeout(5));
    
    //Fully extend climber OR set shot position to Target Zone
    new POVButton(m_operatorController, 0)
      .whenPressed(new ConditionalCommand(
        new Climb(ClimberConstants.kSetFullyExtended, m_climber), 
        new setShootPosition(ShooterConstants.kTargetZone, m_shooter, m_hood), 
        m_climber::getClimbEnable));

    //Buddy climb height OR set shot position to Initiation Line
    new POVButton(m_operatorController, 90)
      .whenPressed(new ConditionalCommand(
        new ParallelCommandGroup(
          new Climb(m_climber.distanceFromGroundToInches(ClimberConstants.kSetBuddyClimb), m_climber),
          new InstantCommand(m_climber::dropBuddyClimb)),
        new setShootPosition(ShooterConstants.kInitiationLine, m_shooter, m_hood),
        m_climber::getClimbEnable));

    //Fully climbed height OR set shot position to Near Trench
    new POVButton(m_operatorController, 180)
      .whenPressed(new ConditionalCommand(
        new Climb(ClimberConstants.kSetFullyClimbed, m_climber),
        new setShootPosition(ShooterConstants.kNearTrench, m_shooter, m_hood),
        m_climber::getClimbEnable));

    //Manual climb
    new Trigger(() -> m_operatorController.getTriggerAxis(Hand.kRight) > 0.05)
      .whileActiveContinuous(new ConditionalCommand(
        new AdjustClimb(() -> m_operatorController.getTriggerAxis(Hand.kRight), m_climber), 
        new InstantCommand(), 
        m_climber::getClimbEnable));


    //Initiate climber-------------------------------------------------
    new JoystickButton(m_operatorController, Button.kStart.value)
      .and(new Trigger(() -> m_driverController.getTriggerAxis(Hand.kRight) >0.25))
      .whenActive(new EnableClimber(m_climber));


    // Driver Controls-------------------------------------------------

    // SmartShoot
    new JoystickButton(m_driverController, Button.kY.value)
      .whileHeld(new SmartShoot(m_feeder, m_shooter, m_stirrer));

    // Limelight
    new JoystickButton(m_driverController, Button.kA.value)
      .whileHeld(new SequentialCommandGroup(
        new InstantCommand(m_drivetrain::setPipelineOne),
        new WaitCommand(0.02),
        new TurnToLimelight(m_drivetrain), 
        new ParallelCommandGroup(
          new TurnToLimelight(m_drivetrain).perpetually(), 
          new SmartShoot(m_feeder, m_shooter, m_stirrer)),
        new InstantCommand(m_drivetrain::setPipelineZero)));


    //temporary commands -- COMMENT OUT THEN DEPLOY BEFORE LEAVING MEETING
    /*
    new JoystickButton(m_driverController, Button.kB.value)
      .whenPressed(new InstantCommand(m_climber::resetBuddyClimb));

    new JoystickButton(m_driverController, Button.kX.value)
      .whenPressed(new InstantCommand(m_climber::zeroClimber));

    new JoystickButton(m_driverController, Button.kStart.value)
      .whenPressed(new ThirtyInchReverse(m_drivetrain));

    new JoystickButton(m_driverController, Button.kBack.value)
      .whenPressed(m_drivetrain::zeroHeading);

    new POVButton(m_driverController, 0)
      .whileHeld(new TurnToAngle(0.0, m_drivetrain));

    new POVButton(m_driverController, 90)
      .whileHeld(new TurnToAngle(-90.0, m_drivetrain));

    new POVButton(m_driverController, 180)
      .whileHeld(new TurnToAngle(180.0, m_drivetrain));

    new POVButton(m_driverController, 270)
      .whileHeld(new TurnToAngle(90.0, m_drivetrain));
      */

    new POVButton(m_driverController, 0)
      .whenPressed(new InstantCommand(m_drivetrain::setPipelineZero));

    new POVButton(m_driverController, 180)
      .whenPressed(new InstantCommand(m_drivetrain::setPipelineOne));
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

  public void setDisabledSettings() {
    m_drivetrain.setCoastMode();
  }

public void setTeleSettings() {
  m_drivetrain.setRampRate();
  m_drivetrain.setBrakeMode();
}

public void printAllValues(){
  m_climber.printClimberValues();
  m_drivetrain.printDriveValues();
  m_feeder.printFeederValues();
  m_shooter.printShooterValues();
}

}
