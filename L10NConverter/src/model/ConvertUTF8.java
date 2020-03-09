// PACKAGE DECLARATIONS
package model;

// JAVA LIBRARIES
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;

import src.JSUtilities;

// PACKAGE IMPORTS

/**
 * Swing Worker for executing the UTF8 conversion of the documents
 * 
 * @author ploh
 * @author maxc
 *
 */
public class ConvertUTF8 extends SwingWorker<Boolean, Integer> {

	private ArrayList<Integer> resultData;
	private List<String> filesString;
	private File tempDir;
	private Model model;

	public ConvertUTF8(Model m, List<String> fileList) {
		this.filesString = fileList;
		this.model = m;
	}

	@Override
	protected Boolean doInBackground() throws Exception {

		resultData = new ArrayList<Integer>();

		// Splits the files at each newline
		int counter = filesString.size();
		int fileProcessed = 0;
		int failedProcessed = 0;

		for (String activeFile : filesString) {

			setupTempDir();
			File fileToConvert = new File(activeFile.trim().replace("\n", ""));

			if (fileToConvert.exists()) {

				JSUtilities.convertUTF8(this.tempDir, fileToConvert);
				fileProcessed = fileProcessed + 1;

			} else {
				failedProcessed = failedProcessed + 1;
			}
		}

		// Performs a cleanup and deletes the temp dir
		JSUtilities.deleteFile(this.tempDir);

		// Populates Results Data
		resultData.add(0, counter);
		resultData.add(1, fileProcessed);
		resultData.add(2, failedProcessed);

		return null;
	}

	@Override
	public void done() {
		this.model.setResultData(resultData);
		this.model.completeConversion();
	}

	protected void setupTempDir() {

		this.tempDir = new File("temp/");

		if (!this.tempDir.exists()) {
			this.tempDir.mkdir();

		} else {
			JSUtilities.deleteFile(this.tempDir);
			this.tempDir.mkdir();
		}
	}
}
