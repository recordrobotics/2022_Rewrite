// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.ExampleCommand;
import frc.robot.control.IControlInput;
import frc.robot.control.LegacyControl;
import frc.robot.subsystems.ExampleSubsystem;
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
		_controlInput = new LegacyControl(RobotMap.LegacyControl.LEGACY_GAMEPAD);
	}

	public Command getAutonomousCommand() {
		return _autoCommand;
	}
}
