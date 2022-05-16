package org.recordrobotics.munchkin.commands.dashboard;

import org.recordrobotics.munchkin.util.Procedure;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DashRunProcedure extends CommandBase {

	private Procedure _procedure;

	public DashRunProcedure(Procedure procedure) {
		_procedure = procedure;
	}

	@Override
	public void initialize() {
		_procedure.execute();
	}

	@Override
	public boolean isFinished() {
		return true;
	}

}
