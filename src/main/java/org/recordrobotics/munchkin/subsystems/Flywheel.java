package org.recordrobotics.munchkin.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.recordrobotics.munchkin.Constants;
import org.recordrobotics.munchkin.RobotMap;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Flywheel extends SubsystemBase {
	// constants related to servo positions
	private static final double RIGHT_SERVO_SHOOT = 0.0;
	private static final double LEFT_SERVO_SHOOT = 0.33;
	private static final double RIGHT_SERVO_RESET = 0.33;
	private static final double LEFT_SERVO_RESET = 0.0;

	// current speed (Idle, Low, High)
	private String _speed = "Idle";

	private NetworkTableEntry _entrySpeed;

	// motor, servo, and limit switch variables
	private WPI_TalonFX _motor = new WPI_TalonFX(RobotMap.Flywheel.MOTOR_PORT);
	private Servo _leftServo = new Servo(RobotMap.Flywheel.LEFT_SERVO_PORT);
	private Servo _rightServo = new Servo(RobotMap.Flywheel.RIGHT_SERVO_PORT);

	/**
	 * resets motor and servos upon creation
	 */
	public Flywheel() {
		spin(0);

		ShuffleboardTab tab = Shuffleboard.getTab(Constants.DATA_TAB);
		_entrySpeed = tab.add("Flywheel Speed", "Idle").getEntry();
	}

	/**
	 * spins motor at speed 'speed'
	 * @param speed speed to spin motor
	 */
	public void spin(double speed) {
		_motor.set(ControlMode.PercentOutput, Subsystems.limitSpeed(speed));
	}

	/**
	 * sets the servos to shooting position (launching the ball into the flywheel in the process)
	 */
	public void shootServos() {
		_leftServo.set(LEFT_SERVO_SHOOT);
		_rightServo.set(RIGHT_SERVO_SHOOT);
	}

	/**
	 * puts the servos back into idle position
	 */
	public void resetServos() {
		_leftServo.set(LEFT_SERVO_RESET);
		_rightServo.set(RIGHT_SERVO_RESET);
	}

	/**
	 * sets speed to parameter
	 * @param newSpeed new speed value (string: "Idle", "Low", or "High")
	 */
	public void setSpeed(String newSpeed) {
		_speed = newSpeed;
	}

	/**
	 * Update dashboard data
	 */
	@Override
	public void periodic() {
		_entrySpeed.setString(_speed);
	}
}
