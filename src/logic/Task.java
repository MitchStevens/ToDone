package logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
	String name;
	LocalDateTime due;
	
	/** 
	 * due must be of form "yyyy-mm-ddThh:mm:ss.mmmm"
	 *  */
	public Task(String name, String due){
		this.name = name;
		this.due = LocalDateTime.parse(due);
	}
	
	public Task(String name){
		this.name = name;
		this.due = null;
	}
	
	public String get_name(){
		return name;
	}
	
	public String due_string(){
		if(due == null)
			return "???";
		else
			return due.format(DateTimeFormatter.ofPattern("dd/MM"));
	}
	
	@Override
	public int hashCode(){
		int hash = 1;
		hash += due.hashCode();
		return hash;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Task))
			return false;
		Task t = (Task)o;
		if(!name.equals(t.name)) return false;
		if(!due.equals(t.due)) return false;

		return true;
	}
}
