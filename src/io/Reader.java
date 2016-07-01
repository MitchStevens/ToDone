package io;

import logic.Task;
import logic.UrgencyMatrix;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Reader {

	/**
	 * FILE is the source file of the JSON data. readFile() can read all the data from the file given the file has been
	 * located and loaded. This is temporary until all the details of data storage are sorted out.
	 *
	 * JSON Structure:
	 * ===============
	 * {
	 *     "tasks": [
	 *          {
	 *              "id": (int),
	 *              "name": (String),
	 *              "due": (String)
	 *          }
	 *      ],
	 *      "matrices": [
	 *          {
	 *              "name": (String),
	 *              "relative_urgencies": [
	 *                  [
	 *                  ]
	 *              ]
	 *          }
	 *      ]
	 * }
	 *
	 */
	private static JSONObject FILE = null;

	/**
	 * Loads all the tasks and matrices from file
	 */
	public static void load_all(){
		load_tasks();
		load_matrices();
	}

	/**
	 * Loads all the tasks from file
	 */
	public static void load_tasks(){
		HashMap<Integer, Task> all_tasks = new HashMap<>();

		JSONArray tasks = FILE.getJSONArray("tasks");
		for (int i = 0; i < tasks.length(); i++){
			JSONObject o = tasks.getJSONObject(i);
			Task t = new Task(o.getString("name"), o.getString("due"));
			all_tasks.put(i, t);
		}

		Data.ALL_TASKS = all_tasks;

	}

	/**
	 * Loads all the matrices from file
	 * =====
	 * Not sure if it'll read from file, but the JSON to UM part works
	 */
	public static void load_matrices(){
		HashMap<Integer, UrgencyMatrix> all_matrices = new HashMap<>();

		JSONArray matrices = FILE.getJSONArray("matrices");
		for (int i = 0; i < matrices.length(); i++){
			JSONObject o = matrices.getJSONObject(i);
			UrgencyMatrix um = new UrgencyMatrix(o.getString("name"), get_relative_urgencies_from_JSON(o.getJSONArray("relative_urgencies")));
			all_matrices.put(i, um);
		}

		Data.ALL_MATRICES = all_matrices;
	}

	/**
	 * Converts the multidimensional JSONArray used to hold the relative urgency matrix into a double[][]
	 * @param array the multidimensional JSONArray
	 * @return the JSONArray in double[][] form
	 */
	public static double[][] get_relative_urgencies_from_JSON(JSONArray array){
		int size = array.length();
		double[][] relative_urgencies = new double[size][size];

		for(int i = 0; i < size; i++){
			JSONArray ar = array.getJSONArray(i);
			for(int j = 0; j < size; j++){
				relative_urgencies[i][j] = ar.getDouble(j);
			}
		}

		return relative_urgencies;
	}

	/**
	 * Reads all the contents of a txt file into a string. Uses the java.io as nio is not
	 * compatible with Android.
	 * @param file The file to read from
	 * @return A string containing the full contents of the file
	 */
	public static String readFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

}





















