package org.recordrobotics.munchkin.commands.auto;

import org.recordrobotics.munchkin.subsystems.Acquisition;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoSpinAcq extends CommandBase {
	private Acquisition _acquisition;
	private double _speed;
	private Timer _collectTimer = new Timer();
	private double _collectTime; // the total time the acquisition system will be running (through the wheels + past the ball channel)

	public AutoSpinAcq(Acquisition acquisition, double speed, double collectTime) {
		if (speed <= 0) {
			throw new IllegalArgumentException("Speed must be positive");
		}
		if (acquisition == null) {
			throw new IllegalArgumentException("Acquisition is null");
		}
		if (collectTime < 0) {
			throw new IllegalArgumentException("Collect Time must be positive");
		}
		// setting _speed to -speed so the aquisition intakes balls
		_speed = -speed;
		_collectTime = collectTime;
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
	 * command ends when a set amt of time passes
	 */
	@Override
	public boolean isFinished() {
		return _collectTimer.get() > _collectTime;
	}

	/**
	 * stop motor once finished
	 */
	@Override
	public void end(boolean interrupted) {
		_acquisition.spin(0);
		_collectTimer.stop();
		_collectTimer.reset();
	}
}
