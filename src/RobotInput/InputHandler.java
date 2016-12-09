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
	
	static int step=0; 	//debug
	public InputHandler(){
		
	}
	
	public  void setup(){
		// LCD kann max 15 Zeichen pro Zeile
		LCD.drawString("set start pos", 1, 1);
		LCD.drawString("Orange to start", 1, 2);
		LCD.drawString("press back anytime to reset ", 1, 3);
		LCD.drawString("if you finished press forward ", 1, 4);
		//not implemented
		//LCD.drawString("press right  to finished current path and make new path ", 1, 1);		
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
	public static void stepForward(String s){
		if(debugging){
		LCD.clear();
		LCD.drawString(s, 1, 1);
		LCD.drawString(": step "+step, 1, 2);
		step++;
		Button.LEFT.waitForPressAndRelease();
		}
		
	}
	public void checkButton(){
		checkIfFinish();
		checkIfCancelled();
		checkIDebug();
		checkLeft();
		checkRight();
	}
	private  void checkIfFinish(){
		if(Button.ENTER.isDown()){
			running=false;
		}
		
	}
	private void checkLeft(){
		if(Button.LEFT.isDown()){
			Configuration.angle+=Math.PI/2.0;
			System.out.println("turned by 90°");
			System.out.println("angle now "+Configuration.angle);
		}
	}
	private void checkRight(){
		if(Button.RIGHT.isDown()){
			Configuration.angle+= -Math.PI/2.0;
			System.out.println("turned by -90°");
			System.out.println("angle now "+Configuration.angle);
		}
	}
	private  void checkIfCancelled(){
		if(Button.ESCAPE.isDown()){
		setup();
		}	
	}
	private  void checkIDebug(){
		if(Button.RIGHT.isDown()){
			int s=shape.paths.get(countPath).points.size();
			LCD.drawString(" "+s, 3, 1);
			LCD.refresh();
		}
	}
	private  void write(String s){
		LCD.clear();
		LCD.drawString(s, 1, 1);
		LCD.refresh();
		
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
	// for training
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
		stepForward("start");
		leftTachocount=leftMotor.getTachoCount();
		rightTachocount=rightMotor.getTachoCount();
		
		differenceL=leftTachocount-oldleftTachocount;
		differenceR=rightTachocount-oldrightTachocount;
		
		
		stepForward("left"+differenceL);
		stepForward("right"+differenceR);
		
		newDataPoint=Configuration.calculatePoint(lastDataPoint, -1.*differenceL, -1.*differenceR);
		if(newDataPoint!=null){
			// TODO :commit in 
			//write("size "+shape.paths.get(countPath).points.size());
			if((newDataPoint.x!=lastDataPoint.x||newDataPoint.y!=lastDataPoint.y)){
				lastDataPoint=newDataPoint;
				addPointToPath(lastDataPoint, countPath);
				//stepForward("x:"+lastDataPoint.x+" // y "+lastDataPoint.y);
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
