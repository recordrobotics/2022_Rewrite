package org.recordrobotics.munchkin.commands.auto;

import org.recordrobotics.munchkin.subsystems.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoMoveDistance extends CommandBase {
	private Drive _drive;
	private double _speed;
	private double _targetDistance;
	private Direction _direction;

	public AutoMoveDistance(Drive drive, double speed, double targetDistance) {
		if (drive == null) {
			throw new IllegalArgumentException("Drive is null");
		}
		if (speed <= 0) {
			throw new IllegalArgumentException("Speed must be positive");
		}
		_drive = drive;

		_speed = speed;
		_targetDistance = targetDistance;
	}

	/**
	 * resets encoders and starts move
	 */
	@Override
	public void initialize() {
		_drive.resetEncoders();
		double dx = _targetDistance - _drive.getPosition();
		_direction = dx > 0 ? Direction.BACKWARD : Direction.FORWARD;
		_drive.move(0, _speed * _direction.value());
	}

	/**
	 * command ends when encoders reach or pass the target distance
	 */
	@Override
	public boolean isFinished() {
		return _drive.getPosition() * _direction.value() >= _targetDistance * _direction.value();
	}
	/**
	 * Stops the wheels once finished
	 */
	@Override
	public void end(boolean interrupted) {
		_drive.move(0, 0);
	}
}
