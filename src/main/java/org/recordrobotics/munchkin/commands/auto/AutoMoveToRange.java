package org.recordrobotics.munchkin.commands.auto;

import org.recordrobotics.munchkin.subsystems.Drive;
import org.recordrobotics.munchkin.subsystems.Sensors;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoMoveToRange extends CommandBase {
	private Drive _drive;
	private Sensors _sensors;
	private double _speed;
	private double _targetRange;
	private Direction _direction;

	/**
	 *
	 * @param drive drive subsystem
	 * @param sensors sensors subsystem
	 * @param speed speed of movement (positive)
	 * @param targetRange target distance to go to (positive, millimeters)
	 */
	public AutoMoveToRange(Drive drive, Sensors sensors, double speed, double targetRange) {
		if (drive == null) {
			throw new IllegalArgumentException("Drive is null");
		}
		if (sensors == null) {
			throw new IllegalArgumentException("Sensors is null");
		}
		if (speed <= 0) {
			throw new IllegalArgumentException("Speed must be positive");
		}
		if (targetRange <= 0) {
			throw new IllegalArgumentException("Target must be positive");
		}
		_drive = drive;
		_speed = speed;
		_targetRange = targetRange;
		_sensors = sensors;
	}

	/**
	 * starts moving at constructor speed
	 */
	@Override
	public void initialize() {
		_direction = _sensors.getDistance() > _targetRange ? Direction.FORWARD : Direction.BACKWARD;
		_drive.move(0, _speed * _direction.value());
	}

	/**
	 * command ends when encoders reach or pass the target range
	 */
	@Override
	public boolean isFinished() {
		if (_direction == Direction.FORWARD) {
			return _sensors.getDistance() <= _targetRange;
		} else {
			return _sensors.getDistance() >= _targetRange;
		}
	}
	/**
	 * Stops the wheels once finished
	 */
	@Override
	public void end(boolean interrupted) {
		_drive.move(0, 0);
	}
}
