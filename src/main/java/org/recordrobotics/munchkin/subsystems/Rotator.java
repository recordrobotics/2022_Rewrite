package org.recordrobotics.munchkin.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.recordrobotics.munchkin.Constants;
import org.recordrobotics.munchkin.RobotMap;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Rotator extends SubsystemBase {

	private CANSparkMax _leftMotor = new CANSparkMax(RobotMap.Rotator.LEFT_MOTOR_PORT, MotorType.kBrushless);
	private CANSparkMax _rightMotor = new CANSparkMax(RobotMap.Rotator.RIGHT_MOTOR_PORT, MotorType.kBrushless);

	private RelativeEncoder _leftEncoder = _leftMotor.getEncoder();
	private RelativeEncoder _rightEncoder = _rightMotor.getEncoder();

	private DigitalInput _forwardLimit = new DigitalInput(RobotMap.Rotator.FWD_LIMIT_PORT);
	private DigitalInput _backwardLimit = new DigitalInput(RobotMap.Rotator.BWD_LIMIT_PORT);

	private NetworkTableEntry _entryEncoder;

	public Rotator() {
		_leftMotor.set(0);
		_rightMotor.set(0);

		ShuffleboardTab tab = Shuffleboard.getTab(Constants.DATA_TAB);
		_entryEncoder = tab.add("Rotator position", 0.0).getEntry();
	}

	/**
	 * gets position from encoders
	 * @return position
	 */
	public double getPosition() {
		// the motors spin in opposite directions, so one encoder is always positive and the other is negative
		return (_leftEncoder.getPosition() - _rightEncoder.getPosition()) / 2;
	}

	/**
	 * sets encoder values to 0
	 */
	public void resetEncoders() {
		_leftEncoder.setPosition(0);
		_rightEncoder.setPosition(0);
	}

	/**
	 * rotate lift forwards and backwards
	 * @param speed speed
	 */
	public void rotate(double speed) {
		if (speed > 0 && _forwardLimit.get() || speed < 0 && _backwardLimit.get()) {
			_leftMotor.set(Subsystems.limitSpeed(speed));
			_rightMotor.set(Subsystems.limitSpeed(-speed));
		} else {
			_leftMotor.set(0);
			_rightMotor.set(0);
		}
	}

	/**
	 * Return the inversed state of the forward limit switch
	 * @return true is pressed, false is not
	 */
	public boolean isFwdLimitPressed() {
		return !_forwardLimit.get();
	}
	
	/*
	 * Update dashboard value
	 */
	@Override
	public void periodic() {
		_entryEncoder.setDouble(getPosition());
	}
}
