package org.recordrobotics.munchkin.commands.auto;

import org.recordrobotics.munchkin.subsystems.Rotator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoResetRotator extends CommandBase {

	private Rotator _rotator;
	private double _speed;

	public AutoResetRotator(Rotator rotator, double speed) {
		if (speed <= 0) {
			throw new IllegalArgumentException("Speed must be positive");
		}
		if (rotator == null) {
			throw new IllegalArgumentException("Rotator is null");
		}

		_rotator = rotator;
		_speed = speed;
		addRequirements(rotator);
	}

	/**
	 * Move lift towards reset position
	 */
	@Override
	public void execute() {
		_rotator.rotate(_speed);
	}

	/**
	 * Command finished when limit switch hit
	 */
	@Override
	public boolean isFinished() {
		return _rotator.isFwdLimitPressed();
	}

	/**
	 * Reset encoder and stop motor at the end
	 */
	@Override
	public void end(boolean interrupted) {
		_rotator.rotate(0);
		_rotator.resetEncoders();
	}

}
