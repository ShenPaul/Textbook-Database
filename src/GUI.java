/**
 *[GUI.java]
 * @author 072971120
 * @date
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI {

	//declares public variables
	public static JTabbedPane tabs = new JTabbedPane();
	//	public static JPanel tablePanel = new JPanel();
	public static JFrame mainWindow = new JFrame ("Textbook Database");
	public static ArrayList <JPanel> tablePanelList = new ArrayList <JPanel> (); //probably used for arraylist of databases
	//	public static int x=0;

	/**
	 *@
	 */
	public static void main(String[] args)  {


		//try catch statements
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException e) {
			// handle exception
		}
		catch (ClassNotFoundException e) {
			// handle exception
		}
		catch (InstantiationException e) {
			// handle exception
		}
		catch (IllegalAccessException e) {
			// handle exception
		}

		//warning to user to not access files while running the database program.
		JOptionPane.showMessageDialog(null, "Do not open database .csv files while program is running!", "WARNING!", JOptionPane.WARNING_MESSAGE);

		//sets GUI to full screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		//creates main JFrame
		mainWindow.setLayout(new BorderLayout());
		mainWindow.setBackground(Color.WHITE);
		//insert rhhs math logo as background picture?
		//yourFrameHere.setBackground(new Color(0, 0, 0, 0));
		//yourContentPaneHere.setOpaque(false);
		mainWindow.setSize((int)width, (int)height);
		mainWindow.setResizable(true);
		mainWindow.setAlwaysOnTop (true);
		mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		//creates menu on west panel
		JPanel menuScreen = new JPanel ();
		menuScreen.setLayout(new GridLayout(7,1));
		menuScreen.setBackground(Color.WHITE);

		//adds button to assign student
		JButton textbookNum = new JButton("Assign Student");
		textbookNum.addActionListener (new textbookNumListener());
		menuScreen.add(textbookNum);

		//adds button to clear all student
		JButton clear = new JButton("Clear Students");
		clear.addActionListener (new clearListener());
		menuScreen.add(clear);

		//adds button to sort students
		JButton sort = new JButton("Sort");
		sort.addActionListener (new sortListener());
		menuScreen.add(sort);

		//adds button to highlight overdue students
		JButton overdue = new JButton("Overdue");
		overdue.addActionListener (new overdueListener());
		menuScreen.add(overdue);

		//adds button to add a textbook
		JButton add = new JButton("Add Entry");
		add.addActionListener (new addListener());
		menuScreen.add(add);

		//adds button to remove a textbook
		JButton remove = new JButton("Remove Entry");
		remove.addActionListener (new removeListener());
		menuScreen.add(remove);

		//adds button to import textbooks from a list
		JButton importBook = new JButton("Import Entries");
		importBook.addActionListener (new importBookListener());
		menuScreen.add(importBook);

		//adds menu to main panel
		mainWindow.add(menuScreen,BorderLayout.WEST);

		//creates a JTabbedPane to handle different spreadsheets, starts off with one textbook by default
		tabs.setTabPlacement(SwingConstants.BOTTOM);
		tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabs.setBackground(Color.WHITE);
//		tabs.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.CYAN, Color.GREEN));
//		BorderFactory.createEmptyBorder(0,10,0,10));


		//creates table to display students, sets it to fill the entire screen
		JTable table = new JTable (new SpreadsheetModel());
		JScrollPane scrollPane = new JScrollPane (table);
		table.setFillsViewportHeight (true);

		//adds JPanel to JTabbedPane
		tabs.addTab ("Textbook Name", null, scrollPane, "Textbook Name");

		//adds JTabbedPane to main panel
		mainWindow.add(tabs, BorderLayout.CENTER);

		//north panel
		JPanel topMenu = new JPanel();
		topMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		topMenu.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
		topMenu.setBackground(Color.white);

		//adds button to open a database
		JButton openData = new JButton("Open Database");
		openData.addActionListener (new openDataListener());
		topMenu.add(openData);

		//adds button to create a new database
		JButton newData = new JButton("New Database");
		newData.addActionListener (new newDataListener());
		topMenu.add(newData);

		//adds search field for the list and highlights matches
		JLabel searchLabel = new JLabel("Search:");
		JTextField search = new JTextField (50);

		//		final Highlighter hilit;
		//		final Highlighter.HighlightPainter painter;
		//		...
		//		hilit = new DefaultHighlighter();
		//		painter = new DefaultHighlighter.DefaultHighlightPainter(HILIT_COLOR);
		//		textArea.setHighlighter(hilit);
		//
		//		entry.getDocument().addDocumentListener(this);
		//
		//		hilit.addHighlight(index, end, painter);
		//		textArea.setCaretPosition(end);
		//		entry.setBackground(entryBg);
		//		message("'" + s + "' found. Press ESC to end search");
		//
		//		private JLabel status;
		//		...
		//		void message(String msg) {
		//		    status.setText(msg);
		//		}
		//
		//		entry.setBackground(ERROR_COLOR);
		//		message("'" + s + "' not found. Press ESC to start a new search");
		//
		//		   class CancelAction extends AbstractAction {
		//		       public void actionPerformed(ActionEvent ev) {
		//		               hilit.removeAllHighlights();
		//		               entry.setText("");
		//		               entry.setBackground(entryBg);
		//		           }
		//		   }

		//adds search text box and label to the north menu
		topMenu.add(searchLabel);
		topMenu.add(search);

		//adds the top menu to the main panel
		mainWindow.add(topMenu,BorderLayout.NORTH);

		//allows window resizing and displays the window
		mainWindow.validate();
		mainWindow.repaint ();
		mainWindow.setVisible(true);
	}

	//assigns student to a textbook
	static class textbookNumListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			JPanel table = new JPanel();

			String input = (String) JOptionPane.showInputDialog (dialog, "What is the textbook number?", "Textbook Number", JOptionPane.QUESTION_MESSAGE, null, null, null);
			System.out.println (input);

			tabs.addTab (input,table);
			//find textbook and move cursor?
			mainWindow.validate();
			mainWindow.repaint();
		}
	}

	//asks user for confirmation then resets the students column values to 0
	static class clearListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			int selection = JOptionPane.showOptionDialog(dialog, "Are you sure you want to clear all students?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			System.out.println (selection);
			if (selection == 0) {
				//set student column to empty values
			}
			mainWindow.repaint();
		}
	}

	//asks user how to sort, then sorts the table
	static class sortListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			//   JDialog confirm = new JDialog (confirm, "Warning");
			mainWindow.setBackground(Color.WHITE);
			mainWindow.setLayout(new BorderLayout());
			mainWindow.setSize(700, 700);
			mainWindow.setAlwaysOnTop (true);
			mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			mainWindow.repaint();
		}
	}

	//asks for confirmation to display all the overdue students
	static class overdueListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			int selection = JOptionPane.showOptionDialog(dialog, "Display all overdue students?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			System.out.println(selection);
			if (selection == 0) { //verify this
				// shift all boolean false values to top, basically sort
				//ask if want emails, display all emails in popup
			}
		}
	}

	//asks to insert a textbook name and creates a new tab
	static class addListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			String input = (String) JOptionPane.showInputDialog (dialog, "What is the textbook number?", "Input Number", JOptionPane.QUESTION_MESSAGE, null, null, null);
			System.out.println (input);

			//add single textbook to the table, add to linklist
			mainWindow.validate();
			mainWindow.repaint();
		}
	}

	//asks for confirmation then removes textbook
	static class removeListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			int selection = JOptionPane.showOptionDialog(dialog, "Are you sure you want to remove this textbook?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			System.out.println (selection);
			if (selection == 0) {
				//remove single textbook from tablet
				System.out.println ("Yes");
			}
			mainWindow.validate();
			mainWindow.repaint();
		}
	}

	//allows user to select a csv file to import for a new textbook
	static class importBookListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);
			JFileChooser picker = new JFileChooser ();

			picker.setDialogTitle("New Database Selector");
			picker.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileFilter filter = new FileNameExtensionFilter("TXT File","txt");
			picker.setFileFilter (filter);

			int returnValue = picker.showOpenDialog(dialog);
			System.out.println (returnValue);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = picker.getSelectedFile();
				System.out.println(selectedFile.getAbsolutePath()); //return this to the data file
			}

			mainWindow.validate();
			mainWindow.repaint();
			//imports text file adds textbook numbers, all other fields enpty
			// multi selection later if time and doable
		}
	}

	//creates new database
	static class newDataListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event){
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			JPanel table = new JPanel();

			String input = (String) JOptionPane.showInputDialog (dialog, "What is the textbook name?", "Input Name", JOptionPane.QUESTION_MESSAGE, null, null, null);
			System.out.println (input);

			tabs.addTab (input,table);

			//name database here

			mainWindow.validate();
			mainWindow.repaint();
		}
	}

	//asks user to select a file to open a database from
	static class openDataListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event){
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);
			JFileChooser picker = new JFileChooser ();

			picker.setDialogTitle("New Database Selector");
			picker.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileFilter filter = new FileNameExtensionFilter("CSV File","csv");
			picker.setFileFilter (filter);

			int returnValue = picker.showOpenDialog(dialog);
			System.out.println (returnValue);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = picker.getSelectedFile();
				System.out.println(selectedFile.getAbsolutePath()); //return this to the data file
			}

			mainWindow.validate();
			mainWindow.repaint();
			// multi selection later if time and doable
		}
	}

	public void displaySpreadsheets () {
		//go through array list here and add each to a new tab and refresh window
	}
}

//get rid of wildcard imports
//make tab names editable?
//add export option and list of emails of not returned
//save button