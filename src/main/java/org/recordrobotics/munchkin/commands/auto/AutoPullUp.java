package org.recordrobotics.munchkin.commands.auto;

import org.recordrobotics.munchkin.subsystems.Climbers;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Pull up the climbers completely
 * 0 is assumed to be fully retracted
 */
public class AutoPullUp extends CommandBase {

	private Climbers _climbers;
	private double _speed;

	public AutoPullUp(Climbers climbers, double speed) {
		if (speed <= 0) {
			throw new IllegalArgumentException("Speed must be positive");
		}
		if (climbers == null) {
			throw new IllegalArgumentException("Climber is null");
		}

		_climbers = climbers;
		_speed = speed;
		addRequirements(climbers);
	}

	/**
	 * Pull climbers up
	 */
	@Override
	public void initialize() {
		_climbers.move(_speed);
	}

	/**
	 * Finished when encoder value reaches zero
	 */
	@Override
	public boolean isFinished() {
		return _climbers.getEncoderValue() >= 0;
	}

	/**
	 * Stop climbers
	 */
	@Override
	public void end(boolean interrupted) {
		_climbers.stop();
	}

}
