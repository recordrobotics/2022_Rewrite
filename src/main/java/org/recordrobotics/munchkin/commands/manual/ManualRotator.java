package org.recordrobotics.munchkin.commands.manual;

import org.recordrobotics.munchkin.control.IControlInput;
import org.recordrobotics.munchkin.subsystems.Rotator;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Rotator teleop command
 */
public class ManualRotator extends CommandBase {
	private Rotator _rotator;
	private IControlInput _controls;

	private static final double MIN_SPEED = 0.15;

	public ManualRotator(Rotator rotator, IControlInput control) {
		if (rotator == null) {
			throw new IllegalArgumentException("Rotator is null");
		}
		if (control == null) {
			throw new IllegalArgumentException("Control is null");
		}

		_rotator = rotator;
		_controls = control;
		addRequirements(_rotator);
	}

	@Override
	public void initialize() {
		_rotator.rotate(0);
	}

	@Override
	public void execute() {
		double speed = _controls.getRotate();
		if (Math.abs(speed) < MIN_SPEED) {
			speed = 0;
		}
		_rotator.rotate(speed);
	}

	@Override
	public void end(boolean isInterrupted) {
		_rotator.rotate(0);
	}
}
