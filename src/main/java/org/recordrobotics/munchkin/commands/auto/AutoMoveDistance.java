package org.recordrobotics.munchkin.commands.auto;

import org.recordrobotics.munchkin.subsystems.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoMoveDistance extends CommandBase {
	private Drive _drive;
	private double _speed;
	private double _targetDistance;

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
		int direction = _targetDistance > 0 ? -1 : 1;
		_drive.move(0, _speed * direction);
	}

	/**
	 * command ends when encoders reach or pass the target distance
	 */
	@Override
	public boolean isFinished() {
		return Math.abs(_drive.getPosition()) >= Math.abs(_targetDistance);
	}
	/**
	 * Stops the wheels once finished
	 */
	@Override
	public void end(boolean interrupted) {
		_drive.move(0, 0);
	}
}
