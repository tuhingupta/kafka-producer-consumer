package io.tuhin.util.simplefilegenerator;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author  Tuhin Gupta
 * @since September 2016
 */

public class FileGenerator {

	public static void main(String[] args) throws IOException {
	
		FileWriter fw = new FileWriter("simple.txt");
		 
		for (int i = 0; i < 10000; i++) {
			fw.write(i+"\n");
		}
	 
		fw.close();
	}
	
	
}
