package org.recordrobotics.munchkin.subsystems;

import org.recordrobotics.munchkin.Dashboard;
import org.recordrobotics.munchkin.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Sensors extends SubsystemBase {
	private DigitalInput _ballDetector = new DigitalInput(RobotMap.Sensors.BALL_DETECTOR_PORT);
	private Ultrasonic _rangeFinderA = new Ultrasonic(RobotMap.Sensors.RANGE_FINDER_A_PING, RobotMap.Sensors.RANGE_FINDER_A_ECHO);
	private Ultrasonic _rangeFinderB = new Ultrasonic(RobotMap.Sensors.RANGE_FINDER_B_PING, RobotMap.Sensors.RANGE_FINDER_B_ECHO);
	private Dashboard _dashboard;

	public Sensors(Dashboard dashboard) {
		if (dashboard == null) {
			throw new IllegalArgumentException("Dashboard is null");
		}
		_dashboard = dashboard;
		_rangeFinderA.setEnabled(true);
		_rangeFinderB.setEnabled(true);
	}

	/**
	 * @return average range returned in millimeters
	 */
	public double getDistance() {
		return (_rangeFinderA.getRangeMM() + _rangeFinderB.getRangeMM()) / 2;
	}
	/**
	 * @return A's range
	 */
	public double getADistance(){
		return _rangeFinderA.getRangeMM();
	}
	/**
	 * @return B's range
	 */
	public double getBDistance(){
		return _rangeFinderB.getRangeMM();
	}

	/**
	 * returns ball detector state
	 * @return ball detector switch is active (true = active, false = not active)
	 */
	public boolean getBallDetector() {
		return !_ballDetector.get();
	}

	@Override
	public void periodic() {
		_dashboard.getTab("values").add("Range Finder Right", getADistance()).getEntry().setDouble(getADistance());
		_dashboard.getTab("values").add("Range Finder Left", getBDistance()).getEntry().setDouble(getBDistance());
		_dashboard.getTab("values").add("Ball Detected", getBallDetector()).getEntry().setBoolean(getBallDetector());
	}
}
