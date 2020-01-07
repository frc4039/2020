/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class RobotContainer {
  // The robot's subsystems and comzzmands are defined here...
  private final DriveTrain m_drivetrain = new DriveTrain();
  XboxController m_driverController = new XboxController(0);
  



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_drivetrain.setDefaultCommand(new ArcadeDrive(() -> m_driverController.getY(Hand.kLeft),
    () -> m_driverController.getX(Hand.kRight), m_drivetrain));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
  }

}
