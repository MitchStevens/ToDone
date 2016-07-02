package io;

import javafx.scene.text.Font;
import logic.Task;
import logic.UrgencyMatrix;

import java.util.HashMap;
import java.util.Map;

public class Data {
	public  static Map<Integer, UrgencyMatrix> ALL_MATRICES = new HashMap<>();
	public  static Map<Integer, Task>		   ALL_TASKS	= new HashMap<>();
	public  static Map<String, Font>           ALL_FONTS    = new HashMap<>();

	static{
		Reader.load_all();
	}

	/**
	 * Gets the oldest UrgencyMatrix from the list
	 * @return The newest UrgencyMatrix.
	 * */
	public static UrgencyMatrix newest_um(){
		return (ALL_MATRICES.isEmpty()) ? null : ALL_MATRICES.get(0);
	}
}
