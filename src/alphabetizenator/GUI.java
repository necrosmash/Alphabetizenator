package alphabetizenator;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUI {
	// Declare everything
	private JFrame jfFrame;
	public static JTextArea jtaAlphabetizedText;
	private JScrollPane jspJTA;
	public JPanel jpnlChooseFile; 
	public JButton jbtnSelectFile, jbtnClear, jbtnCopyText;
	public JCheckBox jcbReverseOrder;
	public JFileChooser jfcDialog;
	File fInputFile;
	StringSelection stringSelection;
	Clipboard clipboard;

	public GUI() {
		// Set up the frame
		jfFrame = new JFrame();
		jfFrame.setTitle("Alphabizenator");
		jfFrame.setSize(550, 600);
		jfFrame.setResizable(false);
		jfFrame.setLayout(new FlowLayout());

		// Set up the panel
		jpnlChooseFile = new JPanel(new FlowLayout(FlowLayout.LEADING));
		jpnlChooseFile.setBorder(new javax.swing.border.TitledBorder("Choose which file you want to alphabetize..."));
		jpnlChooseFile.setPreferredSize(new java.awt.Dimension(500, 550));
		jpnlChooseFile.setMinimumSize(new java.awt.Dimension(500, 100));

		// Instantiate jbtnSelectFile & jbtnClear
		jbtnSelectFile = new JButton("Select file...");
		jbtnClear = new JButton("Clear text");
		jbtnCopyText = new JButton("Copy text");

		// Add the jbtnSelectFile & jbtnClear to jpnlChooseFile
		jpnlChooseFile.add(jbtnSelectFile);
		jpnlChooseFile.add(jbtnClear);
		jpnlChooseFile.add(jbtnCopyText);
		
		// Instantiate & add cbReverseOrder
		jcbReverseOrder = new JCheckBox("Reverse words' order on a per-line basis");
		jpnlChooseFile.add(jcbReverseOrder);
		
		// Set jtaAlphabetizedText
		jtaAlphabetizedText = new JTextArea();
		jtaAlphabetizedText.setPreferredSize(new java.awt.Dimension(480, 460));
		jtaAlphabetizedText.setMinimumSize(new java.awt.Dimension(480, 460));
		jtaAlphabetizedText.setMaximumSize(new java.awt.Dimension(480, 460));
		
		// Make jtaAlphabetizedText scrollable
		jspJTA = new JScrollPane(jtaAlphabetizedText);
		jspJTA.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// Add jtaAlphabetizedText
		jpnlChooseFile.add(jtaAlphabetizedText);

		// Instantiate jfcDialog
		jfcDialog = new JFileChooser();

		// Set up the listener for jbtnSelectFile
		jbtnSelectFile.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int returnVal = jfcDialog.showOpenDialog(jfFrame);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// Get the file
					fInputFile = jfcDialog.getSelectedFile();

					// Pass the file to the Functionality class
					Functionality.readFile(fInputFile);
				}
			}
		});

		// Set up listener for jbtnClear
		jbtnClear.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				jtaAlphabetizedText.setText(null);
			}
		});

		// Set up listener for jbtnCopyText
		jbtnCopyText.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				stringSelection = new StringSelection(GUI.jtaAlphabetizedText.getText());
				clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
			}
		});

		// Last GUI constructor statement
		// adds panel & sets jfFrame to visible
		jfFrame.getContentPane().add(jpnlChooseFile);
		jfFrame.setVisible(true);
	}

	public File getFile() {
		return this.fInputFile;
	}

	public void addText(List<String> lEntries) {
		for (int cnt = 0; cnt < lEntries.size(); cnt++) {
			jtaAlphabetizedText.append(lEntries.get(cnt) + "\n");
		}
	}
	
	public boolean cbReverseOrderIsTicked() {
		return jcbReverseOrder.isSelected();
	}
}
