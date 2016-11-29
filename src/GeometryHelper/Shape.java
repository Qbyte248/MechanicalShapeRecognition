package GeometryHelper;

import java.util.ArrayList;

public class Shape {
	public ArrayList<Path> paths= new ArrayList<>();
	public Vector origin;
	
	// possible optimization to set all origins to (0, 0) and add a (boolean) flag 
	
	private Rectangle rectangle;
	
	/**
	 * @return Smallest rectangle which contains `this`
	 * @note This method only recalculates rectangle if necessary (this.rectangle == null)
	 */
	public Rectangle getRectangle() {
		if (this.rectangle != null) {
			return this.rectangle;
		} else {
			return this.getRectangleRecalculate();
		}
	}
	
	/**
	 * @return Smallest rectangle which contains `this`
	 * @note This method always recalculates the rectangle O(n)
	 */
	public Rectangle getRectangleRecalculate() {
		Vector lowerLeft = new Vector(Double.MAX_VALUE, Double.MAX_VALUE);
		Vector upperRight = new Vector(-Double.MAX_VALUE, -Double.MAX_VALUE);
		
		boolean hasPoints = false;
		for (Path path : this.paths) {
			hasPoints = hasPoints || !path.points.isEmpty();
			for (Vector point : path.points) {
				Vector v = this.origin.add(path.origin.add(point));
				
				// assign lower left and upper right
				lowerLeft.x = Math.min(lowerLeft.x, v.x);
				lowerLeft.y = Math.min(lowerLeft.y, v.y);
				upperRight.x = Math.max(upperRight.x, v.x);
				upperRight.y = Math.max(upperRight.y, v.y);
			}
		}
		
		if (!hasPoints) {
			throw new IllegalArgumentException("Shape has no points => no smallest box");
		}
		
		this.rectangle = new Rectangle(lowerLeft, upperRight.subtract(lowerLeft));
		return this.rectangle;
	}
	
	/**
	 * @param scaleFactor
	 * @param center from which it is scaled (this/Shape coordinate system)
	 */
	public void scaleBy(double scaleFactor, Vector center) {
		Vector pathCenter = center.subtract(origin);
		for (Path path : paths) {
			path.scaleBy(scaleFactor, pathCenter);
		}
		
		origin.subtractInPlace(center);
		origin.multiplyInPlace(scaleFactor);
		origin.addInPlace(center);
	}
	
	public void translateBy(Vector dv) {
		origin.addInPlace(dv);
	}
}
