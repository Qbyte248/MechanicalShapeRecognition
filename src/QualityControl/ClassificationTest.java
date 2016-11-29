package QualityControl;

import Classification.*;

public class ClassificationTest {

	public static void main(String[] args) {
		
		ShapeClassifier classifier = new ShapePotentialClassifier();
		
		String description = classifier.classify(ShapeTemplates.get("A"));
		System.out.println("description: " + description);
	}

}
