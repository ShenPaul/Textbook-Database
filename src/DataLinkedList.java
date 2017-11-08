import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;

class DataLinkedList {
	private DataItem head;
	private String listName;
	
	DataLinkedList(String listName){
		this.listName = listName;
	}
	
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
	
    public DataItem get(int index) { 
        DataItem tempNode = head;
        for(int i=0;i<index;i++){
          tempNode = tempNode.getNext();
        }
        return tempNode;
    }
    
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
    
    public void clear() { 
        head = null;
    }
    
    public int size() { 
        DataItem tempNode = head;
        int i=0;
        while(tempNode != null){
          tempNode = tempNode.getNext();
          i++;
        }
        return i;
    }
    
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
    public int searchByName(String sName){
    	DataItem tempNode = head;
    	int index = 0;
    	while(tempNode != null){
    		if((tempNode.getLastName().compareTo(sName) == 0)||(tempNode.getFirstName().compareTo(sName) == 0)){
    			return index;
    		}
    		index++;
    	}
    	return -1;
    }
    
    public void saveData()throws IOException{
    	BufferedWriter bWrite = new BufferedWriter(new FileWriter(listName+".csv"));
    	DataItem tempNode = head;
    	
    	while(tempNode != null){//add teacher, split name, course code,
    		bWrite.write(tempNode.getItemNum()+","+tempNode.getStudentNum()+","+tempNode.getLastName()+","+tempNode.getFirstName()+","+tempNode.getTeacher()+","+tempNode.getDate()+","+tempNode.getCourseCode()+","+tempNode.getReturned());
    		bWrite.newLine();
    	}
    	
    	bWrite.close();
    }
    
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
    
    public void dataImport(String path)throws IOException{
    	//code for importing from text file
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
    
    public void setListName(String listName){
    	this.listName = listName;
    }
    public String getListName(){
    	return this.listName;
    }
    
}
