/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.I2C;

import com.revrobotics.ColorSensorV3;

public class ColorSensor extends SubsystemBase {
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private static ColorSensorV3 m_colorSensor;

  public ColorSensor() {
    m_colorSensor = new ColorSensorV3(i2cPort);
  }

  @Override
  public void periodic() {
    Color detectedColor = m_colorSensor.getColor();
  
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);

    if (isYellow()) {
        SmartDashboard.putString("Color", "Yellow");
    } else if (isRed()) {
        SmartDashboard.putString("Color", "Red");
    } else if (isGreen()) {
        SmartDashboard.putString("Color", "Green");
    } else if (isBlue()) {
        SmartDashboard.putString("Color", "Blue");
    } else {
        SmartDashboard.putString("Color", "None");
    }
  }

  public boolean isYellow() {
    Color detectedColor = m_colorSensor.getColor();
    if (detectedColor.red < 0.285 && detectedColor.red > 0.245
    && detectedColor.green < 0.520 && detectedColor.green > 0.480
    && detectedColor.blue < 0.250 && detectedColor.blue > 0.210) {
        return true;
    }
    return false;
}

  public boolean isRed() {
    Color detectedColor = m_colorSensor.getColor();
    if (detectedColor.red < 0.555 && detectedColor.red > 0.515
    && detectedColor.green < 0.365 && detectedColor.green > 0.325
    && detectedColor.blue < 0.140 && detectedColor.blue > 0.100) {
        return true;
    }
    return false;
  }

  public boolean isGreen() {
    Color detectedColor = m_colorSensor.getColor();
    if (detectedColor.red < 0.190 && detectedColor.red > 0.150
    && detectedColor.green < 0.610 && detectedColor.green > 0.570
    && detectedColor.blue < 0.260 && detectedColor.blue > 0.220) {
        return true;
    }
    return false;
}

public boolean isBlue() {
    Color detectedColor = m_colorSensor.getColor();
    if (detectedColor.red < 0.145 && detectedColor.red > 0.105
    && detectedColor.green < 0.465 && detectedColor.green > 0.425
    && detectedColor.blue < 0.445 && detectedColor.blue > 0.405) {
        return true;
    }
    return false;
}

  public Color getColorSensor() {
    return m_colorSensor.getColor();
  }
}