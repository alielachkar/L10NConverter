package view;

import javax.swing.JButton;

public class AppButton extends JButton{
	private static final long serialVersionUID = -4507871478405400156L;

	public AppButton(String text){
		super(text);
		this.setBackground(Constants.baseColor);
		this.setForeground(Constants.bgColor);
		this.setContentAreaFilled(false);
		this.setOpaque(true);
		this.setFocusPainted(false);
	}

}
