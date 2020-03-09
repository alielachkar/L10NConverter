// PACKAGE DECLARATIONS
package view;

// JAVA LIBRARIES
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * The main panel of the applications UI which contains the Text Area
 * @author ploh
 *
 */
public class MainPanel {
	
	private JPanel mainPanel;
	private TextScrollPane scrollPane;
	
	public MainPanel(){
		mainPanel = new JPanel();
		scrollPane = new TextScrollPane();
		
		mainPanel.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5),new LineBorder(new Color(200, 200, 200), 1, true)));
		mainPanel.setLayout(new BorderLayout(0, 0));
		mainPanel.setBackground(Constants.bgColor);
		mainPanel.add(scrollPane.getScrollPane(), BorderLayout.CENTER);
	}
	
	public JPanel getMainPanel(){
		return this.mainPanel;
	}
	
	public TextScrollPane getTextScrollPane(){
		return this.scrollPane;
	}
}
