package notepad.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import notepad.gui.NotepadFrame;
import notepad.gui.PreferencesDialog;
import notepad.gui.ThemePanel;
import resources.NotepadProperties;

public class ThemeChangeActionListener implements ActionListener {

	private Object object = null;
	private NotepadFrame notepadFrame = (NotepadFrame) NotepadFrame.getFrame();
	
	public ThemeChangeActionListener(Object object) {

		this.object = object;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {

		if("ok".equalsIgnoreCase(ae.getActionCommand())) {

			if(object instanceof ThemePanel) {
				
				ThemePanel themePanel = (ThemePanel)object;
				String themeName = themePanel.getThemeCombo().getSelectedItem().toString();
				
				Properties properties = new Properties();
				OutputStream fout = null;
				try {
					fout = new FileOutputStream(NotepadProperties.RESOURCE_PATH+"/resources/theme.properties");
					properties.setProperty("themeName", themeName);
					properties.store(fout, "Font Properties");
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				} finally {
					
					try {
						fout.close();
					} catch (IOException e) {

						e.printStackTrace();
					}
				}
				
				LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
				try {
					
					for (int i = 0; i < info.length; i++) {
						if(info[i].getName().equalsIgnoreCase(themeName)){
							
							UIManager.setLookAndFeel(info[i].getClassName());
							SwingUtilities.updateComponentTreeUI(notepadFrame);
						}
						
						PreferencesDialog.getPreferencesDialog().dispose();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		}
		notepadFrame.getTextArea().requestFocus(true);
	}	
}
