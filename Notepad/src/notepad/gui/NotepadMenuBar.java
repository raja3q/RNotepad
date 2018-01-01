package notepad.gui;

import javax.swing.JMenuBar;

import notepad.constants.MenuConstants;
import resources.NotepadProperties;

@SuppressWarnings("serial")
public class NotepadMenuBar extends JMenuBar {

	public NotepadMenuBar() {
		this.setBackground(NotepadProperties.menuBarColor);
		Object[] menus =  MenuConstants.menuObjects;
		for (int i = 0; i < menus.length; i++) {
			
			this.add(((NotepadMenu)menus[i]).getMenu());
		}
	}
}
