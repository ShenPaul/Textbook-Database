import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;

class DataLinkedList {
	private DataItem head;
	
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
    		if(tempNode.getName().compareTo(sName) == 0){
    			return index;
    		}
    		index++;
    	}
    	return -1;
    }
    
    public void dataImport(String path){
    	//code for importing from text file
    	Path filePath = Paths.get(path);
    	
    }
    
}
