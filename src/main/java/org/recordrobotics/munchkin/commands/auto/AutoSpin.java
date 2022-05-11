package org.recordrobotics.munchkin.commands.auto;

import org.recordrobotics.munchkin.subsystems.Acquisition;
import org.recordrobotics.munchkin.subsystems.Sensors;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoSpin extends CommandBase {
	private Acquisition _acquisition;
	private Sensors _sensors;
	private double _speed;

	public AutoSpin(Acquisition acquisition, Sensors sensors, double speed) {
		if (speed <= 0) {
			throw new IllegalArgumentException("Speed must be positive");
		}
		if (acquisition == null) {
			throw new IllegalArgumentException("Acquisition is null");
		}
		if (sensors == null) {
			throw new IllegalArgumentException("Sensors is null");
		}
		// setting _speed to -speed so the aquisition intakes balls
		_speed = -speed;
		_acquisition = acquisition;
		_acquisition.spin(0);
		_sensors = sensors;
		addRequirements(acquisition, sensors);
	}

	/**
	 * spin acquisition down
	 */
	@Override
	public void execute() {
		_acquisition.spin(_speed);
	}

	/**
	 * command ends when ball channel limit switch is hit
	 */
	@Override
	public boolean isFinished() {
		return _sensors.getBallDetector();
	}

	/**
	 * stop motor once finished
	 */
	@Override
	public void end(boolean interrupted) {
		_acquisition.spin(0);
	}
}
