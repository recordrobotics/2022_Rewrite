// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.recordrobotics.munchkin.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.recordrobotics.munchkin.RobotMap;
import org.recordrobotics.munchkin.Constants;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {

	private static final double RAMPING_JUMP_THRESHOLD = 0.1;
	private static final double CTRL_NEUTRAL_POSITION = 0.0; // If the input is less than this constant, it is considered in a neutral position.
	private static final double MAX_CTRL_SCALE = 1.0;

	public boolean _isRamping = true;

	private MotorControllerGroup _leftMotors = new MotorControllerGroup(
		new CANSparkMax(RobotMap.DriveBase.LEFT_FRONT_MOTOR_PORT, MotorType.kBrushless),
		new CANSparkMax(RobotMap.DriveBase.LEFT_BACK_MOTOR_PORT, MotorType.kBrushless)
	);

	private MotorControllerGroup _rightMotors = new MotorControllerGroup(
		new CANSparkMax(RobotMap.DriveBase.RIGHT_FRONT_MOTOR_PORT, MotorType.kBrushless),
		new CANSparkMax(RobotMap.DriveBase.RIGHT_BACK_MOTOR_PORT, MotorType.kBrushless)
	);

	private DifferentialDrive _differentialDrive = new DifferentialDrive(_leftMotors, _rightMotors);

	private NetworkTableEntry _entryRamping;

	public Drive() {
		_leftMotors.set(0);
		_rightMotors.set(0);

		ShuffleboardTab tab = Shuffleboard.getTab(Constants.DATA_TAB);
		_entryRamping = tab.add("Drive Ramping", true).getEntry();

	}

	/**
	 * Limit the double between min and max. Just a safety precaution.
	 * @param value current value of input
	 * @param min minimum value to constrain the input into
	 * @param max maximum value to constrain the input into
	 * @return the limited value of the input
	 */
	private double limitValue(double value, double min, double max) {
		return Math.min(Math.max(value, min), max);
	}

	/**
	 * Calculates the next input value for ramping.
	 * @param _lowerTimeScale slower ramping time scale
	 * @param _higherTimeScale faster ramping time scale
	 * @param _isMaxScalingFaster if you push the joystick to its max, this determines whether it ramps faster
	 * @param currControlScale current ramped control scale
	 * @param input current input, or target value
	 * @return the next control scale
	 */
	public double calcNextCtrlScale(double _lowerTimeScale, double _higherTimeScale, boolean _isMaxScalingFaster, double currControlScale, double input) {
		double timeScale = _lowerTimeScale;
		boolean isControlNeutral = Math.abs(input) <= CTRL_NEUTRAL_POSITION;
		boolean isControlMax = Math.abs(input) == MAX_CTRL_SCALE;
		if (isControlNeutral || isControlMax && _isMaxScalingFaster || !_isMaxScalingFaster && isControlNeutral) {
			timeScale = _higherTimeScale;
		}
		double nextCtrlScale = currControlScale + (input - currControlScale)/timeScale;
		if (Math.abs(input-currControlScale) <= RAMPING_JUMP_THRESHOLD) {
			nextCtrlScale = input;
		}
		nextCtrlScale = limitValue(nextCtrlScale, -MAX_CTRL_SCALE, MAX_CTRL_SCALE);
		return nextCtrlScale;
	}

	public void toggleAccRamping() {
		_isRamping = !_isRamping;
	}

	@Override
	public void periodic() {
		_entryRamping.setBoolean(_isRamping);
	}

	/**
	 * Drive the robot
	 */
	public void move(double longSpeed, double latSpeed) {
		// Arcade drive expects rotational inputs, while get translational
		// inputs. Therefore the values must be switched around
		// https://docs.wpilib.org/en/stable/docs/software/hardware-apis/motors/wpi-drive-classes.html
		_differentialDrive.arcadeDrive(Subsystems.limitSpeed(latSpeed),
			Subsystems.limitSpeed(-longSpeed));
	}

}
