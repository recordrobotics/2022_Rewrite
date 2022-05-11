// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.recordrobotics.munchkin;

import org.recordrobotics.munchkin.control.*;
import org.recordrobotics.munchkin.subsystems.*;
import org.recordrobotics.munchkin.commands.manual.*;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * Contains subsystems, control and command scheduling
 */
public class RobotContainer {
	// Control Scheme
	private IControlInput _controlInput;

	// Subsystems
	private Acquisition _acquisition;
	private Climbers _climbers;
	private Drive _drive;
	private Flywheel _flywheel;
	private Rotator _rotator;
	//private Sensors _sensors;

	public RobotContainer() {
		Shuffleboard.getTab("Values");
		Shuffleboard.getTab("Cameras");
		Shuffleboard.getTab("Commands");
		//_sensors = new Sensors();
		_controlInput = new LegacyControl(RobotMap.Control.LEGACY_GAMEPAD);
		// _controlInput = new DoubleControl(RobotMap.Control.DOUBLE_GAMEPAD_1,
		// RobotMap.Control.DOUBLE_GAMEPAD_2);
		_acquisition = new Acquisition();
		_climbers = new Climbers();
		_flywheel = new Flywheel();
		_rotator = new Rotator();
		_drive = new Drive();
	}

	/**
	 * Create teleop commands
	 */
	public void teleopInit() {
		CommandScheduler.getInstance().schedule(true,
			new ManualAcquisition(_acquisition, _controlInput),
			new ManualClimbers(_climbers, _controlInput),
			new ManualFlywheel(_flywheel, _controlInput),
			new ManualRotator(_rotator, _controlInput),
			new ManualDrive(_drive, _controlInput));
	}

	/**
	 * Clear commands
	 */
	public void resetCommands() {
		CommandScheduler.getInstance().cancelAll();
	}

	public Command getAutonomousCommand() {
		return null;
	}
}
