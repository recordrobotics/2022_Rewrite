package org.recordrobotics.munchkin.subsystems;

import org.recordrobotics.munchkin.Constants;

// put static functions that are used by multiple subsystems here
public final class Subsystems {
	// so that Subsystems can't be instantiated
	private Subsystems() {}
	/**
	 * Checks a motor speed value against Constants.SPEED_LIMIT
	 * @param speed speed value to check
	 * @return speed to spin motor (positive or negative)
	 */
	public static double limitSpeed(double speed) {
		if (speed > 0) {
			return Math.min(speed, Constants.SPEED_LIMIT);
		} else {
			return Math.max(speed, -Constants.SPEED_LIMIT);
		}
	}
}
