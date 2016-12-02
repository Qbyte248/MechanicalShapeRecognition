package GeometryHelper;

import java.util.ArrayList;

public class Path {

	public ArrayList<Vector> points = new ArrayList<>();
	public Vector origin = new Vector(0, 0);
	
	/**
	 * @param separator
	 * @return \n separated vector descriptions if separator == null
	 */
	public String description(String separator, int startIndex, int endIndex) {
		if (separator == null) {
			separator = "\n";
		}
		String result = "";
		for (Vector point : points) {
			result = result + point.description(startIndex, endIndex) + separator;
		}
		return result;
	}
	
	public void addPoint(double x, double y) {
		points.add(new Vector(x, y));
	}
	
	public Path copy() {
		Path pathCopy = new Path();
		pathCopy.origin = this.origin.copy();
		
		for (Vector point : this.points) {
			pathCopy.points.add(point.copy());
		}
		
		return pathCopy;
	}
	
	/**
	 * @param scaleFactor
	 * @param center from which it is scaled (this/Path coordinate system)
	 */
	public void scaleBy(double scaleFactor, Vector center) {
		Vector newPoint;
		for(int i = 0; i < points.size(); i++) {
			newPoint = points.get(i);
			newPoint.addInPlace(origin);
			newPoint.subtractInPlace(center);
			newPoint.multiplyInPlace(scaleFactor);
			newPoint.addInPlace(center);
		}
		
		newPoint = origin;
		newPoint.subtractInPlace(center);
		newPoint.multiplyInPlace(scaleFactor);
		newPoint.addInPlace(center);
	}
	
	/**
	 * @param dv translation Vector
	 */
	public void translateBy(Vector dv) {
		origin.addInPlace(dv);
	}
}
