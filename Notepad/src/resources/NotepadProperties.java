package resources;

import java.awt.Color;

public interface NotepadProperties {

	//public String RESOURCE_PATH = System.getProperty("user.dir");
	//public String RESOURCE_PATH = System.getProperty("user.dir")+"/softwares/RNotepad";
	public String RESOURCE_PATH = "/home/raja/softwares/RNotepad";
	public int[] bounds = {184, 52, 964, 600};
	public Color menuBarColor = Color.GREEN;
	public Color statusBarColor = Color.LIGHT_GRAY;
	public Color sidePanelColor = Color.LIGHT_GRAY;
	public boolean showStatusBar = true;
	public boolean wordWrap = false;
}
