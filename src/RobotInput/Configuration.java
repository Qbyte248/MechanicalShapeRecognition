package RobotInput;


import GeometryHelper.Vector;

public class Configuration {

	static double angle;
	final static double wheeldiameter =0.56; // m 
	final static double achse = 0.14;// m
	static Vector center =new Vector(achse/2, 0);
	
			
	void setCenter(double x,double y){
		center.setXY(x, y);
		
		
	}
	
}
