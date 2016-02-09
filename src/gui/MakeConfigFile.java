package gui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class MakeConfigFile {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Properties config = new Properties();
		config.setProperty("property1", "value 1");
		config.setProperty("targetClasses", "java.lang.String java.util.ArrayList");
		config.store(new FileOutputStream("input_output/aoidhawid"), "");	
	}
}
