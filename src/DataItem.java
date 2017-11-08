
class DataItem {
	private DataItem next;
	private String itemNum;
	private String firstName;
	private String lastName;
	private String studentNum;
	private String teacher;
	private String courseCode;
	private String date;
	private boolean returned;

	public DataItem(String itemNum, String studentNum, String lastName, String firstName, String teacher, String date, String courseCode, boolean returned) {
		this.itemNum = itemNum;
		this.studentNum = studentNum;
		this.firstName = firstName;
		this.lastName = lastName;
		this.teacher = teacher;
		this.courseCode = courseCode;
		this.date = date;
		this.next = null;
		this.returned = returned;
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
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	public String getFirstName(){
		return this.firstName;
	}
	public void setLastName(String name){
		this.lastName = name;
	}
	public String getLastName(){
		return this.lastName;
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
	
	public void setTeacher(String teacher){
		this.teacher = teacher;
	}
	public String getTeacher(){
		return this.teacher;
	}
	
	public void setCourseCode(String courseCode){
		this.courseCode = courseCode;
	}
	public String getCourseCode(){
		return this.courseCode;
	}
	
	public void setReturned(boolean returned){
		this.returned = returned;
	}
	public boolean getReturned(){
		return this.returned;
	}
	
}