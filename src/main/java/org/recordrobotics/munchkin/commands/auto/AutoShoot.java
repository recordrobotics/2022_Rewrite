package org.recordrobotics.munchkin.commands.auto;

import org.recordrobotics.munchkin.subsystems.Flywheel;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoShoot extends CommandBase {

	// Delay to allow the flywheel to spin up
	private static final double SHOOT_DELAY = 0.8;
	// Time needed to execute the command
	private static final double END_TIME = 2.0;

	private Flywheel _flywheel;
	private double _speed;
	private Timer _shootTimer = new Timer();

	public AutoShoot(Flywheel flywheel, double speed) {
		if (speed <= 0) {
			throw new IllegalArgumentException("Speed must be positive");
		}
		if (flywheel == null) {
			throw new IllegalArgumentException("Flywheel is null");
		}
		_speed = speed;
		_flywheel = flywheel;
		addRequirements(flywheel);
	}

	@Override
	public void initialize() {
		_shootTimer.start();
		_flywheel.spin(_speed);
	}

	/**
	 * Shoot after delay
	 */
	@Override
	public void execute() {
		if (_shootTimer.get() > SHOOT_DELAY) {
			_flywheel.shootServos();
		}
	}

	/**
	 * Command ends when time runs out
	 */
	@Override
	public boolean isFinished() {
		return _shootTimer.get() >= END_TIME;
	}
	/**
	 * Stops the flywheel and resets the servos once finished
	 */
	@Override
	public void end(boolean interrupted) {
		_flywheel.spin(0);
		_flywheel.resetServos();
		_shootTimer.stop();
		_shootTimer.reset();
	}
}
