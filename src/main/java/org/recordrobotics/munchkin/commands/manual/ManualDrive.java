// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.recordrobotics.munchkin.commands.manual;

import org.recordrobotics.munchkin.control.IControlInput;
import org.recordrobotics.munchkin.subsystems.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Drive teleop command
 */
public class ManualDrive extends CommandBase {

	private static final double SPEED_MODIFIER = 0.5;
	private static final double CTRL_RAMPING = 25.0;
	private static final double MAX_CTRL_RAMPING = 15.0;

	private Drive _drive;
	private IControlInput _controls;
	private double _controlScaleLong;
	private double _controlScaleLat;

	public ManualDrive(Drive drive, IControlInput controls) {
		if (drive == null) {
			throw new IllegalArgumentException("Drive is null");
		}
		if (controls == null) {
			throw new IllegalArgumentException("Controls is null");
		}

		_drive = drive;
		_controls = controls;
		addRequirements(drive);
	}

	@Override
	public void execute() {
		if (_drive.isRamping()) {
			_controlScaleLong = _drive.calcNextCtrlScale(MAX_CTRL_RAMPING, CTRL_RAMPING, true, _controlScaleLong, _controls.getDriveLong());
			_controlScaleLat = _drive.calcNextCtrlScale(MAX_CTRL_RAMPING, CTRL_RAMPING, true, _controlScaleLat, _controls.getDriveLat());
			_drive.move(_controlScaleLong * SPEED_MODIFIER, _controlScaleLat * SPEED_MODIFIER);
		}
		else {
			_drive.move(_controls.getDriveLong() * SPEED_MODIFIER,
				_controls.getDriveLat() * SPEED_MODIFIER);
		}
	}

	@Override
	public void end(boolean interrupted) {
		_drive.move(0, 0);
	}

}
