// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.recordrobotics.munchkin.commands.manual;

import org.recordrobotics.munchkin.control.IControlInput;
import org.recordrobotics.munchkin.Constants;
import org.recordrobotics.munchkin.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ManualDrive extends CommandBase {

	private static final double SPEED_MODIFIER = 0.5;

	private Drive _drive;
	private IControlInput _controls;
	private double _controlScaleLong;
	private double _controlScaleLat;
	private Boolean _ramping;

	public ManualDrive(Drive drive, IControlInput controls, Boolean ramping) {
		if (drive == null) {
			throw new IllegalArgumentException("Drive is null");
		}
		if (controls == null) {
			throw new IllegalArgumentException("Controls is null");
		}

		_drive = drive;
		_controls = controls;
		_ramping = ramping;
		addRequirements(drive);
	}

	public double calcNextCtrlScale(double currControlScale, double input) {
		double timeScale = Constants.DEFAULT_CONTROL_RAMPING;

		if(input == Constants.ZERO) {
			timeScale = Constants.NEUTRAL_CONTROL_RAMPING;
		}
		double nextCtrlScale = currControlScale + (input - currControlScale)/timeScale;
		if(Math.abs(input-currControlScale) <= Constants.RAMPING_JUMP_THRESHOLD) {
			nextCtrlScale = input;
		}
		if(nextCtrlScale < -1.0 || nextCtrlScale > 1.0) {
			nextCtrlScale = nextCtrlScale/Math.abs(nextCtrlScale);
		}
		return nextCtrlScale;
	}

	@Override
	public void execute() {
		if(_ramping) {
			_controlScaleLong = calcNextCtrlScale(_controlScaleLong, _controls.getDriveLong());
			_controlScaleLat = calcNextCtrlScale(_controlScaleLat, _controls.getDriveLat());
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
