/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

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
    public static final class GeneralConstants {
        public static final int kDriverController = 0;
        public static final int kOperatorController = 1;

        public static final double TicksPerRev = 4096.0;
    }

    public static final class ShooterConstants {
        public static final int kShooterMotor1Port = 20;
        public static final int kShooterMotor2Port = 21;
        public static final int kShooterFeederMotorPort = 25;

        // public static final double kShooterRPM5 = 4250;
        // public static final double kShooterRPM4 = 4250;
        // public static final double kShooterRPM3 = 3375;
        // public static final double kShooterRPM2 = 2250;
        // public static final double kShooterRPM1 = 1125;

        public static final double kTrenchShotRPM = 4500;
        public static final double kWallShotRPM = 3475;
        public static final double k10FtShotRPM = 4250;
        public static final double kShooterFeederSpeed = 0.5;

        public static final int kTargetZone = 1;
        public static final int kNearTrench = 2;
        public static final int kInitiationLine = 3;

        public static final double kF = 0.008;
        public static final double kP = 0.02;

        public static final int kPIDLoopIdx = 0; 
        public static final int kTimeoutMs = 30;

        public static final double kGearRatio = 64 / 20;

        public static final boolean kShooterInversion1 = true;
        public static final boolean kShooterInversion2 = false;
        public static final boolean kShooterFeederInversion = true;
    }

    public static final class IntakeConstants {
        public static final int kIntakeMotorPort = 22;
        public static final double kIntakePercent = 1.00;

        public static final boolean kIntakeInversion = false;
    }

    public static final class StirrerConstants {
        public static final int kStirrerMotor1Port = 23;
        public static final int kStirrerMotor2Port = 28;
		public static final double kStirrerPercent = 1.0;
        public static final int kStirrerCurrentLimit = 40;
        
        public static final boolean kStirrerInversion1 = false;
        public static final boolean kStirrerInversion2 = true;
    }

    public static final class VisionConstants {
        public static final double kP = 1.0;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kTolerance = 0;
        public static final double kRateTolerance = 0;
    }

    public static final class FeederConstants {
        public static final int kFeederMotorPort = 24;
        public static final double kFeederPercent = 0.5;
        public static final int kBreakBeamPort1 = 0;
        public static final int kBreakBeamPort2 = 7;

        public static final boolean kFeederInversion = true;
    }

    public static final class ClimberConstants {
        public static final double kSetpointExtended = 33.77;
        public static final double kSetpointClimbed = 60; // 66.89;
        
        public static final int kClimberMotorLeftPort = 30;
        public static final int kClimberMotorRightPort = 31;

		public static final int kTimeoutMs = 0;
        public static final int kPIDLoopIdx = 0;
        
		public static final double kP = 1;
		public static final double kF = 0;
		public static final double kI = 0;
        public static final double kD = 20;
        
		public static final boolean kExtended = true;
        public static final boolean kRetracted = false;

        public static final double kGearRatio = 36.0;
        public static final double kShaftDiameter = 0.744;

        public static final int kLeftLimitSwitchPort = 2;
        public static final int kRightLimitSwitchPort = 3;

        public static final double kNeutralDeadband = 0.04;

        public static final int kSlotDistance = 0;
        public static final int kSlotTurning = 1;

        public static final int PID_PRIMARY = 0;
        public static final int PID_TURN = 1;
        public static final double kDistanceF = 0;
        public static final double kDistanceP = 0.5;
        public static final double kDistanceI = 0;
        public static final double kDistanceD = 10;
        public static final int kDistanceIZone = 100;
        public static final int kTurnIZone = 200;
        public static final double kTurnF = 0;
        public static final double kTurnP = 0.025; //0.1
        public static final double kTurnI = 0;
        public static final double kTurnD = 0.05;

        public static final double kDistancePeakOutput = 0.8;
        public static final double kTurnPeakOutput = 1.0;
        public static final double kOffset = -1.0;
        
		public static final int kMotor1SoftLimit = -66;
		public static final double kMotor2SoftLimit = -68;
    }

    public static final class HoodConstants {
        public static final int kServoPort1 = 0;
        public static final int kServoPort2 = 1;
        public static double kFullRetract = 0.0;
        public static double kFullExtend = 1.0;
        public static double kPos1 = 0.25;
        public static double kPos2 = 0.50;
        public static double kPos3 = 0.75;
    }

    public static final class DriveConstants {
        public static final int kLeftDriveMotor1Port = 10;
        public static final int kLeftDriveMotor2Port = 11;
        public static final int kRightDriveMotor1Port = 12;
        public static final int kRightDriveMotor2Port = 13;

        public static final double kVoltageRampRate = 0.3; // s / maxVoltage

        public static final double kGearRatio = 0.12068966; //(14/58) * (16/32);
        public static final double kWheelDiameter = 0.157; //nominal 0.1587
        public static final double kEncoderConstant = kWheelDiameter * Math.PI * kGearRatio;
        
        public static final boolean kGyroReversed = true;
		public static final double ksVolts = 0.172;
		public static final double kvVoltSecondsPerMeter = 2.05;
        public static final double kaVoltSecondsSquaredPerMeter = 0.493;
        public static final double kTrackWidthMeters = 0.61; //3.366;
		public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackWidthMeters);
        public static final double kPDriveVel = 1.93;
        
        public static final boolean kLeftInversion = true;
        public static final boolean kRightInversion = false;
    }

    public static final class AutoConstants {
        public static final double kMaxSpeedMetersPerSecond = 0.5;
		public static final double kMaxAccelerationMetersPerSecondSquared = 0.25;
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

        public static final TrajectoryConfig config =
                new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecond,
                                    AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                    // Add kinematics to ensure max speed is actually obeyed
                    .setKinematics(DriveConstants.kDriveKinematics)
                    // Apply the voltage constraint
                    .addConstraint(autoVoltageConstraint);
    }
}
