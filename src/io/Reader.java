package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.text.Font;
import logic.Task;
import logic.UrgencyMatrix;

import org.json.JSONArray;
import org.json.JSONObject;



public class Reader {
	
	static Map<String, UrgencyMatrix> ALL_MATRICES = new HashMap<>();
	static Map<Long, Task>				ALL_TASKS	= new HashMap<>();
	
//	static{
//		read_urgency_matrices();
//	}
	
	public static void read_all(){
		//read_urgency_matrices();
		//read_tasks();
		//read_fonts();
	}
	
	public static void read_urgency_matrices() {	
		try{
			String name;
			int n;
			Long[] task_ids;
			double[][] urgencies;
			
			String str = new String(Files.readAllBytes(Paths.get("src/res/matrices.json")));
			JSONObject obj = new JSONObject(str);
			JSONArray arr = obj.getJSONArray("matrices");
			for (int i = 0; i < arr.length(); i++){
				JSONObject curr = arr.getJSONObject(i);
				name 	= curr.getString("name");
			    n	= curr.getInt("size");
			    
			    JSONArray tasks = curr.getJSONArray("tasks");
			    task_ids = new Long[n];
			    for(int j = 0; j < n; j++)
			    	task_ids[j] = tasks.getLong(j);
			    
			    JSONArray ux, uy;
			    urgencies = new double[n][n];
			    ux = curr.getJSONArray("relative_urgencies");
			    for(int j = 0; j < n; j++){
			    	uy = ux.getJSONArray(j);
			    	for(int k = 0; k < n; k++)
			    		urgencies[j][k] = uy.getDouble(k);
			    }
			    
			    UrgencyMatrix um = new UrgencyMatrix(name, n, urgencies, task_ids);
			    ALL_MATRICES.put(name, um);
			}
			
		} catch (IOException e){
			
		}
	}
	
	private static void read_tasks(){
		long id;
		String name;
		long created, due;
		
		try{			
			String str = new String(Files.readAllBytes(Paths.get("src/res/tasks.json")));
			JSONObject obj = new JSONObject(str);
			JSONArray arr = obj.getJSONArray("tasks");
			for (int i = 0; i < arr.length(); i++){
				JSONObject curr = arr.getJSONObject(i);
				
				id		= curr.getLong("id");
				name 	= curr.getString("name");
				created	= curr.getLong("created");
				due		= curr.getLong("due");
			    
			    Task t = new Task(id, name, created, due);
			    ALL_TASKS.put(id, t);
			}
			
		} catch (IOException e){
			
		}
	}
}














