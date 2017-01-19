package RobotInput;

import GeometryHelper.Path;
import GeometryHelper.Shape;
import GeometryHelper.Vector;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.RegulatedMotor;

public class InputHandler {
	public static boolean running=true;
	public static final boolean debugging=false;
	
	public static Shape shape=new Shape();
	static RegulatedMotor leftMotor = Motor.A;
	static RegulatedMotor rightMotor = Motor.C;	
	static double leftTachocount=0;
	static double rightTachocount=0;
	
	static double oldleftTachocount=0;
	static double oldrightTachocount=0;
	static double differenceL=0;
	static double differenceR=0;
	
	public static Vector lastDataPoint=new Vector(0,0);
	static Vector newDataPoint;
	static int countPath=-1;
	
	boolean leftpressed=false;
	boolean rightpressed=false;
	
	static int step=0; 	//debug
	public InputHandler(){
		
	}
	
	public  void setup(){
		//LCD writing
		LCD.drawString("set start pos", 1, 1);
		LCD.drawString("Orange to start", 1, 2);
		LCD.drawString("press back anytime to reset ", 1, 3);
		LCD.drawString("if you finished press forward ", 1, 4);
		
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		LCD.drawString("orange for end ", 1, 1);
		LCD.drawString("generate data ", 1, 2);
	
		running=true;
		shape=new Shape();
		lastDataPoint=new Vector(0,0);
		countPath=-1;
		generatePath();
		addPointToPath(lastDataPoint, countPath);
	}
	/**
	 * debugging method ,writes the string s on the LCD and wait until left is pressed, only works if debug mode is on
	 * 
	 * @param s
	 */
	public static void stepForward(String s){
		if(debugging){
		LCD.clear();
		LCD.drawString(s, 1, 1);
		LCD.drawString(": step "+step, 1, 2);
		step++;
		Button.LEFT.waitForPressAndRelease();
		}
		
	}
	/**
	 * checks if any button is pressed
	 */
	public void checkButton(){
		checkIfFinish();
		checkIfCancelled();
		//checkIDebug();
		checkLeft();
		checkRight();
	}
	
	private  void checkIfFinish(){
		if(Button.ENTER.isDown()){
			running=false;
		}
		
	}
	/**
	 * if left is pressed and wasn't pressed before ,turn the robot by 90°
	 */
	private void checkLeft(){
		if(Button.LEFT.isDown()&&!leftpressed){
			leftpressed=true;
			Configuration.angle+=Math.PI/2.0;
			System.out.println("turned by 90°");
			System.out.println("angle now "+Configuration.angle);
		}else if(Button.LEFT.isUp()&&leftpressed){
			leftpressed=false;
		}
		
	}
	/**
	 * 
	 * if right is pressed and wasn't pressed before ,turn the robot by -90°
	 */
	 
	private void checkRight(){
		if(Button.RIGHT.isDown()&&!rightpressed){
			rightpressed=true;
			Configuration.angle+= -Math.PI/2.0;
			System.out.println("turned by -90°");
			System.out.println("angle now "+Configuration.angle);
		}else if(Button.RIGHT.isUp()&&rightpressed){
			rightpressed=false;
		}
	}
	/**
	 * if escape is pressed the system goes back to the start
	 */
	private  void checkIfCancelled(){
		if(Button.ESCAPE.isDown()){
			LCD.clear();
			setup();
		}	
	}
	
	public static  void write(String[] s ){
		LCD.clear();
		for (int i=0;i<s.length;i++){
			LCD.drawString(s[i], 1, i+1);
		}
		LCD.refresh();
		
	}
	private  void generatePath(){
		shape.paths.add(new Path());
		countPath++;
	}
	// for training and debug , so you can add path 
	private  void addPath(Path p){
		
		shape.paths.add(p);
		countPath++;
	}
	/**
	 * add the Point (as a vector ) to the specific path with index i
	 * @param v
	 * @param i index of paths (in shape)
	 */
	public  void generatePoint(){
		stepForward("start");	// debug 
		
		// get the current tachocount
		leftTachocount=leftMotor.getTachoCount();
		rightTachocount=rightMotor.getTachoCount();
		
		// calculate the difference 
		differenceL=leftTachocount-oldleftTachocount;
		differenceR=rightTachocount-oldrightTachocount;
		
		// for debug 
		stepForward("left"+differenceL);
		stepForward("right"+differenceR);
		// calculate the new position 
		//! the -1 is in it because the motors are turning backwards in our robot
		newDataPoint=Configuration.calculatePoint(lastDataPoint, -1.*differenceL, -1.*differenceR);
		if(newDataPoint!=null){
			
			// if the new data point is the same as the old one 
			// don't save it
			if((newDataPoint.x!=lastDataPoint.x||newDataPoint.y!=lastDataPoint.y)){
				lastDataPoint=newDataPoint;
				addPointToPath(lastDataPoint, countPath);
			
				stepForward(""+newDataPoint.x);
				stepForward(""+newDataPoint.y);
				oldleftTachocount=leftTachocount;
				oldrightTachocount=rightTachocount;
			}
		}
	}
	
	void addPointToPath(Vector v,int i ){
		shape.paths.get(i).points.add(v);	
	}
}
