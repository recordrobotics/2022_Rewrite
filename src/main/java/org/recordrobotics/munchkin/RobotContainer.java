// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.recordrobotics.munchkin;

import org.recordrobotics.munchkin.commands.ExampleCommand;
import org.recordrobotics.munchkin.control.IControlInput;
import org.recordrobotics.munchkin.control.LegacyControl;
import org.recordrobotics.munchkin.subsystems.ExampleSubsystem;
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

	// Autonomous command
	private ExampleCommand _autoCommand = new ExampleCommand(_exampleSubsystem);

	public RobotContainer() {
		_controlInput = new LegacyControl(RobotMap.Control.LEGACY_GAMEPAD);
	}

	public Command getAutonomousCommand() {
		return _autoCommand;
	}
}
