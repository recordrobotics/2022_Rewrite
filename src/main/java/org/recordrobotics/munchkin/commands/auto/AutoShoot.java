package org.recordrobotics.munchkin.commands.auto;

import org.recordrobotics.munchkin.subsystems.Flywheel;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoShoot extends CommandBase {
	private Flywheel _flywheel;
	private double _speed;
	private Timer _shootTimer = new Timer();
	private static final double START_UP_TIME = 2.0; // the time, in , it takes to start spinning the flywheel
	private static final double SHOOT_TIME = 5.0; // the total time the flywheel will be running (start up time + time it takes to shoot the ball)

	public AutoShoot(Flywheel flywheel, double speed) {
		if (speed <= 0) {
			throw new IllegalArgumentException("Speed must be positive");
		}
		if (flywheel == null) {
			throw new IllegalArgumentException("Flywheel is null");
		}
		_speed = speed;
		_flywheel = flywheel;
		_flywheel.spin(0);
		addRequirements(flywheel);
	}

	@Override
	public void initialize() {
		_shootTimer.start();
		_flywheel.spin(_speed);
	}

	@Override
	public void execute() {
		if (_shootTimer.get() > START_UP_TIME) {
			_flywheel.shootServos();
		}
	}

	@Override
	public boolean isFinished() {
		return _shootTimer.get() >= SHOOT_TIME;
	}
	/**
	 * Stops the flywheel and resets the servos once finished
	 */
	@Override
	public void end(boolean interrupted) {
		_flywheel.spin(0);
		_flywheel.resetServos();
	}
}
