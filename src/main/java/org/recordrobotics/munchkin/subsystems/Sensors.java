package org.recordrobotics.munchkin.subsystems;

import org.recordrobotics.munchkin.Constants;
import org.recordrobotics.munchkin.RobotMap;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Sensors extends SubsystemBase {

	private DigitalInput _ballDetector = new DigitalInput(RobotMap.Sensors.BALL_DETECTOR_PORT);
	private Ultrasonic _rangeFinderA = new Ultrasonic(RobotMap.Sensors.RANGE_FINDER_A_PING, RobotMap.Sensors.RANGE_FINDER_A_ECHO);
	private Ultrasonic _rangeFinderB = new Ultrasonic(RobotMap.Sensors.RANGE_FINDER_B_PING, RobotMap.Sensors.RANGE_FINDER_B_ECHO);

	// Dashboard entries
	private NetworkTableEntry _entryRangeA;
	private NetworkTableEntry _entryRangeB;
	private NetworkTableEntry _entryBallDetection;

	public Sensors() {
		_rangeFinderA.setEnabled(true);
		_rangeFinderB.setEnabled(true);

		// Set up dashboard
		ShuffleboardTab tab = Shuffleboard.getTab(Constants.DATA_TAB);
		_entryRangeA = tab.add("Range Finder A", 0.0).getEntry();
		_entryRangeB =  tab.add("Range Finder B", 0.0).getEntry();
		_entryBallDetection = tab.add("Ball Detection", false).getEntry();
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

	/**
	 * Updates dashboard entries
	 */
	@Override
	public void periodic() {
		_entryRangeA.setDouble(getADistance());
		_entryRangeB.setDouble(getBDistance());
		_entryBallDetection.setBoolean(getBallDetector());
	}
}
