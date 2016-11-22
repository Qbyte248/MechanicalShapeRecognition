package Classification;

import GeometryHelper.Shape;

public interface ShapeClassifier {
	
	/**
	 * @param shape which will be classified
	 * @return 	a String/description which best matches the shape;
	 * 			Returns `null` if it could not classify the shape
	 */
	String classify(Shape shape);
}
