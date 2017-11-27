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
import java.awt.Font;
import java.awt.Component;
import java.awt.Frame;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.JTabbedPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UIDefaults;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.BorderFactory;
import javax.swing.WindowConstants;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;

public class GUI {

	//declares public variables
	public static JTabbedPane tabs = new JTabbedPane();
	public static JFrame mainWindow = new JFrame ("Textbook Database");
	public static ArrayList <SpreadsheetModel> tableList = new ArrayList <SpreadsheetModel> (); //probably used for arraylist of databases, associate data linked list
	public static JTextField search = new JTextField (50);
	public static String searchText = "";
	public static JLabel empty;
	public static Font font = new Font("Courier", Font.BOLD,18);

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

		UIDefaults defaults = UIManager.getLookAndFeelDefaults();

		if (defaults.get("Table.alternateRowColor") == null) {
			defaults.put("Table.alternateRowColor", Color.LIGHT_GRAY);
		}

		//warning to user to not access files while running the database program.
		JOptionPane.showMessageDialog(null, "Do not open database .csv files while program is running!", "WARNING!", JOptionPane.WARNING_MESSAGE);

		//creates main JFrame
		mainWindow.setLayout(new BorderLayout());
		mainWindow.setBackground(Color.WHITE);
		//insert rhhs math logo as background picture?
		//yourFrameHere.setBackground(new Color(0, 0, 0, 0));
		//yourContentPaneHere.setOpaque(false);
		mainWindow.setResizable(true);
		mainWindow.setAlwaysOnTop (true);
		mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		//sets GUI to full screen
		mainWindow.setExtendedState(Frame.MAXIMIZED_BOTH);

		//creates menu on west panel
		JPanel menuScreen = new JPanel ();
		menuScreen.setLayout(new GridLayout(7,1));
		menuScreen.setBackground(Color.WHITE);

		//adds button to assign student
		JButton textbookNum = new JButton("Assign Student");
		textbookNum.setFont(font);
		textbookNum.addActionListener (new textbookNumListener());
		menuScreen.add(textbookNum);

		//adds button to sort students
		JButton sort = new JButton("Sort");
		sort.setFont(font);
		sort.addActionListener (new sortListener());
		menuScreen.add(sort);

		//adds button to highlight overdue students
		JButton overdue = new JButton("Overdue");
		overdue.setFont(font);
		overdue.addActionListener (new overdueListener());
		menuScreen.add(overdue);

		//adds button to add a textbook
		JButton add = new JButton("Add Textbook");
		add.setFont(font);
		add.addActionListener (new addListener());
		menuScreen.add(add);

		//adds button to remove a textbook
		JButton remove = new JButton("Remove Textbook");
		remove.setFont(font);
		remove.addActionListener (new removeListener());
		menuScreen.add(remove);

		//adds button to import textbooks from a list
		JButton importBook = new JButton("Import Textbooks");
		importBook.setFont(font);
		importBook.addActionListener (new importBookListener());
		menuScreen.add(importBook);

		//adds button to clear all student
		JButton clear = new JButton("Clear Students");
		clear.setFont(font);
		clear.addActionListener (new clearListener());
		menuScreen.add(clear);

		//adds menu to main panel
		mainWindow.add(menuScreen,BorderLayout.WEST);

		//creates a JTabbedPane to handle different spreadsheets, starts off with no textbooks by default
		tabs.setTabPlacement(SwingConstants.BOTTOM);
		tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabs.setBackground(Color.WHITE);
//		tabs.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.CYAN, Color.GREEN));
//		BorderFactory.createEmptyBorder(0,10,0,10));


//		//creates table to display students, sets it to fill the entire screen
//		JTable table = new JTable (new SpreadsheetModel());
//		JScrollPane scrollPane = new JScrollPane (table);
//		table.setFillsViewportHeight (true);
//
//		//adds JPanel to JTabbedPane
//		tabs.addTab ("Textbook Name", null, scrollPane, "Textbook Name");
//
//		//adds JTabbedPane to main panel
//		mainWindow.add(tabs, BorderLayout.CENTER);

		Font font1 = new Font("Courier", Font.BOLD,72);
		empty = new JLabel("NO DATABASES HAVE BEEN IMPORTED YET!");
		empty.setBorder (BorderFactory.createEmptyBorder(0,70,0,70));
		empty.setFont(font1);
		mainWindow.add(empty, BorderLayout.CENTER);

		//north panel
		JPanel topMenu = new JPanel();
		topMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		topMenu.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
		topMenu.setBackground(Color.white);

		//adds button to open a database
		JButton openData = new JButton("Open Database");
		openData.setFont(font);
		openData.addActionListener (new openDataListener());
		topMenu.add(openData);

		//adds button to create a new database
		JButton newData = new JButton("New Database");
		newData.setFont(font);
		newData.addActionListener (new newDataListener());
		topMenu.add(newData);

		//adds button to save databases
		JButton save = new JButton("Save");
		save.setFont(font);
		save.addActionListener (new saveListener());
		topMenu.add(save);

		//adds search field for the list and highlights matches
		JLabel searchLabel = new JLabel("Search:");
		searchLabel.setFont(font);
		JButton searchButton = new JButton("Go!");
		searchButton.setFont(font);
		searchButton.addActionListener (new searchListener());

//		Highlighter hilit;
//		Highlighter.HighlightPainter painter;
//
//		hilit = new DefaultHighlighter();
//		painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
//		hilit.install(tabs);
//
//		entry.getDocument().addDocumentListener(this);
//
//		hilit.addHighlight(index, end, painter);
//		textArea.setCaretPosition(end);
//		entry.setBackground(entryBg);
//		message("'" + s + "' found. Press ESC to end search");
//
//		private JLabel status;
//				...
//		void message(String msg) {
//			status.setText(msg);
//		}
//
//		entry.setBackground(ERROR_COLOR);
//		message("'" + s + "' not found. Press ESC to start a new search");
//
//		class CancelAction extends AbstractAction {
//			public void actionPerformed(ActionEvent ev) {
//				hilit.removeAllHighlights();
//				entry.setText("");
//				entry.setBackground(entryBg);
//			}
//		}

		//adds search text box and label to the north menu
		topMenu.add(searchLabel);
		topMenu.add(search);
		topMenu.add(searchButton);

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

//			JFrame email = new JFrame();
//			JPanel emails = new JPanel();
//			JTextPane emailList = new JTextPane();
//
//			//display all emails in text pane
//
//			emails.add(emailList);
//			email.add(emails);
//
//			email.setBackground(Color.WHITE);
//			email.setSize(500, 500);
//			email.setResizable(true);
//			email.setAlwaysOnTop (true);
//			email.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //this.dispose? will this end the program?
//			email.setVisible(true);


			// get textbook number
			String textBookNum = (String) JOptionPane.showInputDialog (dialog, "What is the textbook number?", "Textbook Number", JOptionPane.QUESTION_MESSAGE, null, null, null);
			//System.out.println (textBookNum);

			// get student number
			String studentNum = (String) JOptionPane.showInputDialog (dialog, "What is their student number?", "Student Number", JOptionPane.QUESTION_MESSAGE, null, null, null);

			String lastName = (String) JOptionPane.showInputDialog (dialog, "What is their last name?", "Last Name", JOptionPane.QUESTION_MESSAGE, null, null, null);

			String firstName = (String) JOptionPane.showInputDialog (dialog, "What is their first name?", "First Name", JOptionPane.QUESTION_MESSAGE, null, null, null);

			String teacher = (String) JOptionPane.showInputDialog (dialog, "Who is the teacher?", "Teacher", JOptionPane.QUESTION_MESSAGE, null, null, null);

			String courseCode = (String) JOptionPane.showInputDialog (dialog, "What is the course code?", "Course Code", JOptionPane.QUESTION_MESSAGE, null, null, null);

			String date = (String) JOptionPane.showInputDialog (dialog, "What is the date?", "Date", JOptionPane.QUESTION_MESSAGE, null, null, null);

			boolean isRealTable = tableList.get(tabs.getSelectedIndex()).assignStudent(textBookNum, studentNum, lastName, firstName, teacher, courseCode, date);

			if(!isRealTable){
				System.out.println("Error");
				JOptionPane.showMessageDialog(dialog, "This is not a real textbook number!", "Error!", JOptionPane.WARNING_MESSAGE);
				System.out.println("Error");
			}

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
				tableList.get(tabs.getSelectedIndex()).clear(); //gotten table at index so far, need to clear it
			}
			mainWindow.repaint();
		}
	}

	//asks user how to sort, then sorts the table
	static class sortListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);
			Object[] sortOptions = {"Textbook Number", "Student Number", "Last Name", "First Name", "Teacher", "Date Out", "Course Code"};
			int selection = JOptionPane.showOptionDialog(dialog, "How would you like to sort the students?", "Sort", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, sortOptions, sortOptions [0]); //remove question message?
			System.out.println(selection);
			if (selection == 0){
				tableList.get(tabs.getSelectedIndex()).itemSort();
			}else if (selection == 1){
				//we don't have a student number sort
			}else if (selection == 2){
				tableList.get(tabs.getSelectedIndex()).lastSort();
			}else{
				tableList.get(tabs.getSelectedIndex()).firstSort();
			}
			mainWindow.validate();
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

				// get all the names and student numbers of overdue students
				String overDue = tableList.get(tabs.getSelectedIndex()).getOverdueNames();

				// get all the overdue student numbers in an array
				String[] studentNumbers = tableList.get(tabs.getSelectedIndex()).getOverdueNumbers();

				//ask if want emails
				selection = JOptionPane.showOptionDialog(dialog, "Would you like a list of the student emails?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				// if yes, display all emails in popup
				if (selection == 0) {
					JFrame email = new JFrame();
					JPanel emails = new JPanel();
					JTextPane emailList = new JTextPane();

					//display all emails in text pane

					emails.add(emailList);
					email.add(emails);

					email.setBackground(Color.WHITE);
					email.setSize(500, 500);
					email.setResizable(true);
					email.setAlwaysOnTop (true);
					email.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //this.dispose? will this end the program?
					email.setVisible(true);

				}
			}
			mainWindow.validate();
			mainWindow.repaint();
		}
	}

	//asks to insert a textbook name and creates a new tab
	// GIVES ERROR IF DATABSE IS EMPTY, FIX PLEASE
	static class addListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			String input = (String) JOptionPane.showInputDialog (dialog, "What is the textbook number?", "Input Number", JOptionPane.QUESTION_MESSAGE, null, null, null);
			System.out.println (input);

			tableList.get(tabs.getSelectedIndex()).add(input);

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
				tableList.get(tabs.getSelectedIndex()).remove(0); //need to get selection row here, temporarily set to 0
				tabs.getModel().clearSelection();
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
				tableList.get(tabs.getSelectedIndex()).importData(selectedFile.getAbsolutePath());
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

			String input = (String) JOptionPane.showInputDialog (dialog, "What is the textbook name?", "Input Name", JOptionPane.QUESTION_MESSAGE, null, null, null);
			System.out.println (input);

			//creates table to display students, sets it to fill the entire screen
			JTable table = new JTable (new SpreadsheetModel(input, 0));
			SpreadsheetModel model = (SpreadsheetModel) table.getModel();
			model.addEmpty();
			tableList.add (model);
			JScrollPane scrollPane = new JScrollPane (table);
			table.setFillsViewportHeight (true);

			//adds JPanel to JTabbedPane
			tabs.addTab (input, null, scrollPane, input);
			//must find way to pass in the textbook name as well

			//adds JTabbedPane to main panel
			mainWindow.add(tabs, BorderLayout.CENTER);
			mainWindow.remove(empty);

			mainWindow.validate();
			mainWindow.repaint();
			// multi selection later if time and doable
		}
	}

	//asks user to select a file to open a database from
	static class openDataListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event){
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);
			JFileChooser picker = new JFileChooser ();
			File selectedFile = new File ("");
			String fileName;
			String path = "";

			picker.setDialogTitle("New Database Selector");
			picker.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileFilter filter = new FileNameExtensionFilter("CSV File","csv");
			picker.setFileFilter (filter);

			int returnValue = picker.showOpenDialog(dialog);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				selectedFile = picker.getSelectedFile();
				path = selectedFile.getAbsolutePath();
			}

			fileName = (selectedFile.getName().substring(0, selectedFile.getName().length()-4));

			//creates table to display students, sets it to fill the entire screen
			JTable table = new JTable (new SpreadsheetModel(path));
			SpreadsheetModel model = (SpreadsheetModel) table.getModel();
			tableList.add (model);
			JScrollPane scrollPane = new JScrollPane (table);
			table.setFillsViewportHeight (true);

			//adds JPanel to JTabbedPane
			tabs.addTab (fileName, null, scrollPane, fileName);
			//must find way to pass in the textbook name as well

			//adds JTabbedPane to main panel
			mainWindow.add(tabs, BorderLayout.CENTER);
			mainWindow.remove(empty);

			mainWindow.validate();
			mainWindow.repaint();
			// multi selection later if time and doable
		}
	}

	//saves the file
	static class saveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			tableList.get(tabs.getSelectedIndex()).saveData();
			JOptionPane.showMessageDialog(dialog,"You have saved the database!", "Saved!", JOptionPane.QUESTION_MESSAGE, null);
		}
	}

	//conducts the search
	static class searchListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			searchText = search.getText();
			mainWindow.repaint(); //repaints entire main window (index of table???), make sure matches array list
		}
	}

//	private TableCellRenderer searchRenderer() {
//		return new TableCellRenderer() {
//			@Override
//			public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
//				if (arg1 != null) {
//					search.setText(arg1.toString());
//					String string = arg1.toString();
//					if (string.contains(searchText)) {
//						int indexOf = string.indexOf(searchText);
//						try {
//							search.getHighlighter().addHighlight(indexOf, indexOf + searchText.length(), new javax.swing.text.DefaultHighlighter.DefaultHighlightPainter(Color.RED));
//						} catch (BadLocationException e) {
//							e.printStackTrace();
//						}
//					}
//				} else {
//					search.setText("");
//					search.getHighlighter().removeAllHighlights();
//				}
//				return search;
//			}
//		};
//	}

}

//TODO:
//make tab names editable: https://stackoverflow.com/questions/27124121/how-to-change-the-tab-name-in-jtabbedpane
//highlighted cell different colour: https://stackoverflow.com/questions/6862102/swing-jtable-highlight-selected-cell-in-a-different-color-from-rest-of-the-sel
//search by drop down menu or search all and highlight search: https://stackoverflow.com/questions/20113920/highlighting-the-text-of-a-jtable-cell
//add comments
//table filter: http://www.java2s.com/Tutorials/Java/Java_Swing/1100__Java_Swing_JTable.htm
//http://www.informit.com/articles/article.aspx?p=24130&seqNum=3
// try to get rid of repeated middle man code in spreadsheetmodel
//overdue method - David
//textbooknum method - David  DONE
//import method - David
//add method error - David DONE
//new database - David DONE
// clear students add more warning
//change every other row colour
//change date format, input selection?
//tablemodellistener, fire inserted row? https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#modelchange, https://docs.oracle.com/javase/tutorial/uiswing/events/tablemodellistener.html, http://www.java2s.com/Tutorial/Java/0240__Swing/ListeningtoJTableEventswithaTableModelListener.htm, http://www.codejava.net/java-se/swing/editable-jtable-example
//prevent header movement?
//tooltips?
//TableColumn sportColumn = table.getColumnModel().getColumn(2);
//...
//		JComboBox comboBox = new JComboBox();
//		comboBox.addItem("Snowboarding");
//		comboBox.addItem("Rowing");
//		comboBox.addItem("Chasing toddlers");
//		comboBox.addItem("Speed reading");
//		comboBox.addItem("Teaching high school");
//		comboBox.addItem("None");
//		sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
// for date?
//https://stackoverflow.com/questions/17762214/java-jtable-alternate-row-color-not-working, checkboxes colour not changed