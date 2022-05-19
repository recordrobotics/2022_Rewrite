// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.recordrobotics.munchkin;

import org.recordrobotics.munchkin.control.*;
import org.recordrobotics.munchkin.subsystems.*;

import java.util.Arrays;
import java.util.List;

import org.recordrobotics.munchkin.commands.dashboard.DashRunProcedure;
import org.recordrobotics.munchkin.commands.dashboard.DashResetClimbEncoder;
import org.recordrobotics.munchkin.commands.group.SeqLiftMid;
import org.recordrobotics.munchkin.commands.manual.*;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
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
	// This will be used in auto commands later
	@SuppressWarnings({"PMD.SingularField", "PMD.UnusedPrivateField", "unused"})
	private Sensors _sensors;

	// Commands
	private List<Command> _teleopCommands;
	private Command _autoCommand;

	// Dashboard data
	private NetworkTableEntry _entryControl;

	public RobotContainer() {
		_controlInput = new LegacyControl(RobotMap.Control.LEGACY_GAMEPAD);
		_acquisition = new Acquisition();
		_climbers = new Climbers();
		_flywheel = new Flywheel();
		_rotator = new Rotator();
		_drive = new Drive();
		_sensors = new Sensors();

		initEntries();
		initDashCommands();
		initTeleopCommands();
		initAutoCommand();
	}

	private void initTeleopCommands() {
		_teleopCommands = Arrays.asList(
			new ManualAcquisition(_acquisition, _controlInput),
			new ManualClimbers(_climbers, _controlInput),
			new ManualFlywheel(_flywheel, _controlInput),
			new ManualRotator(_rotator, _controlInput),
			new ManualDrive(_drive, _controlInput));
		_entryControl.setValue(_controlInput.toString());
	}

	private void initAutoCommand() {
		_autoCommand = new SeqLiftMid(_rotator, _climbers);
	}

	/**
	 * Create dashboard commands
	 */
	private void initDashCommands() {
		ShuffleboardTab tab = Shuffleboard.getTab(Constants.COMMANDS_TAB);
		tab.add("Reset Climbers Encoder", new DashResetClimbEncoder(_climbers));
		tab.add("Legacy Control", new DashRunProcedure(this::controlLegacy));
		tab.add("Double Control", new DashRunProcedure(this::controlDouble));
	}

	/**
	 * Initialize dashboard entries
	 */
	private void initEntries() {
		ShuffleboardTab tab = Shuffleboard.getTab(Constants.DATA_TAB);
		_entryControl = tab.add("Control Type", "").getEntry();
	}

	/**
	 * Set control scheme to legacy
	 */
	private void controlLegacy() {
		resetCommands();
		_controlInput = new LegacyControl(RobotMap.Control.LEGACY_GAMEPAD);
		initTeleopCommands();
		teleopInit();
	}

	/**
	 * Set control scheme to double
	 */
	private void controlDouble() {
		resetCommands();
		_controlInput = new DoubleControl(RobotMap.Control.DOUBLE_GAMEPAD_1,
			RobotMap.Control.DOUBLE_GAMEPAD_2);
		initTeleopCommands();
		teleopInit();
	}


	/**
	 * Create teleop commands
	 */
	public void teleopInit() {
		for (Command c : _teleopCommands) {
			CommandScheduler.getInstance().schedule(true, c);
		}
	}

	/**
	 * Create autonomous mode commands
	 */
	public void autoInit() {
		CommandScheduler.getInstance().schedule(true, _autoCommand);
	}

	/**
	 * Clear commands
	 */
	public void resetCommands() {
		CommandScheduler.getInstance().cancelAll();
	}

}
