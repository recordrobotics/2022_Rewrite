package org.recordrobotics.munchkin.commands.manual;

import org.recordrobotics.munchkin.subsystems.Flywheel;
import org.recordrobotics.munchkin.Constants;
import org.recordrobotics.munchkin.control.IControlInput;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Flywheel teleop command
 */
public class ManualFlywheel extends CommandBase {
	private Flywheel _flywheel;
	private IControlInput _controls;
	private static final double HIGH_SPEED = 0.35;
	private static final double LOW_SPEED = 0.22;
	private static final double IDLE_SPEED = 0;
	private static final String HIGH = "HIGH";
	private static final String LOW = "LOW";
	private static final String IDLE = "IDLE";
	// used to reset servos
	private boolean _servosUp;

	private NetworkTableEntry _entrySpeed;

	public ManualFlywheel(Flywheel flywheel, IControlInput controlInput) {
		if (flywheel == null) {
			throw new IllegalArgumentException("Flywheel is null");
		}
		if (controlInput == null) {
			throw new IllegalArgumentException("Control is null");
		}

		_flywheel = flywheel;
		_controls = controlInput;
		addRequirements(_flywheel);

		ShuffleboardTab tab = Shuffleboard.getTab(Constants.DATA_TAB);
		_entrySpeed = tab.add("Flywheel Speed", "Idle").getEntry();
	}

	@Override
	public void execute() {
		switch (_controls.getFlywheel()) {
			case OFF:
				_flywheel.spin(IDLE_SPEED);
				_flywheel.resetServos();
				_entrySpeed.setString(IDLE);
				return;
			case LOW:
				_flywheel.spin(LOW_SPEED);
				_entrySpeed.setString(LOW);
				break;
			case HIGH:
				_flywheel.spin(HIGH_SPEED);
				_entrySpeed.setString(HIGH);
				break;
		}

		if (_controls.getServos()) {
			_flywheel.shootServos();
			_servosUp = true;
		} else if (_servosUp) {
			_flywheel.resetServos();
			_servosUp = false;
		}
	}

	@Override
	public void end(boolean interrupted) {
		_flywheel.spin(0);
		_flywheel.resetServos();
	}
}
