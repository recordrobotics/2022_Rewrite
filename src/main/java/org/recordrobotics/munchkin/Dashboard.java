package org.recordrobotics.munchkin;

import java.util.HashMap;
import java.util.Map;

import org.recordrobotics.munchkin.subsystems.*;
//import org.recordrobotics.munchkin.commands.auto.*;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Dashboard extends SubsystemBase {
	private Map<String, ShuffleboardTab> _tabs = new HashMap<>();
	private Sensors _sensors;
	private Climbers _climbers;
	private Rotator _rotator;

	public Dashboard(Sensors sensors, Climbers climbers, Rotator rotator) {
		if (sensors == null) {
			throw new IllegalArgumentException("Sensors is null");
		}
		if (climbers == null) {
			throw new IllegalArgumentException("Climbers is null");
		}
		if (rotator == null) {
			throw new IllegalArgumentException("Rotator is null");
		}
		_tabs.put("values", Shuffleboard.getTab("Values"));
		_tabs.put("cameras", Shuffleboard.getTab("Cameras"));
		_tabs.put("commands", Shuffleboard.getTab("Commands"));
		_sensors = sensors;
		_climbers = climbers;
		_rotator = rotator;
		addCommands();
	}

	private void addCommands() {
		// once the commands are written, put their calls here
	}

	public ShuffleboardTab getTab(String tabName) {
		return _tabs.get(tabName);
	}

	@Override
	public void periodic() {
		ShuffleboardTab value = getTab("values");
		value.add("Range Finder Right", _sensors.getADistance()).getEntry().setDouble(_sensors.getADistance());
		value.add("Range Finder Left", _sensors.getBDistance()).getEntry().setDouble(_sensors.getBDistance());
		value.add("Ball Detected", _sensors.getBallDetector()).getEntry().setBoolean(_sensors.getBallDetector());

		value.add("Rotator Encoder", _rotator.getPosition()).getEntry().setDouble(_rotator.getPosition());
		value.add("CIB Encoder", _climbers.getEncoderValue()).getEntry().setDouble(_climbers.getEncoderValue());
	}
}
