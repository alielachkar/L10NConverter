// PACKAGE DECLARATIONS
package src;

// JAVA LIBRARIES
import java.awt.EventQueue;

// PACKAGE IMPORTS
import controller.Controller;
import view.ViewFrame;
import model.Model;

public class Main {
	
	public Main(){

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Model model = new Model();
					ViewFrame view = new ViewFrame();
					model.addObserver(view);

					Controller controller = new Controller();
					controller.addModel(model);
					controller.addView(view);

					view.setController(controller);
					view.addModel(model);
					
					view.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String [] args){
		new Main();
	}
}
