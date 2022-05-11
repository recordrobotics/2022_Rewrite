package org.recordrobotics.munchkin.commands.auto;

import org.recordrobotics.munchkin.subsystems.Rotator;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Rotate to encoder value target
 */
public class AutoRotateTo extends CommandBase {

	private Rotator _rotator;
	private double _speed;
	private double _target;
	private Direction _direction;

	public AutoRotateTo(Rotator rotator, double target, double speed) {
		if (speed <= 0) {
			throw new IllegalArgumentException("Speed must be positive");
		}
		if (rotator == null) {
			throw new IllegalArgumentException("Rotator is null");
		}

		_rotator = rotator;
		_speed = speed;
		_target = target;
		addRequirements(rotator);
	}

	/**
	 * Rotate lift towards target
	 */
	@Override
	public void initialize() {
		// Calculate direction
		double dx = _target - _rotator.getPosition();
		_direction = dx > 0 ? Direction.FORWARD : Direction.BACKWARD;

		_rotator.rotate(_speed * _direction.value());
	}

	/**
	 * Finished when target is reached or passed
	 */
	@Override
	public boolean isFinished() {
		if (_direction == Direction.FORWARD) {
			return _rotator.getPosition() >= _target;
		} else {
			return _rotator.getPosition() <= _target;
		}
	}

	/**
	 * Reset motors when ending
	 */
	@Override
	public void end(boolean interrupted) {
		_rotator.rotate(0);
	}

}
