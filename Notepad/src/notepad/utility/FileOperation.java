package notepad.utility;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.undo.UndoManager;

import notepad.gui.NotepadFrame;
import notepad.listeners.TextAreaDocumentListener;
import notepad.listeners.TextAreaUndoRedoManager;

public class FileOperation {

	private NotepadFrame notepadFrame;
	private boolean saved;
	private boolean newFileFlag;
	private String fileName;
	private String applicationTitle = NotepadConstants.title;
	private File fileRef;
	private JFileChooser chooser;
	private static FileOperation fileHandler;
	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

	public static FileOperation getFileHandler() {

		return fileHandler;
	}

	public boolean isSaved() {

		return saved;
	}

	public void setSaved(boolean saved) {

		this.saved = saved;
	}

	public String getFileName() {

		return new String(fileName);
	}

	public void setFileName(String fileName) { 

		this.fileName = new String(fileName);
	}

	public FileOperation(NotepadFrame notepadFrame) {

		this.notepadFrame = notepadFrame;

		saved = true;
		newFileFlag = true;
		fileName = new String("Untitled");
		fileRef = new File(fileName);
		this.notepadFrame.setTitle(fileName+" - "+applicationTitle);

		chooser = new JFileChooser();
		//chooser.addChoosableFileFilter(new MyFileFilter(".java","Java Source Files(*.java)"));
		//chooser.addChoosableFileFilter(new MyFileFilter(".txt","Text Files(*.txt)"));
		chooser.setCurrentDirectory(new File("."));
		fileHandler = this;
	}

	public boolean saveFile(File temp) {

		FileWriter fout = null;

		try	{

			fout = new FileWriter(temp);
			fout.write(notepadFrame.getTextArea().getText());
		}

		catch(IOException ioe) {

			updateStatus(temp,false);
			return false;
		}
		finally	{
			try {

				fout.close();
			} catch(IOException excp){}
		}

		updateStatus(temp,true);
		notepadFrame.getTextArea().requestFocus(true);
		return true;
	}

	public boolean saveThisFile() {

		if(!newFileFlag) {

			return saveFile(fileRef);}

		return saveAsFile();
	}

	public boolean saveAsFile() {

		File temp = null;
		chooser.setDialogTitle("Save As...");
		chooser.setApproveButtonText("Save Now"); 
		chooser.setApproveButtonToolTipText("Click me to save!");

		do {

			if(chooser.showSaveDialog(this.notepadFrame) != JFileChooser.APPROVE_OPTION)
				return false;
			temp = chooser.getSelectedFile();
			if(!temp.exists()) break;
			if(JOptionPane.showConfirmDialog(
					this.notepadFrame,"<html>"+temp.getPath()+" already exists.<br>Do you want to replace it?<html>",
					"Save As",JOptionPane.YES_NO_OPTION
					) == JOptionPane.YES_OPTION)
				break;
		} while(true);


		return saveFile(temp);
	}

	public boolean openFile(File temp)	{

		FileInputStream fin = null;
		BufferedReader din = null;

		try {

			fin = new FileInputStream(temp);
			din = new BufferedReader(new InputStreamReader(fin));
			String str = " ";
			while(str != null) {

				str=din.readLine();
				if(str == null)
					break;
				notepadFrame.getTextArea().append(str+"\n");
			}

		}
		catch(IOException ioe) {

			updateStatus(temp,false);
			return false;
		} finally {

			try {

				din.close();
				fin.close();
			} catch(IOException excp){}
		}
		updateStatus(temp,true);
		notepadFrame.getTextArea().setCaretPosition(0);
		return true;
	}

	public void openFile()	{

		if(!confirmSave()) return;
		chooser.setDialogTitle("Open File...");
		chooser.setApproveButtonText("Open this"); 
		chooser.setApproveButtonToolTipText("Click me to open the selected file.!");

		File temp=null;
		do {

			if(chooser.showOpenDialog(this.notepadFrame)!=JFileChooser.APPROVE_OPTION)
				return;
			temp = chooser.getSelectedFile();

			if(temp.exists())	break;

			JOptionPane.showMessageDialog(this.notepadFrame,
					"<html>"+temp.getName()+"<br>file not found.<br>"+
							"Please verify the correct file name was given.<html>",
							"Open",	JOptionPane.INFORMATION_MESSAGE);

		} while(true);

		notepadFrame.getTextArea().setText("");

		if(!openFile(temp))	{

			fileName = "Untitled"; saved=true; 
			this.notepadFrame.setTitle(fileName+" - "+applicationTitle);
		}

		if(!temp.canWrite())
			newFileFlag = true;

		notepadFrame.getTextArea().requestFocus(true);
	}

	public void updateStatus(File temp,boolean saved) {

		if(saved) {

			this.saved = true;
			fileName = new String(temp.getName());
			if(!temp.canWrite()) {

				fileName += "(Read only)";
				newFileFlag = true;
			}

			fileRef = temp;
			notepadFrame.setTitle(fileName + " - "+applicationTitle);
			//notepadFrame.getStatusBar().setText("File : "+temp.getPath()+" saved/opened successfully.");
			newFileFlag=false;
		}
		else {

			//notepadFrame.getStatusBar().setText("Failed to save/open : "+temp.getPath());
		}
	}

	public boolean confirmSave() {

		String strMsg = "<html>The text in the " + fileName + " file has been changed.<br>"+
				"Do you want to save the changes?<html>";

		if(!saved) {

			int x = JOptionPane.showConfirmDialog(this.notepadFrame,strMsg,applicationTitle,JOptionPane.YES_NO_CANCEL_OPTION);

			if(x == JOptionPane.CANCEL_OPTION) return false;
			if(x == JOptionPane.YES_OPTION && !saveAsFile()) return false;
		}
		return true;
	}

	public void newFile() {

		JTextArea textArea = notepadFrame.getTextArea();

		if(!confirmSave()) return;

		Document olDocument = notepadFrame.getTextArea().getDocument();
		if(olDocument != null) {
			olDocument.removeUndoableEditListener(notepadFrame.getTextAreaUndoRedoManager());
			olDocument.removeDocumentListener(notepadFrame.getTextAreaDocumentListener());
		}

		textArea.setDocument(new PlainDocument());

		notepadFrame.getUndoMenu().setEnabled(false);
		notepadFrame.getRedoMenu().setEnabled(false);

		notepadFrame.getPopupUndoMenu().setEnabled(false);
		notepadFrame.getPopupRedoMenu().setEnabled(false);

		TextAreaUndoRedoManager textAreaUndoRedoManager = new TextAreaUndoRedoManager();

		//To discard all edits of old document
		UndoManager undoManager = TextAreaUndoRedoManager.getUndoManager();
		undoManager.discardAllEdits();

		//TextAreaDocumentListener textAreaDocumentListener = new TextAreaDocumentListener(this);

		//notepadFrame.setTextAreaDocumentListener(textAreaDocumentListener);
		notepadFrame.setTextAreaUndoRedoManager(textAreaUndoRedoManager);

		textArea.getDocument().addUndoableEditListener(textAreaUndoRedoManager);
		textArea.getDocument().addDocumentListener(new TextAreaDocumentListener(this));

		notepadFrame.revalidate();
		textArea.setText("");
		fileName = new String("Untitled");

		textArea.requestFocus(true);
		textArea.setTabSize(3);

		/*fileRef = new File(fileName);
		saved=true;
		newFileFlag = true;
		this.notepadFrame.setTitle(fileName+" - "+applicationTitle);*/
	}

	public void cut() {

		/*String selection = notepadFrame.getTextArea().getSelectedText();
		if(selection == null)
			return;

		StringSelection clipString = new StringSelection(selection);
		clipboard.setContents(clipString, clipString);
		notepadFrame.getTextArea().replaceSelection("");*/
		notepadFrame.getTextArea().cut();
	}

	public void copy() {

		notepadFrame.getTextArea().copy();
		/*String selection = notepadFrame.getTextArea().getSelectedText();
		if(selection == null)
			return;

		StringSelection clipString = new StringSelection(selection);
		clipboard.setContents(clipString, clipString);*/
	}

	public void paste() {

		/*Transferable clip_data = clipboard.getContents(this);
		try {

			String  clip_String = (String)clip_data.getTransferData(DataFlavor.stringFlavor);
			notepadFrame.getTextArea().replaceSelection(clip_String);
		} catch (UnsupportedFlavorException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		notepadFrame.getTextArea().paste();
	}

	public void selectAll() {

		notepadFrame.getTextArea().selectAll();
	}

	public void showTimeAndDate() {

		//JTextArea textArea = textArea;
		notepadFrame.getTextArea().append(Calendar.getInstance().getTime()+"");
	}

	public void print() {

		try {

			notepadFrame.getTextArea().print();
		} catch (PrinterException e) {

			e.printStackTrace();
		}
	}
}