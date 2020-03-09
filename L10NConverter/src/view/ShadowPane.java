package view;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Alpha Pane for outer background for JFrame
 * @author ploh
 */
public class ShadowPane extends JPanel {
	private static final long serialVersionUID = -1581630148195185817L;

	public ShadowPane() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBackground(Constants.darkGray);
        setBorder(new EmptyBorder(0, 2, 2, 2));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }
}