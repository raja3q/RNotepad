package notepad.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoManager;

import notepad.listeners.TextAreaUndoRedoManager;

@SuppressWarnings("serial")
public class UndoAction extends AbstractAction {
	
	UndoManager undoManager = TextAreaUndoRedoManager.getUndoManager();
	
	public UndoAction() {
		
		this.putValue(Action.NAME, undoManager.getUndoPresentationName());
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
		this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.isEnabled()) {
			undoManager.undo();
			this.update();
			RedoAction redoAction = TextAreaUndoRedoManager.getRedoAction();
			redoAction.update();
		}
	}

	public void update() {
		//this.putValue(Action.NAME, undoManager.getUndoPresentationName());
		this.setEnabled(undoManager.canUndo());
	}
}