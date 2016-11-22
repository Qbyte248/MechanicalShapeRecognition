package Classification;

import GeometryHelper.Path;
import GeometryHelper.Shape;
import GeometryHelper.Vector;

public class ShapePotentialClassifier implements ShapeClassifier {
	
	/**
	 * @param shape1
	 * @param shape2
	 * @return number which is smaller the closer the shapes are
	 */
	private static double classificationValue(Shape shape1, Shape shape2) {
		double potential = 0.0;
		for (Path path1 : shape1.paths) {
			for (Vector point1 : path1.points) {
				potential += pointPotential(point1, shape2);
			}
		}
		for (Path path2 : shape2.paths) {
			for (Vector point2 : path2.points) {
				potential += pointPotential(point2, shape1);
			}
		}
		
		return potential;
	}
	
	/**
	 * @param point in the real coordinate system
	 * @param shape in the real coordinate system
	 * @return "potential" to point
	 */
	private static double pointPotential(Vector point, Shape shape) {
		
		double potential = Double.MAX_VALUE;
		
		for (Path path : shape.paths) {
			if (path.points.size() == 0) {
				continue;
			}
			Vector p1 = null;
			Vector p2 = null;
			
			for (Vector p : path.points) {
				Vector shapePoint = path.origin.add(p);
				shapePoint.addInPlace(shape.origin);
				
				// potential between two points
				potential = Math.min(potential, point.subtract(shapePoint).norm());
				
				p1 = p2;
				p2 = shapePoint;
				
				if (p1 == null) {
					continue;
				}
				// p1 != null && p2 != null
				// define line segment between p1, p2:
				// g(t) = p1 + (p2 - p1) * t; with v = p2 - p1
				
				Vector v = p2.subtract(p1);
				double t0 = (point.subtract(p1).scalarProduct(v)) / v.norm();
				
				// redefine v to g(t0)
				v.multiplyInPlace(t0);
				v.addInPlace(p1);
				
				// potential between point and line segment from p1 to p2
				potential = Math.min(potential, v.subtract(point).norm());
			}
		}
		
		return 0;
	}
	
	@Override
	public String classify(Shape shape) {
		// TODO Auto-generated method stub
		return null;
	}
}
