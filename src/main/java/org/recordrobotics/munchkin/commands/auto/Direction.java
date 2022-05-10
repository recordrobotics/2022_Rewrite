package org.recordrobotics.munchkin.commands.auto;

/**
 * Direction for auto commands
 */
public enum Direction {

	BACKWARD(-1),
	DOWN(-1),
	FORWARD(1),
	UP(1);

	private final int _value;

	private Direction(int value) {
		_value = value;
	}

	public int value() {
		return _value;
	}

}
