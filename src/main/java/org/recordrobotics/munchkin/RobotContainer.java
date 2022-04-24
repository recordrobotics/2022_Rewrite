// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.recordrobotics.munchkin;

import org.recordrobotics.munchkin.commands.ExampleCommand;
import org.recordrobotics.munchkin.control.IControlInput;
import org.recordrobotics.munchkin.control.LegacyControl;
import org.recordrobotics.munchkin.subsystems.Acquisition;
import org.recordrobotics.munchkin.subsystems.ExampleSubsystem;
import org.recordrobotics.munchkin.commands.manual.ManualAcquisition;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * Contains subsystems, control and command scheduling
 */
public class RobotContainer {
	// Subsystems
	private ExampleSubsystem _exampleSubsystem = new ExampleSubsystem();

	// Control scheme
	@SuppressWarnings({"unused", "PMD.SingularField"})
	private IControlInput _controlInput;

	// Acquisition
	@SuppressWarnings({"unused", "PMD.SingularField"})
	private Acquisition _acquisition;

	// Autonomous command
	private ExampleCommand _autoCommand = new ExampleCommand(_exampleSubsystem);

	public RobotContainer() {
		_controlInput = new LegacyControl(RobotMap.Control.LEGACY_GAMEPAD);
		_acquisition = new Acquisition();
		_acquisition.setDefaultCommand(new ManualAcquisition(_acquisition, _controlInput));
	}

	public Command getAutonomousCommand() {
		return _autoCommand;
	}
}
