package org.recordrobotics.munchkin.commands.dashboard;

import org.recordrobotics.munchkin.subsystems.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DashToggleDriveRamping extends CommandBase {

	private Drive _drive;

	public DashToggleDriveRamping(Drive drive) {
		if (drive == null) {
			throw new IllegalArgumentException("Drive is null");
		}

		_drive = drive;
	}

	/**
	 * changes drive ramping to true if false and false if true
	 */
	@Override
	public void initialize() {
		_drive.toggleRamping();
	}

	/**
	 * Command finished immediately
	 */
	@Override
	public boolean isFinished() {
		return true;
	}

}
