package notepad.constants;

import java.util.Locale;

import notepad.gui.NotepadPopupMenu;
import notepad.utility.GetStaticData;

public interface PopopMenuConstants {

	NotepadPopupMenu undoEdit = new NotepadPopupMenu(GetStaticData.getStaticData("notepad.menu.edit.undo", "resources.staticdata", Locale.getDefault()), "2", "2.1", false, ' ', 'Z');
	NotepadPopupMenu redoEdit = new NotepadPopupMenu(GetStaticData.getStaticData("notepad.menu.edit.redo", "resources.staticdata", Locale.getDefault()), "2", "2.2", false, ' ', 'Y');
	NotepadPopupMenu cutEdit = new NotepadPopupMenu(GetStaticData.getStaticData("notepad.menu.edit.cut", "resources.staticdata", Locale.getDefault()), "2", "2.3", false, ' ', 'X');
	NotepadPopupMenu copyEdit = new NotepadPopupMenu(GetStaticData.getStaticData("notepad.menu.edit.copy", "resources.staticdata", Locale.getDefault()), "2", "2.4", false, ' ', 'C');
	NotepadPopupMenu pasteEdit = new NotepadPopupMenu(GetStaticData.getStaticData("notepad.menu.edit.paste", "resources.staticdata", Locale.getDefault()), "2", "2.5", false, ' ', 'V');
	NotepadPopupMenu selectAllEdit = new NotepadPopupMenu(GetStaticData.getStaticData("notepad.menu.edit.selectAll", "resources.staticdata", Locale.getDefault()), "2", "2.6", false, ' ', 'A');
	
	Object [] popupMenuItems = {undoEdit, redoEdit, cutEdit, copyEdit, pasteEdit, selectAllEdit};
}
