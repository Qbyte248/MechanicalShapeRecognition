package Classification;

import GeometryHelper.Shape;

public interface ShapeClassifier {
	
	/**
	 * @param shape which will be classified
	 * @return a String/description which best matches the shape
	 */
	String classify(Shape shape);
}
