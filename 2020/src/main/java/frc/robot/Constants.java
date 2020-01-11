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
    }

    public static final class ShooterConstants {
        public static final int kShooterMotor1Port = 20;
        public static final int kShooterMotor2Port = 21;

        public static final double kShooter25 = 0.25;
        public static final double kShooter50 = 0.50;
        public static final double kShooter75 = 0.75;
        public static final double kShooter100 = 1.00;

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
