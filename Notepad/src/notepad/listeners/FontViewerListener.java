package notepad.listeners;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JEditorPane;

import notepad.gui.FontPanel;

public class FontViewerListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent ae) {

		FontPanel fontPanel = FontPanel.getFontPanel();
		JEditorPane fontViewer = fontPanel.getFontViewer();
		String fontName = fontPanel.getFontNamesCombo().getSelectedItem().toString();
		int fontSize = Integer.parseInt(fontPanel.getFontSizeCombo().getSelectedItem().toString());
		String fontStyle = fontPanel.getFontStyleCombo().getSelectedItem().toString();

		int fontStyleInt = 0;

		switch (fontStyle) {
		case "Regular":

			fontStyleInt = Font.PLAIN;
			break;

		case "Italic":

			fontStyleInt = Font.ITALIC;
			break;

		case "Bold":

			fontStyleInt = Font.BOLD;
			break;

		case "Bold Italic":

			fontStyleInt = Font.BOLD + Font.ITALIC;
			break;
		}
		
		fontPanel.getFontNameTxt().setText(fontName);
		fontPanel.getFontStyleTxt().setText(fontStyle);
		fontPanel.getFontSizeTxt().setText(fontSize+"");
		
		fontViewer.setFont(new Font(fontName, fontStyleInt, fontSize));
	}
}
