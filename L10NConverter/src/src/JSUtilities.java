// PACKAGE DECLARATIONS
package src;

// JAVA LIBRARIES
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import java.util.Enumeration;

import javax.swing.UIManager;

// THIRD PARTY LIBRARIES
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;



/**
 * Contains utility methods which are commonly used by classes
 * @author ploh
 * @author maxc
 *
 */
public class JSUtilities {

	public static final String UTF8_BOM = "\uFEFF"; // UTF BOM Marker Indicator
	

	/**
	 * Deletes a directory and all its contents
	 * @param dir - The path of the directory to delete
	 */
	public static void deleteFile(File file){

		if (!file.exists())
			return;
		else if (file.isDirectory()){
			for (File subFile : file.listFiles()){
				deleteFile(subFile);
			}
		}
		file.delete();	
	}

	/**
	 * Overrides all the applications font property values
	 * @param f - the font parameters for the application
	 */
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get (key);
			if (value != null && value instanceof javax.swing.plaf.FontUIResource)
				UIManager.put (key, f);
		}
	} 
	
	/**
	 * Copies a file from the given source to its destination
	 * @param source
	 * @param destination
	 * @return true - if completed successfully and false if otherwise
	 * @throws IOException 
	 */
	public static boolean copyFile(File source, File destination) throws IOException{

		// Using Apache Commons IO to copy files as it allows preserving of file metadata
		FileUtils.copyFile(source, destination, true);
		return true; 
	}

	/**
	 * Converts a File character set encoding and uni-code formatting to a desired format
	 * @param fileToConvert The file which to perform the format conversion 
	 * @throws Exception 
	 */
	public static  boolean convertFile(File tempDir, File fileToConvert) throws Exception{

		
		boolean firstLine = true;
		File tempFile = new File(tempDir, fileToConvert.getName());
		CharsetEncoder encoderType = Charset.forName("US-ASCII").newEncoder();

		FileInputStream fis = new FileInputStream(fileToConvert);

		// Reads the input file following the UTF8 Encoding Set
		BufferedReader r = new BufferedReader(new InputStreamReader(fis,"UTF8"));
		FileOutputStream fos = new FileOutputStream(tempFile);

		// Writes the output file following the UTF8 Encoding Set
		Writer w = new BufferedWriter(new OutputStreamWriter(fos, "UTF8"));

		// Line Iterator is used to keep track if EOF is reached
		LineIterator lineit = new LineIterator(r);
		
		while (lineit.hasNext()) {
          
			String s = lineit.nextLine();
          
			if (firstLine) {
				
				s = removeUTF8BOM(s);
				firstLine = false;
				
			}

			final StringBuilder result = new StringBuilder();

			/**
			 * Performs the rendering of the character to a Hex Representation Sets the
			 * encoder to the US-ASCII Format. If the character cannot be encoded it is
			 * transformed to its unicode representation.
			 */
			
				for (Character character : s.toCharArray()) {
					if (encoderType.canEncode(character)) {
						result.append(character);
					} else {
						result.append("\\u");
						result.append(Integer.toHexString(0x10000 | character).substring(1).toUpperCase());
					}
				}
			
			// Writes the converted line to the file
			w.write(result.toString());
			if(lineit.hasNext())
				w.write(System.getProperty("line.separator"));

			w.flush();
		}

		w.close();
		r.close();

		// Replace the original file with the converted file format
		JSUtilities.copyFile(tempFile, fileToConvert);
		return true;
	}
	
public static  boolean removeBOM(File tempDir, File fileToConvert) throws Exception{

		
		boolean firstLine = true;
		File tempFile = new File(tempDir, fileToConvert.getName());
		
		FileInputStream fis = new FileInputStream(fileToConvert);

		// Reads the input file following the UTF8 Encoding Set
		BufferedReader r = new BufferedReader(new InputStreamReader(fis,"UTF8"));
		FileOutputStream fos = new FileOutputStream(tempFile);

		// Writes the output file following the UTF8 Encoding Set
		Writer w = new BufferedWriter(new OutputStreamWriter(fos, "UTF8"));

		// Line Iterator is used to keep track if EOF is reached
		LineIterator lineit = new LineIterator(r);
		
		while (lineit.hasNext()) {
          
			String s = lineit.nextLine();
          
			if (firstLine) {
				
				s = removeUTF8BOM(s);
				firstLine = false;
				
			}

			final StringBuilder result = new StringBuilder();

			/**
			 * Add Original lines from the file to the result.
			 */
			
				for (Character character : s.toCharArray()) {
					
						result.append(character);
					 
				}
			
			// Writes the converted line to the file
			w.write(result.toString());
			if(lineit.hasNext())
				w.write(System.getProperty("line.separator"));

			w.flush();
		}

		w.close();
		r.close();

		// Replace the original file with the converted file format
		JSUtilities.copyFile(tempFile, fileToConvert);
		return true;
	}

public static  boolean addEmptyLine(File tempDir, File fileToConvert) throws Exception{

	
	boolean firstLine = true;
	File tempFile = new File(tempDir, fileToConvert.getName());
	
	FileInputStream fis = new FileInputStream(fileToConvert);

	// Reads the input file following the UTF8 Encoding Set
	BufferedReader r = new BufferedReader(new InputStreamReader(fis,"UTF8"));
	FileOutputStream fos = new FileOutputStream(tempFile);

	// Writes the output file following the UTF8 Encoding Set
	Writer w = new BufferedWriter(new OutputStreamWriter(fos, "UTF8"));

	// Line Iterator is used to keep track if EOF is reached
	LineIterator lineit = new LineIterator(r);
	
	while (lineit.hasNext()) {
      
		String s = lineit.nextLine();
      
		if (firstLine) {
			
			s = removeUTF8BOM(s);
			firstLine = false;
			
		}

		final StringBuilder result = new StringBuilder();

		/**
		 * Add Original lines from the file to the result.
		 */
		
			for (Character character : s.toCharArray()) {
				
					result.append(character);
				 
			}
			
		
		
		// Writes the converted line to the file
		w.write(result.toString());
		if(lineit.hasNext())
			w.write(System.getProperty("line.separator"));

		w.flush();
	}
    w.write("\n");
	w.close();
	r.close();

	// Replace the original file with the converted file format
	JSUtilities.copyFile(tempFile, fileToConvert);
	return true;
}

public static  boolean convertUTF8(File tempDir, File fileToConvert) throws Exception{

	
	
	File tempFile = new File(tempDir, fileToConvert.getName());
	CharsetEncoder encoderType = Charset.forName("US-ASCII").newEncoder();

	FileInputStream fis = new FileInputStream(fileToConvert);

	// Reads the input file following the UTF8 Encoding Set
	BufferedReader r = new BufferedReader(new InputStreamReader(fis,"UTF8"));
	FileOutputStream fos = new FileOutputStream(tempFile);

	// Writes the output file following the UTF8 Encoding Set
	Writer w = new BufferedWriter(new OutputStreamWriter(fos, "UTF8"));

	// Line Iterator is used to keep track if EOF is reached
	LineIterator lineit = new LineIterator(r);
	
	while (lineit.hasNext()) {
      
		String s = lineit.nextLine();
      
		
		final StringBuilder result = new StringBuilder();

		/**
		 * Performs the rendering of the character to a Hex Representation Sets the
		 * encoder to the US-ASCII Format. If the character cannot be encoded it is
		 * transformed to its unicode representation.
		 */
		
		for (Character character : s.toCharArray()) {
			if (encoderType.canEncode(character)) {
				result.append(character);
			} else {
				result.append("\\u");
				result.append(Integer.toHexString(0x10000 | character).substring(1).toUpperCase());
			}
		}
		
		// Writes the converted line to the file
		w.write(result.toString());
		if(lineit.hasNext())
			w.write(System.getProperty("line.separator"));

		w.flush();
	}

	w.close();
	r.close();

	// Replace the original file with the converted file format
	JSUtilities.copyFile(tempFile, fileToConvert);
	return true;
}
	/**
	 * Removes the UTF8 BOM marker from the document
	 * @param line 
	 */
	private static  String removeUTF8BOM(String line) {
		if (line.startsWith(UTF8_BOM)) {
			line = line.substring(1);
		}
		return line;
	}
	
public static  boolean addBOM(File tempDir, File fileToConvert) throws Exception{

		
		boolean firstLine = true;
		File tempFile = new File(tempDir, fileToConvert.getName());
		
		FileInputStream fis = new FileInputStream(fileToConvert);

		// Reads the input file following the UTF8 Encoding Set
		BufferedReader r = new BufferedReader(new InputStreamReader(fis,"UTF8"));
		FileOutputStream fos = new FileOutputStream(tempFile);

		// Writes the output file following the UTF8 Encoding Set
		Writer w = new BufferedWriter(new OutputStreamWriter(fos, "UTF8"));

		// Line Iterator is used to keep track if EOF is reached
		LineIterator lineit = new LineIterator(r);
		
		while (lineit.hasNext()) {
          
			String s = lineit.nextLine();
          
			if (firstLine) {
				
				s = removeUTF8BOM(s);
				firstLine = false;
				
			}

			final StringBuilder result = new StringBuilder();

			/**
			 * Add Original lines from the file to the result.
			 */
			
				for (Character character : s.toCharArray()) {
					
						result.append(character);
					 
				}
			
			// Writes the converted line to the file
			w.write(result.toString());
			if(lineit.hasNext())
				w.write(System.getProperty("line.separator"));

			w.flush();
		}

		w.close();
		r.close();

		// Replace the original file with the converted file format
		JSUtilities.copyFile(tempFile, fileToConvert);
		return true;
	}
}


