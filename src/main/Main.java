package main;

import java.io.StringWriter;
import java.io.Writer;

import Classification.ShapePotentialClassifier;
import Classification.ShapeTemplates;
import QualityControl.GenerateDataTest;
import RobotInput.InputHandler;
import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Main {
	//for debug use , if true a text file with the coordinates are created
	static boolean createTExt = true;

	public static void main(String[] args) {

		// start of the programm
		InputHandler inputhandler = new InputHandler();
		ShapePotentialClassifier cl = new ShapePotentialClassifier();
		ShapeTemplates.setup(2);
		inputhandler.stepForward("fg");
		inputhandler.setup();
		// calibrated

		//
		while (inputhandler.running) {
			// generate data
			inputhandler.generatePoint();
			inputhandler.checkButton();
			try {
				Thread.sleep(200); // so that it doesn't permanent try to
									// generate new data
			} catch (InterruptedException e) {
			}

		}
		LCD.clear();
		try {
			if (createTExt) {
				System.out.println("create data");
				GenerateDataTest.createFile(inputhandler.shape);
				System.out.println("finished data");
				// GenerateDataTest.readFile(inputhandler.shape);
			}
		} catch (Exception e) {
			// if the text file failed , try to write the error log to the text file
			GenerateDataTest.createFile(e.toString());
		}
		
		// now compare and write the output
		
		System.out.println("it's a " + cl.classify(inputhandler.shape));
		
		// when you are finished press enter to close 
		Button.ENTER.waitForPressAndRelease();

	

	}


}
