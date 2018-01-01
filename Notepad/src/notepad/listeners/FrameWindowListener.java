package notepad.listeners;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import notepad.utility.FileOperation;

public class FrameWindowListener extends WindowAdapter {

	private FileOperation fileHandler;
	
	public FrameWindowListener(FileOperation fileHandler) {

		this.fileHandler = fileHandler;
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		
		if(fileHandler.confirmSave()) {
			
			System.exit(0);
		}
	}
}
