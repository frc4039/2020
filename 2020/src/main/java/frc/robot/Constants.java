/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */

public final class Constants {

    private static DriverStation DS = DriverStation.getInstance();

    public static final class GeneralConstants {
        public static final int kDriverController = 0;
        public static final int kOperatorController = 1;

        public static final boolean realMatch = DS.isFMSAttached();
    }

    public static final class ShooterConstants {
        public static final int kShooterMotor1Port = 20;
        public static final int kShooterMotor2Port = 21;
        public static final int kShooterFeederMotorPort = 25;

        public static final double kNearTrenchShotRPM = 5250;
        public static final double kFarTrenchShotRPM = 5500;
        public static final double kWallShotRPM = 3475;
        public static final double k10ftBackBumperShotRPM = 4350;
        public static final double k10ftMidBumperShotRPM = 4425;
        public static final double k10ftFrontBumperShotRPM = 4600;

        public static final double kShooterFeederSpeed = 0.8;

        public static final int kTargetZone = 1;
        public static final int kNearTrench = 2;
        public static final int kBackBumpers = 3;
        public static final int kFrontBumpers = 4;
        public static final int kMidBumpers = 5;
        public static final int kFarTrench = 6;

        public static final double kF = 0.008;
        public static final double kP = 0.02;

        public static final int kPIDLoopIdx = 0; 
        public static final int kTimeoutMs = 30;

        public static final double kGearRatio = 64 / 20;

        public static final int kShooterFeederMotorCurrentLimit = 30;

        public static final boolean kShooterInversion1 = true;
        public static final boolean kShooterInversion2 = false;

        public static final boolean kShooterFeederInversion = false;
		public static double kShooterThreshold = 500;
    }

    public static final class IntakeConstants {
        public static final int kIntakeMotorPort = 22;

        public static final double kIntakePercent = 1.00;

        public static final boolean kIntakeInversion = false;
    }

    public static final class StirrerConstants {
        public static final int kStirrerMotor1Port = 23;
        public static final int kStirrerMotor2Port = 28;

        public static final double kStirrerPercent1 = 0.35;
        public static final double kStirrerPercent2 = 0.175; //half the other
        
		public static final int kStirrerCurrentLimit = 30;
       
        public static final boolean kStirrerInversion1 = false;
        public static final boolean kStirrerInversion2 = true;

        public static final double kAlternatingTime = 4.00;
    }

    public static final class VisionConstants {
        public static final double kP = 0.6;
        public static final double kI = 1.0;
        public static final double kD = 0.01;
        public static final double kFF = 0.2;
        public static final double kTolerance = 0.03;
        public static final double kRateTolerance = 0.0;
        public static final double kMaxI = 0.10;
        public static final double kLimelightOffset = 0.1;
    }

    public static final class TurningConstants {
        public static final double kP = 0.0015;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kFF = 0.35;
        public static final double kTolerance = 1;
        public static final double kRateTolerance = 10;
    }

    public static final class FeederConstants {
        public static final int kFeederMotorPort = 24;
        
        public static final int kBreakBeamPort1 = 0;
        public static final int kBreakBeamPort2 = 7;

        public static final double kFeederPercent = 0.5;
        public static final double kFeederSlow = 0.15;

        public static final int kCurrentLimit = 30;
        
        public static final boolean kFeederInversion = true;

        public static final double kFeederTicksPerRev = 42;
        public static final double kFeederGearRatio = 20;
        public static final double kFeederRotation = 0.375;

        public static final double kAdjustBallTwoPos = 12;
		//public static final double kAdjustBallTwoPos = kFeederTicksPerRev * kFeederGearRatio * kFeederRotation;
    }

    public static final class ClimberConstants {
        
        public static final int kClimberMotorLeftPort = 30;
        public static final int kClimberMotorRightPort = 31;

        public static final int kLeftLimitSwitchPort = 2;
        public static final int kRightLimitSwitchPort = 3;

        public static final double kSetFullyExtended = 33.77 + 1.675 - 0.25;
        public static final double kSetFullyClimbed = 60; // 66.89;
        public static final double kSetBuddyClimb = 20; //Must be less than 26 (60-33.77)

        public static final int kServoPort1 = 2;
        public static final int kServoPort2 = 3;

		public static final int kTimeoutMs = 0;
        public static final int kPIDLoopIdx = 0;
        
        public static final int kSlotDistance = 0;
        public static final int kSlotTurning = 1;
        
		public static final boolean kExtended = true;
        public static final boolean kRetracted = false;

        public static final double kGearRatio = 36.0;
        public static final double kShaftDiameter = 0.885;

        public static final double kNeutralDeadband = 0.0;

        public static final int PID_PRIMARY = 0;
        public static final int PID_TURN = 1;

        public static final double kDistanceF = 0;
        public static final double kDistanceP = 0.5;
        public static final double kDistanceI = 0;
        public static final double kDistanceD = 10;
        public static final int kDistanceIZone = 100;
        
        public static final double kTurnF = 0;
        public static final double kTurnP = 0.025;
        public static final double kTurnI = 0;
        public static final double kTurnD = 0.05;
        public static final int kTurnIZone = 200;

        public static final double kDistancePeakOutput = 0.8;
        public static final double kTurnPeakOutput = 1.0;

        public static final double kOffsetDown = 1.25; //positive brings left down
        public static final double kOffsetUp = 0;
        
		public static final int kMotor1SoftLimitReverse = -66;
		public static final int kMotor2SoftLimitReverse = -68;
		public static final int kMotor1SoftLimitForward = 0;
		public static final int kMotor2SoftLimitForward = 0;
    }

    public static final class HoodConstants {
        public static final int kServoPort1 = 0;
        public static final int kServoPort2 = 1;

        public static final double kWallPos = 1.00;
        public static final double k10ftBackPos = 0.00;
        public static final double k10ftMidPos = 0.00;
        public static final double k10ftFrontPos = 0.00;
		public static final double kFarTrenchPos = 0.00;
		public static final double kNearTrenchPos = 0.00;
    }

    public static final class DriveConstants {
        public static final int kLeftDriveMotor1Port = 10;
        public static final int kLeftDriveMotor2Port = 11;
        public static final int kRightDriveMotor1Port = 12;
        public static final int kRightDriveMotor2Port = 13;

        public static final double kGearRatio = 0.12068966; //(14/58) * (16/32);
        public static final double kWheelDiameter = 0.157; //nominal 0.1587
        public static final double kEncoderConstant = kWheelDiameter * Math.PI * kGearRatio;
        
        public static final boolean kGyroReversed = true;
        
		public static final double ksVolts = 0.172;
		public static final double kvVoltSecondsPerMeter = 2.05;
        public static final double kaVoltSecondsSquaredPerMeter = 0.493;
        public static final double kTrackWidthMeters = 1.1; //3.366;
		public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackWidthMeters);
        public static final double kPDriveVel = 1.93;
        
        public static final double kOpenLoopRampRate = 0.3; //1114 had 0.2 in 2019
        public static final int kCurrentLimitStall = 60; //1114 had 60 in 2019
        public static final int kCurrentLimitFree = 10; //1114 had 10 in 2019
        
        public static final boolean kLeftInversion = true;
        public static final boolean kRightInversion = false;
    }

    public static final class AutoConstants {
        public static final double kMaxSpeedMetersPerSecondSlow = 2;
        public static final double kMaxAccelerationMetersPerSecondSquaredSlow = 0.5;
        public static final double kMaxSpeedMetersPerSecondMedium = 3.5;
        public static final double kMaxAccelerationMetersPerSecondSquaredMedium = 1.5;
        public static final double kMaxSpeedMetersPerSecondFast = 4.0;
        public static final double kMaxAccelerationMetersPerSecondSquaredFast = 1.8;
		public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
        public static final DifferentialDriveVoltageConstraint autoVoltageConstraint =
            new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(DriveConstants.ksVolts,
                                        DriveConstants.kvVoltSecondsPerMeter,
                                        DriveConstants.kaVoltSecondsSquaredPerMeter),
                DriveConstants.kDriveKinematics,
                10
            );

        public static final TrajectoryConfig slowConfig =
                new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecondSlow,
                                    AutoConstants.kMaxAccelerationMetersPerSecondSquaredSlow)
                    // Add kinematics to ensure max speed is actually obeyed
                    .setKinematics(DriveConstants.kDriveKinematics)
                    // Apply the voltage constraint
                    .addConstraint(autoVoltageConstraint);

        public static final TrajectoryConfig fastConfig =
                new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecondFast,
                                    AutoConstants.kMaxAccelerationMetersPerSecondSquaredFast)
                    // Add kinematics to ensure max speed is actually obeyed
                    .setKinematics(DriveConstants.kDriveKinematics)
                    // Apply the voltage constraint
                    .addConstraint(autoVoltageConstraint);
    
        public static final TrajectoryConfig MediumConfig =
                new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecondMedium,
                                    AutoConstants.kMaxAccelerationMetersPerSecondSquaredMedium)
                    // Add kinematics to ensure max speed is actually obeyed
                    .setKinematics(DriveConstants.kDriveKinematics)
                    // Apply the voltage constraint
                    .addConstraint(autoVoltageConstraint);
    }
}
