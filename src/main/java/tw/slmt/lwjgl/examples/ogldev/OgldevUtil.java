package tw.slmt.lwjgl.examples.ogldev;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OgldevUtil {
	
	public static String RESOURCE_DIR_PATH = "res/ogldev_shaders";
	
	public static String readFile(String filename) {
		BufferedReader reader = null;

		try {
			// Open the file
			reader = new BufferedReader(new FileReader(filename));
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
			
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Close the file
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
