// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.recordrobotics.munchkin;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
//import org.recordrobotics.munchkin.subsystems.Vision;

public class Robot extends TimedRobot {

	private RobotContainer _robotContainer;


	org.recordrobotics.munchkin.subsystems.Vision vision = new org.recordrobotics.munchkin.subsystems.Vision();

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

		boolean hasTargets = vision.checkForTarget(vision.camera);
		//System.out.println("WE GOT HERE");

		if (hasTargets){
			double[] globalPose = org.recordrobotics.munchkin.subsystems.Vision.estimateGlobalPose(vision.camera);
			System.out.println("POSE: " + globalPose[0] + " " + globalPose[1] + " " + globalPose[2]);
		}
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