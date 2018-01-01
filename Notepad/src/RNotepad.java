import javax.swing.SwingUtilities;

import notepad.gui.NotepadFrame;

public class RNotepad {

	public static void main(final String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				String filePath = null;

				if(args.length >= 1) {
					
					filePath = "";
					for (int i = 0; i < args.length; i ++) {
					
						filePath = filePath +" "+args[i];
					}
				}
				new NotepadFrame(filePath);
			}
		});
	}
}
