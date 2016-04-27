package br.unifor.iadapter.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertieUtil {
	
	public static String getProperty(String name){
		Properties prop = new Properties();
		InputStream input = null;

		String property;
		try {

			input = new FileInputStream("workload.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
		
			property = prop.getProperty(name);
			return property;
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
