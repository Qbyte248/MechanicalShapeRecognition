package Classification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import GeometryHelper.Path;
import GeometryHelper.Shape;
import GeometryHelper.Vector;

@SuppressWarnings("deprecation")
public class ShapeTemplates {
	
	public static HashMap<String, Shape> shapes = new HashMap<>();
	
	public static void setup(int numberOfAdditionalMidpoints) {
		// TODO Add Shapes to "shapes"
		
		// A
		Shape A_Shape = new Shape();
		
		Path A_Diagonal = new Path();
		A_Diagonal.addPoint(-0.5, 0);
		A_Diagonal.addPoint(0, 2);
		A_Shape.paths.add(A_Diagonal);
		
		A_Diagonal = new Path();
		A_Diagonal.addPoint(0.5, 0);
		A_Diagonal.addPoint(0, 2);
		A_Shape.paths.add(A_Diagonal);
		
		Path A_Horizontal = new Path();
		A_Horizontal.addPoint(-0.25, 1);
		A_Horizontal.addPoint(0.25, 1);
		A_Shape.paths.add(A_Horizontal);
		
		shapes.put("A", A_Shape);
		
		// 0
		Shape shape_0 = new Shape();
		Path path_0 = new Path();
		path_0.addPoint(1, 2);
		path_0.addPoint(0, 2);
		path_0.addPoint(0, 0);
		path_0.addPoint(1, 0);
		path_0.addPoint(1, 2);
		shape_0.paths.add(path_0);
		
		shapes.put("0", shape_0);
		
		// 1
		Shape shape_1 = new Shape();
		Path path_1 = new Path();
		path_1.addPoint(0, 0);
		path_1.addPoint(0, 2);
		path_1.addPoint(-0.25, 1.5);
		shape_1.paths.add(path_1);

		shapes.put("1", shape_1);

		// 2
		Shape shape_2 = new Shape();
		Path path_2 = new Path();
		path_2.addPoint(0, 2);
		path_2.addPoint(1, 2);
		path_2.addPoint(1, 1);
		path_2.addPoint(0, 1);
		path_2.addPoint(0, 0);
		path_2.addPoint(1, 0);
		shape_2.paths.add(path_2);

		shapes.put("2", shape_2);
		
		// 3
		Shape shape_3 = new Shape();
		Path path_3 = new Path();
		path_3.addPoint(0, 2);
		path_3.addPoint(1, 2);
		path_3.addPoint(1, 0);
		path_3.addPoint(0, 0);
		shape_3.paths.add(path_3);
		
		path_3 = new Path();
		path_3.addPoint(0, 1);
		path_3.addPoint(1, 1);
		shape_3.paths.add(path_3);
		
		shapes.put("3", shape_3);
		
		// 4
		Shape shape_4 = new Shape();
		Path path_4 = new Path();
		path_4.addPoint(0, 2);
		path_4.addPoint(0, 1);
		path_4.addPoint(1, 1);
		shape_4.paths.add(path_4);
		
		path_4 = new Path();
		path_4.addPoint(1, 2);
		path_4.addPoint(1, 0);
		shape_4.paths.add(path_4);
		
		shapes.put("4", shape_4);
		
		// 5
		Shape shape_5 = new Shape();
		Path path_5 = new Path();
		// mirror the 2 Shape
		for (Vector point : path_2.points) {
			path_5.addPoint(-point.x, point.y);
		}
		shape_5.paths.add(path_5);
		
		shapes.put("5", shape_5);
		
		// 6
		Shape shape_6 = new Shape();
		Path path_6 = new Path();
		path_6.addPoint(0, 2);
		path_6.addPoint(0, 0);
		path_6.addPoint(1, 0);
		path_6.addPoint(1, 1);
		path_6.addPoint(0, 1);
		shape_6.paths.add(path_6);
		
		shapes.put("6", shape_6);
		
		// 7
		Shape shape_7 = new Shape();
		Path path_7 = new Path();
		path_7.addPoint(0, 2);
		path_7.addPoint(1, 2);
		path_7.addPoint(1, 0);
		shape_7.paths.add(path_7);
		
		shapes.put("7", shape_7);
		
		// 8
		Shape shape_8 = new Shape();
		Path path_8 = new Path();
		path_8.addPoint(0, 1);
		path_8.addPoint(1, 1);
		path_8.addPoint(1, 2);
		path_8.addPoint(0, 2);
		path_8.addPoint(0, 0);
		path_8.addPoint(1, 0);
		path_8.addPoint(1, 1);
		shape_8.paths.add(path_8);
		
		shapes.put("8", shape_8);
		
		// 9
		Shape shape_9 = new Shape();
		Path path_9 = new Path();
		path_9.addPoint(1, 1);
		path_9.addPoint(0, 1);
		path_9.addPoint(0, 2);
		path_9.addPoint(1, 2);
		path_9.addPoint(1, 0);
		shape_9.paths.add(path_9);
		
		shapes.put("9", shape_9);
		
		
		if (numberOfAdditionalMidpoints <= 0) {
			return;
		}
		
		// add one point in between two connected points
		for (Shape shape : shapes.values()) {
			for (Path path : shape.paths) {
				ArrayList<Vector> newPoints = new ArrayList<>();
				if (path.points.size() < 2) {
					continue;
				}
				for (int i = 1; i < path.points.size(); i++) {
					// add first point
					Vector firstPoint = path.points.get(i-1);
					newPoints.add(firstPoint);
					
					Vector dv = path.points.get(i).subtract(firstPoint).multiply(1 / ((double)numberOfAdditionalMidpoints));
					Vector currentMidpoint = firstPoint;
					for (int j = 0; j < numberOfAdditionalMidpoints; j++) {
						currentMidpoint = currentMidpoint.add(dv);
						// add midpoint
						newPoints.add(currentMidpoint);
					}
				}
				newPoints.add(path.points.get(path.points.size()-1));
				
				path.points = newPoints;
			}
		}
	}
	
	public static Shape get(String shapeDescription) {
		return shapes.get(shapeDescription);
	}
	
	public static Set<String> getShapeDescriptions() {
		return shapes.keySet();
	}
}
