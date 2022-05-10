package org.recordrobotics.munchkin.commands.auto;

import org.recordrobotics.munchkin.subsystems.Rotator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoRotateTo extends CommandBase {
	
	private Rotator _rotator;
	private double _speed;
	private double _target;
	private int _direction;

	public AutoRotateTo(Rotator rotator, double target, double speed) {
		if (speed <= 0) {
			throw new IllegalArgumentException("Speed must be positive");
		}
		if (rotator == null) {
			throw new IllegalArgumentException("Rotator is null");
		}

		_rotator = rotator;
		_speed = speed;
		double dx = target - rotator.getPosition();
		_direction = (dx > 0) ? 1 : -1;
		_target = target;
		addRequirements(rotator);
	}

	@Override
	public void execute() {

	}

	@Override
	public boolean isFinished() {
		
	}

	@Override
	public void end(boolean interrupted) {

	}

}
