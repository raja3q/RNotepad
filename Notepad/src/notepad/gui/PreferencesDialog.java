package notepad.gui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class PreferencesDialog {
	
	
	private static JDialog jDialog = null;
	private static PreferencesDialog preferencesObject = null;
	
	public static PreferencesDialog getPreferencesObject() {
		
		if(jDialog != null)
			jDialog.setVisible(true);
		return preferencesObject;
	}
	
	static {
		
		preferencesObject = new PreferencesDialog();
	}
	
	public static JDialog getPreferencesDialog() {
	
		return jDialog;
	}
	
	private PreferencesDialog() {
		
		jDialog = new JDialog(NotepadFrame.getFrame());
		init();
	}
	
	private void init() {

		jDialog.setTitle("Settings");
		jDialog.setVisible(true);
		jDialog.setResizable(false);
		
		createAndAddComponents();
		
		jDialog.setBounds(100, 100, 600, 300);
	}
	
	private void createAndAddComponents() {

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		jDialog.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel Font = new FontPanel();
		tabbedPane.addTab("Font", Font);
		
		JPanel Theme = new ThemePanel();
		tabbedPane.addTab("Theme", Theme);
	}
}
