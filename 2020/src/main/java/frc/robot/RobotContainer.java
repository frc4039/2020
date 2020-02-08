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
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intaker;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.Climb;
import frc.robot.commands.Intake;
import frc.robot.commands.Shoot;
import frc.robot.Constants.GeneralConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;;

public class RobotContainer {
  // The robot's subsystems and comzzmands are defined here...
  private final DriveTrain m_drivetrain = new DriveTrain();
  private final Shooter m_shooter = new Shooter();
  private final Intaker m_intaker = new Intaker();
  private final Climber m_climber = new Climber();
  
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
    m_climber.setDefaultCommand(new Climb(() -> m_operatorController.getTriggerAxis(Hand.kLeft),() -> m_operatorController.getTriggerAxis(Hand.kRight), m_climber));
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    // Shoots 25%
    new JoystickButton(m_operatorController, Button.kX.value)
      .whenHeld(new Shoot(ShooterConstants.kShooterRPM6380, m_shooter));

    // Shoots 50%
    new JoystickButton(m_operatorController, Button.kY.value)
      .whenHeld(new Shoot(ShooterConstants.kShooterRPM11760, m_shooter));

    // Shoots 75%
    new JoystickButton(m_operatorController, Button.kA.value)
      .whenHeld(new Shoot(ShooterConstants.kShooterRPM15000, m_shooter));

    // Shoots 100%
    new JoystickButton(m_operatorController, Button.kB.value)
      .whenHeld(new Shoot(ShooterConstants.kShooterRPM18000, m_shooter));

    // Intakes 25%
    new POVButton(m_operatorController, 0)
      .whenHeld(new Intake(IntakeConstants.kIntake25, m_intaker));

    // Intakes 50%
    new POVButton(m_operatorController, 90)
      .whenHeld(new Intake(IntakeConstants.kIntake50, m_intaker));

    // Intakes 75%
    new POVButton(m_operatorController, 180)
      .whenHeld(new Intake(IntakeConstants.kIntake75, m_intaker));
    
    // Intake 100%
    new POVButton(m_operatorController, 270)
      .whenHeld(new Intake(IntakeConstants.kIntake25, m_intaker));
  }
}
