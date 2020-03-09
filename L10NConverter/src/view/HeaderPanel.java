// PACKAGE DECLARATIONS
package view;

// JAVA LIBRARIES
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class HeaderPanel {
	
	JLabel jsPanelTitle;
	JPanel headerPanel;
	
	public HeaderPanel(){
		ImageIcon headerIcon = new ImageIcon(HeaderPanel.class.getResource("images/header_ICON.png"));
		
		headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(Constants.baseColor);
		headerPanel.setBorder(new EmptyBorder(5,5,5,5));
		
		jsPanelTitle = new JLabel("FORMAT CONVERTER V2.0", headerIcon, SwingConstants.TRAILING);
		jsPanelTitle.setFont(Constants.titleFont);
		jsPanelTitle.setForeground(Constants.bgColor);
		
		headerPanel.add(jsPanelTitle, BorderLayout.WEST);
	}
	
	public JPanel getHeaderPanel(){
		return this.headerPanel;
	}
	
}
