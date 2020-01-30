/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class GeneralConstants {
        public static final int kDriverController = 0;
        public static final int kOperatorController = 1;

        public static final double TicksPerRev = 4096.0;
    }

    public static final class ShooterConstants {
        public static final int kShooterMotor1Port = 20;
        public static final int kShooterMotor2Port = 21;

        public static final double kShooterRPM5 = 4500;
        public static final double kShooterRPM4 = 4250;
        public static final double kShooterRPM3 = 3375;
        public static final double kShooterRPM2 = 2250;
        public static final double kShooterRPM1 = 1125;

        public static final double kF = 0.013;
        public static final double kP = 0.12;

        public static final int kPIDLoopIdx = 0; 
        public static final int kTimeoutMs = 30;
    }

    public static final class IntakeConstants {
        public static final int kIntakeMotorPort = 22;
        public static final double kIntake25 = 0.25;
        public static final double kIntake50 = 0.50;
        public static final double kIntake75 = 0.75;
        public static final double kIntake100 = 1.00;
    }

    public static final class StirrerConstants {
        public static final int kStirrerMotorPort = 23;
		public static final double kStirrerPercent = 1.00;
		public static final int kStirrerCurrentLimit = 40;
    }

    public static final class VisionConstants {
        public static final double kP = 1.0;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kTolerance = 0;
        public static final double kRateTolerance = 0;
    }

    public static final class DriveConstants {
        public static final int kLeftDriveMotor1Port = 10;
        public static final int kLeftDriveMotor2Port = 11;
        public static final int kRightDriveMotor1Port = 12;
        public static final int kRightDriveMotor2Port = 13;
        public static final double kDistancePerTick = 5;
        public static final boolean kGyroReversed = false;
    }
}
