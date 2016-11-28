package main;

import Classification.ShapePotentialClassifier;
import RobotInput.InputHandler;
import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Main {

	
	
	public static void main(String[] args) {
		// start of the programm
		InputHandler inputhandler=new InputHandler();
		ShapePotentialClassifier cl= new ShapePotentialClassifier();
		inputhandler.setup();
		// calibrated
		
		//
		while(inputhandler.running){
			// generate data
			inputhandler.generatePoint();
			inputhandler.checkButton();
			try {
				Thread.sleep(200);	// so that it doesn't permanent try to generate new data
			} catch (InterruptedException e) {}
			
		}// finished generate data
		//here optimize the data 
		LCD.clear();
		LCD.drawString("end"+inputhandler.shape.paths.get(0).points.size(), 1, 1);
		LCD.drawString("x"+inputhandler.lastDataPoint.x, 1, 2);
		LCD.drawString("y"+inputhandler.lastDataPoint.y, 1, 3);
		Button.ENTER.waitForPressAndRelease();
		// now compare 
			
		//ouput
		
	}
	

}
