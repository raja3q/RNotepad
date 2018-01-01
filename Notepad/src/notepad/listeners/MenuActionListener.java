package notepad.listeners;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import notepad.gui.FindDialog;
import notepad.gui.NotepadFrame;
import notepad.gui.PreferencesDialog;
import notepad.utility.FileOperation;
import notepad.utility.NotepadConstants;
import resources.NotepadProperties;

public class MenuActionListener implements ActionListener, NotepadConstants {

	Frame[] frames = NotepadFrame.getFrames();
	NotepadFrame notepadFrame = (NotepadFrame)frames[0];

	private FileOperation fileHandler;

	public MenuActionListener(FileOperation fileHandler) {

		this.fileHandler = fileHandler;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if(NEW.equalsIgnoreCase(ae.getActionCommand())) {

			fileHandler.newFile();
		}

		if(OPEN.equalsIgnoreCase(ae.getActionCommand())) {

			fileHandler.openFile();
		}

		if(SAVE.equalsIgnoreCase(ae.getActionCommand())) {

			fileHandler.saveThisFile();
		}

		if(SAVEAS.equalsIgnoreCase(ae.getActionCommand())) {

			fileHandler.saveAsFile();
		}

		if(PRINT.equalsIgnoreCase(ae.getActionCommand())) {

			fileHandler.print();
		}

		/*if(close.equalsIgnoreCase(ae.getActionCommand())) {

			if(fileHandler.confirmSave()) System.exit(0);
		}*/


		if(CUT.equalsIgnoreCase(ae.getActionCommand())) {

			fileHandler.cut();
		}

		if(COPY.equalsIgnoreCase(ae.getActionCommand())) {

			fileHandler.copy();
		}

		if(PASTE.equalsIgnoreCase(ae.getActionCommand())) {

			fileHandler.paste();
		}

		if(SELECTALL.equalsIgnoreCase(ae.getActionCommand())) {

			fileHandler.selectAll();
		}

		if(TIMEANDDATE.equalsIgnoreCase(ae.getActionCommand())) {

			fileHandler.showTimeAndDate();
		}

		if(WORDWRAP.equalsIgnoreCase(ae.getActionCommand())) {

			//notepadFrame.getTextArea().setLineWrap(((JCheckBoxMenuItem) ae.getSource()).isSelected());
			
			boolean isSelected = notepadFrame.getWordWrapButton().isSelected();
			
			Properties properties = new Properties();
			OutputStream fout = null;
			try {
				fout = new FileOutputStream(NotepadProperties.RESOURCE_PATH+"/resources/wordwrap.properties");
				properties.setProperty("wordWrap", isSelected+"");
				properties.store(fout, "Word Wrap Properties");
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} finally {
				
				try {
					fout.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			
			notepadFrame.getTextArea().setLineWrap(notepadFrame.getWordWrapButton().isSelected());
		}

		if(STATUSBAR.equalsIgnoreCase(ae.getActionCommand())) {

			notepadFrame.getStatusBar().setVisible(((JCheckBoxMenuItem) ae.getSource()).isSelected());
		}

		if(FIND.equalsIgnoreCase(ae.getActionCommand())) {

			FindDialog.getFindDialog(notepadFrame.getTextArea(), true).showDialog(notepadFrame, true);
		}

		if(REPLACE.equalsIgnoreCase(ae.getActionCommand())) {

			FindDialog.getFindDialog(notepadFrame.getTextArea(), false).showDialog(notepadFrame, false);
		}

		if(GOTO.equalsIgnoreCase(ae.getActionCommand())) {

			int lineNumber = 0;
			JTextArea textArea = notepadFrame.getTextArea();
			try {

				lineNumber=textArea.getLineOfOffset(textArea.getCaretPosition())+1;
				String tempStr = JOptionPane.showInputDialog(notepadFrame,"Enter Line Number:",""+lineNumber);
				if(tempStr==null) {

					return;
				}

				lineNumber = Integer.parseInt(tempStr);
				textArea.setCaretPosition(textArea.getLineStartOffset(lineNumber-1));
			} catch (BadLocationException e) {

				JOptionPane.showMessageDialog(notepadFrame,"No Such Line","Goto",JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if(SETTINGS.equalsIgnoreCase(ae.getActionCommand())) {

			PreferencesDialog.getPreferencesObject();
		}
		
		if(HELP.equalsIgnoreCase(ae.getActionCommand())) {

			JOptionPane.showMessageDialog(notepadFrame,"Not yet developed","Help",JOptionPane.INFORMATION_MESSAGE);
		}

		/*if(about.equalsIgnoreCase(ae.getActionCommand())) {

			JOptionPane.showMessageDialog(notepadFrame,"RNotepad Version 5","About",JOptionPane.INFORMATION_MESSAGE);		
		}*/

	}
}
