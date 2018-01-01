package notepad.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class FindDialog extends JPanel implements ActionListener {

	private static FindDialog findDialog;
	private JTextArea jta;
	private int lastIndex;
	private JLabel replaceWithLabel;
	private TextField findWhat;
	private JTextField replaceWithTxt;
	private JCheckBox matchCase;
	private JRadioButton up, down;
	private JButton findNextButton, replaceButton, replaceAllButton, cancelButton;
	private JPanel direction, replaceButtonPanel;
	private boolean ok;
	private JDialog dialog;
	
	public static FindDialog getFindDialog(JTextArea jta, boolean isFind) {
	
		if(findDialog == null & isFind)
			findDialog = new FindDialog(jta);
		
		else 
			findDialog.setVisible(true);
		
		return findDialog;
	}
	
	private FindDialog(JTextArea jta) {

		this.jta=jta;
		findWhat=new TextField(20);
		replaceWithTxt=new JTextField(20);

		matchCase=new JCheckBox("Match case");

		up=new JRadioButton("Up");
		down=new JRadioButton("Down");

		down.setSelected(true);
		ButtonGroup bg=new ButtonGroup();
		bg.add(up);
		bg.add(down);

		direction=new JPanel();
		Border etched=BorderFactory.createEtchedBorder();
		Border titled=BorderFactory.createTitledBorder(etched,"Direction");
		direction.setBorder(titled);
		direction.setLayout(new GridLayout(1,2));
		direction.add(up);
		direction.add(down);

		JPanel southPanel=new JPanel();
		southPanel.setLayout(new GridLayout(1,2));
		southPanel.add(matchCase);
		southPanel.add(direction);


		findNextButton=new JButton("Find Next");
		replaceButton=new JButton("Replace");
		replaceAllButton=new JButton("Replace All");
		cancelButton=new JButton("Cancel");
		replaceButtonPanel=new JPanel();
		replaceButtonPanel.setLayout(new GridLayout(4,1));
		replaceButtonPanel.add(findNextButton);
		replaceButtonPanel.add(replaceButton);
		replaceButtonPanel.add(replaceAllButton);
		replaceButtonPanel.add(cancelButton);

		JPanel textPanel=new JPanel();
		textPanel.setLayout(new GridLayout(3,2));
		textPanel.add(new JLabel("Find what "));
		textPanel.add(findWhat);
		textPanel.add(replaceWithLabel=new JLabel("Replace With "));
		textPanel.add(replaceWithTxt);
		textPanel.add(new JLabel(" "));								//dummy Lable
		textPanel.add(new JLabel(" "));								//dummy Lable

		setLayout(new BorderLayout());

		add(new JLabel("       "),BorderLayout.NORTH);				//dummy label
		add(textPanel,BorderLayout.CENTER);
		add(replaceButtonPanel,BorderLayout.EAST);
		add(southPanel,BorderLayout.SOUTH);

		setSize(200,200);

		findNextButton.addActionListener(this);
		replaceButton.addActionListener(this);
		replaceAllButton.addActionListener(this);

		cancelButton.addActionListener(new ActionListener()	{

			public void actionPerformed(ActionEvent ev) {
				dialog.setVisible(false);
			}
		});

		findWhat.addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent te) {

				enableDisableButtons();
			}
		});

		findWhat.addTextListener(new TextListener() {

			public void textValueChanged(TextEvent te) {

				enableDisableButtons();
			}
		});

	}

	public void enableDisableButtons() {

		if(findWhat.getText().length()==0) {

			findNextButton.setEnabled(false);
			replaceButton.setEnabled(false);
			replaceAllButton.setEnabled(false);
		}

		else {

			findNextButton.setEnabled(true);
			replaceButton.setEnabled(true);
			replaceAllButton.setEnabled(true);
		}
	}

	public void actionPerformed(ActionEvent ev) {

		if(ev.getSource()==findNextButton)
			findNextWithSelection();
		else if(ev.getSource()==replaceButton)
			replaceNext();
		else if(ev.getSource()==replaceAllButton)
			JOptionPane.showMessageDialog(null,"Total replacements made= "+replaceAllNext());

	}

	public int findNext() {

		String s1=jta.getText();
		String s2=findWhat.getText();

		lastIndex=jta.getCaretPosition();

		int selStart=jta.getSelectionStart();
		int selEnd=jta.getSelectionEnd();

		if(up.isSelected())	{

			if(selStart!=selEnd)
				lastIndex=selEnd-s2.length()-1;

			/*****Notepad doesnt use the else part, but it should be, instead of using caretPosition.***
			else
				lastIndex=lastIndex-s2.length();
			 ******/

			if(!matchCase.isSelected())
				lastIndex=s1.toUpperCase().lastIndexOf(s2.toUpperCase(),lastIndex);

			else
				lastIndex=s1.lastIndexOf(s2,lastIndex);	
		}

		else {

			if(selStart!=selEnd)
				lastIndex=selStart+1;
			if(!matchCase.isSelected())
				lastIndex=s1.toUpperCase().indexOf(s2.toUpperCase(),lastIndex);
			else
				lastIndex=s1.indexOf(s2,lastIndex);	
		}

		return lastIndex;
	}

	public void findNextWithSelection() {

		int idx=findNext();

		if(idx!=-1)	{

			jta.setSelectionStart(idx);
			jta.setSelectionEnd(idx+findWhat.getText().length());
		}

		else
			JOptionPane.showMessageDialog(this,
					"Cannot find"+" \""+findWhat.getText()+"\"",
					"Find",JOptionPane.INFORMATION_MESSAGE);
	}

	public void replaceNext() {

		// if nothing is selectd
		if(jta.getSelectionStart()==jta.getSelectionEnd()) {

			findNextWithSelection();
			return;
		}

		String searchText=findWhat.getText();
		String temp=jta.getSelectedText();	//get selected text

		//check if the selected text matches the search text then do replacement

		if(
				(matchCase.isSelected() && temp.equals(searchText))
				||
				(!matchCase.isSelected() && temp.equalsIgnoreCase(searchText))
				)
			jta.replaceSelection(replaceWithTxt.getText());

		findNextWithSelection();
	}

	public int replaceAllNext()	{

		if(up.isSelected())
			jta.setCaretPosition(jta.getText().length()-1);
		else
			jta.setCaretPosition(0);

		int idx=0;
		int counter=0;

		do {

			idx=findNext();
			if(idx==-1) break;
			counter++;
			jta.replaceRange(replaceWithTxt.getText(),idx,idx+findWhat.getText().length());
		} while(idx!=-1);

		return counter;
	}

	public boolean showDialog(Component parent, boolean isFind ) {

		Frame owner=null;

		if(parent instanceof Frame) 
			owner=(Frame)parent;

		else
			owner=(Frame)SwingUtilities.getAncestorOfClass(Frame.class,parent);

		if(dialog == null || dialog.getOwner() != owner) {

			dialog = new JDialog(owner,false);
			dialog.add(this);
			//dialog.getRootPane().setDefaultButton(findNextButton);
		}

		if(findWhat.getText().length()==0)
			findNextButton.setEnabled(false);

		else
			findNextButton.setEnabled(true);

		replaceButton.setVisible(false);
		replaceAllButton.setVisible(false);
		replaceWithTxt.setVisible(false);
		replaceWithLabel.setVisible(false);

		if(isFind) {

			//card.show(buttonPanel,"find");
			dialog.setSize(460,180);
			dialog.setTitle("Find");
		}
		else {

			replaceButton.setVisible(true);
			replaceAllButton.setVisible(true);
			replaceButton.setEnabled(false);
			replaceAllButton.setEnabled(false);
			replaceWithTxt.setVisible(true);
			replaceWithLabel.setVisible(true);

			//card.show(buttonPanel,"replace");
			dialog.setSize(450,200);
			dialog.setTitle("Replace");
		}

		dialog.setVisible(true);
		dialog.setResizable(false);
		//System.out.println(dialog.getWidth()+" "+dialog.getHeight());
		return ok;
	}
}