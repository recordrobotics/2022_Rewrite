// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.recordrobotics.munchkin.subsystems;

import com.revrobotics.CANSparkMax;

import org.recordrobotics.munchkin.RobotMap;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {
	private CANSparkMax[] _left = {
		new CANSparkMax(RobotMap.DriveBase.LEFT_FRONT_MOTOR_PORT, CANSparkMax.MotorType.kBrushless),
		new CANSparkMax(RobotMap.DriveBase.LEFT_BACK_MOTOR_PORT, CANSparkMax.MotorType.kBrushless)
	};
	private CANSparkMax[] _right = {
		new CANSparkMax(RobotMap.DriveBase.RIGHT_FRONT_MOTOR_PORT, CANSparkMax.MotorType.kBrushless),
		new CANSparkMax(RobotMap.DriveBase.RIGHT_BACK_MOTOR_PORT, CANSparkMax.MotorType.kBrushless)
	};

	private MotorControllerGroup _leftMotors = new MotorControllerGroup(_left);
	private MotorControllerGroup _rightMotors = new MotorControllerGroup(_right);

	private DifferentialDrive _differentialDrive = new DifferentialDrive(_leftMotors, _rightMotors);

	public Drive() {
		_leftMotors.set(0);
		_rightMotors.set(0);
	}

	/**
	 * drive the robot
	 * @param longSpeed rotation speed (positive is clockwise)
	 * @param latSpeed forward/backward speed (positive is forward)
	 */
	public void move(double longSpeed, double latSpeed) {
		// Arcade drive expects rotational inputs, while get translational
		// inputs. Therefore the values must be switched around
		// https://docs.wpilib.org/en/stable/docs/software/hardware-apis/motors/wpi-drive-classes.html
		_differentialDrive.arcadeDrive(Subsystems.limitSpeed(longSpeed),
			Subsystems.limitSpeed(-latSpeed));
	}

	/**
	 * @return The value of the right encoder in FEET
	 */
	private double getRightEncoder() {
		return (_right[0].getEncoder().getPosition() + _right[1].getEncoder().getPosition()) / 2;
	}

	/**
	 * @return The value of the left encoder in FEET
	 */
	private double getLeftEncoder() {
		return (_left[0].getEncoder().getPosition() + _left[1].getEncoder().getPosition()) / 2;
	}

	/**
	 * @return The average value of the two encoders, left and right, in FEET
	 */
	public double getPosition() {
		return (getRightEncoder() + getLeftEncoder()) / 2;
	}

	/**
	 * Reset all encoders to zero
	 */
	public void resetEncoders() {
		_right[0].getEncoder().setPosition(0.0);
		_right[1].getEncoder().setPosition(0.0);
		_left[0].getEncoder().setPosition(0.0);
		_left[1].getEncoder().setPosition(0.0);
	};

}
