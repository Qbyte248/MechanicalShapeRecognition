package Test;

import lejos.nxt.*;

public class TestMain {

	public static void main(String[] args) {

		System.out.println("Hello World");
//testing
		while (true) {
			// get tacho count
			int tachoCount = Motor.A.getTachoCount();
			// testing 
			// show tacho count on screen
			LCD.drawString("Tacho count: " + tachoCount, 1, 1);

		}
	}

}
