package org.recordrobotics.munchkin;

import org.recordrobotics.munchkin.control.*;
import org.recordrobotics.munchkin.subsystems.*;

import java.util.ArrayList;
import java.util.List;

import org.recordrobotics.munchkin.commands.dashboard.DashRunProcedure;
import org.recordrobotics.munchkin.commands.dashboard.DashResetClimbEncoder;
import org.recordrobotics.munchkin.commands.group.SeqLiftHigh;
import org.recordrobotics.munchkin.commands.group.SeqLiftMid;
import org.recordrobotics.munchkin.commands.group.SeqLiftNext;
import org.recordrobotics.munchkin.commands.manual.*;
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
	private List<Pair<Subsystem, Command>> _teleopPairs;
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
		_teleopPairs = new ArrayList<>();
		_teleopPairs.add(new Pair<Subsystem, Command>(_acquisition, new ManualAcquisition(_acquisition, _controlInput)));
		_teleopPairs.add(new Pair<Subsystem, Command>(_climbers, new ManualClimbers(_climbers, _controlInput)));
		_teleopPairs.add(new Pair<Subsystem, Command>(_flywheel, new ManualFlywheel(_flywheel, _controlInput)));
		_teleopPairs.add(new Pair<Subsystem, Command>(_rotator, new ManualRotator(_rotator, _controlInput)));
		_teleopPairs.add(new Pair<Subsystem, Command>(_drive, new ManualDrive(_drive, _controlInput)));
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
		tab.add("Drive Acceleration Ramping", new DashRunProcedure(_drive::toggleAccRamping));
		tab.add("Legacy Control", new DashRunProcedure(this::controlLegacy));
		tab.add("Double Control", new DashRunProcedure(this::controlDouble));
		tab.add("Middle Bar", new SeqLiftMid(_rotator, _climbers));
		tab.add("High Bar", new SeqLiftHigh(_rotator, _climbers));
		tab.add("Traversal Bar", new SeqLiftHigh(_rotator, _climbers));
		tab.add("Next Bar", new SeqLiftNext(_rotator, _climbers));
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
		for (Pair<Subsystem, Command> c : _teleopPairs) {
			c.getKey().setDefaultCommand(c.getValue());
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
