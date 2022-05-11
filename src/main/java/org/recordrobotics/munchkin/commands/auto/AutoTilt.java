package org.recordrobotics.munchkin.commands.auto;

import org.recordrobotics.munchkin.subsystems.Acquisition;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoTilt extends CommandBase {
	private Acquisition _acquisition;
	private double _speed;

	public AutoTilt(Acquisition acquisition, double speed) {
		if (speed <= 0) {
			throw new IllegalArgumentException("Speed must be positive");
		}
		if (acquisition == null) {
			throw new IllegalArgumentException("Acquisition is null");
		}
		// setting _speed to -speed so the aquisition tilts down
		_speed = -speed;
		_acquisition = acquisition;
		_acquisition.tilt(0);
		addRequirements(acquisition);
	}

	/**
	 * tilt acquisition down
	 */
	@Override
	public void execute() {
		_acquisition.tilt(_speed);
	}

	/**
	 * command ends when limit switch is hit
	 */
	@Override
	public boolean isFinished() {
		return !_acquisition.getTiltState();
	}

	/**
	 * stop motor once finished
	 */
	@Override
	public void end(boolean interrupted) {
		_acquisition.tilt(0);
	}
}
