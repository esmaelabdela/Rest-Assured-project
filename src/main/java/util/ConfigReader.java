package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;



public class ConfigReader {
	
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            FileInputStream input = new FileInputStream("src/main/java/util/config.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
        
    }
    
    
}

	
	
	
	
	
	
	
	

