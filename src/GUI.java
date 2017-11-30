/**
 * [GUI.java]
 * @authors Paul Shen, Sasha Maximovitch, David Stewart
 * @date November 29,2017
 */


// imports
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI {// start of GUI class

    //declare public variables
    public static JTabbedPane tabs = new JTabbedPane();
    public static JFrame mainWindow = new JFrame("Textbook Database");
    public static ArrayList<SpreadsheetModel> tableList = new ArrayList<SpreadsheetModel>(); //probably used for arraylist of databases, associate data linked list
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
        mainWindow.setAlwaysOnTop(true);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //sets GUI to full screen
        mainWindow.setSize(1600, 800);
        mainWindow.setExtendedState(Frame.MAXIMIZED_BOTH);

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
        mainWindow.repaint();
        mainWindow.setVisible(true);
    }

    /**
     * showEmpty
     * The display that shows up when the program is just started an no database is selected
     */
    public static void showEmpty() {
        if (tableList.size() != 0) {
            System.out.println(tableList.size());
            empty.setVisible(false);
            mainWindow.validate();
            mainWindow.repaint();
            mainWindow.setVisible(true);

        } else {
            System.out.println(tableList.size());
            Font font1 = new Font("Courier", Font.BOLD, 67);
            empty = new JLabel("NO DATABASES HAVE BEEN IMPORTED YET!");
            empty.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 70));
            empty.setFont(font1);
            mainWindow.add(empty, BorderLayout.CENTER);
            empty.setVisible(true);
            mainWindow.validate();
            mainWindow.repaint();
            mainWindow.setVisible(true);
        }
    }

    /**
     * textBookNumListener
     * assigns a student to a textbook and takes in the student information, this updates the database
     */
    static class textbookNumListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);

            // get textbook number
            String textBookNum = (String) JOptionPane.showInputDialog(dialog, "What is the textbook number?", "Textbook Number", JOptionPane.QUESTION_MESSAGE, null, null, null);
            //System.out.println (textBookNum);

            // get student number
            String studentNum = (String) JOptionPane.showInputDialog(dialog, "What is their student number?", "Student Number", JOptionPane.QUESTION_MESSAGE, null, null, null);

            // get student's last name
            String lastName = (String) JOptionPane.showInputDialog(dialog, "What is their last name?", "Last Name", JOptionPane.QUESTION_MESSAGE, null, null, null);

            // get students first name
            String firstName = (String) JOptionPane.showInputDialog(dialog, "What is their first name?", "First Name", JOptionPane.QUESTION_MESSAGE, null, null, null);

            // get teacher
            String teacher = (String) JOptionPane.showInputDialog(dialog, "Who is the teacher?", "Teacher", JOptionPane.QUESTION_MESSAGE, null, null, null);

            // get course code
            String courseCode = (String) JOptionPane.showInputDialog(dialog, "What is the course code?", "Course Code", JOptionPane.QUESTION_MESSAGE, null, null, null);

            //get date
            String date = (String) JOptionPane.showInputDialog(dialog, "What is the date?", "Date", JOptionPane.QUESTION_MESSAGE, null, null, null);

            // attempts to assign student to the textbook in data, will return false if textbook is not valid
            boolean isRealTable = tableList.get(tabs.getSelectedIndex()).assignStudent(textBookNum, studentNum, lastName, firstName, teacher, courseCode, date);

            if (!isRealTable) {// if textbook is not valid
                JOptionPane.showMessageDialog(dialog, "This is not a real textbook number!", "Error!", JOptionPane.WARNING_MESSAGE);
            }

            mainWindow.validate();
            mainWindow.repaint();
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
            // all the options for sort
            Object[] sortOptions = {"Textbook Number", "Student Number", "Last Name", "First Name", "Teacher", "Course Code"};
            int selection = JOptionPane.showOptionDialog(dialog, "How would you like to sort the students?", "Sort", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, sortOptions, sortOptions[0]); //remove question message?
            // with the selection complete the sort
            if (selection == 0) {
                tableList.get(tabs.getSelectedIndex()).itemSort();
            } else {
                tableList.get(tabs.getSelectedIndex()).sortByType(selection);
            }
            mainWindow.validate();
            mainWindow.repaint();
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

            // asks user if they want to get all the overdue student names
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
                overdue.setAlwaysOnTop(true);
                overdue.dispatchEvent(new WindowEvent(overdue, WindowEvent.WINDOW_CLOSING));
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
                    String[] studentEmails = tableList.get(tabs.getSelectedIndex()).getOverdueEmails();

                    //  Add the emails to the panel
                    for (int i = 0; i < studentEmails.length; i++) {
                        emailList.append(studentEmails[i] + "\n");
                    }

                    email.add(scroller1);

                    email.setBackground(Color.WHITE);
                    email.setSize(500, 500);
                    email.setResizable(true);
                    email.setAlwaysOnTop(true);
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

    /**
     * addListener
     * asks to insert a textbook name and creates a new tab
     */
    static class addListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);

            // user inputs the name of the textbook to be added
            String input = (String) JOptionPane.showInputDialog(dialog, "What is the textbook number?", "Input Number", JOptionPane.QUESTION_MESSAGE, null, null, null);

            // add the textbook to the database
            tableList.get(tabs.getSelectedIndex()).add(input);

            mainWindow.validate();
            mainWindow.repaint();
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

            // gets the textbook to be removed
            String selection = (String) JOptionPane.showInputDialog(dialog, "Which textbook do you want to remove?", "Warning", JOptionPane.QUESTION_MESSAGE, null, null, null);

            // remove the selected textbook
            tableList.get(tabs.getSelectedIndex()).remove(selection); //need to get selection row here, temporarily set to 0

            mainWindow.validate();
            mainWindow.repaint();
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
            JFileChooser picker = new JFileChooser();

            // get the .txt file to be imported
            picker.setDialogTitle("New Database Selector");
            picker.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileFilter filter = new FileNameExtensionFilter("TXT File", "txt");
            picker.setFileFilter(filter);

            int returnValue = picker.showOpenDialog(dialog);
            System.out.println(returnValue);

            // put the imported textbook numbers into database
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = picker.getSelectedFile();
                tableList.get(tabs.getSelectedIndex()).importData(selectedFile.getAbsolutePath());
            }

            mainWindow.validate();
            mainWindow.repaint();
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

            // reminds the user that only the math department head can clear
            int selection = JOptionPane.showOptionDialog(dialog, "Only the Math Department Head can use this function. Proceed?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
            if (selection == 0) {// if the user selects yes
                // asks for confirmation on clear
                selection = JOptionPane.showOptionDialog(dialog, "Are you sure you want to clear all students?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
                if (selection == 0) { // clear all the students
                    tableList.get(tabs.getSelectedIndex()).clear();
                }
            }
            mainWindow.repaint();
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
            }

            // saves the name of the file
            fileName = (selectedFile.getName().substring(0, selectedFile.getName().length() - 4));

            //creates table to display students, sets it to fill the entire screen
            JTable table = new JTable(new SpreadsheetModel(path));
            SpreadsheetModel model = (SpreadsheetModel) table.getModel();
            model.setTable(table);
            tableList.add(model);
            table.getTableHeader().setReorderingAllowed(false);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);

            //adds JPanel to JTabbedPane
            tabs.addTab(fileName, null, scrollPane, fileName);
            //must find way to pass in the textbook name as well

            //sets new database to selected database
            tabs.setSelectedIndex(tableList.size() - 1);

            //adds JTabbedPane to main panel
            mainWindow.add(tabs, BorderLayout.CENTER);

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
                picker.setDialogTitle("New Database Selector");
                picker.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                picker.setAcceptAllFileFilterUsed(false);

                int returnValue = picker.showOpenDialog(dialog);

                // chose a save location
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = picker.getSelectedFile();
                    path = selectedFile.getAbsolutePath();
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

    /**
     * closeDataListener
     * closes the current database tab
     */
    static class closeDataListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            // asks user if they want to close their database
            int selection = JOptionPane.showOptionDialog(dialog, "Close the current database?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
            if (selection == 0) {
                // asks user if they have saved
                selection = JOptionPane.showOptionDialog(dialog, "Have you saved your work?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
                if (selection == 0) {
                    // close the database
                    tableList.remove(tabs.getSelectedIndex());
                    tabs.remove(tabs.getSelectedIndex());
                    showEmpty();
                    mainWindow.repaint();
                }
            }
            showEmpty();
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

            // save the database
            tableList.get(tabs.getSelectedIndex()).saveData();
            JOptionPane.showMessageDialog(dialog, "You have saved the database!", "Saved!", JOptionPane.QUESTION_MESSAGE, null);
        }
    }

    /**
     * searchListner
     * gets a search query from the user and highlights the first valid row
     */
    static class searchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // get the search query
            String searchText = search.getText();
            // get table from spreadsheet model
            JTable table = tableList.get(tabs.getSelectedIndex()).getTable();
            // if query is null or empty clear the selection
            if (searchText == null || searchText.isEmpty()) {
                table.clearSelection();
                return;
            }

            // get coordinates of valid rows
            int[][] coordinates = tableList.get(tabs.getSelectedIndex()).searchAll(searchText);

            // if there is no valid option, clear selection
            if (coordinates.length == 0) { // nothing found
                table.clearSelection();
            } else {// highlight first row only
                table.setRowSelectionInterval(coordinates[0][0], coordinates[0][0]);
            }
            mainWindow.repaint(); //repaints entire main window (index of table???), make sure matches array list
        }
    }
}
