package org.recordrobotics.munchkin;

import java.util.HashMap;
import java.util.Map;

//import org.recordrobotics.munchkin.commands.auto.*;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Dashboard {
	private Map<String, ShuffleboardTab> _tabs = new HashMap<>();

	public Dashboard() {
		addCommands();
	}

	private void addCommands() {
	}

	public ShuffleboardTab getTab(String tabName) {
		return _tabs.get(tabName);
	}
}
