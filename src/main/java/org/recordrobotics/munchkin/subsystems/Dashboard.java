package org.recordrobotics.munchkin.subsystems;

import java.util.HashMap;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Dashboard {
	private HashMap<String, ShuffleboardTab> _tabs = new HashMap<>();

	public Dashboard() {
		_tabs.put("values", Shuffleboard.getTab("Values"));
		_tabs.put("cameras", Shuffleboard.getTab("Cameras"));
		_tabs.put("commands", Shuffleboard.getTab("Commands"));
	}
}
