package org.recordrobotics.munchkin.commands.dashboard;

import org.recordrobotics.munchkin.subsystems.Climbers;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DashResetClimbEncoder extends CommandBase {

	private Climbers _climbers;

	public DashResetClimbEncoder(Climbers climbers) {
		if (climbers == null) {
			throw new IllegalArgumentException("Climbers is null");
		}

		_climbers = climbers;
	}

	/**
	 * Reset climber encoder values when started
	 */
	@Override
	public void initialize() {
		_climbers.resetEncoderValue();
	}

	/**
	 * Command finished immediately
	 */
	@Override
	public boolean isFinished() {
		return true;
	}

}
