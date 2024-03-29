/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.HoodConstants;

public class Hood extends SubsystemBase {

  private Servo m_servo1;
  private Servo m_servo2;

  public Hood() {
    m_servo1 = new Servo(HoodConstants.kServoPort1);
    m_servo1.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    
    m_servo2 = new Servo(HoodConstants.kServoPort2);
    m_servo2.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
  }

  @Override
  public void periodic() {
    
  }

  public void setPosition(double pos) {
    m_servo1.setPosition(pos);
    m_servo2.setPosition(pos);
  }

   public void stop(){
     m_servo1.stopMotor();
     m_servo2.stopMotor();
  }
}
