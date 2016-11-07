package com.example.ndtwo;

public class Info {

	private int id;
	private String applicationname;
	private int time;
	private String name;
	
	public Info(int id, String applicationname, String name, int time){
		this.id=id;
		this.applicationname=applicationname;
		this.time=time;
		this.name = name;
	}
	
	public int getId () {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getApplicationname() {
        return applicationname;
    }
    public void setApplicationname(String applicationname) {
        this.applicationname = applicationname;
    }
    public String getName(){
    	return name;
    }
    public void setName(String name){
    	this.name = name;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
/*    
    public String toString() {
        return String.format("%s %s", name ,time);
    } 
*/

}


