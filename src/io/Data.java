package io;

import java.util.HashMap;
import java.util.Map;

import logic.Task;
import logic.UrgencyMatrix;

public class Data {
	//LACHLAN IS COOL(lachlan is a homo)
	public  static Map<Integer, UrgencyMatrix> ALL_MATRICES = new HashMap<>();
	public  static Map<Integer, Task>		   ALL_TASKS	= new HashMap<>();
	
	static{
		ALL_TASKS.put(0, new Task("Clean the car"	, "2016-06-29T00:00:00"));
		ALL_TASKS.put(1, new Task("Read a book"		, "2016-07-05T00:00:00"));
		ALL_TASKS.put(2, new Task("Cardio"			, "2016-07-09T00:00:00"));
		ALL_TASKS.put(3, new Task("Lift heavy shit"	, "2016-08-01T00:00:00"));
		ALL_TASKS.put(4, new Task("Complete exam"	, "2016-08-02T00:00:00"));
	}
	
	/**
	 * Gets the oldest UrgencyMatrix from the list
	 * @return The newest UrgencyMatrix.
	 * */
	public static UrgencyMatrix newest_um(){
		return null;
	}
}
