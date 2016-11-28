package RobotInput;


import GeometryHelper.Vector;

public class Configuration {

	static double angle;	// in rad
	static double newangle;	
	final static double wheeldiameter =0.056; // m 
	final static double axis = 0.14;// in meter
	final static double deviationAngle=0.08; // use to ignore small changes in angle	
	static Vector center =new Vector(axis/2, 0);	// center.y should stay 0
	static double radius=0;
	static double distance=0;
	Vector rotationCenter ;
	private static double h=0.0;
			
	void setCenter(double x,double y){
		center.setXY(x, y);	
	}
	//a>b
	private static double  computeAngle(double a, double b){
		//radius=(achse *a*0.5+achse *b*0.5)/(a-b);
		
		//angle=(a-b)/1; // for testing 
		 newangle=(b-a)/axis;
		return newangle;		
	}
	/**
	 * calculate the sekante or if the angle is small , a distance
	 * @param left
	 * @param right
	 * @return
	 */
	private static  double calculateSekante(double left,double right){
	/*	else{
			distance=(left+right)*(axis+center.x*axis)/((left-right));
		}*/
		// if there was only a small change in angle , should be unnecessary
		if(Math.abs(newangle)<deviationAngle){
			InputHandler.stepForward("fuck."+newangle);
			distance=left;
			return distance;
		}
		// compute radius
		if(left>right){
			h=left;
		}else{
			h=right;
		}
			radius=h/newangle;
		// compute distance with the center pos	
		distance= 2*(radius-center.x)*Math.sin(newangle/2);	
		return distance ;
	}
	static Vector calculatePoint(Vector v,double left ,double right ){
		
		left=convertTachoCountToDistance(left);
		right=convertTachoCountToDistance(right);
		computeAngle(left, right);
		InputHandler.stepForward("angle."+newangle);
		
		angle+=newangle;
		if(Math.abs(newangle)<deviationAngle){
			InputHandler.stepForward("small."+newangle);
			return null;
		}
		calculateSekante(left, right);
		InputHandler.stepForward("sekante."+distance);
		Vector b= new Vector(distance*Math.cos(angle),distance*Math.sin(angle));
		//v.add(new Vector(distance*Math.cos(angle),distance*Math.sin(angle)));
		v=v.add(b);
		
		
		return v;
	}
	/**
	 * // convert the degree Tachocount in a distance(meter)
	 * @param TachoCount
	 * @return tachocount in meter
	 */
	static public double convertTachoCountToDistance(double TachoCount){
		TachoCount=(TachoCount *0.5 * wheeldiameter* Math.PI/180);
		return TachoCount;
	}
	
	
	
}
