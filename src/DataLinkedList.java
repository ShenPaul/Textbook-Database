/**
 *[DataLinked.java]
 * @author 349271213
 * @date November 29, 2017
 */

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
	private Path filePath;

	//constructors
	DataLinkedList(String listName){
		this.listName = listName;
	}
	DataLinkedList(){}

	//methods

	/** add *******************************************
	 * adds a DataItem to the end of the list
	 * @param item to be added to the list
	 * @return true if method run successfully
	 */
	public boolean add(DataItem item) {
		DataItem tempNode = head;
		if (head==null) { //if list is empty
			head = item;
			return true;
		}
		while(tempNode.getNext() != null) { //loop through the list until the last item
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
		for(int i=0;i<index;i++){ //loop until the index
			tempNode = tempNode.getNext();
		}
		return tempNode;
	}

	/** get *******************************************
	 * gets DataItem with specified number
	 * @param textbookNum of DataItem to be retrieved
	 * @return DataItem with specified number
	 */
	public DataItem get(String textbookNum){
		DataItem tempNode = head;
		for (int i = 0; i < size(); i++) { //loop through the list
			if(tempNode.getItemNum().equals(textbookNum)){ //check if numbers match
				return tempNode;
			}
			tempNode = tempNode.getNext();
		}
		return null;
	}

	/** remove *******************************************
	 * removes and returns DataItem at specified index
	 * @param textbookNum of DataItem to be removed
	 * @return removed DataItem
	 */
	public DataItem remove(String textbookNum) {
		DataItem tempNode = null;
		DataItem prevNode = head;
		if(head.getItemNum().equals(textbookNum)){
			head = head.getNext();
			return prevNode;
		}
		tempNode = head.getNext();
		for(int i=0;i<size();i++){ //loop through the list until the index
			if(tempNode.getItemNum().equals(textbookNum)){
				if (tempNode.getNext() != null){
					prevNode.setNext(tempNode.getNext());
					tempNode.setNext(null);
					return prevNode;
				} else{
					prevNode.setNext(null);
					return prevNode;
				}
			}
			tempNode = tempNode.getNext();
			prevNode = prevNode.getNext();
		}
		return tempNode;
	}

	/** setFilePath *******************************************
	 * sets the file location
	 * @param str representation of the path
	 */
	public void setFilePath(String str)throws IOException{
		this.filePath = Paths.get(str + "\\" +listName+ ".csv");
		BufferedWriter bWrite = new BufferedWriter(new FileWriter(filePath.toFile()));
		bWrite.close();
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
	public DataItem[] getOverdue(String semester){
		DataItem tempNode = head;

		ArrayList<DataItem> dataArray = new ArrayList<DataItem>();
		while(tempNode != null){
			if(!tempNode.getReturned() && !tempNode.getStudentNum().isEmpty()&& tempNode.getCourseCode().substring(9).equals(semester)){
				dataArray.add(tempNode);
			}
			tempNode = (tempNode.getNext());
		}
		return dataArray.toArray(new DataItem[] {});
	}

	/** searchAll *******************************************
	 * locates all instances of the searched String
	 * @param str to be searched for
	 * @return double integer array of all locations of the specified String in [row][column] form
	 */
	public int[][] searchAll(String str){
		int[][] sArr;
		int count = 0;

		for(int i=0;i<this.size();i++) { //count number of instances of the searched String
			for(int j=0;j<7;j++) {
				if(((String)(this.get(i).get(j))).contains(str)) {
					count++;
				}
			}
		}

		sArr = new int[count][2]; //[number of instances][0=row OR 1=column]
		count = 0;

		for(int i=0;i<this.size();i++) { //write all instances into a 2D integer array
			for(int j=0;j<7;j++) {
				if(((String)(this.get(i).get(j))).equals(str)) {
					sArr[count][0] = i;
					sArr[count][1] = j;
					count++;
				}
			}
		}

		return sArr;
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
	} //does this cover multiple names?

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

		BufferedWriter bWrite;

		if(filePath != null){ //initialize connection with the file
			bWrite = new BufferedWriter(new FileWriter(filePath.toFile()));
		}else{
			bWrite = new BufferedWriter(new FileWriter(listName+".csv"));
		}
		DataItem tempNode = head;

		while(tempNode != null){ //loop through data
			if(!tempNode.getStudentNum().equals("")){
				bWrite.write(tempNode.getItemNum()+","+tempNode.getStudentNum()+","+tempNode.getLastName()+","+tempNode.getFirstName()+","+tempNode.getTeacher()+","+tempNode.getDate()+","+tempNode.getCourseCode()+","+tempNode.getReturned());
			}else{
				bWrite.write(tempNode.getItemNum());
			}
			bWrite.newLine();
			tempNode = tempNode.getNext();
		}

		bWrite.close();
	}

	/** loadData *******************************************
	 * loads data from specified file name
	 * @param path the name of file to be read
	 * @throws IOException
	 */
	public void loadData(String path)throws IOException{
		Path filePath = Paths.get(path); //create reference to the file from given path
		BufferedReader bRead = new BufferedReader(new FileReader(filePath.toFile()));
		this.filePath = filePath; //update class variable for file path
		String str;
		String[] strArr;
		DataItem tempNode;
		DataItem prevNode;
		//set data head
		str = bRead.readLine();
		strArr = str.split(",");
		if (strArr.length == 1){
			head = new DataItem(strArr[0],"","","","","","",false);
		}else{
			head = new DataItem(strArr[0],strArr[1],strArr[2],strArr[3],strArr[4],strArr[5],strArr[6],(strArr[7].equals("true")));
		}
		prevNode = head;

		while((str = bRead.readLine()) != null){//loop through file
			strArr = str.split(",");
			if(strArr.length > 1){//create 1 node per line
				tempNode = new DataItem(strArr[0],strArr[1],strArr[2],strArr[3],strArr[4],strArr[5],strArr[6],(strArr[7].equals("true")));
			}else{
				tempNode = new DataItem(strArr[0],"","","","","","",false);
			}
			prevNode.setNext(tempNode);
			prevNode = tempNode;
		}

		bRead.close();
	}

	/** dataImport *******************************************
	 * creates DataItems from .txt file with item numbers only
	 * @param path to the file to be read
	 * @throws IOException
	 */
	public void dataImport(String path)throws IOException{
		Path filePath = Paths.get(path);//create reference to the file from given path

		BufferedReader bRead = new BufferedReader(new FileReader(filePath.toFile()));

		String str;

		DataItem tempNode;
		DataItem prevNode;
		//set head data
		if (head.getItemNum().isEmpty()) {
			head.setItemNum(bRead.readLine());
			prevNode = head;
		}else{
			prevNode = get(size()-1);
		}

		while((str = bRead.readLine()) != null){//loop through file
			tempNode = new DataItem(str);//create 1 node per line
			prevNode.setNext(tempNode);
			prevNode = tempNode;
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
		DataItem[] newArr = new DataItem[fullArr.size()];
		fullArr.toArray(newArr);
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
		DataItem[] residueArr = new DataItem[emptyArr.size()];
		emptyArr.toArray(residueArr);
		for(int i=0;i<residueArr.length;i++){
			tempNode.setNext(residueArr[i]);
			tempNode = tempNode.getNext();
		}
		tempNode.setNext(null);

	}

	/** sortByType *******************************************
	 * sorts the list alphabetically by given column
	 * not meant to be used with itemNum and returned
	 */
	public void sortByType(int l){
		ArrayList<DataItem> fullArr = new ArrayList<DataItem>();
		ArrayList<DataItem> emptyArr = new ArrayList<DataItem>();
		DataItem tempNode = head;

		//separate list into two arrays: assigned and not assigned
		while(tempNode != null){
			if(tempNode.get(l) == null){
				emptyArr.add(tempNode);
			}else{
				fullArr.add(tempNode);
			}

			tempNode = tempNode.getNext();
		}

		//insertion sort on the array with assigned items
		DataItem[] newArr = new DataItem[fullArr.size()];
		fullArr.toArray(newArr);
		tempNode = null;
		for(int i=1;i<newArr.length;i++){
			for(int j=i;j>0;j--){
				if(((String)newArr[j].get(l)).compareTo((String)(newArr[j-1].get(l))) < 0){
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
		DataItem[] residueArr = new DataItem[emptyArr.size()];
		emptyArr.toArray(residueArr);
		for(int i=0;i<residueArr.length;i++){
			tempNode.setNext(residueArr[i]);
			tempNode = tempNode.getNext();
		}
		tempNode.setNext(null);

	}

	/** sortByFirstName *******************************************
	 * sorts the list alphabetically by first name
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
		DataItem[] newArr = new DataItem[fullArr.size()];
		fullArr.toArray(newArr);
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
		DataItem[] residueArr = new DataItem[emptyArr.size()];
		emptyArr.toArray(residueArr);
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

		boolean allIntegers = true;

		//debug if statement to bypass broken code
		if(this.head.getItemNum().equals("")) {
			(this.head) = (this.head.getNext());
		}

		DataItem tempNode = (this.head);
		DataItem[] newArr = new DataItem[this.size()];

		//run through the data to check if all numbers are of integer type
		for(int i=0;i<newArr.length;i++){
			newArr[i] = tempNode;
			try {
				int testNum = Integer.parseInt(tempNode.getItemNum());
			}catch(NumberFormatException e){
				allIntegers = false;
			}
			tempNode = tempNode.getNext();
		}

		if(allIntegers){ //insertion sort on values as integers
			for(int i=1;i<newArr.length;i++){
				for(int j=i;j>0;j--){
					if((Integer.parseInt(newArr[j].getItemNum())) < (Integer.parseInt(newArr[j-1].getItemNum()))){
						tempNode = newArr[j];
						newArr[j] = newArr[j-1];
						newArr[j-1] = tempNode;
					}
				}
			}
		}else { //insertion sort on values as strings
			for(int i=1;i<newArr.length;i++){
				for(int j=i;j>0;j--){
					if(newArr[j].getItemNum().compareTo(newArr[j-1].getItemNum()) < 0){
						tempNode = newArr[j];
						newArr[j] = newArr[j-1];
						newArr[j-1] = tempNode;
					}
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

	/** itemClear *******************************************
	 * removes data of a specified textbook
	 * @param index index of the item to be cleared
	 */
	public void itemClear(int index){
		DataItem item = this.get(index);
		item.setCourseCode("");
		item.setDate("");
		item.setFirstName("");
		item.setLastName("");
		item.setStudentNum("");
		item.setTeacher("");
		item.setReturned(false);
	}

	/** itemClear *******************************************
	 * removes data of all items
	 */
	public void itemClear(String semester){
		for(int i=0;i<this.size();i++){
			DataItem item = this.get(i);
			if (item.getCourseCode().isEmpty()){
				continue;
			}

			if (item.getReturned() && item.getCourseCode().substring(9).equals(semester)) {
				item.setCourseCode("");
				item.setDate("");
				item.setFirstName("");
				item.setLastName("");
				item.setStudentNum("");
				item.setTeacher("");
				item.setReturned(false);
			}
		}
	}


}//end of class