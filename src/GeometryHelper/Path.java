package GeometryHelper;

import java.util.ArrayList;

public class Path {

	public ArrayList<Vector> points= new ArrayList<>();
	public Vector origin;
	
	public void addPoint(double x, double y) {
		points.add(new Vector(x, y));
	}
}
