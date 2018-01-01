package notepad.gui;

import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

@SuppressWarnings({"rawtypes", "unchecked"})
public class NotepadPopupMenu {

	private JMenuItem menuItem;
	private static Map menus = new Hashtable();

	public JMenuItem getMenuItem() {
	
		return menuItem;
	}
	
	public static Map getMenus() {
		return menus;
	}

	public NotepadPopupMenu(String menuName, String parentId, String childID, boolean isParent, char mnemonic, char shot_key) {

		menuItem = new JMenuItem(menuName);
		menus.put(parentId, menuItem);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(shot_key,ActionEvent.CTRL_MASK));
	}
	
}
