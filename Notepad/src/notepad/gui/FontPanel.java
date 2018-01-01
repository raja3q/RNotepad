package notepad.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import notepad.listeners.CancelButtonListener;
import notepad.listeners.FontChooserActionListener;
import notepad.listeners.FontViewerListener;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class FontPanel extends JPanel {

	private String[] fontStyles={"Regular","Bold","Italic","Bold Italic"};

	private final JPanel contentPanel = new JPanel();

	private JTextField fontNameTxt;

	private JTextField fontStyleTxt;

	private JDialog fontDialog;

	private JTextField fontSizeTxt;

	private JComboBox fontNamesCombo;

	private static FontPanel fontPanel;

	private JComboBox fontStyleCombo;

	private JComboBox fontSizeCombo;

	private JEditorPane fontViewer;

	public JEditorPane getFontViewer() {

		return fontViewer;
	}

	public static FontPanel getFontPanel() {

		return fontPanel;
	}

	public JComboBox getFontNamesCombo() {

		return fontNamesCombo;
	}

	public JComboBox getFontStyleCombo() {

		return fontStyleCombo;
	}

	public JComboBox getFontSizeCombo() {

		return fontSizeCombo;
	}

	public JDialog getFontDialog() {
		return fontDialog;
	}

	public JTextField getFontNameTxt() {
		return fontNameTxt;
	}

	public JTextField getFontStyleTxt() {
		return fontStyleTxt;
	}

	public JTextField getFontSizeTxt() {
		return fontSizeTxt;
	}

	public FontPanel() {

		fontPanel = this;
		//fontDialog = new JDialog(owener, model);
		//fontDialog.setTitle("Preferences");
		createAndAddComponents();
		this.setVisible(true);
	}

	private void createAndAddComponents() {

		this.setLayout(new BorderLayout());
		setBorder(new EmptyBorder(5, 5, 5, 5));
		this.add(contentPanel, BorderLayout.CENTER);

		JLabel lblFont = new JLabel("Font :");

		JLabel lblFontStyle = new JLabel("Font Style :");

		JLabel lblSize = new JLabel("Size :");

		fontNameTxt = new JTextField();
		fontNameTxt.setEnabled(false);
		fontNameTxt.setColumns(10);

		fontStyleTxt = new JTextField();
		fontStyleTxt.setEnabled(false);
		fontStyleTxt.setColumns(10);

		fontSizeTxt = new JTextField();
		fontSizeTxt.setEnabled(false);
		fontSizeTxt.setColumns(10);

		this.fontNamesCombo = new JComboBox();
		addFontNameComboValues();

		this.fontStyleCombo = new JComboBox();
		addFontStyleComboValues();

		this.fontSizeCombo = new JComboBox();
		addFontSizeValues();

		fontViewer = new JEditorPane();
		fontViewer.setEditable(false);

		fontViewer.setText("Aa Bb Cc Dd Ee Ff");

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
				gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(fontViewer, Alignment.LEADING)
								.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
												.addComponent(lblFont)
												.addComponent(fontNameTxt)
												.addComponent(fontNamesCombo, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
										.addGap(40)
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(fontStyleTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblFontStyle)
												.addComponent(fontStyleCombo, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE))
										.addGap(26)
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(fontSizeTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblSize)
												.addComponent(fontSizeCombo, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(59, Short.MAX_VALUE))
				);
		gl_contentPanel.setVerticalGroup(
				gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblFont)
								.addComponent(lblSize)
								.addComponent(lblFontStyle))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
										.addComponent(fontNameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(fontNamesCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPanel.createSequentialGroup()
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
												.addComponent(fontSizeTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(fontStyleTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(fontSizeCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(fontStyleCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGap(33)
						.addComponent(fontViewer, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
						.addGap(29))
				);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			this.add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");  
				buttonPane.add(okButton);
				okButton.addActionListener(new FontChooserActionListener());
			}
			{
				JButton cancelButton = new JButton("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new CancelButtonListener(PreferencesDialog.getPreferencesDialog()));
			}
		}

		//fontDialog.setBounds(100, 100, 500, 300);
		//fontDialog.setResizable(false);

		fontNamesCombo.addActionListener(new FontViewerListener());
		fontSizeCombo.addActionListener(new FontViewerListener());
		fontStyleCombo.addActionListener(new FontViewerListener());

		setDefaultFontProperties();
	}

	private void addFontNameComboValues() {

		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		for(int i = 0; i < fontNames.length; i ++) {

			fontNamesCombo.addItem(fontNames[i]);
		}
	}

	private void addFontStyleComboValues() {

		for(int i = 0; i < fontStyles.length; i ++) {

			fontStyleCombo.addItem(fontStyles[i]);
		}
	}

	private void addFontSizeValues() {

		for(int j=0; j<30; j++)

			fontSizeCombo.addItem(new String(10+j*2+""));
	}

	private void setDefaultFontProperties() {

		String fontName = null;
		int fontSize = 0;
		int fontStyle = 0;

		NotepadFrame notepadFrame = (NotepadFrame) NotepadFrame.getFrame();
		Font font = notepadFrame.getTextArea().getFont();

		fontName = font.getName();

		fontStyle = font.getStyle();

		fontSize = font.getSize();

		fontNamesCombo.setSelectedItem(fontName);
		fontStyleCombo.setSelectedItem(fontStyles[fontStyle]);
		fontSizeCombo.setSelectedItem(fontSize+"");


		fontNameTxt.setText(fontName);
		fontStyleTxt.setText(fontStyles[fontStyle]);
		fontSizeTxt.setText(fontSize+"");
	}

}
