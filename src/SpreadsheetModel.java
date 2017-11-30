/**
 * [SpreadsheetModel.java]
 * @authors Paul Shen, Sasha Maximovitch, David Stewart
 * @data November 29, 2017
 */

// imports
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

// start class
class SpreadsheetModel extends AbstractTableModel {
    // all the columns for the database
    private String[] columnNames = {"Textbook Number", "Student Number", "Last Name", "First Name", "Teacher", "Course Code", "Date", "Returned"};
    // holds the datalinkedlist for all the data
    private DataLinkedList list;
    // holds the table for the current tab
    private JTable table;

    // constructor for new Databases
    SpreadsheetModel (String name, int none) {
        // create a new database and name it
        list = new DataLinkedList(name);
    }

    // constructor for existing databases
    SpreadsheetModel (String path) {
        // create a new database and load the data from the file path
        list = new DataLinkedList();
        try {
            list.loadData(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * setTable
     * sets the JTable for the tab
     * @param table
     */
    public void setTable(JTable table) {
        this.table = table;
    }

    /**
     * getTable
     * @return JTable the current tab's JTable
     */

    public JTable getTable() {
        return table;
    }

    /**
     * getColumnCount
     * @return int - the number of columns
     */
    public int getColumnCount() {
        return 8;
    }

    /**
     * getRowCount
     * @return int - the number of rows
     */
    public int getRowCount() {
        return list.size();
    }

    /**
     * getColumnName
     * @param col - column number
     * @return String - name of column
     */
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * getValueAt
     * @param row - row number
     * @param col - column number
     * @return Object - DataItem at selected coordinate
     */
    public Object getValueAt(int row, int col) {
        return list.get(row).get(col);
    }

    /**
     * getColumnClass
     * @param c - column number
     * @return Class - the class of the current column
     */
    public Class getColumnClass(int c){
        return getValueAt(0, c).getClass();
    }

    /**
     * clear
     * clears all the students from the database
     */
    public void clear () {
        list.itemClear();
    }

    /*
     * isCellEditable
     * makes all the cells in the database editable by clicking and then typing
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return true;
    }

    /**
     * itemSort
     * sort the database by the textbook number
     */
    public void itemSort() {
        list.sortByItemNum();
    }

    /**
     * tableChanged
     * checks if table is changed
     * @param e - gets even
     */
    public void tableChanged(TableModelEvent e) {
        System.out.println(e);
    }

    /**
     * setValueAt
     * @param value - DataItem value
     * @param row - row number
     * @param col - column number
     */
    public void setValueAt(Object value, int row, int col) {
        list.get(row).set(col, value);
        try {
            list.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fireTableCellUpdated(row, col); //check what this does
    }

    /**
     * remove
     * removes specified textbook
     * @param selection the name of the textbook to be removed
     */
    public void remove(String selection) {
        list.remove(selection);
    }

    /**
     * saveData
     * saves the database
     */
    public void saveData() {
        try {
            list.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * add
     * adds a textbook to the database
     * @param name the name of the textbook to be added
     */
    public void add(String name) {
        DataItem newTextBook =  new DataItem(name);
        // if this is the first item set it to the first row
        if( ( (String)getValueAt(0, 0) ).equals("") ){
            list.get(0).set(0, name);
        }else{
            list.add(newTextBook);
        }
    }

    /**
     * importData
     * imports a list of textbook numbers from a .txt file
     * @param absolutePath - path to .txt file
     */
    public void importData(String absolutePath) {
        try {
            list.dataImport(absolutePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * addEmpty
     * creates an empty row, this is used when database is empty
     */
    public void addEmpty() {
        DataItem temp = new DataItem("","","","","","","", false);
        list.add(temp);
    }

    /**
     * assignStudent
     * assigns a student to a textbook
     * @param textBookNum - textbook number
     * @param studentNum - student number
     * @param lastName - last name
     * @param firstName - first name
     * @param teacher - teacher
     * @param courseCode - courseCode
     * @param date - date
     * @return boolean - if textbook is not valid return false, if it is return true
     */
    public boolean assignStudent(String textBookNum, String studentNum, String lastName, String firstName, String teacher, String courseCode, String date) {
        DataItem assigned = list.get(textBookNum);
        if (assigned == null){
            System.out.println("ERROR");
            return false;
        }
        else{
            assigned.set(1, studentNum);
            assigned.set(2, lastName);
            assigned.set(3, firstName);
            assigned.set(4,teacher);
            assigned.set(5,courseCode);
            assigned.set(6,date);
            return true;
        }
    }

    /**
     * getOverdueNames
     * get all the names & numbers of students that have overdue textbooks and gives it to gui
     * @return String[] - array of all the student names + student numbers
     */
    public String[] getOverdueNames() {
        DataItem[] overdue = list.getOverdue();
        // map that holds name of student and student number
        String[] overdueStudentNames = new String[overdue.length];

        for (int i = 0; i < overdue.length; i++) {
            // if this is not the last overdue textbook
            if (i != overdue.length-1){
                overdueStudentNames[i] = overdue[i].getFirstName() + " " + overdue[i].getLastName() + ":" + overdue[i].getStudentNum();
            }else{// if this is the last overdue textbook
                overdueStudentNames[i] = overdue[i].getFirstName() + " " + overdue[i].getLastName() + " : " + overdue[i].getStudentNum();
            }
        }

        return overdueStudentNames;
    }

    /**
     * getOverdueEmails
     * get all the emails of the students that have overdue textbooks and give it to gui
     * @return String[] - array of all the student emails
     */
    public String[] getOverdueEmails() {
        DataItem[] overdue = list.getOverdue();

        String[] overdueNums = new String[overdue.length];

        for (int i = 0; i <overdue.length ; i++) {
            // get the student numbers
            overdueNums[i] = overdue[i].getStudentNum() + "@gapps.yrdsb.ca";
        }

        return overdueNums;
    }

    /**
     * setFilePath
     * set the path of the database file, used when creating a new database
     * @param path - path of file to be saved
     */
    public void setFilePath(String path){
        try {
            list.setFilePath(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sortByType
     * sort by a specified column
     * @param column - column to be sorted
     */
    public void sortByType(int column){
        list.sortByType(column);
    }

    /**
     * searchAll
     * returns all the coordinates of valid options from user's search query
     * @param search - string to be searched
     * @return int[][] - array of possible coordinates
     */
    public int[][] searchAll(String search){
        return list.searchAll(search);
    }


}