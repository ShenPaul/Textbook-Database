/**
 *[GUI.java]
 * @author 072971120
 * @date
 */

/*
 import java.awt.event.ActionListener;
  import java.awt.Font;
  import java.awt.Component;
 +import java.awt.Frame;

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
 +import javax.swing.UIDefaults;
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

 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import java.io.File;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class GUI {

	//declares public variables
	public static JTabbedPane tabs = new JTabbedPane();
	public static JFrame mainWindow = new JFrame ("Textbook Database");
	public static ArrayList <SpreadsheetModel> tableList = new ArrayList <SpreadsheetModel> (); //probably used for arraylist of databases, associate data linked list
	public static JTextField search = new JTextField (50);
	public static String searchText = "";
	public static JLabel empty;
	public static Font font = new Font("Courier", Font.BOLD,18);
	public static JFrame assign = new JFrame ("Assign Student");
	public static boolean isRealTable = false;

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

		UIManager.put("TabbedPane.selected", Color.GREEN);

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
		mainWindow.setSize(1600, 800);
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

		showEmpty();

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

		//adds button to close database
		JButton closeData = new JButton("Close Current Database");
		closeData.setFont(font);
		closeData.addActionListener (new closeDataListener());
		topMenu.add(closeData);

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

	public static void showEmpty () {
		if (tableList.size() != 0) {
			System.out.println(tableList.size());
			empty.setVisible(false);
			mainWindow.validate();
			mainWindow.repaint ();
			mainWindow.setVisible(true);

		} else {
			System.out.println(tableList.size());
			Font font1 = new Font("Courier", Font.BOLD,67);
			empty = new JLabel("NO DATABASES HAVE BEEN IMPORTED YET!");
			empty.setBorder (BorderFactory.createEmptyBorder(0,70,0,70));
			empty.setFont(font1);
			mainWindow.add(empty, BorderLayout.CENTER);
			empty.setVisible(true);
			mainWindow.validate();
			mainWindow.repaint ();
			mainWindow.setVisible(true);
		}
	}

	//assigns student to a textbook
	static class textbookNumListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			JButton doneButton = new JButton("Done");
			doneButton.setFont(font);
			doneButton.addActionListener (new doneListener()); //add in the finish button that verifies and then sends data

			assign.setAlwaysOnTop(true);
			JPanel assigned = new JPanel(new GridLayout(8, 2));

			//get textbook number
			JLabel textNumLabel = new JLabel("Textbook Number:");
			textNumLabel.setFont(font);
			JTextField textNumIn = new JTextField (10);
			String textNum = textNumIn.getText();

			//get student number
			JLabel studentNumLabel = new JLabel("Student Number:");
			studentNumLabel.setFont(font);
			JTextField studentNumIn = new JTextField (10);
			String studentNum = studentNumIn.getText();

			//get last name
			JLabel lastLabel = new JLabel("Last Name:");
			lastLabel.setFont(font);
			JTextField lastIn = new JTextField (10);
			String last = lastIn.getText();

			//get first name
			JLabel firstLabel = new JLabel("First Name:");
			firstLabel.setFont(font);
			JTextField firstIn = new JTextField (10);
			String first = firstIn.getText();

			//get teacher name, DROP DOWN MENU????
			JLabel teacherLabel = new JLabel("Teacher Name:");
			teacherLabel.setFont(font);
			JTextField teacherIn = new JTextField (10);
			String teacher = teacherIn.getText();

			//get course code, DROP DOWN MENU???
			JLabel courseLabel = new JLabel("Course Code:");
			courseLabel.setFont(font);
			JTextField courseIn = new JTextField (10);
			String course = courseIn.getText();

			//get date, concatenate strings then pass in, radio buttons
			JLabel dateLabel = new JLabel("Date:");
			dateLabel.setFont(font);
			JTextField dateIn = new JTextField (10);
			String date = dateIn.getText();

			//adds fields to the panel
//			assigned.add(textNumLabel, 0, 0);
//			assigned.add(textNumIn, 0, 1);
//			assigned.add(studentNumLabel, 1, 0);
//			assigned.add(studentNumIn, 1, 1);
//			assigned.add(lastLabel, 2, 0);
//			assigned.add(lastLabel, 2, 1);
//			assigned.add(firstLabel, 3, 0);
//			assigned.add(firstLabel, 3, 1);
//			assigned.add(teacherLabel, 4, 0);
//			assigned.add(teacherLabel, 4, 1);
//			assigned.add(courseLabel, 5, 0);
//			assigned.add(courseLabel, 5, 1);
//			assigned.add(dateLabel, 6, 0);
//			assigned.add(dateLabel, 6, 1);

//			assigned.add(textNumLabel);
//			assigned.add(studentNumLabel);
//			assigned.add(lastLabel);
//			assigned.add(firstLabel);
//			assigned.add(teacherLabel);
//			assigned.add(courseLabel);
//			assigned.add(dateLabel);
//
//			assigned.add(textNumIn);
//			assigned.add(studentNumIn);
//			assigned.add(lastLabel);
//			assigned.add(firstLabel);
//			assigned.add(teacherLabel);
//			assigned.add(courseLabel);
//			assigned.add(dateLabel);

			assigned.add(textNumLabel);
			assigned.add(textNumIn);
			assigned.add(studentNumLabel);
			assigned.add(studentNumIn);
			assigned.add(lastLabel);
			assigned.add(lastLabel);
			assigned.add(firstLabel);
			assigned.add(firstLabel);
			assigned.add(teacherLabel);
			assigned.add(teacherLabel);
			assigned.add(courseLabel);
			assigned.add(courseLabel);
			assigned.add(dateLabel);
			assigned.add(dateLabel);

			assign.add(assigned);
			assign.setBackground(Color.WHITE);
			assign.setSize(500, 500);
			assign.setResizable(true);
			assign.setAlwaysOnTop (true);
			assign.dispatchEvent(new WindowEvent(assign, WindowEvent.WINDOW_CLOSING));
			assign.validate();
			assign.repaint();
			assign.setVisible(true);

			boolean isRealTable = tableList.get(tabs.getSelectedIndex()).assignStudent(textNum, studentNum, last, first, teacher, course, date);

			//find textbook and move cursor?
			mainWindow.validate();
			mainWindow.repaint();
		}
	}

	//asks user how to sort, then sorts the table
	static class doneListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			if(!isRealTable){
				System.out.println("Error");
				JOptionPane.showMessageDialog(dialog, "This is not a real textbook number!", "Error!", JOptionPane.WARNING_MESSAGE);
				System.out.println("Error");
			} else {
				JOptionPane.showMessageDialog(dialog, "Student added!", "SUCCESS!", JOptionPane.INFORMATION_MESSAGE);
				assign.dispose();
			}
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
			}else{
				tableList.get(tabs.getSelectedIndex()).sortByType(selection);
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
			if (selection == 0) {
				JFrame overdue = new JFrame("Overdue Names");
				JTextArea overdueList = new JTextArea();
				JScrollPane scroller = new JScrollPane(overdueList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

				// get all the names and student numbers of overdue students
				String overdueNames[] = tableList.get(tabs.getSelectedIndex()).getOverdueNames();

				//  Add some text
				for (int i = 0; i < overdueNames.length; i++) {
					overdueList.append(overdueNames[i] + "\n");
				}

				overdue.add(scroller);
				overdue.setBackground(Color.WHITE);
				overdue.setSize(500, 500);
				overdue.setResizable(true);
				overdue.setAlwaysOnTop (true);
				overdue.dispatchEvent(new WindowEvent(overdue, WindowEvent.WINDOW_CLOSING));
				overdue.validate();
				overdue.repaint();
				overdue.setVisible(true);

				//ask if want emails
				selection = JOptionPane.showOptionDialog(dialog, "Would you like a list of the student emails?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				// if yes, display all emails in popup
				if (selection == 0) {
					JFrame email = new JFrame("Overdue Emails");
					JTextArea emailList = new JTextArea();
					JScrollPane scroller1 = new JScrollPane(emailList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

					// get all the overdue student numbers in an array
					String[] studentEmails = tableList.get(tabs.getSelectedIndex()).getOverdueEmails();

					//  Add some text
					for (int i = 0; i < studentEmails.length; i++) {
						emailList.append(studentEmails[i] + "\n");
					}

					email.add(scroller1);

					email.setBackground(Color.WHITE);
					email.setSize(500, 500);
					email.setResizable(true);
					email.setAlwaysOnTop (true);
					email.dispatchEvent(new WindowEvent(email, WindowEvent.WINDOW_CLOSING));
					email.validate();
					email.repaint();
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

	//asks user for confirmation then resets the students column values to 0
	static class clearListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			int selection = JOptionPane.showOptionDialog(dialog, "Only the Math Department Head can use this function. Proceed?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			System.out.println (selection);
			if (selection == 0) {
				selection = JOptionPane.showOptionDialog(dialog, "Are you sure you want to clear all students?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				if (selection == 0) {
					tableList.get(tabs.getSelectedIndex()).clear();
					//gotten table at index so far, need to clear it, clears table so far? fix?
				}
			}
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
			table.getTableHeader().setReorderingAllowed(false);
			JScrollPane scrollPane = new JScrollPane (table);
			table.setFillsViewportHeight (true);

			//adds JPanel to JTabbedPane
			tabs.addTab (fileName, null, scrollPane, fileName);
			//must find way to pass in the textbook name as well

			//sets new database to selected database
			tabs.setSelectedIndex(tableList.size() - 1);

			//adds JTabbedPane to main panel
			mainWindow.add(tabs, BorderLayout.CENTER);

			showEmpty();
			// multi selection later if time and doable
		}
	}

	//creates new database
	static class newDataListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event){
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);
			JFileChooser picker = new JFileChooser ();
			File selectedFile = new File ("");
			String fileName;
			String path = "";

			String input = (String) JOptionPane.showInputDialog (dialog, "What is the textbook name?", "Input Name", JOptionPane.QUESTION_MESSAGE, null, null, null);

			if (input != null) {
				picker.setDialogTitle("New Database Selector");
				picker.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				picker.setAcceptAllFileFilterUsed(false);

				int returnValue = picker.showOpenDialog(dialog);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = picker.getSelectedFile();
					path = selectedFile.getAbsolutePath();
					System.out.println(path);
				}

				//creates table to display students, sets it to fill the entire screen
				JTable table = new JTable(new SpreadsheetModel(input, 0));
				SpreadsheetModel model = (SpreadsheetModel) table.getModel();
				table.getTableHeader().setReorderingAllowed(false);
				model.addEmpty();
				tableList.add(model);

				JScrollPane scrollPane = new JScrollPane(table);
				table.setFillsViewportHeight(true);

				//adds JPanel to JTabbedPane
				tabs.addTab(input, null, scrollPane, input);

				//sets directory
				tableList.get(tabs.getSelectedIndex()).setFilePath(path);

				//sets new database to selected database
				tabs.setSelectedIndex(tableList.size() - 1);

				//adds JTabbedPane to main panel
				mainWindow.add(tabs, BorderLayout.CENTER);

				mainWindow.validate();
				mainWindow.repaint();

				tableList.get(tabs.getSelectedIndex()).saveData();
				// multi selection later if time and doable
			}
			showEmpty();
		}
	}

	//asks user for confirmation then resets the students column values to 0
	static class closeDataListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event){
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);
			int selection = JOptionPane.showOptionDialog(dialog, "Close the current database?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			if (selection == 0) {
				selection = JOptionPane.showOptionDialog(dialog, "Have you saved your work?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				if (selection == 0) {
					tableList.remove(tabs.getSelectedIndex());
					tabs.remove(tabs.getSelectedIndex());
					showEmpty();
					mainWindow.repaint();
				}
			}
			showEmpty();
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
//overdue method - David
//textbooknum method - check valid textbook number while typing in field?
//import method - David
//add method error - David DONE
//sort method - add other sort types
//change every other row colour, fix check boxes, https://stackoverflow.com/questions/17762214/java-jtable-alternate-row-color-not-working
//change date format, input selection?
//tablemodellistener, fire inserted row? https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#modelchange, https://docs.oracle.com/javase/tutorial/uiswing/events/tablemodellistener.html, http://www.java2s.com/Tutorial/Java/0240__Swing/ListeningtoJTableEventswithaTableModelListener.htm, http://www.codejava.net/java-se/swing/editable-jtable-example
//tooltips?
// add error messages as popup when no textbook open? method?
// jlabel empty popping up randomly