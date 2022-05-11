package org.recordrobotics.munchkin;

import java.util.HashMap;
import java.util.Map;

//import org.recordrobotics.munchkin.commands.auto.*;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Dashboard {
	private Map<String, ShuffleboardTab> _tabs = new HashMap<>();

	public Dashboard() {
		_tabs.put("values", Shuffleboard.getTab("Values"));
		_tabs.put("cameras", Shuffleboard.getTab("Cameras"));
		_tabs.put("commands", Shuffleboard.getTab("Commands"));
		addCommands();
	}

	private void addCommands() {
		//ShuffleboardTab commands = getTab("commands");
		// once the commands are written, put their calls here
	}

	public ShuffleboardTab getTab(String tabName) {
		return _tabs.get(tabName);
	}
}
