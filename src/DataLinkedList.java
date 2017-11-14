
//start of imports
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
//end of imports

class DataLinkedList {//start of class
	
	//class variables
	private DataItem head;
	private String listName;
	
	//constructor
	DataLinkedList(String listName){
		this.listName = listName;
	}
	
	//methods
	
	/** add *******************************************
	 * adds a DataItem to the list
	 * @param item to be added to the list
	 * @return true if method run successfully
	 */
    public boolean add(DataItem item) { 
        DataItem tempNode = head;
        if (head==null) {
          head = item;
          return true;
        }
        while(tempNode.getNext() != null) { 
          tempNode = tempNode.getNext();
        }
        tempNode.setNext(item);
        return true;
    }
	
    /** get *******************************************
     * gets DataItem at specified index
     * @param index of DataItem to be retrieved
     * @return DataItem at specified index
     */
    public DataItem get(int index) { 
        DataItem tempNode = head;
        for(int i=0;i<index;i++){
          tempNode = tempNode.getNext();
        }
        return tempNode;
    }
    
    /** remove *******************************************
     * removes and returns DataItem at specified index
     * @param index of DataItem to be removed
     * @return removed DataItem
     */
    public DataItem remove(int index) { 
        DataItem tempNode;
        DataItem prevNode = head;
        if(index == 0){
          head = head.getNext();
        }
        for(int i=0;i<index-1;i++){
          prevNode = prevNode.getNext();
        }
        tempNode = prevNode.getNext();
        if(prevNode.getNext() != null){
          prevNode.setNext(tempNode.getNext());
        }
        if(index == 0){
          return prevNode;
        }
        return tempNode;
    }
    
    /** clear *******************************************
     * clears the list
     */
    public void clear() { 
        head = null;
    }
    
    /** size *******************************************
     * calculates size of the list
     * @return integer size of the list
     */
    public int size() { 
        DataItem tempNode = head;
        int i=0;
        while(tempNode != null){
          tempNode = tempNode.getNext();
          i++;
        }
        return i;
    }
    
    /** getOverdue *******************************************
     * locates not returned DataItems
     * @return array of all not returned items
     */
    public DataItem[] getOverdue(){
    	DataItem tempNode = head;
        
        ArrayList<DataItem> dataArray = new ArrayList<DataItem>();
        while(tempNode != null){
        	if(!tempNode.getReturned()){
        		dataArray.add(tempNode);
        	}
        }
        return (DataItem[]) dataArray.toArray();
    }
    
    /** searchByItemNum *******************************************
     * locates the DataItem with specified number
     * @param sNum number of the item 
     * @return index of the DataItem with specified item number
     */
    public int searchByItemNum(String sNum){
    	DataItem tempNode = head;
    	int index = 0;
    	while(tempNode != null){
    		if(tempNode.getItemNum().compareTo(sNum) == 0){
    			return index;
    		}
    		index++;
    	}
    	return -1;
    }
    
    /** searchByLastName *******************************************
     * locates the DataItem with specified student last name
     * @param sName student last name
     * @return index of the DataItem with specified student last name
     */
    public DataItem[] searchByLastName(String sName){
    	DataItem tempNode = head;
    	int s = 0;
    	while(tempNode != null){
    		if(tempNode.getLastName().compareTo(sName) == 0){
    			s++;
    		}
    	}
    	
    	DataItem[] dA = new DataItem[s];
    	int index = 0;
    	
    	tempNode = head;
    	while(tempNode != null){
    		if(tempNode.getLastName().compareTo(sName) == 0){
    			dA[index] = tempNode;
    			index++;
    		}
    		tempNode = tempNode.getNext();
    	}
    	return dA;
    }
    
    /** searchByFirstName *******************************************
     * locates the DataItem with specified student first name
     * @param sName student first name
     * @return index of the DataItem with specified first name
     */
    public DataItem[] searchByFirstName(String sName){
    	DataItem tempNode = head;
    	int s = 0;
    	while(tempNode != null){
    		if(tempNode.getFirstName().compareTo(sName) == 0){
    			s++;
    		}
    	}
    	
    	DataItem[] dA = new DataItem[s];
    	int index = 0;
    	
    	tempNode = head;
    	while(tempNode != null){
    		if(tempNode.getFirstName().compareTo(sName) == 0){
    			dA[index] = tempNode;
    			index++;
    		}
    		tempNode = tempNode.getNext();
    	}
    	return dA;
    }
    
    /** saveData *******************************************
     * save data to a .csv file
     * @throws IOException
     */
    public void saveData()throws IOException{
    	BufferedWriter bWrite = new BufferedWriter(new FileWriter(listName+".csv"));
    	DataItem tempNode = head;
    	
    	while(tempNode != null){
    		bWrite.write(tempNode.getItemNum()+","+tempNode.getStudentNum()+","+tempNode.getLastName()+","+tempNode.getFirstName()+","+tempNode.getTeacher()+","+tempNode.getDate()+","+tempNode.getCourseCode()+","+tempNode.getReturned());
    		bWrite.newLine();
    		tempNode = tempNode.getNext();
    	}
    	
    	bWrite.close();
    }
    
    /** loadData *******************************************
     * loads data from specified file name
     * @param fileName the name of file to be read
     * @throws IOException
     */
    public void loadData(String fileName)throws IOException{
    	BufferedReader bRead = new BufferedReader(new FileReader(fileName+".csv"));
    	String str;
    	String[] strArr;
    	DataItem tempNode = head;
    	while((str = bRead.readLine()) != null){
    		strArr = str.split(",");
    		tempNode = new DataItem(strArr[0],strArr[1],strArr[2],strArr[3],strArr[4],strArr[5],strArr[6],Boolean.getBoolean(strArr[7]));
    		tempNode = tempNode.getNext();
    	}
    	bRead.close();
    }
    
    /** dataImport *******************************************
     * creates DataItems from .csv file with item numbers only
     * @param path to the file to be read
     * @throws IOException
     */
    public void dataImport(String path)throws IOException{
    	Path filePath = Paths.get(path);
    	
    	BufferedReader bRead = new BufferedReader(new FileReader(filePath.toFile()));
    	String str;
    	DataItem tempNode = head;
    	while((str = bRead.readLine()) != null){
    		tempNode = new DataItem(str);
    		tempNode = tempNode.getNext();
    	}
    	bRead.close();
    }
    
    //get and set methods for list name
    public void setListName(String listName){
    	this.listName = listName;
    }
    public String getListName(){
    	return this.listName;
    }
    
    /** sortByLastName *******************************************
     * sorts the list alphabetically by last name
     */
	public void sortByLastName(){
		ArrayList<DataItem> fullArr = new ArrayList<DataItem>();
		ArrayList<DataItem> emptyArr = new ArrayList<DataItem>();
		DataItem tempNode = head;
		
		//separate list into two arrays: assigned and not assigned
		while(tempNode != null){
			if(tempNode.getLastName() == null){
				emptyArr.add(tempNode);
			}else{
				fullArr.add(tempNode);
			}
			
			tempNode = tempNode.getNext();
		}

		//insertion sort on the array with assigned items
		DataItem[] newArr = (DataItem[])fullArr.toArray();
		tempNode = null;
		for(int i=1;i<newArr.length;i++){
			for(int j=i;j>0;j--){
				if(newArr[j].getLastName().compareTo(newArr[j-1].getLastName()) < 0){
                   		tempNode = newArr[j];
                   		newArr[j] = newArr[j-1];
                   		newArr[j-1] = tempNode;
               	}
			}
		}
		
		//copy sorted array to the list
		head = newArr[0];
		tempNode = head;
		for(int i=1; i<newArr.length;i++){
			tempNode.setNext(newArr[i]);
			tempNode = tempNode.getNext();
		}

		//add unassigned items to the list
		DataItem[] residueArr = (DataItem[])emptyArr.toArray();
		for(int i=0;i<residueArr.length;i++){
			tempNode.setNext(residueArr[i]);
			tempNode = tempNode.getNext();
		}
		tempNode.setNext(null);

	}
    
	/** sortByFirstName *******************************************
	 * sorts the list alphabetically by last name
	 */
	public void sortByFirstName(){
		ArrayList<DataItem> fullArr = new ArrayList<DataItem>();
		ArrayList<DataItem> emptyArr = new ArrayList<DataItem>();
		DataItem tempNode = head;
		
		//separate list into two arrays: assigned and not assigned
		while(tempNode != null){
			if(tempNode.getFirstName() == null){
				emptyArr.add(tempNode);
			}else{
				fullArr.add(tempNode);
			}
			
			tempNode = tempNode.getNext();
		}

		//insertion sort on the array with assigned items
		DataItem[] newArr = (DataItem[])fullArr.toArray();
		tempNode = null;
		for(int i=1;i<newArr.length;i++){
			for(int j=i;j>0;j--){
				if(newArr[j].getFirstName().compareTo(newArr[j-1].getFirstName()) < 0){
                   		tempNode = newArr[j];
                   		newArr[j] = newArr[j-1];
                   		newArr[j-1] = tempNode;
               	}
			}
		}
		
		//copy sorted array to the list
		head = newArr[0];
		tempNode = head;
		for(int i=1; i<newArr.length;i++){
			tempNode.setNext(newArr[i]);
			tempNode = tempNode.getNext();
		}

		//add unassigned items to the list
		DataItem[] residueArr = (DataItem[])emptyArr.toArray();
		for(int i=0;i<residueArr.length;i++){
			tempNode.setNext(residueArr[i]);
			tempNode = tempNode.getNext();
		}
		tempNode.setNext(null);

	}
	
	/** sortByItemNum *******************************************
	 * sorts the list alphabetically item number
	 */
	public void sortByItemNum(){

		DataItem[] newArr = new DataItem[this.size()];
		DataItem tempNode = head;
		
		//copy list items to an array
		for(int i=0;i<newArr.length;i++){
			newArr[i] = tempNode;
			tempNode = tempNode.getNext();
		}
		
		//insertion sort 
		for(int i=1;i<newArr.length;i++){
			for(int j=i;j>0;j--){
				if(newArr[j].getItemNum().compareTo(newArr[j-1].getItemNum()) < 0){
                   		tempNode = newArr[j];
                   		newArr[j] = newArr[j-1];
                   		newArr[j-1] = tempNode;
               	}
			}
		}
		
		//copy sorted data to the list
		head = newArr[0];
		tempNode = head;
		for(int i=1; i<newArr.length;i++){
			tempNode.setNext(newArr[i]);
			tempNode = tempNode.getNext();
		}
		tempNode.setNext(null);

	}
	
    
}//end of class
