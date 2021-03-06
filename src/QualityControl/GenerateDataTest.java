package QualityControl;

import GeometryHelper.Shape;
import lejos.nxt.LCD;

import java.io.*;

public class GenerateDataTest {

	public static void createFile(Shape s) {
	
		int count = 0;
		FileOutputStream fileStream = null;
		try {
			fileStream = new FileOutputStream(new File("Test.txt"));
		} catch (Exception e) {
			System.out.println("Can't make a file");
			System.exit(1);
		}

		DataOutputStream dataStream = new DataOutputStream(fileStream);
			try {
				//dataStream.writeBytes(s.description(null));
				dataStream.writeBytes(s.description(null,0,30));
				fileStream.flush();
				count++;
			} catch (IOException e) {
				System.out.println("Can't write to the file");
				System.exit(1);
			}
		

		try {
			fileStream.close();
		} catch (IOException e) {
			LCD.drawString("Can't save the file", 0, 1);
			System.exit(1);
		}
	}
	public static void createFile( String s) {
		
		int count = 0;
		FileOutputStream fileStream = null;
		try {
			fileStream = new FileOutputStream(new File("Fail.txt"));
		} catch (Exception e) {
			System.out.println("Can't make a file");
			System.exit(1);
		}

		DataOutputStream dataStream = new DataOutputStream(fileStream);
			try {
			
				dataStream.writeBytes(s);
				fileStream.flush();
				count++;
			} catch (IOException e) {
				System.out.println("Can't write to the file");
				System.exit(1);
			}
		

		try {
			fileStream.close();
		} catch (IOException e) {
			LCD.drawString("Can't save the file", 0, 1);
			System.exit(1);
		}
	}
	 

}
