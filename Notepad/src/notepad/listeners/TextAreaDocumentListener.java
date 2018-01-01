package notepad.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import notepad.gui.NotepadFrame;
import notepad.utility.FileOperation;

public class TextAreaDocumentListener implements DocumentListener {
	
	private FileOperation fileHandler;
	
	public TextAreaDocumentListener(FileOperation fileHandler) {
		
		this.fileHandler = fileHandler;
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {

		fileHandler.setSaved(false);
		((NotepadFrame)NotepadFrame.getFrame()).getUndoMenu().setEnabled(true);
		((NotepadFrame)NotepadFrame.getFrame()).getPopupUndoMenu().setEnabled(true);
		
	}
	
	@Override
	public void removeUpdate(DocumentEvent e) {

		fileHandler.setSaved(false);
		((NotepadFrame)NotepadFrame.getFrame()).getUndoMenu().setEnabled(true);
		((NotepadFrame)NotepadFrame.getFrame()).getPopupUndoMenu().setEnabled(true);
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {

		fileHandler.setSaved(false);
		((NotepadFrame)NotepadFrame.getFrame()).getUndoMenu().setEnabled(true);
		((NotepadFrame)NotepadFrame.getFrame()).getPopupUndoMenu().setEnabled(true);
	}

}