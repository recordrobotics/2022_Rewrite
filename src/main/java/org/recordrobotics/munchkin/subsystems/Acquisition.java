package org.recordrobotics.munchkin.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.recordrobotics.munchkin.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Acquisition extends SubsystemBase {
	// motor and limit switch variables
	private CANSparkMax _spinMotor = new CANSparkMax(RobotMap.Acquisition.SPIN_MOTOR_PORT, MotorType.kBrushless);
	private CANSparkMax _tiltMotor = new CANSparkMax(RobotMap.Acquisition.TILT_MOTOR_PORT, MotorType.kBrushed);
	private CANSparkMax _ballChannelMotor = new CANSparkMax(RobotMap.Acquisition.BALL_CHANNEL_MOTOR_PORT, MotorType.kBrushless);
	private DigitalInput _tiltLimitSwitch = new DigitalInput(RobotMap.Acquisition.LIMIT_SWITCH);
	// constant for ball channel motor speed modifier
	private static final double BALL_CHANNEL_MOD = -5.0 / 3;

	public Acquisition() {
		_spinMotor.set(0);
		_tiltMotor.set(0);
		_ballChannelMotor.set(0);
	}

	/**
	 * returns the state of the acquisition tilt
	 * @return true if down, false if up
	 */
	public boolean getTiltState() {
		return !_tiltLimitSwitch.get();
	}

	/**
	 * spins spin motor at 'speed' speed and ball channel motor at '-5/3 * speed' speed
	 * @param speed base value for speed calculations, always >0
	 */
	public void spin(double speed) {
		_spinMotor.set(Subsystems.limitSpeed(speed));
		_ballChannelMotor.set(Subsystems.limitSpeed(speed * BALL_CHANNEL_MOD));
	}

	/**
	 * spins tilt motor at 'speed' speed if it's raising it or lowering it and hasn't hit the limit switch
	 * @param speed speed of motor
	 */
	public void tilt(double speed) {
		if (speed < 0 && _tiltLimitSwitch.get() || speed > 0 ) {
			_tiltMotor.set(Subsystems.limitSpeed(speed));
		} else {
			_tiltMotor.set(0);
		}
	}

	/**
	 * returns if the acquisition is spinning (this is used for dashboard)
	 * @return acquisition spinning (true = spinning, false = not spinning)
	 */
	public boolean getSpinState() {
		return _spinMotor.get() != 0;
	}
}
