import java.io.IOException;

public class DataLauncherDemo {

	public static void main(String[] args)throws IOException {
		DataLinkedList list = new DataLinkedList("DemoTextbook");
		
		list.add(new DataItem("1010-1","0349271211","Last1","First1","Mangat","99/99/9001","ICS4U",false));
		list.add(new DataItem("1010-2","0349271212","Last2","First2","Mangat","99/99/9001","ICS4U",false));
		list.add(new DataItem("1010-3","0349271213","Last3","First3","Mangat","99/99/9001","ICS4U",false));
		list.add(new DataItem("1010-4","0349271214","Last4","First4","Mangat","99/99/9001","ICS4U",false));
		list.saveData();
	}

}
