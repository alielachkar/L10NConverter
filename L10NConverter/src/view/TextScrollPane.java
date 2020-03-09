// PACKAGE DECLARATIONS
package view;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultEditorKit;

public class TextScrollPane {
	
	private JTextArea jsTextArea;
	private JScrollPane scrollPane;

	public TextScrollPane(){
		
		final ImageIcon copyIcon = new ImageIcon(TextScrollPane.class.getResource("images/copy_ICON.png"));
		final ImageIcon pasteIcon = new ImageIcon(TextScrollPane.class.getResource("images/paste_ICON.png"));
		
		jsTextArea = new JTextArea();
		jsTextArea.setEditable(true);
		
		// Creates the context menu option
		jsTextArea.addMouseListener(new MouseAdapter() {
			public void mouseReleased(final MouseEvent e) {
				if (e.isPopupTrigger()) {
					final JPopupMenu menu = new JPopupMenu();
					JMenuItem copyItem;
					copyItem = new JMenuItem(new DefaultEditorKit.CopyAction());
					copyItem.setText("Copy");
					copyItem.setIcon(copyIcon);
					copyItem.setEnabled(jsTextArea.getSelectionStart() != jsTextArea.getSelectionEnd());
					menu.add(copyItem);

					JMenuItem pasteItem = new JMenuItem(new DefaultEditorKit.PasteAction());
					pasteItem.setText("Paste");
					pasteItem.setIcon(pasteIcon);
					menu.add(pasteItem);
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		// Creates the drag and drop functionality 
		jsTextArea.setDropTarget(new DropTarget() {
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

					// APPENDS THE FILE PATHS TO THE TEXT AREA
					for (File file : droppedFiles) {
						jsTextArea.append(file.getAbsolutePath() + Constants.NEWLINE);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error when setting up application!", "Notification", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		});

		scrollPane = new JScrollPane(jsTextArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
	}

	public JScrollPane getScrollPane(){
		return this.scrollPane;
	}

	public JTextArea getTextArea(){
		return this.jsTextArea;
	}

}
