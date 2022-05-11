// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.recordrobotics.munchkin;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

	private RobotContainer _robotContainer;

	/**
	 * Robot initialization
	 */
	@Override
	public void robotInit() {
		// Create container
		_robotContainer = new RobotContainer();
	}

	/**
	 * Runs every robot tick
	 */
	@Override
	public void robotPeriodic() {
		// Run command scheduler
		CommandScheduler.getInstance().run();
	}

	/**
	 * Runs when robot enters disabled mode
	 */
	@Override
	public void disabledInit() {
		_robotContainer.resetCommands();
	}

	/**
	 * Runs every tick during disabled mode
	 */
	@Override
	public void disabledPeriodic() {
		// TODO
	}

	/**
	 * Runs when robot enters auto mode
	 */
	@Override
	public void autonomousInit() {
		_robotContainer.resetCommands();
		_robotContainer.autoInit();
	}

	/**
	 * Runs every tick during auto mode
	 */
	@Override
	public void autonomousPeriodic() {
		// TODO
	}

	/**
	 * Runs when robot enters teleop mode
	 */
	@Override
	public void teleopInit() {
		_robotContainer.resetCommands();
		_robotContainer.teleopInit();
	}

	/**
	 * Runs every tick in teleop mode
	 */
	@Override
	public void teleopPeriodic() {
		// TODO
	}
}
