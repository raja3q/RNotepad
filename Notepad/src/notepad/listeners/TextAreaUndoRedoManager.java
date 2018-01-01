package notepad.listeners;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

import notepad.action.RedoAction;
import notepad.action.UndoAction;

public class TextAreaUndoRedoManager implements UndoableEditListener {

	private static UndoManager undoManager = new UndoManager();
	private static UndoAction undoAction = new UndoAction();
	private static RedoAction redoAction = new RedoAction();
	
	public static void setUndoManager(UndoManager undoManager) {
		TextAreaUndoRedoManager.undoManager = undoManager;
	}

	public static UndoAction getUndoAction() {

		return undoAction;
	}
	
	public static RedoAction getRedoAction() {

		return redoAction;
	}

	public static UndoManager getUndoManager() {

		return undoManager;
	}
	
	@Override
	public void undoableEditHappened(UndoableEditEvent uee) {

		undoManager.addEdit(uee.getEdit());
		undoAction.update();
		redoAction.update();
	}
}
