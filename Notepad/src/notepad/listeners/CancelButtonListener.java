package notepad.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

import notepad.gui.NotepadFrame;

public class CancelButtonListener implements ActionListener {

	private JDialog jDialog;

	public CancelButtonListener(JDialog jDialog) {

		this.jDialog = jDialog;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if(ae.getActionCommand().equalsIgnoreCase("Cancel")) {

			jDialog.dispose();
		}
		((NotepadFrame)NotepadFrame.getFrame()).getTextArea().requestFocus(true);
	}
}
