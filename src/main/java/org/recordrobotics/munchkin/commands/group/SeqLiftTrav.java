package org.recordrobotics.munchkin.commands.group;

import org.recordrobotics.munchkin.subsystems.Climbers;
import org.recordrobotics.munchkin.subsystems.Rotator;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * Climb robot from ground to traversal bar
 * Setup:
 * 	- Climbers extended and placed directly above middle bar
 * Expected outcome:
 * 	- Robot will hang from traversal bar with climbers
 */
public class SeqLiftTrav extends SequentialCommandGroup {
	
	public SeqLiftTrav(Rotator rotator, Climbers climbers) {
		addCommands(
			new SeqLiftMid(rotator, climbers),
			new SeqLiftNext(rotator, climbers),
			new SeqLiftNext(rotator, climbers)
			// todo: maybe raise acq?
		);
	}

}
