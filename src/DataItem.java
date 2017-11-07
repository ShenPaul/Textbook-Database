
class DataItem {
	private DataItem next;
	private String itemNum;
	private String name;
	private String studentNum;
	private String date;           //get format
	private boolean returned;

	public DataItem(String itemNum, String name, String studentNum, String date) {
		this.itemNum = itemNum;
		this.name = name;
		this.studentNum = studentNum;
		this.date = date;
		this.next = null;
	}
	public DataItem(String itemNum) {
		this.itemNum = itemNum;
		
	}

	public DataItem getNext(){
		return this.next;
	}
	public void setNext(DataItem next){
		this.next = next;
	}

	
	public void setItemNum(String itemNum){
		this.itemNum = itemNum;
	}
	public String getItemNum(){
		return this.itemNum;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	
	public void setStudentNum(String studentNum){
		this.studentNum = studentNum;
	}
	public String getStudentNum(){
		return this.studentNum;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	public String getDate(){
		return this.date;
	}
	
	public void setReturned(boolean returned){
		this.returned = returned;
	}
	public boolean getReturned(){
		return this.returned;
	}
	
}