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
	private double _time;
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

	public double calcNextCtrlScale(double input, double time) {
		double nextCtrlScale = 0.0;
		if(time == Constants.ZERO) {
			return 0.0;
		}
		if(input != Constants.ZERO) {
			nextCtrlScale = (input/Math.abs(input))*Math.pow(Constants.MOVEMENT_EXPONENTIAL_BASE, time/50 - 1);
		}
		if(nextCtrlScale < -1.0 || nextCtrlScale > 1.0) {
			nextCtrlScale = nextCtrlScale/Math.abs(nextCtrlScale);
		}
		return nextCtrlScale;
	}

	@Override
	public void execute() {
		if(_ramping) {
			if (_controls.getDriveLong() == 0.0 && _controls.getDriveLat() == 0.0) {
				_time = 0.0;
			}
			else {
				_time += 1;
			}
			_controlScaleLong = calcNextCtrlScale(_controls.getDriveLong(), _time);
			_controlScaleLat = calcNextCtrlScale(_controls.getDriveLat(), _time);
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
