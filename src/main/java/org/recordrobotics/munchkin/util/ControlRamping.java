package org.recordrobotics.munchkin.util;

public class ControlRamping {

	private double _higherTimeScale;
	private double _lowerTimeScale;
	private boolean _isMaxScalingFaster;

	private final static double RAMPING_JUMP_THRESHOLD = 0.1;
	private final static double CTRL_NEUTRAL_POSITION = 0.0; // If the input is less than this constant, it is considered in a neutral position.
	private final static double MAX_CTRL_SCALE = 1.0;

	/** Constructor function. higherTimeScale is used when acceleration ramping needs to be faster,
	 * lowerTimeScale is when it needs to be slower. isMaxScaling Faster determines whether
	 * the higher scaling value is used when the control stick is pressed all the way forwards,
	 * instead of just when it returns to a neutral position.
	 */
	public ControlRamping (double higherTimeScale, double lowerTimeScale, boolean isMaxScalingFaster) {
		_higherTimeScale = higherTimeScale;
		_lowerTimeScale = lowerTimeScale;
		_isMaxScalingFaster = isMaxScalingFaster;
	}

	private double limitValue(double value, double min, double max) {
		return Math.min(Math.max(value, min), max);
	}

	/** This function is for acceleration ramping. My approach to this is not to ramp the actual
	* value used to tell the motors to spin, but to rather ramp the "input value", something I
	* call the "control scale." This value is a value between 0 to 1, and somewhat represents where
	* the control stick is in the space it could be, with ramping.
	* <p>
	* Returns a double that represents the next control scale, according to ramping. Both of the
	* arguments need to be doubles.
	* <p>
	* This method automatically limits the control scale, so the control scale will never be greater
	* than 1 or less than -1.
	* <p>
	* The calculation for ramping takes the current distance between the target and current, then
	* divides that by a "time scale" that can change if you need to slow down or get to the max
	* speed quickly.

	@param currControlScale The current control scale, used to determine how far the target is, then how
	@param input The target control scale, and what the control stick actually inputs.
	@return nextCtrlScale, based on the distance, and setting of ramping.
	*/

	public double calcNextCtrlScale(double currControlScale, double input) {
		double timeScale = _lowerTimeScale;
		boolean isControlNeutral = Math.abs(input) <= CTRL_NEUTRAL_POSITION;
		boolean isControlMax = Math.abs(input) == MAX_CTRL_SCALE;
		if (_isMaxScalingFaster && (isControlNeutral || isControlMax) || !_isMaxScalingFaster && isControlNeutral) {
			timeScale = _higherTimeScale;
		}
		double nextCtrlScale = currControlScale + (input - currControlScale)/timeScale;
		if (Math.abs(input-currControlScale) <= RAMPING_JUMP_THRESHOLD) {
			nextCtrlScale = input;
		}
		nextCtrlScale = limitValue(nextCtrlScale, -MAX_CTRL_SCALE, MAX_CTRL_SCALE);
		return nextCtrlScale;
	}
}
