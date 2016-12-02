package GeometryHelper;

public class Vector {
	public double x;
	public double y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public String description(int startIndex, int endIndex) {
		String xS = x + "";
		String yS = y + "";
		return "(" + xS.substring(startIndex, endIndex) + ", " + yS.substring(startIndex, endIndex) + ")";
	}
	
	public Vector copy() {
		return new Vector(x, y);
	}
	
	/** Euclidian norm (||v|| == |v|^2) **/
	public double norm() {
		return x * x + y * y;
	}
	
	public double length() {
		return Math.sqrt(x * x + y * y);
	}
	
	// addition
	
	public Vector add(Vector v) {
		return new Vector(x + v.x, y + v.y);
	}
	
	public void addInPlace(Vector v) {
		x += v.x;
		y += v.y;
	}
	
	
	
	// subtraction
	
	public Vector subtract(Vector v) {
		return new Vector(x - v.x, y - v.y);
	}
	
	public void subtractInPlace(Vector v) {
		x -= v.x;
		y -= v.y;
	}
	
	
	// multiplication
	
	public Vector multiply(double s) {
		return new Vector(x * s, y * s);
	}
	
	public void multiplyInPlace(double s) {
		x *= s;
		y *= s;
	}
	
	public double scalarProduct(Vector v) {
		return x * v.x + y * v.y;
	}
	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
