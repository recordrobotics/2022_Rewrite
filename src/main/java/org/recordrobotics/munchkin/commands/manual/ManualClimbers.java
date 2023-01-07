package org.recordrobotics.munchkin.commands.manual;

import org.recordrobotics.munchkin.subsystems.Climbers;
import org.recordrobotics.munchkin.control.IControlInput;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Climber teleop command
 */
public class ManualClimbers extends CommandBase {
	private Climbers _climbers;
	private IControlInput _controls;

	private static final double MIN_SPEED = 0.10;

	public ManualClimbers(Climbers climber, IControlInput controlInput) {
		if (climber == null) {
			throw new IllegalArgumentException("Climber is null");
		}
		if (controlInput == null) {
			throw new IllegalArgumentException("Control is null");
		}

		_climbers = climber;
		_controls = controlInput;
		addRequirements(_climbers);
	}

	@Override
	public void execute() {
		double speed = _controls.getClimb();
		if (Math.abs(speed) < MIN_SPEED) {
			speed = 0;
		}
		_climbers.move(speed);
	}

	@Override
	public void end(boolean interrupted) {
		_climbers.move(0);
	}
}
