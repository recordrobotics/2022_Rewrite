package org.recordrobotics.munchkin.control;

import org.recordrobotics.munchkin.Constants;

public class ControlRamping {

	private double _higherTimeScale;
	private double _lowerTimeScale;
	private boolean _maxScaling;

	public ControlRamping (double higherTimeScale, double lowerTimeScale, boolean maxScaling) {
		_higherTimeScale = higherTimeScale;
		_lowerTimeScale = lowerTimeScale;
		_maxScaling = maxScaling;
	}

	public double calcNextCtrlScale(double currControlScale, double input) {
		double timeScale = _lowerTimeScale;
		if(_maxScaling) {
			if(input == Constants.ZERO || Math.abs(input) == 1.0) {
				timeScale = _higherTimeScale;
			}
		} else {
			if(input == Constants.ZERO) {
				timeScale = _higherTimeScale;
			}
		}
		double nextCtrlScale = currControlScale + (input - currControlScale)/timeScale;
		if(Math.abs(input-currControlScale) <= Constants.RAMPING_JUMP_THRESHOLD) {
			nextCtrlScale = input;
		}
		if(nextCtrlScale < -1.0 || nextCtrlScale > 1.0) {
			nextCtrlScale = nextCtrlScale/Math.abs(nextCtrlScale);
		}
		return nextCtrlScale;
	}
}
