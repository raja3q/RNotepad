package notepad.listeners;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JTextArea;

import notepad.gui.FontPanel;
import notepad.gui.NotepadFrame;
import notepad.gui.PreferencesDialog;
import resources.NotepadProperties;

public class FontChooserActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent ae) {

		FontPanel fontPanel = FontPanel.getFontPanel();
		NotepadFrame frame = (NotepadFrame) NotepadFrame.getFrame();
		
		JTextArea textArea = frame.getTextArea();

		if(ae.getActionCommand().equalsIgnoreCase("Ok")) {

			String fontName = fontPanel.getFontNamesCombo().getSelectedItem().toString();
			String fontSize = fontPanel.getFontSizeCombo().getSelectedItem().toString();
			String fontStyle = fontPanel.getFontStyleCombo().getSelectedItem().toString();
			
			Properties properties = new Properties();
			OutputStream fout = null;
			try {
				fout = new FileOutputStream(NotepadProperties.RESOURCE_PATH + "/resources/preferences.properties");
				properties.setProperty("fontName", fontName);
				properties.setProperty("fontSize", fontSize);
				properties.setProperty("fontStyle", fontStyle);
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
			
			Font font = new Font(fontName, setFontStyle(fontStyle), Integer.parseInt(fontSize));
			textArea.setFont(font);
			
			PreferencesDialog.getPreferencesDialog().dispose();
		}
		
		frame.getTextArea().requestFocus(true);
	}
	
	private int setFontStyle(String fontStyle) {

		int style = 0;
		
		if(fontStyle.equalsIgnoreCase("Regular"))
			style = Font.PLAIN;
		
		if(fontStyle.equalsIgnoreCase("Italic"))
			style = Font.ITALIC;
		
		if(fontStyle.equalsIgnoreCase("Bold"))
			style = Font.BOLD;
		
		if(fontStyle.equalsIgnoreCase("Bold Italic"))
			style = Font.BOLD + Font.ITALIC;
		return style;
	}
}
