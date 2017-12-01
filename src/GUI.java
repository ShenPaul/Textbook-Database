/**
 * [GUI.java]
 * @authors Paul Shen, Sasha Maximovitch, David Stewart
 * @date November 29,2017
 */


// imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Component;

import java.io.File;

import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.UIManager;
import javax.swing.UIDefaults;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.BorderFactory;
import javax.swing.WindowConstants;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;

public class GUI {// start of GUI class

	//declare public variables
	public static JTabbedPane tabs = new JTabbedPane();
	public static JFrame mainWindow = new JFrame("Textbook Database");
	public static ArrayList<SpreadsheetModel> spreadsheetList = new ArrayList<SpreadsheetModel>();
	public static ArrayList<JTable> tableList = new ArrayList<JTable>();
	public static JTextField search = new JTextField(50);
	public static JLabel empty;
	public static Font font = new Font("Courier", Font.BOLD, 18);

	// main method to run GUI
	public static void main(String[] args) {


		//try catch statements
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
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
		mainWindow.setResizable(true);
		mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		//sets GUI to full screen
		mainWindow.setSize(1600, 800);
		mainWindow.setExtendedState(Frame.MAXIMIZED_BOTH);

		Font font1 = new Font("Courier", Font.BOLD, 67);
		empty = new JLabel("NO DATABASES HAVE BEEN IMPORTED YET!");
		empty.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 70));
		empty.setFont(font1);
		mainWindow.add(empty, BorderLayout.CENTER);

		//creates menu on west panel
		JPanel menuScreen = new JPanel();
		menuScreen.setLayout(new GridLayout(7, 1));
		menuScreen.setBackground(Color.WHITE);

		//adds button to assign student
		JButton textbookNum = new JButton("Assign Student");
		textbookNum.setFont(font);
		textbookNum.addActionListener(new textbookNumListener());
		menuScreen.add(textbookNum);

		//adds button to sort students
		JButton sort = new JButton("Sort");
		sort.setFont(font);
		sort.addActionListener(new sortListener());
		menuScreen.add(sort);

		//adds button to highlight overdue students
		JButton overdue = new JButton("Overdue");
		overdue.setFont(font);
		overdue.addActionListener(new overdueListener());
		menuScreen.add(overdue);

		//adds button to add a textbook
		JButton add = new JButton("Add Textbook");
		add.setFont(font);
		add.addActionListener(new addListener());
		menuScreen.add(add);

		//adds button to remove a textbook
		JButton remove = new JButton("Remove Textbook");
		remove.setFont(font);
		remove.addActionListener(new removeListener());
		menuScreen.add(remove);

		//adds button to import textbooks from a list
		JButton importBook = new JButton("Import Textbooks");
		importBook.setFont(font);
		importBook.addActionListener(new importBookListener());
		menuScreen.add(importBook);

		//adds button to clear all student
		JButton clear = new JButton("Clear Students");
		clear.setFont(font);
		clear.addActionListener(new clearListener());
		menuScreen.add(clear);

		//adds menu to main panel
		mainWindow.add(menuScreen, BorderLayout.WEST);

		//creates a JTabbedPane to handle different spreadsheets, starts off with no textbooks by default
		tabs.setTabPlacement(SwingConstants.BOTTOM);
		tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabs.setBackground(Color.WHITE);
		showEmpty();

		//north panel
		JPanel topMenu = new JPanel();
		topMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		topMenu.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		topMenu.setBackground(Color.white);

		//adds button to open a database
		JButton openData = new JButton("Open Database");
		openData.setFont(font);
		openData.addActionListener(new openDataListener());
		topMenu.add(openData);

		//adds button to create a new database
		JButton newData = new JButton("New Database");
		newData.setFont(font);
		newData.addActionListener(new newDataListener());
		topMenu.add(newData);

		//adds button to close database
		JButton closeData = new JButton("Close Current Database");
		closeData.setFont(font);
		closeData.addActionListener(new closeDataListener());
		topMenu.add(closeData);

		//adds button to save databases
		JButton save = new JButton("Save");
		save.setFont(font);
		save.addActionListener(new saveListener());
		topMenu.add(save);

		//adds search field for the list and highlights matches
		JLabel searchLabel = new JLabel("Search:");
		searchLabel.setFont(font);
		JButton searchButton = new JButton("Go!");
		searchButton.setFont(font);
		searchButton.addActionListener(new searchListener());

		//adds search text box and label to the north menu
		topMenu.add(searchLabel);
		topMenu.add(search);
		topMenu.add(searchButton);

		//adds the top menu to the main panel
		mainWindow.add(topMenu, BorderLayout.NORTH);

		//allows window resizing and displays the window
		mainWindow.validate();
		mainWindow.setVisible(true);
		mainWindow.toFront();
		mainWindow.repaint();
	}

	/**
	 * showEmpty
	 * The display that shows up when the program is just started and no database is selected
	 */
	public static void showEmpty() {
		if (spreadsheetList.size() != 0) {
			empty.setVisible(false);
			mainWindow.validate();
			mainWindow.repaint();
			mainWindow.setVisible(true);

		} else {
			empty.setVisible(true);
			mainWindow.validate();
			mainWindow.repaint();
			mainWindow.setVisible(true);
		}
	}

	/**
	 * textBookNumListener
	 * assigns a student to a textbook and takes in the student information, then updates the database
	 */
	static class textbookNumListener implements ActionListener{

		//variables to be used in both buttons
		public static JFrame assign = new JFrame ("Assign Student");
		public static JTextField textNumIn = new JTextField (10);
		public static JTextField studentNumIn = new JTextField (10);
		public static JTextField lastIn = new JTextField (10);
		public static JTextField firstIn = new JTextField (10);
		public static JTextField teacherIn = new JTextField (10);
		public static JTextField courseIn = new JTextField (10);
		public static JTextField dateIn = new JTextField (10);
		public static JRadioButton sem1 = new JRadioButton("Semester 1", true);
		public static JRadioButton sem2 = new JRadioButton("Semester 2");
		public static boolean isFirst = true;

		@Override
		public void actionPerformed(ActionEvent event) {
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			if (spreadsheetList.size() == 0) {
				//Displays error message if user has not opened a database and tries to click a button
				JOptionPane.showMessageDialog(dialog, "Please open a database!", "Error!", JOptionPane.WARNING_MESSAGE);
			} else {
				assign.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
				if (isFirst == true) {
					isFirst = false;

					//creates "done" button
					JButton doneButton = new JButton("Done");
					doneButton.setFont(font);
					doneButton.addActionListener(new doneListener());

					//assigns panel layouts
					JPanel assignBorder = new JPanel(new BorderLayout());
					JPanel assigned = new JPanel(new GridLayout(8, 2));

					//get textbook number
					JLabel textNumLabel = new JLabel("Textbook Number:");
					textNumLabel.setFont(font);

					//get student number
					JLabel studentNumLabel = new JLabel("Student Number:");
					studentNumLabel.setFont(font);

					//get last name
					JLabel lastLabel = new JLabel("Last Name:");
					lastLabel.setFont(font);

					//get first name
					JLabel firstLabel = new JLabel("First Name:");
					firstLabel.setFont(font);

					//get teacher name
					JLabel teacherLabel = new JLabel("Teacher Name:");
					teacherLabel.setFont(font);

					//get course code
					JLabel courseLabel = new JLabel("Course Code:");
					courseLabel.setFont(font);

					//date label
					JLabel dateLabel = new JLabel("Date:");
					dateLabel.setFont(font);

					//date panel
					JPanel datePanel = new JPanel(new FlowLayout());

					//get semester
					ButtonGroup group = new ButtonGroup();
					group.add(sem1);
					group.add(sem2);
					datePanel.add(sem1);
					datePanel.add(sem2);

					//get year
					datePanel.add(dateIn);

					//adds fields to the panel
					assigned.add(textNumLabel);
					assigned.add(textNumIn);
					assigned.add(studentNumLabel);
					assigned.add(studentNumIn);
					assigned.add(lastLabel);
					assigned.add(lastIn);
					assigned.add(firstLabel);
					assigned.add(firstIn);
					assigned.add(teacherLabel);
					assigned.add(teacherIn);
					assigned.add(courseLabel);
					assigned.add(courseIn);
					assigned.add(dateLabel);
					assigned.add(datePanel);

					//adds components
					assignBorder.add(assigned, BorderLayout.CENTER);
					assignBorder.add(doneButton, BorderLayout.SOUTH);
					assign.add(assignBorder);
					assign.setBackground(Color.WHITE);
					assign.setSize(500, 500);
					assign.setResizable(true);
					assign.setAlwaysOnTop(true);

					//paints windows
					assign.pack();
					assign.invalidate();
					assign.validate();
					assign.revalidate();
					assign.repaint();
					assign.repaint();
					assign.setVisible(true);
					mainWindow.validate();
					mainWindow.repaint();
				} else {
					assign.setVisible(true);
				}

			}
		}
		//button for the done button, gets data and verifies it
		static class doneListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent event) {
				JDialog dialog = new JDialog();
				dialog.setAlwaysOnTop(true);

				int selection;

				//gets text from input field
				String textNum = textNumIn.getText();
				String studentNum = studentNumIn.getText();
				String last = lastIn.getText();
				String first = firstIn.getText();
				String teacher = teacherIn.getText();
				String course = courseIn.getText();
				String semester = "";
				String year = dateIn.getText();

				//checks which radio button is selected
				if (sem1.isSelected()){
					semester = "Semester 1";
				} else if (sem2.isSelected()) {
					semester = "Semester 2";
				}

				//assigns date
				String date = semester + "-" + year;

				//checks if it is a real textbook number
				boolean isRealTable = spreadsheetList.get(tabs.getSelectedIndex()).assignStudent(textNum, studentNum, last, first, teacher, course, date);

				//gives error if textbook number is not found
				if (!isRealTable) {
					System.out.println("Error");
					JOptionPane.showMessageDialog(dialog, "This is not a real textbook number!", "Error!", JOptionPane.WARNING_MESSAGE);
					System.out.println("Error");
				} else {
					//closes window if student is added
					Object[] textOptions = {"OK"};
					selection = JOptionPane.showOptionDialog(dialog, "Student added!", "SUCCESS!", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, textOptions, null);

					if (selection == 0) {
						//resets text fields
						textNumIn.setText("");
						studentNumIn.setText("");
						lastIn.setText("");
						firstIn.setText("");
						teacherIn.setText("");
						courseIn.setText("");
						dateIn.setText("");
						semester = "";

						//disposes the window and redraws the main window
						assign.setVisible(false);
						mainWindow.validate();
						mainWindow.repaint();
					}
				}
			}
		}
	}

	/**
	 * sortListener
	 * asks user how to sort, then sorts the table
	 */
	static class sortListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			if (spreadsheetList.size() == 0) {
				//Displays error message if user has not opened a database and tries to click a button
				JOptionPane.showMessageDialog(dialog, "Please open a database!", "Error!", JOptionPane.WARNING_MESSAGE);
			} else {
				// all the options for sort
				Object[] sortOptions = {"Textbook Number", "Student Number", "Last Name", "First Name", "Teacher", "Course Code"};
				int selection = JOptionPane.showOptionDialog(dialog, "How would you like to sort the students?", "Sort", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, sortOptions, sortOptions[0]); //remove question message?
				// with the selection complete the sort
				if (selection == 0) {
					spreadsheetList.get(tabs.getSelectedIndex()).itemSort();
				} else {
					spreadsheetList.get(tabs.getSelectedIndex()).sortByType(selection);
				}
				mainWindow.validate();
				mainWindow.repaint();
			}
		}
	}

	/**
	 * overDueListener
	 * asks for confirmation to display all the overdue students
	 */
	static class overdueListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			if (spreadsheetList.size() == 0) {
				//Displays error message if user has not opened a database and tries to click a button
				JOptionPane.showMessageDialog(dialog, "Please open a database!", "Error!", JOptionPane.WARNING_MESSAGE);
			} else {
				// asks user if they want to get all the overdue student names
				int selection = JOptionPane.showOptionDialog(dialog, "Display all overdue students?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				if (selection == 0) {
					JFrame overdue = new JFrame("Overdue Names");
					JTextArea overdueList = new JTextArea();
					JScrollPane scroller = new JScrollPane(overdueList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

					// get all the names and student numbers of overdue students
					String overdueNames[] = spreadsheetList.get(tabs.getSelectedIndex()).getOverdueNames();

					// add some text
					for (int i = 0; i < overdueNames.length; i++) {
						overdueList.append(overdueNames[i] + "\n");
					}

					overdue.add(scroller);
					overdue.setBackground(Color.WHITE);
					overdue.setSize(500, 500);
					overdue.setResizable(true);
					overdue.setAlwaysOnTop(true);
					overdue.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					overdue.validate();
					overdue.repaint();
					overdue.setVisible(true);

					//ask if user wants overdue emails
					selection = JOptionPane.showOptionDialog(dialog, "Would you like a list of the student emails?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
					// if yes, display all emails in popup
					if (selection == 0) {
						JFrame email = new JFrame("Overdue Emails");
						JTextArea emailList = new JTextArea();
						JScrollPane scroller1 = new JScrollPane(emailList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

						// get all the overdue student numbers in an array
						String[] studentEmails = spreadsheetList.get(tabs.getSelectedIndex()).getOverdueEmails();

						//  Add the emails to the panel
						for (int i = 0; i < studentEmails.length; i++) {
							emailList.append(studentEmails[i] + "\n");
						}

						email.add(scroller1);

						email.setBackground(Color.WHITE);
						email.setSize(500, 500);
						email.setResizable(true);
						email.setAlwaysOnTop(true);
						email.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						email.validate();
						email.repaint();
						email.setVisible(true);

					}
				}
				mainWindow.validate();
				mainWindow.repaint();
			}
		}
	}

	/**
	 * addListener
	 * asks to insert a textbook name and creates a new tab
	 */
	static class addListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			if (spreadsheetList.size() == 0) {
				//Displays error message if user has not opened a database and tries to click a button
				JOptionPane.showMessageDialog(dialog, "Please open a database!", "Error!", JOptionPane.WARNING_MESSAGE);
			} else {
				// user inputs the name of the textbook to be added
				String input = (String) JOptionPane.showInputDialog(dialog, "What is the textbook number?", "Input Number", JOptionPane.QUESTION_MESSAGE, null, null, null);

				// add the textbook to the database
				spreadsheetList.get(tabs.getSelectedIndex()).add(input);

				mainWindow.validate();
				mainWindow.repaint();
			}
		}
	}

	/**
	 * removeListener
	 * asks for confirmation then removes specified textbook
	 */
	static class removeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			if (spreadsheetList.size() == 0) {
				//Displays error message if user has not opened a database and tries to click a button
				JOptionPane.showMessageDialog(dialog, "Please open a database!", "Error!", JOptionPane.WARNING_MESSAGE);
			} else {
				// gets the textbook to be removed
				String selection = (String) JOptionPane.showInputDialog(dialog, "Which textbook do you want to remove?", "Warning", JOptionPane.QUESTION_MESSAGE, null, null, null);

				// remove the selected textbook
				spreadsheetList.get(tabs.getSelectedIndex()).remove(selection); //need to get selection row here, temporarily set to 0

				mainWindow.validate();
				mainWindow.repaint();
			}
		}
	}

	/**
	 * importBookListener
	 * allows user to select a txt file to import for a new textbook
	 */
	static class importBookListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			if (spreadsheetList.size() == 0) {
				//Displays error message if user has not opened a database and tries to click a button
				JOptionPane.showMessageDialog(dialog, "Please open a database!", "Error!", JOptionPane.WARNING_MESSAGE);
			} else {
				JFileChooser picker = new JFileChooser();

				// get the .txt file to be imported
				picker.setDialogTitle("New Database Selector");
				picker.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileFilter filter = new FileNameExtensionFilter("TXT File", "txt");
				picker.setFileFilter(filter);

				int returnValue = picker.showOpenDialog(dialog);

				// put the imported textbook numbers into database
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = picker.getSelectedFile();
					spreadsheetList.get(tabs.getSelectedIndex()).importData(selectedFile.getAbsolutePath());
					spreadsheetList.get(tabs.getSelectedIndex()).saveData();
				}

				mainWindow.validate();
				mainWindow.repaint();
			}
		}
	}

	/**
	 * clearListener
	 * asks user for confirmation then resets the students column values to empty
	 */
	static class clearListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			if (spreadsheetList.size() == 0) {
				//Displays error message if user has not opened a database and tries to click a button
				JOptionPane.showMessageDialog(dialog, "Please open a database!", "Error!", JOptionPane.WARNING_MESSAGE);
			} else {
				// reminds the user that only the math department head can clear
				int selection = JOptionPane.showOptionDialog(dialog, "Only the Math Department Head can use this function. Proceed?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				if (selection == 0) {// if the user selects yes
					// asks for confirmation on clear
					selection = JOptionPane.showOptionDialog(dialog, "Are you sure you want to clear all students?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
					if (selection == 0) { // clear all the students
						spreadsheetList.get(tabs.getSelectedIndex()).clear();
					}
				}
				mainWindow.repaint();
			}
		}
	}

	/**
	 * openDataListener
	 * asks user to select a file to open a database from a csv file
	 */
	static class openDataListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);
			JFileChooser picker = new JFileChooser();
			File selectedFile = new File("");
			String fileName;
			String path = "";

			// create file selector
			picker.setDialogTitle("New Database Selector");
			picker.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileFilter filter = new FileNameExtensionFilter("CSV File", "csv");
			picker.setFileFilter(filter);

			// user selects file
			int returnValue = picker.showOpenDialog(dialog);

			// get path of .csv file to be opened
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				selectedFile = picker.getSelectedFile();
				path = selectedFile.getAbsolutePath();

				// saves the name of the file
				fileName = (selectedFile.getName().substring(0, selectedFile.getName().length() - 4));

				//creates table to display students, sets it to fill the entire screen
				JTable table = new JTable(new SpreadsheetModel(path));
				SpreadsheetModel model = (SpreadsheetModel) table.getModel();
				model.setTable(table);
				spreadsheetList.add(model);
				tableList.add(table);
				table.getTableHeader().setReorderingAllowed(false);
				JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				table.setFillsViewportHeight(true);

				//adds JPanel to JTabbedPane
				tabs.addTab(fileName, null, scrollPane, fileName);
				//must find way to pass in the textbook name as well

				//sets new database to selected database
				tabs.setSelectedIndex(spreadsheetList.size() - 1);

				for(int i =0;i<table.getColumnCount()-1;i++){
					table.getColumnModel().getColumn(i).setCellRenderer(new SpreadsheetRenderer());
				}

				//adds JTabbedPane to main panel
				mainWindow.add(tabs, BorderLayout.CENTER);

				showEmpty();
			}

			showEmpty();
			// multi selection later if time and doable
		}
	}

	/**
	 * newDataListener
	 * creates a new database
	 */
	static class newDataListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);
			JFileChooser picker = new JFileChooser();
			File selectedFile = new File("");
			String fileName;
			String path = "";

			// get the name of the database of textbooks to be created
			String input = (String) JOptionPane.showInputDialog(dialog, "What is the database name?", "Input Name", JOptionPane.QUESTION_MESSAGE, null, null, null);

			if (input != null) {
				JOptionPane.showMessageDialog(dialog, "Please select a file location", "Location", JOptionPane.INFORMATION_MESSAGE, null);

				picker.setDialogTitle("Location Selector");
				picker.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				picker.setAcceptAllFileFilterUsed(false);

				int returnValue = picker.showOpenDialog(dialog);

				// chose a save location
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = picker.getSelectedFile();
					path = selectedFile.getAbsolutePath();


					//creates table to display students, sets it to fill the entire screen
					JTable table = new JTable(new SpreadsheetModel(input, 0));
					SpreadsheetModel model = (SpreadsheetModel) table.getModel();
					table.getTableHeader().setReorderingAllowed(false);
					model.addEmpty();
					spreadsheetList.add(model);
					tableList.add (table);

					JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					table.setFillsViewportHeight(true);

					//adds JPanel to JTabbedPane
					tabs.addTab(input, null, scrollPane, input);

					//sets new database to selected database
					tabs.setSelectedIndex(spreadsheetList.size() - 1);

					//sets directory
					spreadsheetList.get(tabs.getSelectedIndex()).setFilePath(path);

					//adds JTabbedPane to main panel
					mainWindow.add(tabs, BorderLayout.CENTER);

					mainWindow.validate();
					mainWindow.repaint();

					spreadsheetList.get(tabs.getSelectedIndex()).saveData();
					// multi selection later if time and doable
				}
			}
			showEmpty();
		}
	}

	/**
	 * closeDataListener
	 * closes the current database tab
	 */
	static class closeDataListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			if (spreadsheetList.size() == 0) {
				//Displays error message if user has not opened a database and tries to click a button
				JOptionPane.showMessageDialog(dialog, "Please open a database!", "Error!", JOptionPane.WARNING_MESSAGE);
			} else {
				// asks user if they want to close their database
				int selection = JOptionPane.showOptionDialog(dialog, "Close the current database?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				if (selection == 0) {
					// asks user if they have saved
					selection = JOptionPane.showOptionDialog(dialog, "Have you saved your work?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
					if (selection == 0) {
						// close the database
						spreadsheetList.remove(tabs.getSelectedIndex());
						tabs.remove(tabs.getSelectedIndex());
						showEmpty();
						mainWindow.repaint();
					}
				}
				showEmpty();
			}
		}
	}

	/**
	 * saveListener
	 * saves the current database tab
	 */
	static class saveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			if (spreadsheetList.size() == 0) {
				//Displays error message if user has not opened a database and tries to click a button
				JOptionPane.showMessageDialog(dialog, "Please open a database!", "Error!", JOptionPane.WARNING_MESSAGE);
			} else {
				if (spreadsheetList.size() == 0) {
					//Displays error message if user has not opened a database and tries to click a button
					JOptionPane.showMessageDialog(dialog, "Please open a database!", "Error!", JOptionPane.WARNING_MESSAGE);
				} else {
					// save the database
					spreadsheetList.get(tabs.getSelectedIndex()).saveData();
					JOptionPane.showMessageDialog(dialog, "You have saved the database!", "Saved!", JOptionPane.QUESTION_MESSAGE, null);
				}
			}
		}
	}

	/**
	 * searchListener
	 * gets a search query from the user and highlights the first valid row
	 */
	static class searchListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);

			if (spreadsheetList.size() == 0) {
				//Displays error message if user has not opened a database and tries to click a button
				JOptionPane.showMessageDialog(dialog, "Please open a database!", "Error!", JOptionPane.WARNING_MESSAGE);
			} else {
				tableList.get(tabs.getSelectedIndex()).selectAll();
			}
		}
	}

	public static class SpreadsheetRenderer extends DefaultTableCellRenderer{

		public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus,int row, int column) {

			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			Object valueAt = table.getModel().getValueAt(row, column);
			String compareText = valueAt.toString();
			String searchText = search.getText();

			if (searchText.contains(compareText)) {
				isSelected = true;
				hasFocus = true;
				if (table.isCellSelected(row, column)) {
					setBackground(Color.red);
				}
			} else if (row % 2 == 0 ){
				setBackground(Color.LIGHT_GRAY);
			} else if (row % 2 != 0){
				setBackground(Color.WHITE);
			}
			mainWindow.validate();
			mainWindow.repaint();
			return c;
		}


	}

}
