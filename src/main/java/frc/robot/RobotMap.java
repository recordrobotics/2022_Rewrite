package frc.robot;

/**
 * Hardware ports for computer and robot
 */
public class RobotMap {

	/**
	 * Control ports (PC USB)
	 */
	public class Control {
		// Gamepad port for LegacyControl scheme
		public static final int LEGACY_GAMEPAD = 0;
	}

	/**
	 * Climber ports (CAN)
	 */
	public class Climbers {
		// Ports for Climbers
		public static final int LEFT_MOTOR_PORT = 10;
		public static final int RIGHT_MOTOR_PORT = 6;
	}
}
