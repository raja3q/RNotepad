package notepad.gui;

import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

@SuppressWarnings({"rawtypes", "unchecked"})
public class NotepadMenu {

	private JMenu menu;
	private JMenuItem menuItem;
	private static Map menus = new Hashtable();

	public JMenu getMenu() {

		return menu;
	}

	public JMenuItem getMenuItem() {

		return menuItem;
	}

	public NotepadMenu(String menuName, String parentId, String childID,boolean isParent, char mnemonic, char shot_key) {

		if(isParent) {

			this.menu = new JMenu(menuName);
			if(mnemonic != ' ') {

				menu.setMnemonic(mnemonic);
			}
			menus.put(parentId, menu);
		}

		else {

			menu = (JMenu) menus.get(parentId);
			JMenuItem menuItem = new JMenuItem(menuName);
			menu.add(menuItem);
			if(shot_key != ' ')

				menuItem.setAccelerator(KeyStroke.getKeyStroke(shot_key,ActionEvent.CTRL_MASK));

			if(menuName.equalsIgnoreCase("Save As")) {

				menuItem.setAccelerator(KeyStroke.getKeyStroke(shot_key,ActionEvent.ALT_MASK+ActionEvent.CTRL_MASK));
			}

			if(menuName.equalsIgnoreCase("Undo") | menuName.equalsIgnoreCase("Redo")) {

				menuItem.setEnabled(false);
			}
			menus.put(childID, menuItem);
			this.menuItem = menuItem;
		}

		/*if("open".equalsIgnoreCase(menuName) | "Save As".equalsIgnoreCase(menuName) | "Print".equalsIgnoreCase(menuName) | "Redo".equalsIgnoreCase(menuName)
				| "Paste".equalsIgnoreCase(menuName) | "Select All".equalsIgnoreCase(menuName)) {

			menu.addSeparator();
		}*/
	}

	/*public NotepadMenu(String menuName, String parentId, String childID,boolean isParent, char mnemonic, char shot_key, boolean checkBoxMenu) {

		if(checkBoxMenu) {

			menu = (JMenu) menus.get(parentId);
			JCheckBoxMenuItem checkboxMenuItem = new JCheckBoxMenuItem(menuName);
			menu.add(checkboxMenuItem);
		}
	}*/

}