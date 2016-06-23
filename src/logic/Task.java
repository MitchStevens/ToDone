package logic;

import java.util.Date;

public class Task {
	long id;
	String name;
	Date created;
	Date due;
	
	public Task(long  id, String name, long created, long  due){
		this.id = id;
		this.name = name;
		this.created = new Date(created);
		this.due = new Date(due);
	}
	
	public Task(long  id, String name){
		this.id = id;
		this.name = name;
		this.created = null;
		this.due = null;
	}
	
	public String get_name(){
		return name;
	}
}
