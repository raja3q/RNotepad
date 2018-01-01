package notepad.constants;

import notepad.gui.NotepadMenu;
import notepad.utility.NotepadConstants;

public interface MenuConstants extends NotepadConstants {
	
	//NotepadMenu fileMenu = new NotepadMenu(FILE, "1", "", true, 'F', ' ');
	//NotepadMenu newFile = new NotepadMenu(NotepadConstants.new_, "1", "1.1", false, ' ', 'N');
	//NotepadMenu openFile = new NotepadMenu(NotepadConstants.open, "1", "1.2", false, ' ', 'O');
	//NotepadMenu saveFile = new NotepadMenu(NotepadConstants.save, "1", "1.3", false, ' ', 'S');
	//NotepadMenu saveAsFile = new NotepadMenu(SAVEAS, "1", "1.4", false, ' ', 'S');
	//NotepadMenu printFile = new NotepadMenu(PRINT, "1", "1.5", false, ' ', 'P');
	//NotepadMenu closeFile = new NotepadMenu(NotepadConstants.close, "1", "1.5", false, ' ', 'C');
		
	NotepadMenu editMenu = new NotepadMenu(EDIT, "2", "", true, 'E', ' ');
	NotepadMenu undoEdit = new NotepadMenu(UNDO, "2", "2.1", false, ' ', 'Z');
	NotepadMenu redoEdit = new NotepadMenu(REDO, "2", "2.2", false, ' ', 'Y');
	/*NotepadMenu cutEdit = new NotepadMenu(NotepadConstants.cut, "2", "2.3", false, ' ', 'X');
	NotepadMenu copyEdit = new NotepadMenu(NotepadConstants.copy, "2", "2.4", false, ' ', 'C');
	NotepadMenu pasteEdit = new NotepadMenu(NotepadConstants.paste, "2", "2.5", false, ' ', 'V');
	NotepadMenu selectAllEdit = new NotepadMenu(NotepadConstants.selectAll, "2", "2.6", false, ' ', 'A');
	NotepadMenu timeDateEdit = new NotepadMenu(NotepadConstants.timeAndDate, "2", "2.7", false, ' ', ' ');*/
	
	/*NotepadMenu viewMenu = new NotepadMenu(NotepadConstants.viewMenu, "3", "", true, 'V', ' ');	
	NotepadMenu wordWrapView = new NotepadMenu(NotepadConstants.wordWrap, "3", "3.1", false, ' ', ' ', true);*/
	//NotepadMenu statusBarView = new NotepadMenu(NotepadConstants.statusbar, "3", "3.2", false, ' ', ' ', true);
	
	NotepadMenu searchMenu = new NotepadMenu(SEARCH, "4", "", true, 'A', ' ');		
	NotepadMenu findSearch = new NotepadMenu(FIND, "4", "4.1", false, ' ', 'F');
	NotepadMenu replaceSearch = new NotepadMenu(REPLACE, "4", "4.2", false, ' ', 'H');
	NotepadMenu gotoSearch = new NotepadMenu(GOTO, "4", "4.3", false, ' ', 'G');
	
	/*NotepadMenu settingsMenu = new NotepadMenu(NotepadConstants.settingsMenu, "5", "", true, 'T', ' ');
	NotepadMenu preferences = new NotepadMenu(NotepadConstants.SETTINGS, "5", "5.3", false, ' ', ' ');*/
	
	/*NotepadMenu helpMenu = new NotepadMenu(NotepadConstants.helpMenu, "6", "", true, 'H', ' ');	
	NotepadMenu help = new NotepadMenu(NotepadConstants.help, "6", "6.1", false, ' ', ' ');*/
	//NotepadMenu about = new NotepadMenu(NotepadConstants.about, "6", "6.2", false, ' ', ' ');
	
	Object[] menuObjects = new Object[]{editMenu, searchMenu};	
	
	Object [] SEARCHMENUS = {findSearch, replaceSearch, gotoSearch};
}
