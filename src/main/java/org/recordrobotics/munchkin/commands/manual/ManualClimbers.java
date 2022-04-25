package org.recordrobotics.munchkin.commands.manual;

import org.recordrobotics.munchkin.subsystems.Climbers;
import org.recordrobotics.munchkin.control.IControlInput;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ManualClimbers extends CommandBase {
	private Climbers _climbers;
	private IControlInput _controls;
	
	public ManualClimbers(Climbers climber, IControlInput controlInput) {
		_climbers = climber;
		_controls = controlInput;
		addRequirements(_climbers);
	}

	@Override
	public void execute() {		
		// [-1, 0) = Climbers down, (0, 1] = Climbers extend, 0 = Stop
		_climbers.moveLift(_controls.getClimb());
	}
}
