package GeometryHelper;

import java.util.ArrayList;

public class Path {

	public ArrayList<Vector> points= new ArrayList<>();
	public Vector origin;
	
	public void addPoint(double x, double y) {
		points.add(new Vector(x, y));
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
