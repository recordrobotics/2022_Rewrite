package org.recordrobotics.munchkin.commands.group;

import org.recordrobotics.munchkin.subsystems.Climbers;
import org.recordrobotics.munchkin.subsystems.Rotator;
import org.recordrobotics.munchkin.commands.auto.AutoPullUp;
import org.recordrobotics.munchkin.commands.auto.AutoResetRotator;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * Climb robot from ground to middle bar
 * Setup:
 * 	- Climbers extended and placed directly above bar
 *	- Acquisition is completely out
 * Expected outcome:
 * 	- Robot will hang from middle bar with climbers
 */
public class SeqLiftMid extends SequentialCommandGroup {

	private static final double ROTATOR_SPEED = 0.5;
	private static final double CLIMBERS_SPEED = 0.5;

	public SeqLiftMid(Rotator rotator, Climbers climbers) {
		addCommands(
			// todo: drop acq
			// Lift to mid
			new ParallelCommandGroup(
				new AutoResetRotator(rotator, ROTATOR_SPEED),
				new AutoPullUp(climbers, CLIMBERS_SPEED)
			)
		);
	}

}
