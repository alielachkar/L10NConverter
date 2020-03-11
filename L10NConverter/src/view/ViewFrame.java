// PACKAGE DECLARATIONS
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;



import src.JSUtilities;
// PACKAGE IMPORTS
import model.Model;

/**
 * View Component of the JS Converter
 * @author ploh
 * @author maxc
 *
 */
public class ViewFrame implements Observer{

	JFrame frmJSTool;
	MainPanel mainPanel;
	FooterPanel footerPanel;
	HeaderPanel headerPanel;
	RightPanel rightPanel;
	Model model;
	JPanel buttonPanel;
	JPanel optionalPanel;
	JButton clearBtn;
	JButton convertBtn;
	JCheckBox checkbox1;
	JCheckBox checkbox2;
	JCheckBox checkbox3;
	JCheckBox checkbox4;

	int pX, pY = 0;

	/**
	 * Initializes the view frame with
	 */
	public ViewFrame(){
		initialize();
	}

	/**
	 * Initializes the Applications UI Components
	 */
	private void initialize(){

		ImageIcon appIcon = new ImageIcon(ViewFrame.class.getResource("images/icon.png"));
		UIManager.put("OptionPane.background", Constants.bgColor);
		UIManager.put("Panel.background", Constants.bgColor);

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int top = gd.getDisplayMode().getWidth()/3;
		int height = gd.getDisplayMode().getHeight()/4;

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		JSUtilities.setUIFont(Constants.baseFont);

		// Application JFrame Setup
		frmJSTool = new JFrame();
		frmJSTool.setTitle("Format Converter v2");
		frmJSTool.setResizable(true);
		frmJSTool.setUndecorated(true);
		frmJSTool.setBounds(top, height, 500, 400);
		frmJSTool.setMinimumSize(new Dimension(500, 400));
		frmJSTool.setIconImage(appIcon.getImage());
		frmJSTool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createLayoutRegions();

		ComponentResizer cr = new ComponentResizer();
		cr.setMinimumSize(new Dimension(500, 400));
		cr.registerComponent(frmJSTool);
		cr.setSnapSize(new Dimension(10, 10));

		frmJSTool.setContentPane(new ShadowPane());
		frmJSTool.add(headerPanel.getHeaderPanel(), BorderLayout.NORTH);
		frmJSTool.add(mainPanel.getMainPanel(), BorderLayout.CENTER);
		frmJSTool.add(footerPanel.getFooterPanel(), BorderLayout.SOUTH);
		frmJSTool.add(rightPanel.getRightPanel(),BorderLayout.EAST);  //Add option panel
		frmJSTool.pack();
	}
	
	public JFrame getFrame(){
		return this.frmJSTool;
	}
	
	/**
	 * Updates the GUI when the Model Component notifies a change of state
	 */
	@Override
	public void update(Observable arg0, Object obj) {
		
		// Model has initiated conversion sequence
		if(model.getValue() == 1){
			mainPanel.getTextScrollPane().getTextArea().setEditable(false);
			mainPanel.getTextScrollPane().getTextArea().setEnabled(false);
			clearBtn.setEnabled(false);
			convertBtn.setEnabled(false);
		}

		// Model has completed conversion sequence
		else if(model.getValue() == 2){
			ArrayList<Integer> data = model.getResultData();
			displayNotification("Conversion Details",String.format("Total Files: %s \nConverted Files: %s \nFailed Files: %s ", 
					data.get(0), data.get(1), data.get(2)), JOptionPane.INFORMATION_MESSAGE);
			mainPanel.getTextScrollPane().getTextArea().setEditable(true);
			mainPanel.getTextScrollPane().getTextArea().setEnabled(true);
			clearBtn.setEnabled(true);
			convertBtn.setEnabled(true);
		}
	}

	/**
	 * Adds a model reference to the View Component
	 * @param m - the model component to add
	 */
	public void addModel(Model m){
		this.model = m;
	}

	/**
	 * Returns the String contain in the text area 
	 * @return - A string representation of the text of the text area
	 */
	public String getTextAreaData(){
		return mainPanel.getTextScrollPane().getTextArea().getText();
	}

	/**
	 * Clears the text area 
	 */
	public void clearTextAreaData(){
		this.mainPanel.getTextScrollPane().getTextArea().setText("");
	}

	/**
	 * Determines whether to display the JFrame of the application
	 * @param value - true to display the application JFrame and false otherwise
	 */
	public void setVisible(boolean value){
		frmJSTool.setVisible(value);
	}

	/**
	 * Creates the Header Panel buttons
	 */
	public JPanel createHeaderButtons(){

		ImageIcon closeIcon = new ImageIcon(ViewFrame.class.getResource("images/close_ICON.png"));
		ImageIcon minimizeIcon = new ImageIcon(ViewFrame.class.getResource("images/minimize_ICON.png"));

		// Menu Bar Buttons
		JLabel closeBtn = new JLabel(closeIcon);
		closeBtn.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me)
			{
				System.exit(0);
			}
		});

		JLabel minimizeBtn = new JLabel(minimizeIcon);
		minimizeBtn.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me)
			{
				frmJSTool.setState(Frame.ICONIFIED);
			}
		});

		JPanel frameBtns = new JPanel();
		frameBtns.setOpaque(false);
		frameBtns.setLayout(new GridLayout(1,2,5,5));
		frameBtns.setBorder(new EmptyBorder(5,5,0,10));
		frameBtns.add(minimizeBtn);
		frameBtns.add(closeBtn);

		return frameBtns;
	}

	/**
	 * Creates the Button Panel of the application and initializes the buttons
	 */
	public JPanel createFooterButtonPanel(){

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2,5,5));

		clearBtn = new AppButton("CLEAR");
		clearBtn.setActionCommand("Clear");

		convertBtn = new AppButton("CONVERT");
		convertBtn.setActionCommand("Convert");

		buttonPanel.add(convertBtn);
		buttonPanel.add(clearBtn);

		return buttonPanel;
	}
	/**
	 * Creates the optional Panel of the application and initializes the check box
	 */
	public JPanel createOptionalCheckBox() {
		optionalPanel = new JPanel();
		optionalPanel.setLayout(new GridLayout(5,1));
		
		
		checkbox1=new JCheckBox("Remove BOM");
		//checkbox1.setActionCommand("BOM");

		
		checkbox2=new JCheckBox("UTF8 Escape");
		//checkbox2.setActionCommand("UTF8");
		
		checkbox3=new JCheckBox("Add Empty-line");
		
		checkbox4=new JCheckBox("Add BOM");

		optionalPanel.add(checkbox1,optionalPanel);
		optionalPanel.add(checkbox2,optionalPanel);
		optionalPanel.add(checkbox3,optionalPanel);
		optionalPanel.add(checkbox4,optionalPanel);
		
		return optionalPanel;
	}

	/**
	 * Sets the Action Listener of the applications buttons
	 * and the check boxes
	 * @param controller - the action listener component
	 */
	public void setController(ActionListener controller){
		clearBtn.addActionListener(controller);
		convertBtn.addActionListener(controller);
		checkbox1.addActionListener(controller);
		checkbox2.addActionListener(controller);
		checkbox3.addActionListener(controller);
		checkbox4.addActionListener(controller);
		
	}

	public void createLayoutRegions(){
		
		// Creates panels of the application
		mainPanel = new MainPanel();
		headerPanel = new HeaderPanel();
		footerPanel = new FooterPanel();
		rightPanel = new RightPanel();
		
		headerPanel.getHeaderPanel().addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me)
			{
				// Get x,y coordinates and store them
				pX= me.getX();
				pY= me.getY();
			}
		});
		headerPanel.getHeaderPanel().addMouseMotionListener(new MouseAdapter(){
			public void mouseDragged(MouseEvent me)
			{
				frmJSTool.setLocation(frmJSTool.getLocation().x+me.getX()-pX,frmJSTool.getLocation().y+me.getY()-pY);
			}
		});
		
		headerPanel.getHeaderPanel().add(createHeaderButtons(), BorderLayout.EAST);
		footerPanel.getFooterPanel().add(createFooterButtonPanel(), BorderLayout.WEST);
		rightPanel.getRightPanel().add(createOptionalCheckBox(),BorderLayout.CENTER);
	}

	/**
	 * Displays a message dialog with a specific priority to the user 
	 * @param message - the message to display
	 * @param value - the integer value of the message type
	 */
	public void displayNotification(String title, String message, int value){

		final JDialog dialog = new JDialog(this.getFrame(), "");
		JOptionPane op = new JOptionPane(message, value);
		op.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String name = evt.getPropertyName();
				if ("value".equals(name)) {
					dialog.dispose();
				}
			}
		});

		JLabel headerPanel = new JLabel(title);
		headerPanel.setOpaque(true);
		headerPanel.setBackground(Constants.darkGray);
		headerPanel.setForeground(Constants.bgColor);
		headerPanel.setBorder(new EmptyBorder(5,5,5,5));

		JPanel bodyPanel = new JPanel();
		bodyPanel.add(op);
		bodyPanel.setBorder(new LineBorder(Color.GRAY, 3));

		dialog.setUndecorated(true);
		dialog.setLayout(new BorderLayout());
		
		dialog.add(headerPanel, BorderLayout.NORTH);
		dialog.add(bodyPanel, BorderLayout.CENTER);
		dialog.pack();
		
		dialog.setLocationRelativeTo(this.getFrame());
		dialog.setVisible(true);
	}
	
	public JCheckBox getCheckBox1() {
		return checkbox1;
	}
	
	public JCheckBox getCheckBox2() {
		return checkbox2;
	}
	public JCheckBox getCheckBox3() {
		return checkbox3;
	}
	public JCheckBox getCheckBox4() {
		return checkbox4;
	}


	/**
	 * Displays a message dialog with a specific priority to the user 
	 * @param message - the message to display
	 * @param value - the integer value of the message type
	 */
	public JOptionPane showOptionDialog(String title, String message, int value){

		final JDialog dialog = new JDialog(this.getFrame(), "");
		JOptionPane op = new JOptionPane(message, value,JOptionPane.YES_NO_OPTION);
		op.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String name = evt.getPropertyName();
				if ("value".equals(name)) {
					dialog.dispose();
				}
			}
		});

		JLabel headerPanel = new JLabel(title);
		headerPanel.setOpaque(true);
		headerPanel.setBackground(Constants.darkGray);
		headerPanel.setForeground(Constants.bgColor);
		headerPanel.setBorder(new EmptyBorder(5,5,5,5));

		JPanel bodyPanel = new JPanel();
		bodyPanel.add(op);
		bodyPanel.setBorder(new LineBorder(Color.GRAY, 3));

		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setUndecorated(true);
		dialog.setLayout(new BorderLayout());
		
		dialog.add(headerPanel, BorderLayout.NORTH);
		dialog.add(bodyPanel, BorderLayout.CENTER);
		dialog.pack();
		
		dialog.setLocationRelativeTo(this.getFrame());
		dialog.setVisible(true);

		return op;
	}

}
