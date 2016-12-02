package Classification;

import java.util.Enumeration;
import java.util.HashMap;

import GeometryHelper.Path;
import GeometryHelper.Rectangle;
import GeometryHelper.Shape;
import GeometryHelper.Vector;

@SuppressWarnings("deprecation")
public class ShapePotentialClassifier implements ShapeClassifier {
	
	public ShapePotentialClassifier() {}
	
	/**
	 * @param shape1
	 * @param shape2
	 * @return number which is smaller the closer the shapes are
	 */
	public static double classificationValue(Shape shape1, Shape shape2) {
		double potential = 0.0;
		// potential of shape1 in relation to shape2
		for (Path path1 : shape1.paths) {
			for (Vector point1 : path1.points) {
				potential += pointPotential(shape1.origin.add(path1.origin.add(point1)), shape2);
			}
		}
		// potential of shape2 in relation to shape1
		for (Path path2 : shape2.paths) {
			for (Vector point2 : path2.points) {
				potential += pointPotential(shape2.origin.add(path2.origin.add(point2)), shape1);
			}
		}
		
		return potential;
	}
	
	/**
	 * @param point in the real coordinate system
	 * @param shape in the real coordinate system
	 * @return "potential" to point
	 */
	public static double pointPotential(Vector point, Shape shape) {
		
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
		
		return potential;
	}
	
	@Override
	public String classify(Shape shape) {
		String shapeDescription = null;
		
		double lowestClassificationValue = Double.MAX_VALUE;
		
		for (String description : ShapeTemplates.getShapeDescriptions()) {
		      
		      // copy of template
		      Shape templateShape = ShapeTemplates.get(description).copy();
		      
		      // TODO: Rectangle.getRectangle; translate and scale; compare
		      Rectangle templateRect = ShapeTemplates.get(description).getRectangle();
		      
		      Rectangle shapeRect = shape.getRectangleRecalculate();
		      
		      // Attention with templateRect.size.q = 0 where q element {x, y}
		      double widthScale = shapeRect.size.x / templateRect.size.x;
		      double heightScale = shapeRect.size.y / templateRect.size.y;
		      
		      // this is only approximate
		      double scaleFactor = (widthScale + heightScale) / 2;
		      
		      // scale and translate
		      templateShape.scaleBy(scaleFactor, templateRect.center());
		      templateShape.translateBy(shapeRect.center().subtract(templateRect.center()));
		      
		      // classification value
		      double classificationValue = ShapePotentialClassifier.classificationValue(shape, templateShape);
		      
		      // if classificationValue is lower than the lowest one
		      // use current shapeDescription as return value
		      if (classificationValue < lowestClassificationValue) {
		    	  lowestClassificationValue = classificationValue;
		    	  
		    	  shapeDescription = description;
		      }
		}
		
		return shapeDescription;
	}
}
