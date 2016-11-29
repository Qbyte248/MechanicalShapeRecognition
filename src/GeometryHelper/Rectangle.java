package GeometryHelper;

public class Rectangle {
	public Vector origin;
	public Vector size;
	
	public Rectangle(double x, double y, double width, double height) {
		origin = new Vector(x, y);
		size = new Vector(width, height);
	}
	
	public Rectangle(Vector origin, Vector size) {
		this.origin = origin;
		this.size = size;
	}
	
	public Vector center() {
		return new Vector(origin.x + size.x / 2, origin.y + size.y / 2);
	}
}