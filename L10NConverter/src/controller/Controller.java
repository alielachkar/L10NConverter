// PACKAGE DECLARATIONS
package controller;

// JAVA LIBRARIES
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;


import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;


// PACKAGE IMPORTS
import view.ViewFrame;
import model.Model;

/**
 * Controller Component of the JS Converter
 * @author ploh
 * @author maxc
 */
public class Controller implements ActionListener{
	
	
    private boolean BOM=false;
    private boolean UTF8=false;
    private boolean EmptyLine=false;
	ViewFrame appFrame;
	Model modelConverter;

	public Controller(){}

	/**
	 * Initializes the View Component
	 * @param v - An instance of a view object
	 */
	public void addView(ViewFrame v){
		this.appFrame = v;
	}

	/**
	 * Initializes the Model Component
	 * @param v - An instance of a model object
	 */
	public void addModel(Model m){
		this.modelConverter = m;
	} 
	
	/**
	 * Handles all action events initiated by the view component
	 * @param e - the ActionEvent component
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source instanceof JCheckBox) {

			JCheckBox checkBox = (JCheckBox) e.getSource();
			if (checkBox == appFrame.getCheckBox1()) {
				if (checkBox.isSelected() == true) {
					BOM = true;
				} else {
					BOM = false;
				}
			} else if (checkBox == appFrame.getCheckBox2()) {
				if (checkBox.isSelected() == true) {
					UTF8 = true;
				} else {
					UTF8 = false;
				}
			}
			else if (checkBox == appFrame.getCheckBox3()) {
				if (checkBox.isSelected() == true) {
					EmptyLine = true;
				} else {
					EmptyLine = false;
				}
			}
		}

		else if (source instanceof JButton) {
			if (e.getActionCommand().equals("Clear")) {
				appFrame.clearTextAreaData();
			} else if (e.getActionCommand().equals("Convert")) {
				try {

					if (appFrame.getTextAreaData().trim().replace("\n", "").equals("")) {
						appFrame.displayNotification("Notification", "No Files Specified",
								JOptionPane.INFORMATION_MESSAGE);
					}
					else if(EmptyLine==false && BOM==false && UTF8 ==false){
						appFrame.displayNotification("Notification", "Please Select an Option",
								JOptionPane.INFORMATION_MESSAGE);
					}else {

						List<String> listOfFiles = Arrays.asList(appFrame.getTextAreaData().split("\n"));

						int test = -1;
						
						/**
						 * Warming message for inputing non-js format files
						 */

						/*for (String file : listOfFiles) {
							if (!(new File(file.trim().replace("\n", "")).getName().endsWith(".js"))) {
								test = (Integer) appFrame.showOptionDialog("Notification",
										"Non-js files detected. Continue Conversion Sequence?",
										JOptionPane.WARNING_MESSAGE).getValue();
								break;
							}
						}*/

						if (test != 1) {
							if(BOM&&UTF8) {
							modelConverter.executeConversion(listOfFiles);
							}
							else if (BOM) {
								modelConverter.RemoveBOM(listOfFiles);	
							}
							else if(UTF8) {
								modelConverter.ConvertUTF8(listOfFiles);
							}
							else if(EmptyLine) {
								modelConverter.AddEmptyLine(listOfFiles);
							}
						}

					}
				} catch (Exception e1) {
					appFrame.displayNotification("Error", e1.getMessage(), JOptionPane.ERROR_MESSAGE);
				}
			}
			;
		}
	}
   
}
