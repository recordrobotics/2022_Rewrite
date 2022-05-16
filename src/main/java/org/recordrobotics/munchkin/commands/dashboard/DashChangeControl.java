package org.recordrobotics.munchkin.commands.dashboard;

import org.recordrobotics.munchkin.util.Procedure;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DashChangeControl extends CommandBase {

	private Procedure _procedure;

	public DashChangeControl(Procedure procedure) {
		_procedure = procedure;
	}

	@Override
	public void initialize() {
		if (DriverStation.isDisabled()) {
			_procedure.execute();
		}
	}

	@Override
	public boolean isFinished() {
		return true;
	}

}
