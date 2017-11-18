class DataItem {
	
	//class variables
	private DataItem next;
	private String itemNum;
	private String firstName;
	private String lastName;
	private String studentNum;
	private String teacher;
	private String courseCode;
	private String date;
	private boolean returned;

	//constructors
	
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

	//get and set methods
	
	public Object get(int n) {
		if(n==0){
			return (this.itemNum);
		}else if(n==1) {
			return (this.studentNum);
		}else if(n==2) {
			return (this.lastName);
		}else if(n==3) {
			return (this.firstName);
		}else if(n==4) {
			return (this.teacher);
		}else if(n==5) {
			return (this.date);
		}else if(n==6) {
			return (this.courseCode);
		}else {
			return (this.returned);
		}
	}
	public void set(int n, Object value) {
		if(n==0){
			this.itemNum = (String)value;
		}else if(n==1) {
			this.studentNum = (String)value;
		}else if(n==2) {
			this.lastName = (String)value;
		}else if(n==3) {
			this.firstName = (String)value;
		}else if(n==4) {
			this.teacher = (String)value;
		}else if(n==5) {
			this.date = (String)value;
		}else if(n==6) {
			this.courseCode = (String)value;
		}else {
			this.returned = (Boolean)value;
		}
	}
	
	public DataItem getNext(){
		return (this.next);
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