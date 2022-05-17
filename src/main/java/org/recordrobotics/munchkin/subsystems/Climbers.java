package org.recordrobotics.munchkin.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.recordrobotics.munchkin.Constants;
import org.recordrobotics.munchkin.RobotMap;

public class Climbers extends SubsystemBase {

	private CANSparkMax _motorLeft = new CANSparkMax(RobotMap.Climbers.LEFT_MOTOR_PORT, MotorType.kBrushless);
	private CANSparkMax _motorRight = new CANSparkMax(RobotMap.Climbers.RIGHT_MOTOR_PORT, MotorType.kBrushless);
	private MotorControllerGroup _motors = new MotorControllerGroup(_motorLeft, _motorRight);

	private RelativeEncoder _encoder = _motorLeft.getEncoder();

	private NetworkTableEntry _entryEncoder;

	public Climbers() {
		_motors.set(0);

		ShuffleboardTab tab = Shuffleboard.getTab(Constants.DATA_TAB);
		_entryEncoder = tab.add("Climbers position", 0.0).getEntry();
	}

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
		_motors.set(Subsystems.limitSpeed(v));
	}

	/**
	 * Update dashboard data
	 */
	@Override
	public void periodic() {
		_entryEncoder.setDouble(getEncoderValue());
	}
}
