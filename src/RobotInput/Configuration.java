package RobotInput;

import GeometryHelper.Vector;

public class Configuration {

	static double angle = Math.PI / 2.0; // in rad the
	static double newangle; // also in rad
	final static double wheeldiameter = 0.056; // m
	final static double axis = 0.126;// in meter
	final static double deviationAngle = 0.01; // use to ignore small changes in
												// angle
	static Vector center = new Vector(axis / 2, 0); // center.y should stay 0
	static double radius = 0;
	static double distance = 0;
	Vector rotationCenter;

	void setCenter(double x, double y) {
		center.setXY(x, y);
	}

	private static double computeAngle(double left, double right) {
		newangle = (right - left) / axis;
		return newangle;
	}

	/**
	 * calculate the sekante or if the angle is small a distance
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	private static double calculateSekante(double left, double right) {

		// if there was only a small change in angle , should be unnecessary
		if (Math.abs(newangle) < deviationAngle) {
			distance = (left + right) / 2.0;
			// isLine = false;
			return distance;
		}
		// compute radius

		double r = 0.5 * (left + right) / newangle;
		radius = r;

		InputHandler.stepForward("ra." + radius);

		// compute distance with the center pos
		distance = 2.0 * (r) * Math.sin(newangle / 2.0);

		return (distance);
	}

	static Vector calculatePoint(Vector v, double left, double right) {
		left = convertTachoCountToDistance(left);
		right = convertTachoCountToDistance(right);
		convertNegativeNullToNull(left, right);

		computeAngle(left, right);

		InputHandler.stepForward("angle." + radToDegree(newangle));

		if (Math.abs(newangle) < deviationAngle) {

		}
		calculateSekante(left, right);
		InputHandler.stepForward("sekante." + distance);
	
		if (InputHandler.debugging) {
			InputHandler.write(new String[] { "d " + distance, "a " + newangle, "r " + radius, "" + left, "" + right,
					"" + (right / newangle), "" + (left / newangle) });
		}
		v = v.add(new Vector(distance * Math.cos(angle + newangle / 2), distance * Math.sin(angle + newangle / 2)));
		angle += newangle;
		return v;
	}

	/**
	 * // convert the degree Tachocount in a distance(meter)
	 * 
	 * @param TachoCount
	 * @return tachocount in meter
	 */
	static public double convertTachoCountToDistance(double TachoCount) {
		return TachoCount = (TachoCount * 0.5 * wheeldiameter * Math.PI / 180);
	}

	static public void convertNegativeNullToNull(double left, double right) {
		if (Math.abs(left) == 0.0) {
			left = 0.0;
		}
		if (Math.abs(right) == 0.0) {
			right = 0.0;
		}
	}

	static public double radToDegree(double a) {
		return a = a * 180 / Math.PI;
	}

}
