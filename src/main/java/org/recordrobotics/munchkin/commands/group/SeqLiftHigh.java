package org.recordrobotics.munchkin.commands.group;

import org.recordrobotics.munchkin.subsystems.Climbers;
import org.recordrobotics.munchkin.subsystems.Rotator;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * Climb robot from ground to high bar
 * Setup:
 * 	- Climbers extended and placed directly above middle bar
 * Expected outcome:
 * 	- Robot will hang from high bar with climbers
 */
public class SeqLiftHigh extends SequentialCommandGroup {
	
	public SeqLiftHigh(Rotator rotator, Climbers climbers) {
		addCommands(
			new SeqLiftMid(rotator, climbers),
			new SeqLiftNext(rotator, climbers)
		);
	}

}
