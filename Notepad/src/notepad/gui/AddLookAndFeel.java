package notepad.gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class AddLookAndFeel {

	private NotepadFrame notepadFrame = (NotepadFrame) NotepadFrame.getFrame();

	public AddLookAndFeel(String themeName) {

		LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();

		try {
			
			for (int i = 0; i < info.length; i++) {
				if(info[i].getName().equalsIgnoreCase(themeName)){
					
					System.out.println(info[i].getClassName());
					UIManager.setLookAndFeel(info[i].getClassName());
					SwingUtilities.updateComponentTreeUI(notepadFrame);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
