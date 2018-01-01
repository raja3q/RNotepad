package notepad.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import notepad.listeners.CancelButtonListener;
import notepad.listeners.ThemeChangeActionListener;

@SuppressWarnings({"rawtypes", "unchecked", "serial"})
public class ThemePanel extends JPanel {



	private final JPanel contentPanel = new JPanel();
	JComboBox themeCombo = null;

	public JComboBox getThemeCombo() {
	
		return themeCombo;
	}
	public ThemePanel() {

		//themeDialog = new JDialog(owener, model);
		//themeDialog.setTitle("Theme");
		createAndAddComponents();
		this.setVisible(true);
	}

	private void createAndAddComponents() {

		this.setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.add(contentPanel);
		
		contentPanel.setLayout(new FlowLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			this.add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(new ThemeChangeActionListener(this));
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new CancelButtonListener(PreferencesDialog.getPreferencesDialog()));
				buttonPane.add(cancelButton);
			}
		}
		
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTheme = new JLabel("Select Theme :");
		this.themeCombo = new JComboBox();
		setThemeComboValues();
		
		contentPanel.add(lblTheme);
		contentPanel.add(themeCombo);


		/*themeDialog.setBounds(100, 100, 500, 300);
		themeDialog.setResizable(false);*/

		setDefaultTheme();
	}
	
	private void setThemeComboValues() {

		LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
		for(int i = 0; i < info.length; i ++) {
			
			themeCombo.addItem(info[i].getName());
		}
	}
	
	private void setDefaultTheme() {
		
		LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
		
		String name = lookAndFeel.getID();
		
		if("GTK".equalsIgnoreCase(name))
			name = "GTK+";
		
		if("Motif".equalsIgnoreCase(name))
			name = "CDE/Motif";
		
		themeCombo.setSelectedItem(name);
	}

}
