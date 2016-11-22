package Classification;

import java.util.Hashtable;

import GeometryHelper.Shape;

public class ShapeTemplates {
	
	private static Hashtable<String, Shape> shapes = new Hashtable<>();
	
	public static void setup() {
		// TODO Add Shapes to "shapes"
	}
	
	public static Shape get(String shapeDescription) {
		return shapes.get(shapeDescription);
	}
}
