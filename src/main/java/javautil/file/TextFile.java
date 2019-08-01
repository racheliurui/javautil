/**
 * 
 */
package javautil.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
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

	public static boolean FileExist(String filepath) {
		File f = new File(filepath);
		if (f.exists() && !f.isDirectory()) {
			return true;
		} else
			return false;

	}

	public static void ReadFileAndWriteUsingUTF8(String inputFile, String outputFile) {

		try {
			Reader reader = new InputStreamReader(new FileInputStream(inputFile), "UTF-8");
			BufferedReader fin = new BufferedReader(reader);
			Writer writer = new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8");
			BufferedWriter fout = new BufferedWriter(writer);
			String s;
			while ((s = fin.readLine()) != null) {
				fout.write(s);
				fout.newLine();
			}

			// Remember to call close.
			// calling close on a BufferedReader/BufferedWriter
			// will automatically call close on its underlying stream
			fin.close();
			fout.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
