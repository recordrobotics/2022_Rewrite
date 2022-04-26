package org.recordrobotics.munchkin.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.recordrobotics.munchkin.RobotMap;

public class Climbers extends SubsystemBase {
	private CANSparkMax _motorLeft = new CANSparkMax(RobotMap.Climbers.LEFT_MOTOR_PORT, MotorType.kBrushless);
	private CANSparkMax _motorRight = new CANSparkMax(RobotMap.Climbers.RIGHT_MOTOR_PORT, MotorType.kBrushless);
	private MotorControllerGroup _motors = new MotorControllerGroup(_motorLeft, _motorRight);
	private RelativeEncoder _encoder = _motorLeft.getEncoder();

	/**
	 * Stops the motors.
	 */
	public void stop() {
		_motors.stopMotor();
	}

	/**
	 * Gets encoder values.
	 * @return Encoder value as a double.
	 */
	public double getEncoderValue(){
		return _encoder.getPosition();
	}

	/**
	 * Resets encoder value to zero.
	 */
	public void resetEncoderValue(){
		_encoder.setPosition(0);
	}

	/**
	 * Moves lift up and down.
	 * @param v how fast the lift motors spins.
	 */
	public void move(double v) {
		if (v < 0 || v > 0 ) {
			_motors.set(Subsystems.limitSpeed(v));
		} else {
			_motors.set(v);
		}
	}
}
