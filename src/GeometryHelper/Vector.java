package GeometryHelper;

public class Vector {
	double x;
	double y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/** Euclidian norm (||v|| == |v|^2) **/
	double norm() {
		return x * x + y * y;
	}
	
	double length() {
		return Math.sqrt(x * x + y * y);
	}
	
	// addition
	
	Vector add(Vector v) {
		return new Vector(x + v.x, y + v.y);
	}
	
	void addInPlace(Vector v) {
		x += v.x;
		y += v.y;
	}
	
	
	
	// subtraction
	
	Vector subtract(Vector v) {
		return new Vector(x - v.x, y - v.y);
	}
	
	void subtractInPlace(Vector v) {
		x -= v.x;
		y -= v.y;
	}
	
	
	// multiplication
	
	Vector multiply(double s) {
		return new Vector(x * s, y * s);
	}
	
	void multiplyInPlace(double s) {
		x *= s;
		y *= s;
	}
	
	double scalarProduct(Vector v) {
		return x * v.x + y * v.y;
	}
	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
