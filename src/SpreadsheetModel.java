import java.io.IOException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

class SpreadsheetModel extends AbstractTableModel {
   private String[] columnNames = {"Textbook Number", "Student Number", "Last Name", "First Name", "Teacher", "Course Code", "Date", "Returned"};
//   private  ArrayList <DataItem> data = new ArrayList <DataItem>();
   private DataLinkedList list;

//    private Object[][] data = {
//         {"Kathy", "Smith",
//             "Snowboarding", new Integer(5), new Boolean(false)},
//            {"John", "Doe",
//             "Rowing", new Integer(3), new Boolean(true)},
//            {"Sue", "Black",
//             "Knitting", new Integer(2), new Boolean(false)},
//            {"Jane", "White",
//             "Speed reading", new Integer(20), new Boolean(true)},
//            {"Joe", "Brown",
//             "Pool", new Integer(10), new Boolean(false)},
//        };
   
   // For new Databases
    SpreadsheetModel (String name, int none) {
        list = new DataLinkedList(name);
    }

   // for existing databases
   SpreadsheetModel (String path) {
        list = new DataLinkedList();
	try {
		list.loadData(path);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   

   
    public int getColumnCount() {
        return 8;
    }

    public int getRowCount() {
     return list.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return list.get(row).get(col);
    }

    public Class getColumnClass(int c){
    	return getValueAt(0, c).getClass();
    }
    
    public void clear () {
    	//list.itemClear();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
     return true;
    }

    public void firstSort() {
        list.sortByFirstName();
    }

    public void lastSort() {
        list.sortByLastName();
    }

    public void itemSort() {
        list.sortByItemNum();
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        list.get(row).set(col, value);
        fireTableCellUpdated(row, col); //check what this does
    }

    public void remove(int index) {
        list.remove(index);
    }

    public void saveData() {
        try {
            list.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(String name) {
		DataItem newTextBook =  new DataItem(name);
		if( ( (String)getValueAt(0, 0) ).equals("") ){
			list.get(0).set(0, name);
		}else{
			list.add(newTextBook);
		}
    }

    public void importData(String absolutePath) {
        try {
            list.dataImport(absolutePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void addEmpty() {
		DataItem temp = new DataItem("","","","","","","", false);
		list.add(temp);
	}
	
}

//https://docs.oracle.com/javase/tutorial/uiswing/components/table.html save data after editing
//https://docs.oracle.com/javase/7/docs/api/javax/swing/table/AbstractTableModel.html