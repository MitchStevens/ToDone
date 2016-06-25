package io;

import java.util.HashMap;
import java.util.Map;

import logic.Task;
import logic.UrgencyMatrix;

public class Data {
	public  static Map<Integer, UrgencyMatrix> ALL_MATRICES = new HashMap<>();
	public  static Map<Integer, Task>		   ALL_TASKS	= new HashMap<>();
	
	static{
		ALL_TASKS.put(0, new Task("Clean the car"));
		ALL_TASKS.put(1, new Task("Read a book"));
		ALL_TASKS.put(2, new Task("Cardio"));
		ALL_TASKS.put(3, new Task("Lift heavy shit"));
		ALL_TASKS.put(4, new Task("Complete exam"));
	}
	
	/**
	 * Gets the oldest UrgencyMatrix from the list
	 * @return The newest UrgencyMatrix.
	 * */
	public static UrgencyMatrix newest_um(){
		return null;
	}
}
