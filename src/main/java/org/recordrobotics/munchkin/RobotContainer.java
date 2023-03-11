package org.recordrobotics.munchkin;

import org.recordrobotics.munchkin.control.*;
import org.recordrobotics.munchkin.subsystems.*;

import java.util.ArrayList;
import java.util.List;

import org.recordrobotics.munchkin.commands.dashboard.DashRunProcedure;
import org.recordrobotics.munchkin.util.Pair;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Contains subsystems, control and command scheduling
 */
public class RobotContainer {

	private Vision _vision;

	// Control Scheme
	private IControlInput _controlInput;

	// Subsystems

	// This will be used in auto commands later
	@SuppressWarnings({"PMD.SingularField", "PMD.UnusedPrivateField", "unused"})

	// Commands
	private List<Pair<Subsystem, Command>> _teleopPairs;
	private Command _autoCommand;

	// Dashboard data
	private NetworkTableEntry _entryControl;

	public RobotContainer() {
		_controlInput = new LegacyControl(RobotMap.Control.LEGACY_GAMEPAD);

		_vision = new Vision();

		initEntries();
		initDashCommands();
		//initTeleopCommands();
		initAutoCommand();
	}



	private void initTeleopCommands() {
		//_teleopPairs = new ArrayList<>();

		//_entryControl.setValue(_controlInput.toString());
	}

	private void initAutoCommand() {
	}

	/**
	 * Create dashboard commands
	 */
	private void initDashCommands() {
		ShuffleboardTab tab = Shuffleboard.getTab(Constants.COMMANDS_TAB);
		tab.add("Legacy Control", new DashRunProcedure(this::controlLegacy));
		tab.add("Double Control", new DashRunProcedure(this::controlDouble));

	}

	/**
	 * Initialize dashboard entries
	 */
	private void initEntries() {
		ShuffleboardTab tab = Shuffleboard.getTab(Constants.DATA_TAB);
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
		for (Pair<Subsystem, Command> c : _teleopPairs) {
			c.getKey().setDefaultCommand(c.getValue());
		}
	}

	/**
	 * Create autonomous mode commands
	 */
	public void autoInit() {
	}

	/**
	 * Clear commands
	 */
	public void resetCommands() {
		CommandScheduler.getInstance().cancelAll();
	}

}
