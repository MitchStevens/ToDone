package io;

import javafx.scene.text.Font;
import logic.Task;
import logic.UrgencyMatrix;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
	private static JSONObject MATRICES_JSON = null;
	private static JSONObject TASKS_JSON = null;

	private static final String JSON_FILE_LOC = System.getProperty("user.dir")+"/src/res/json";

	private static final File FONT_FILE_DIR = new File(System.getProperty("user.dir")+"/src/res/font");

	/**
	 * Loads all the tasks and matrices from file
	 */
	public static void load_all(){
		load_tasks();
		load_matrices();
		//This method not working
		//load_fonts();
	}

	/**
	 * Loads all the tasks from file
	 */
	public static void load_tasks(){
		if (TASKS_JSON == null) read_in_data();
		JSONArray tasks = TASKS_JSON.getJSONArray("tasks");
		for (int i = 0; i < tasks.length(); i++){
			JSONObject o = tasks.getJSONObject(i);
			Task t = new Task(o.getString("name"), o.getString("due"));
			Data.ALL_TASKS.put(i, t);
		}

	}

	/**
	 * Loads all the matrices from file
	 * =====
	 * Not sure if it'll read from file, but the JSON to UM part works
	 */
	public static void load_matrices(){
		if (MATRICES_JSON == null) read_in_data();

		JSONArray matrices = MATRICES_JSON.getJSONArray("matrices");
		for (int i = 0; i < matrices.length(); i++){
			JSONObject o = matrices.getJSONObject(i);
			UrgencyMatrix um = new UrgencyMatrix(o.getString("name"), get_relative_urgencies_from_JSON(o.getJSONArray("relative_urgencies")));
			Data.ALL_MATRICES.put(i, um);
		}
	}

	public static void load_fonts(){
		for (File file : FONT_FILE_DIR.listFiles()){
			Font f = Font.loadFont(file.getPath(), 0);
			Data.ALL_FONTS.put(f.getName(), f);
		}
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

	/**
	 * Reads the json information from file
	 */
	private static void read_in_data(){
		try {
			File md = new File(JSON_FILE_LOC +File.separator+"matrices.json");
			MATRICES_JSON = new JSONObject(readFile(md));

			File td = new File(JSON_FILE_LOC +File.separator+"tasks.json");
			TASKS_JSON = new JSONObject(readFile(td));
		} catch (IOException e){e.printStackTrace();}
	}

}





















