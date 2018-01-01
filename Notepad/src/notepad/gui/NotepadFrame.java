package notepad.gui;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import notepad.action.RedoAction;
import notepad.action.UndoAction;
import notepad.constants.ImageConstants;
import notepad.constants.PopopMenuConstants;
import notepad.listeners.FrameWindowListener;
import notepad.listeners.MenuActionListener;
import notepad.listeners.TextAreaDocumentListener;
import notepad.listeners.TextAreaUndoRedoManager;
import notepad.utility.FileOperation;
import notepad.utility.NotepadConstants;
import resources.NotepadProperties;

@SuppressWarnings("serial")
public class NotepadFrame extends JFrame implements ImageConstants {

	private TextAreaUndoRedoManager textAreaUndoRedoManager = null;
	private TextAreaDocumentListener textAreaDocumentListener = null; 
	private JLabel statusBar;
	private JTextArea textArea;
	private FileOperation fileHandler;
	private static NotepadFrame frame;
	private JMenuItem undoMenu;
	private JMenuItem redoMenu;
	private JMenuItem popupUndoMenu;
	private JMenuItem popupRedoMenu;
	private JPopupMenu popupMenu;
	private ResourceBundle resourceBundle = null;
	private JRadioButton wordWrapButton = null;

	public FileOperation getFileHandler() {
		return fileHandler;
	}
	
	public TextAreaUndoRedoManager getTextAreaUndoRedoManager() {
		return textAreaUndoRedoManager;
	}
	public void setTextAreaDocumentListener(TextAreaDocumentListener textAreaDocumentListener) {
		this.textAreaDocumentListener = textAreaDocumentListener;
	}
	
	public void setTextAreaUndoRedoManager(TextAreaUndoRedoManager textAreaUndoRedoManager) {
		this.textAreaUndoRedoManager = textAreaUndoRedoManager;
	}
	
	public TextAreaDocumentListener getTextAreaDocumentListener() {
		return textAreaDocumentListener;
	}
	
	public JRadioButton getWordWrapButton() {
		return wordWrapButton;
	}
	
	public JMenuItem getPopupUndoMenu() {
		return popupUndoMenu;
	}
	
	public JMenuItem getPopupRedoMenu() {
		return popupRedoMenu;
	}
	
	public JPopupMenu getPopupMenu() {

		return popupMenu;
	}

	public JMenuItem getUndoMenu() {
		return undoMenu;
	}

	public JMenuItem getRedoMenu() {
		return redoMenu;
	}

	public JTextArea getTextArea() {

		return textArea;
	}
	
	public static NotepadFrame getFrame() {

		return frame;
	}

	public NotepadFrame(String filePath) {

		//this.setUndecorated(true);
		NotepadFrame.frame = this;
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(NotepadProperties.RESOURCE_PATH+"/images/Notepad.png"));
		this.fileHandler = new FileOperation(this);
		int []bounds = NotepadProperties.bounds;
		setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
		this.setVisible(true);		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		init();

		if(filePath != null)

			openFile(filePath.trim());
		
	}

	public void init() {

		createAndAddComponents();
		setWordWrap();
		addLookAndFeel();
		
		/*this.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println(e.getComponent().getX());
				System.out.println(e.getComponent().getY());
				System.out.println(e.getComponent().getHeight());
				System.out.println(e.getComponent().getWidth());
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});*/
	}

	public JLabel getStatusBar() {

		return statusBar;
	}

	private void createAndAddComponents() {
		
		this.textAreaDocumentListener = new TextAreaDocumentListener(fileHandler);

		this.setJMenuBar(new NotepadMenuBar());
		//this.getJMenuBar().setVisible(false);
		
		//if(textArea == null) 
			this.textArea = new JTextArea();
		
		textArea.setTabSize(3);
		
		textArea.setComponentPopupMenu(addPopupMenu());

		this.textAreaUndoRedoManager = new TextAreaUndoRedoManager();
		
		textArea.getDocument().addUndoableEditListener(textAreaUndoRedoManager);
		setFont();
		JScrollPane textJScrollPane = new JScrollPane(textArea);

		this.getContentPane().add(textJScrollPane);

		getContentPane().add(createStatusBar(),BorderLayout.SOUTH);
		//getContentPane().add(createStatusBar(),BorderLayout.SOUTH);
		//getContentPane().add(getSideBar(),BorderLayout.EAST);
		//getContentPane().add(getSideBar(),BorderLayout.WEST);

		textArea.addCaretListener(
				new CaretListener()	{

					public void caretUpdate(CaretEvent e) {

						int lineNumber=0, column=0, pos=0;

						try {

							pos=textArea.getCaretPosition();
							lineNumber=textArea.getLineOfOffset(pos);
							column=pos-textArea.getLineStartOffset(lineNumber);
						} catch(Exception excp){}

						if(textArea.getText().length()==0) {

							lineNumber=0; column=0;
						}
						statusBar.setText("Ln "+(lineNumber+1)+", Col "+(column+1));
					}
				});


		textArea.getDocument().addDocumentListener(textAreaDocumentListener);
		//textArea.setText(NotepadProperties.RESOURCE_PATH);
		this.addWindowListener(new FrameWindowListener(fileHandler));

		JMenuBar menuBar = this.getJMenuBar();
		if(menuBar != null) {
			for(int i = 0; i < menuBar.getMenuCount(); i ++) {

				JMenu menu = menuBar.getMenu(i);
				
				for(int j = 0; j < menu.getMenuComponents().length; j ++) {

					JMenuItem menuItem = menu.getItem(j);

					if(menuItem != null) {

						UndoAction undoAction = TextAreaUndoRedoManager.getUndoAction();
						RedoAction redoAction = TextAreaUndoRedoManager.getRedoAction();

						if(menuItem.getActionCommand().equalsIgnoreCase(NotepadConstants.UNDO)) {

							undoMenu = menuItem;
							menuItem.setAction(undoAction);
						}

						else if(menuItem.getActionCommand().equalsIgnoreCase(NotepadConstants.REDO)) {

							redoMenu = menuItem;
							menuItem.setAction(redoAction);
						}

						else 
							menuItem.addActionListener(new MenuActionListener(fileHandler));

						if(menuItem instanceof JCheckBoxMenuItem) {

							addDefultAction((JCheckBoxMenuItem) menuItem);
						}

					}
				}
			}
		}

		//createRightMenuBarButtons(menuBar, NotepadConstants.PRINT, SPACE, "Get help", null);
		menuBar.add(Box.createGlue());
		createRightMenuBarButtons(menuBar);
		textArea.requestFocus(true);
	}

	JPopupMenu addPopupMenu() {

		JPopupMenu popupMenu = new JPopupMenu();
		
		//popupMenu.registerKeyboardAction(new MenuActionListener(fileHandler), KeyStroke.getKeyStroke('C',ActionEvent.CTRL_MASK), 1);
		
		Object[] popupMenuItems = PopopMenuConstants.popupMenuItems;
		for (int i = 0; i < popupMenuItems.length; i++) {

			NotepadPopupMenu popupMenuItem = (NotepadPopupMenu) popupMenuItems[i];
			JMenuItem menuItem = popupMenuItem.getMenuItem();

			UndoAction undoAction = TextAreaUndoRedoManager.getUndoAction();
			RedoAction redoAction = TextAreaUndoRedoManager.getRedoAction();

			if(menuItem.getActionCommand().equalsIgnoreCase(NotepadConstants.UNDO)) {

				undoMenu = menuItem;
				popupUndoMenu = undoMenu;
				menuItem.setAction(undoAction);
			}

			else if(menuItem.getActionCommand().equalsIgnoreCase(NotepadConstants.REDO)) {

				redoMenu = menuItem;
				popupRedoMenu = redoMenu;
				menuItem.setAction(redoAction);
			} else
				menuItem.addActionListener(new MenuActionListener(fileHandler));
			popupMenu.add(menuItem);
			String menuName = menuItem.getActionCommand();
			if(menuName.equalsIgnoreCase(NotepadConstants.REDO) || menuName.equalsIgnoreCase(NotepadConstants.PASTE))
				popupMenu.addSeparator();
		}

		return popupMenu;
	}

	private void openFile(String filePath) {

		FileOperation fileHandler = FileOperation.getFileHandler(); 
		fileHandler.openFile(new File(filePath));
		fileHandler.setSaved(true);
		undoMenu.setEnabled(false);
		
		//textArea.setText(NotepadProperties.RESOURCE_PATH);
	}

	private JPanel createStatusBar() {

		JPanel statusPanel = new JPanel();
		//statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		statusBar=new JLabel("Ln 1, Col 1 ",JLabel.CENTER);
		statusBar.setOpaque(true);
		statusBar.setBackground(NotepadProperties.statusBarColor);
		
		statusPanel.add(statusBar);
		
		//word wrap
		
		/*ImageIcon icon = null;
		try {
			
			icon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream(ImageConstants.prefix + "wrap" + ImageConstants.suffix)));
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		this.wordWrapButton = new JRadioButton(NotepadConstants.WORDWRAP, icon);*/
		
		//statusPanel.setOpaque(true);
		
		this.wordWrapButton = new JRadioButton(NotepadConstants.WORDWRAP);
		wordWrapButton.addActionListener(new MenuActionListener(fileHandler));
		
		statusPanel.add(wordWrapButton);
		
		JButton printButton = new JButton(NotepadConstants.PRINT);
		printButton.addActionListener(new MenuActionListener(fileHandler));
		
		statusPanel.add(printButton);
		
		/*JMenuBar menuBar = new JMenuBar();
	
		JMenu menu = new JMenu("Tools");
		Object[] searchMenus = MenuConstants.SEARCHMENUS;
		
		for(int i = 0; i < searchMenus.length; i ++) {
			
			NotepadMenu notepadMenu = (NotepadMenu)searchMenus[i];
			JMenuItem menuItem = notepadMenu.getMenuItem();
			menuItem.addActionListener(new MenuActionListener(fileHandler));
			menu.add(menuItem);
		}
	
		menuBar.add(menu);
		statusPanel.add(menuBar);*/
		
		return statusPanel;
	}
	
	/*private JLabel createStatusBar1() {

		//JLabel statusPanel = new JLabel();
		//statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		//statusPanel.setPreferredSize(new Dimension(100, 60));
		statusBar=new JLabel("Ln 1, Col 1 ",JLabel.CENTER);
		statusBar.setOpaque(true);
		statusBar.setBackground(NotepadProperties.statusBarColor);
		
		//statusPanel.add(statusBar);
		
		//word wrap
		
		ImageIcon icon = null;
		try {
			
			icon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream(ImageConstants.prefix + "wrap" + ImageConstants.suffix)));
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		this.wordWrapButton = new JRadioButton(NotepadConstants.WORDWRAP, icon);
		
		this.wordWrapButton = new JRadioButton(NotepadConstants.WORDWRAP);
		wordWrapButton.addActionListener(new MenuActionListener(fileHandler));
		wordWrapButton.setVisible(true);
		
		statusBar.add(wordWrapButton);
		
		JMenuBar menuBar = new JMenuBar();
	
		JMenu menu = new JMenu("Tools");
		Object[] searchMenus = MenuConstants.SEARCHMENUS;
		
		for(int i = 0; i < searchMenus.length; i ++) {
			
			NotepadMenu notepadMenu = (NotepadMenu)searchMenus[i];
			JMenuItem menuItem = notepadMenu.getMenuItem();
			menuItem.addActionListener(new MenuActionListener(fileHandler));
			menu.add(menuItem);
		}
	
		menuBar.add(menu);
		statusPanel.add(menuBar);
		
		return statusBar;
	}*/

	/*private JLabel getSideBar() {

		JLabel panel = new JLabel("  ");
		panel.setOpaque(true);
		panel.setBackground(NotepadProperties.sidePanelColor);
		return panel;
	}*/

	private void addDefultAction(JCheckBoxMenuItem checkboxMenuItem) {

		String menuName = checkboxMenuItem.getActionCommand();

		if(menuName.equalsIgnoreCase("Word Wrap")) {

			checkboxMenuItem.setSelected(NotepadProperties.wordWrap);
			getTextArea().setLineWrap(checkboxMenuItem.isSelected());
		}

		if(menuName.equalsIgnoreCase("Status Bar")) {

			checkboxMenuItem.setSelected(NotepadProperties.showStatusBar);
			getStatusBar().setVisible(checkboxMenuItem.isSelected());
		}
	}

	private void setFont() {

		this.resourceBundle = null;
		try {

			resourceBundle = new PropertyResourceBundle(new FileInputStream(NotepadProperties.RESOURCE_PATH+"/resources/preferences.properties"));
		} catch (Exception e) {

			e.printStackTrace();
		}
		String fontName = null;
		String fontSize = null;
		int fontStyle = 0;

		if(resourceBundle != null) {

			if(resourceBundle.containsKey("fontName"))
				fontName = resourceBundle.getString("fontName");

			if(resourceBundle.containsKey("fontStyle"))
				fontStyle = setFontStyle(resourceBundle.getString("fontStyle"));

			if(resourceBundle.containsKey("fontStyle"))
				fontSize = resourceBundle.getString("fontSize");

			if(fontName != null && fontSize != null) 
				textArea.setFont(new Font(fontName, fontStyle, Integer.parseInt(fontSize)));
		}
	}
	
	private void setWordWrap() {

		this.resourceBundle = null;
		try {
			resourceBundle = new PropertyResourceBundle(new FileInputStream(NotepadProperties.RESOURCE_PATH + "/resources/wordwrap.properties"));
			if(resourceBundle.containsKey("wordWrap")) {
				
				if(resourceBundle.getString("wordWrap").equalsIgnoreCase("true")) {
					
					wordWrapButton.setSelected(true);
				} else
					wordWrapButton.setSelected(false);
			}
				
		} catch (Exception e) {

			e.printStackTrace();
		}
		textArea.setLineWrap(wordWrapButton.isSelected());
	}

	private int setFontStyle(String fontStyle) {

		int style = 0;

		if(fontStyle.equalsIgnoreCase("Regular"))
			style = Font.PLAIN;

		if(fontStyle.equalsIgnoreCase("Italic"))
			style = Font.ITALIC;

		if(fontStyle.equalsIgnoreCase("Bold"))
			style = Font.BOLD;

		if(fontStyle.equalsIgnoreCase("Bold Italic"))
			style = Font.BOLD + Font.ITALIC;
		return style;
	}

	private void addLookAndFeel() {

		this.resourceBundle = null;
		try {

			resourceBundle = new PropertyResourceBundle(new FileInputStream(NotepadProperties.RESOURCE_PATH+"/resources/theme.properties"));
		} catch (Exception e) {

			e.printStackTrace();
		}
		if(resourceBundle != null) {

			if(resourceBundle.containsKey("themeName"))
				new AddLookAndFeel(resourceBundle.getString("themeName"));
		}
	}

	private void createRightMenuBarButtons(JMenuBar menuBar) {

		int space = 127;
		
		//Open Button

		createRightMenuBarButtons(menuBar, NotepadConstants.OPEN, KeyEvent.VK_O, "Open a file", OPENFILE);
		
		//New Button

		createRightMenuBarButtons(menuBar, NotepadConstants.NEW, KeyEvent.VK_N, "Create new document", NEWFILE);
		

		//Save button
		
		createRightMenuBarButtons(menuBar, NotepadConstants.SAVE, KeyEvent.VK_S, "Save the current file", SAVEFILE);
		
		//Save As Button
		
		createRightMenuBarButtons(menuBar, NotepadConstants.SAVEAS, KeyEvent.VK_S, "Save as the current file", SAVEASFILE);
		
		//settings
		createRightMenuBarButtons(menuBar, NotepadConstants.SETTINGS, space, "Customize your Notepad", PREFERENCES);
		
		//help
		createRightMenuBarButtons(menuBar, NotepadConstants.HELP, space, "Get help", HELP);
		
		//word wrap
		
		/*this.wordWrapButton = new JRadioButton(NotepadConstants.WORDWRAP);
		wordWrapButton.addActionListener(new MenuActionListener(fileHandler));
		menuBar.add(wordWrapButton);*/
	}	
	
	private void createRightMenuBarButtons(JMenuBar menuBar, String buttonName, int eventKey, String toolTipText, String imagesName) {

		int space = 127;
		
		ImageIcon icon = null;
		try {
			if(imagesName != null)
				icon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream(PREFIX + imagesName + SUFFIX)));
		} catch (IOException e) {

			e.printStackTrace();
		}

		JButton button = new JButton(buttonName);
		if(icon != null)
			button = new JButton(icon);
		button.setActionCommand(buttonName);
		button.setToolTipText(toolTipText);
		button.addActionListener(new MenuActionListener(fileHandler));
		menuBar.add(button);

		if(eventKey != space) {

			KeyStroke keyAction = KeyStroke.getKeyStroke(eventKey, Event.CTRL_MASK);
			
			if(buttonName.equalsIgnoreCase(NotepadConstants.SAVEAS))
				keyAction = KeyStroke.getKeyStroke(eventKey, Event.CTRL_MASK + Event.ALT_MASK);
			
			Action performAction = new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent e) {

					String action = ((JButton)e.getSource()).getText();
					if(action.equalsIgnoreCase(NotepadConstants.SAVE))
						fileHandler.saveThisFile();
					else if(action.equalsIgnoreCase(NotepadConstants.NEW))
						fileHandler.newFile();
					else if(action.equalsIgnoreCase(NotepadConstants.OPEN))
						fileHandler.openFile();
					else if(action.equalsIgnoreCase(NotepadConstants.SAVEAS))
						fileHandler.saveAsFile();
				}
			};
			button.getActionMap().put("performAction", performAction);
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyAction, "performAction");
		}
	}
}
