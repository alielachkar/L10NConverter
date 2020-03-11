// PACKAGE DECLARATIONS
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Model Component of the JS Converter
 * @author ploh
 * @author maxc
 *
 */
public class Model extends Observable {
	
	private ArrayList<Integer> resultData;
	private int value = 0;
	private FileConverter fileConverter;
	private RemoveBOM removeBOM;
	private ConvertUTF8 convertUTF8;
	private AddEmptyLine addEmptyLine;
	private AddBOM addBOM;

	public Model (){
	}

	public void executeConversion(List<String> filesString) throws Exception{

		setValue(1);
		setChanged();
		notifyObservers();

		// Send Conversion Task to Swing Worker
		fileConverter = new FileConverter(this,filesString);
		fileConverter.execute();
	}
	
	public void RemoveBOM(List<String> filesString) throws Exception{

		setValue(1);
		setChanged();
		notifyObservers();

		// Send Conversion Task to Swing Worker
		removeBOM = new RemoveBOM(this,filesString);
		removeBOM.execute();
	}
	
	public void ConvertUTF8(List<String> filesString) throws Exception{

		setValue(1);
		setChanged();
		notifyObservers();

		// Send Conversion Task to Swing Worker
		convertUTF8 = new ConvertUTF8(this,filesString);
		convertUTF8.execute();
	}
	
	public void AddEmptyLine(List<String> filesString) throws Exception{

		setValue(1);
		setChanged();
		notifyObservers();

		// Send Conversion Task to Swing Worker
		addEmptyLine = new AddEmptyLine(this,filesString);
		addEmptyLine.execute();
	}
	
	public void AddBOM(List<String> filesString) throws Exception{

		setValue(1);
		setChanged();
		notifyObservers();

		// Send Conversion Task to Swing Worker
		addBOM = new AddBOM(this,filesString);
		addBOM.execute();
	}

	public void setValue(int value){
		this.value = value;
	}

	public int getValue(){
		return this.value;
	}

	public void setResultData( ArrayList<Integer> populatedData){
		this.resultData = populatedData;
	}
	
	public ArrayList<Integer> getResultData(){
		return this.resultData;
	}
	
	public void completeConversion(){
		// Sets the conversion completed flag
		setValue(2);

		// Notifies the observers that the process has completed
		setChanged();
		notifyObservers();
	}
	
}	
