package org.recordrobotics.munchkin.commands.manual;

import org.recordrobotics.munchkin.control.IControlInput;
import org.recordrobotics.munchkin.subsystems.Acquisition;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ManualAcquisition extends CommandBase {
	private Acquisition _acquisition;
	private IControlInput _controls;
	// FIXME: set fields to the proper value
	private static final int SPIN_SPEED = 0;
	private static final int TILT_SPEED = 0;

	public ManualAcquisition(Acquisition acquisition, IControlInput controlInput) {
		_acquisition = acquisition;
		_controls = controlInput;
		addRequirements(_acquisition);
	}

	@Override
	public void execute() {
		// spin controls (if = intake balls, else if = expel balls,  else = stop motor)
		if (_controls.getAcqSpin() > 0) {
			_acquisition.spin(SPIN_SPEED);
		} else if (_controls.getAcqSpin() < 0) {
			_acquisition.spin(-SPIN_SPEED);
		} else {
			_acquisition.spin(0);
		}

		// tilt controls (if = tilt down, else if = tilt up,  else = stop motor)
		if (_controls.getAcqTilt() > 0) {
			_acquisition.tilt(TILT_SPEED);
		} else if (_controls.getAcqTilt() < 0) {
			_acquisition.tilt(-TILT_SPEED);
		} else {
			_acquisition.tilt(0);
		}
	}
}
