package org.recordrobotics.munchkin.commands.group;

import org.recordrobotics.munchkin.subsystems.Climbers;
import org.recordrobotics.munchkin.subsystems.Rotator;
import org.recordrobotics.munchkin.commands.auto.AutoResetRotator;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SeqLiftMid extends SequentialCommandGroup {

	private static final double ROTATOR_SPEED = 0.5;

	// TODO: remove suppress warnings annotation
	public SeqLiftMid(Rotator rotator, @SuppressWarnings("PMD.UnusedFormalParameter") Climbers climbers) {
		addCommands(
			new AutoResetRotator(rotator, ROTATOR_SPEED)
		);
	}

}
