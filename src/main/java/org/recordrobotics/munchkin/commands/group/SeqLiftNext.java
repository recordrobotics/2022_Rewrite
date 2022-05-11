package org.recordrobotics.munchkin.commands.group;

import org.recordrobotics.munchkin.commands.auto.AutoClimbTo;
import org.recordrobotics.munchkin.commands.auto.AutoPullUp;
import org.recordrobotics.munchkin.commands.auto.AutoResetRotator;
import org.recordrobotics.munchkin.commands.auto.AutoRotateTo;
import org.recordrobotics.munchkin.subsystems.Rotator;
import org.recordrobotics.munchkin.subsystems.Climbers;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * Climb robot to from one bar next bar
 * Setup:
 * 	- Robot is hanging from one bar by the hooks
 * 	- Hooks are fully retracted (encoder 0)
 * Expected outcome:
 * 	- Robot will get onto the next bar
 */
public class SeqLiftNext extends SequentialCommandGroup {

	private static final double ROTATOR_SPEED = 0.5;
	private static final double CLIMBERS_SPEED = 0.5;

	private static final double ROTATOR_BAR_TARGET = -42.0;
	private static final double ROTATOR_EXTEND_TARGET = -96.0;
	private static final double ROTATOR_PULL_TARGET = -83.0;

	private static final double CLIMBERS_BAR_TARGET = -60.0;
	private static final double CLIMBERS_EXTEND_TARGET = -410.0;
	private static final double CLIMBERS_PULL_TARGET = -250.0;

	public SeqLiftNext(Rotator rotator, Climbers climbers) {
		addCommands(
			// Hang robot using rotator tower
			new AutoRotateTo(rotator, ROTATOR_BAR_TARGET, ROTATOR_SPEED),
			// Push climbers off bar
			new AutoClimbTo(climbers, CLIMBERS_BAR_TARGET, CLIMBERS_SPEED),
			// Extend to next bar
			new ParallelCommandGroup(
				new AutoRotateTo(rotator, ROTATOR_EXTEND_TARGET, ROTATOR_SPEED),
				new AutoClimbTo(climbers, CLIMBERS_EXTEND_TARGET, CLIMBERS_SPEED)
			),
			// Align hooks with bar
			new AutoRotateTo(rotator, ROTATOR_PULL_TARGET, ROTATOR_SPEED),
			// Lift robot from lower bar
			new AutoClimbTo(climbers, CLIMBERS_PULL_TARGET, CLIMBERS_SPEED),
			// Lift to next bar
			new ParallelCommandGroup(
				new AutoResetRotator(rotator, ROTATOR_SPEED),
				new AutoPullUp(climbers, CLIMBERS_SPEED)
			)
		);
	}

}
