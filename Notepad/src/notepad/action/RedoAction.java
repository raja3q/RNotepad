package notepad.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoManager;

import notepad.listeners.TextAreaUndoRedoManager;

@SuppressWarnings("serial")
public class RedoAction extends AbstractAction {
	
	private UndoManager undoManager = TextAreaUndoRedoManager.getUndoManager();
	private UndoAction undoAction = TextAreaUndoRedoManager.getUndoAction();
	
	public RedoAction() {
		
		this.putValue(Action.NAME, undoManager.getRedoPresentationName());
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_MASK));
		this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.isEnabled()) {
			undoManager.redo();
			undoAction.update();
			this.update();
		}
	}
	
	public void update() {
		//this.putValue(Action.NAME, undoManager.getRedoPresentationName());
		this.setEnabled(undoManager.canRedo());
	}
}