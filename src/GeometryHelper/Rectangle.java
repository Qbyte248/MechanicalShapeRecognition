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
	
	public Rectangle copy() {
		return new Rectangle(origin.x, origin.y, size.x, size.y);
	}
	
	public Vector center() {
		return new Vector(origin.x + size.x / 2, origin.y + size.y / 2);
	}
	
	public void scaleBy(double scaleFactor, Vector center) {
		size.multiplyInPlace(scaleFactor);
		
		origin.subtractInPlace(center);
		origin.multiplyInPlace(scaleFactor);
		origin.addInPlace(center);
	}
}
