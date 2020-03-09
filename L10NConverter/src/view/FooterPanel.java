// PACKAGE DECLARATIONS
package view;

// JAVA LIBRARIES
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class FooterPanel {
	
	private JLabel versionInfo;
	private JPanel footerPanel;
	private JLabel resizeLabel;
	
	public FooterPanel(){
		ImageIcon coporateLogo = new ImageIcon(FooterPanel.class.getResource("images/OT_Logo.png"));
		ImageIcon resizeImage = new ImageIcon(FooterPanel.class.getResource("images/resize_ICON.png"));
		
		footerPanel = new JPanel();
		footerPanel.setBackground(Constants.bgColor);
		
		footerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		footerPanel.setLayout(new BorderLayout(0, 0));
		
		resizeLabel = new JLabel(resizeImage);
		versionInfo = new JLabel(coporateLogo);
		
		versionInfo.setHorizontalAlignment(SwingConstants.RIGHT);
		
		footerPanel.add(versionInfo, BorderLayout.CENTER);
		footerPanel.add(resizeLabel, BorderLayout.EAST);
	}
	
	public JPanel getFooterPanel(){
		return this.footerPanel;
	}	
}
