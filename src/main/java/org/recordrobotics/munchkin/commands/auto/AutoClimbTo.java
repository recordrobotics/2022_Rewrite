package org.recordrobotics.munchkin.commands.auto;

import org.recordrobotics.munchkin.subsystems.Climbers;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoClimbTo extends CommandBase {

	private Climbers _climbers;
	private double _speed;
	private double _target;
	private Direction _direction;

	public AutoClimbTo(Climbers climbers, double target, double speed) {
		if (speed <= 0) {
			throw new IllegalArgumentException("Speed must be positive");
		}
		if (climbers == null) {
			throw new IllegalArgumentException("Climbers is null");
		}

		_climbers = climbers;
		_target = target;
		_speed = speed;
		addRequirements(climbers);

		// Calculate direction
		double dx = target - climbers.getEncoderValue();
		_direction = dx > 0 ? Direction.DOWN : Direction.UP;
	}

	@Override
	public void execute() {
		_climbers.move(_speed * _direction.value());
	}

	@Override
	public boolean isFinished() {
		if (_direction == Direction.DOWN) {
			return _climbers.getEncoderValue() >= _target;
		} else {
			return _climbers.getEncoderValue() <= _target;
		}
	}

	@Override
	public void end(boolean interrupted) {
		_climbers.move(0);
	}

}
