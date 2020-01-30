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
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intaker;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Stirrer;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.Intake;
import frc.robot.commands.Shoot;
import frc.robot.commands.Stir;
import frc.robot.commands.TurnToLimelight;
import frc.robot.Constants.GeneralConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrain m_drivetrain = new DriveTrain();
  private final Shooter m_shooter = new Shooter();
  private final Intaker m_intaker = new Intaker();
  private final Stirrer m_stirrer = new Stirrer();
  
  XboxController m_driverController = new XboxController(GeneralConstants.kDriverController);
  XboxController m_operatorController = new XboxController(GeneralConstants.kOperatorController);
  
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_drivetrain.setDefaultCommand(new ArcadeDrive(() -> m_driverController.getY(Hand.kLeft),
    () -> m_driverController.getX(Hand.kRight), m_drivetrain));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    m_drivetrain.printDriveValues();
    
    // Shoots
    new JoystickButton(m_operatorController, Button.kA.value)
      .whenHeld(new Shoot(ShooterConstants.kShooterRPM4, m_shooter));

    // Intakes
    new JoystickButton(m_operatorController, Button.kB.value)
      .whenHeld(new Intake(IntakeConstants.kIntake100, m_intaker));

    // Stir
    new JoystickButton(m_operatorController, Button.kY.value)
      .whileHeld(new Stir(IntakeConstants.kIntake100, m_stirrer));

    // Limelight
    new JoystickButton(m_driverController, Button.kBumperLeft.value)
      .whileHeld(new TurnToLimelight(m_drivetrain));
  }
}
