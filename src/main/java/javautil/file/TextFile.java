/**
 * 
 */
package javautil.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author RachelRuiLiu
 *
 */
public class TextFile {
	
	public static String readXMLFileAsString(String filePath) throws Exception {	
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
	    StringBuilder builder = new StringBuilder();
	    String currentLine = reader.readLine();
	    while (currentLine != null) {
	        builder.append(currentLine);
	        builder.append(System.getProperty("line.separator"));
	        currentLine = reader.readLine();
	    }
	    reader.close();
	    
		return builder.toString();
	}
	
	public static void writeStringToFile(String filepath, String filecontent) throws Exception {

	    Path path = Paths.get(filepath);
	    byte[] strToBytes = filecontent.getBytes();
	 
	    Files.write(path, strToBytes);
	}
	
	
	public static boolean FileExist(String filepath)  {	
	    File f = new File(filepath);
	    if(f.exists() && !f.isDirectory()) { 
	        return true;
	    }else
	    	return false;
	    
	}
	
}
