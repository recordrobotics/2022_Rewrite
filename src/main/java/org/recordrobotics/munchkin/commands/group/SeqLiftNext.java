package org.recordrobotics.munchkin.commands.group;

import org.recordrobotics.munchkin.commands.auto.AutoPullUp;
import org.recordrobotics.munchkin.commands.auto.AutoResetRotator;
import org.recordrobotics.munchkin.commands.auto.AutoRotateTo;
import org.recordrobotics.munchkin.subsystems.Rotator;
import org.recordrobotics.munchkin.subsystems.Climbers;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SeqLiftNext extends SequentialCommandGroup {
	
	private static final double ROTATOR_SPEED = 0.5;
	private static final double CLIMBERS_SPEED = 0.5;

	private static final double ROTATOR_BAR_TARGET = -42.0;
	private static final double ROTATOR_EXTEND_TARGET = -96.0;
	private static final double ROTATOR_PULL_TARGET = -83.0;

	public SeqLiftNext(Rotator rotator, Climbers climbers) {
		addCommands(
			new AutoRotateTo(rotator, ROTATOR_BAR_TARGET, ROTATOR_SPEED),
			// todo: Push CIB off bar
			new AutoRotateTo(rotator, ROTATOR_EXTEND_TARGET, ROTATOR_SPEED),
			// todo: Extend to target
			new AutoRotateTo(rotator, ROTATOR_PULL_TARGET, ROTATOR_SPEED),
			// tdod: Push hooks from bar
			new ParallelCommandGroup(
				new AutoResetRotator(rotator, ROTATOR_SPEED),
				new AutoPullUp(climbers, CLIMBERS_SPEED)
			)
		);
	}
	
}
