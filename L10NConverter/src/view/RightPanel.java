package view;


import java.awt.BorderLayout;
/**
 * The right panel of the applications UI which contains the check boxes
 * @author maxc
 *
 */


import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class RightPanel {

	JPanel optionPanel;
	
	public RightPanel() {
		optionPanel = new JPanel(new BorderLayout());
		optionPanel.setBackground(Constants.baseColor);
		optionPanel.setBorder(new EmptyBorder(5,5,5,5));
		
	}
	public JPanel getRightPanel() {
		return this.optionPanel;
	}
	
}
