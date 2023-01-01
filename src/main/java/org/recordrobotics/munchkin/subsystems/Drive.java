// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.recordrobotics.munchkin.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.recordrobotics.munchkin.Constants;
import org.recordrobotics.munchkin.RobotMap;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {

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
	private boolean _ramping;

	private static final double RAMPING_JUMP_THRESHOLD = 0.1;
	private static final double CTRL_NEUTRAL_POSITION = 0.0; // If the input is less than this constant, it is considered in a neutral position.
	private static final double MAX_CTRL_SCALE = 1.0;

	public Drive() {
		_leftMotors.set(0);
		_rightMotors.set(0);

		ShuffleboardTab tab = Shuffleboard.getTab(Constants.DATA_TAB);
		_entryRamping = tab.add("Drive Ramping", true).getEntry();
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

	/**
	 * ramps acceleration. ramps the input rather than the output
	 * @param higherTimeScale used when ramping needs to be faster (not a good description)
	 * @param lowerTimeScale used when ramping needs to be slower (not a good description)
	 * @param isMaxScalingFaster whether code uses higherTimeScale (what is this? not a good description)
	 * @param currControlScale current control scale value (not a good description)
	 * @param input the raw value outputted by the control files
	 * @return next control scale (what is this? not a good description)
	 */
	public double calcNextCtrlScale(double higherTimeScale, double lowerTimeScale, boolean isMaxScalingFaster, double currControlScale, double input) {
		double timeScale = lowerTimeScale;
		boolean isControlNeutral = Math.abs(input) <= CTRL_NEUTRAL_POSITION;
		boolean isControlMax = Math.abs(input) == MAX_CTRL_SCALE;
		if (isMaxScalingFaster && (isControlNeutral || isControlMax) || !isMaxScalingFaster && isControlNeutral) {
			timeScale = higherTimeScale;
		}
		double nextCtrlScale = currControlScale + (input - currControlScale)/timeScale;
		if (Math.abs(input-currControlScale) <= RAMPING_JUMP_THRESHOLD) {
			nextCtrlScale = input;
		}
		nextCtrlScale = Math.min(Math.max(nextCtrlScale, -MAX_CTRL_SCALE), MAX_CTRL_SCALE);
		return nextCtrlScale;
	}

	/**
	 * toggles drive ramping
	 */
	public void toggleRamping() {
		_ramping = !_ramping;
	}

	/**
	 * gets the value of _ramping
	 * @return is drive ramping
	 */
	public boolean isRamping() {
		return _ramping;
	}

	/**
	 * Updates dashboard data
	 */
	@Override
	public void periodic() {
		_entryRamping.setBoolean(_ramping);
	}
}
