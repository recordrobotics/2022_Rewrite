package org.recordrobotics.munchkin.commands.auto;

import org.recordrobotics.munchkin.subsystems.Acquisition;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoSpinAcq extends CommandBase {
	private Acquisition _acquisition;
	private double _speed;
	private Timer _collectTimer = new Timer();
	private static final double COLLECT_TIME = 10.0; // the total time the flywheel will be running (start up time + time it takes to shoot the ball)

	public AutoSpinAcq(Acquisition acquisition, double speed) {
		if (speed <= 0) {
			throw new IllegalArgumentException("Speed must be positive");
		}
		if (acquisition == null) {
			throw new IllegalArgumentException("Acquisition is null");
		}
		// setting _speed to -speed so the aquisition intakes balls
		_speed = -speed;
		_acquisition = acquisition;
		addRequirements(acquisition);
	}

	/**
	 * spin acquisition down
	 */
	@Override
	public void initialize() {
		_acquisition.spin(_speed);
		_collectTimer.start();
	}

	/**
	 * command ends when ball channel limit switch is hit
	 */
	@Override
	public boolean isFinished() {
		return _collectTimer.get() > COLLECT_TIME;
	}

	/**
	 * stop motor once finished
	 */
	@Override
	public void end(boolean interrupted) {
		_acquisition.spin(0);
		_collectTimer.reset();
		_collectTimer.stop();
	}
}
