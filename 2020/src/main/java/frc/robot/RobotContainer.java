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
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Intaker;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stirrer;
import frc.robot.commands.AdjustHood;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.Climb;
import frc.robot.commands.Feed;
import frc.robot.commands.Intake;
import frc.robot.commands.Shoot;
import frc.robot.commands.SmartIntake;
import frc.robot.commands.SmartShoot;
import frc.robot.commands.TurnToLimelight;
import frc.robot.Constants.ClimberConstants;
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
  private final Climber m_climber = new Climber();
  private final Stirrer m_stirrer = new Stirrer();
  private final Feeder m_feeder = new Feeder();
  private final Hood m_hood = new Hood();
  
  XboxController m_driverController = new XboxController(GeneralConstants.kDriverController);
  XboxController m_operatorController = new XboxController(GeneralConstants.kOperatorController);
  
  /**
   * The container for the robot.  Contains subsy
   * 
   * stems, OI devices, and commands.
   */
  public RobotContainer() {
    m_drivetrain.setDefaultCommand(new ArcadeDrive(() -> m_driverController.getY(Hand.kLeft),
    () -> m_driverController.getX(Hand.kRight), m_drivetrain));
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    

    // Operator Controls---------------------------------------------

    // Shoots
    new JoystickButton(m_operatorController, Button.kA.value)
      .whenHeld(new Shoot(ShooterConstants.kShooterRPM3, m_shooter));

    // // Intakes
    // new JoystickButton(m_operatorController, Button.kB.value)
    //   .whenHeld(new Intake(IntakeConstants.kIntakePercent, m_intaker));

    // Smart Intake
    new JoystickButton(m_operatorController, Button.kB.value)
      .whenHeld(new SmartIntake(IntakeConstants.kIntakePercent, FeederConstants.kFeederPercent, StirrerConstants.kStirrerPercent, m_intaker, m_feeder, m_stirrer));

    // Revv the shooter for SmartShoot
    new JoystickButton(m_operatorController, Button.kX.value)
      .toggleWhenPressed(new Shoot(ShooterConstants.kShooterRPM3, m_shooter));

    // SmartShoot
    new JoystickButton(m_operatorController, Button.kBumperLeft.value)
      .whileHeld(new SmartShoot(ShooterConstants.kShooterRPM3, StirrerConstants.kStirrerPercent, m_feeder, m_shooter, m_stirrer));


    /*
    //extend
    new JoystickButton(m_operatorController, Button.kBumperLeft.value)
      .toggleWhenPressed(new Climb(m_climber, 2));

    //retract
    new JoystickButton(m_operatorController, Button.kBumperRight.value)
      .toggleWhenPressed(new Climb(m_climber, 0));
    */

    // Driver Controls-------------------------------------------------

    // Shoots 
    //new JoystickButton(m_driverController, Button.kA.value)
      //.whileHeld(new Shoot(ShooterConstants.kShooterRPM4, m_shooter));

    //cancel climber
    new JoystickButton(m_driverController, Button.kA.value)
      .whenPressed(new InstantCommand(m_climber::stop, m_climber));

    new JoystickButton(m_driverController, Button.kY.value)
      .whenPressed(new InstantCommand(m_climber::zeroClimber));

    new JoystickButton(m_driverController, Button.kB.value)
      .whenPressed(new Climb(2, m_climber));

    new JoystickButton(m_driverController, Button.kX.value)
      .whenPressed(new Climb(-2, m_climber));

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

  public void printValues(){
    m_climber.printClimberValues();
  }
}
