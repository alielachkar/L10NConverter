// PACKAGE DECLARATION
package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.plaf.FontUIResource;

/**
 * This class is to hold the applications global constants.
 * @author ploh
 * @version 1.0
 */

public final class Constants {
	// Use a newline constant to allow compatibility with various platforms
	public static final String NEWLINE = System.getProperty("line.separator");
	
	// Application Colors
	public static final Color bgColor = new Color(255,255,255);
	public static final Color baseColor = new Color(0,90,140);
	public static final Color darkGray = new Color(50,50,50);
	
	// Application Fonts
	public static final FontUIResource baseFont = new FontUIResource("Segoe UI", Font.PLAIN, 12);
	public static final Font titleFont = new Font("Segoe UI", Font.PLAIN, 15);
	
}
