package io;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {

	/**
	 * Temporary directory path to the save location of the files.
	 * This will be modified once all the details are sorted out.
	 */
	private static File DIR = null;
	private static String FILE_NAME = null;

	/**
	 * Saves all the tasks and matrices to a file "DIR/FILE_NAME"
	 */
	public static void save_all(){
		save_tasks();
		save_matrices();
	}

	/**
	 * Saves all the tasks to a file "DIR/FILE_NAME"
	 * =====
	 * JSON conversion functionality tested and working, saving functionality not tested
	 */
	public static void save_tasks(){
		JSONArray tasks = new JSONArray();
		final int[] count = {0}; //Counter outside of lambda needs to be final because ???

		Data.ALL_TASKS.forEach((i, t) -> {
			JSONObject o = new JSONObject();
			o.put("id", i);
			o.put("name", t.get_name());
			o.put("due", t.due_string());
			tasks.put(count[0], o);
			count[0]++;
		});

		try { saveFile(FILE_NAME, tasks.toString()); }
		catch (IOException e){e.printStackTrace();}
	}

	/**
	 * Saves all the matrices to a file "DIR/FILE_NAME"
	 * =====
	 * JSON conversion functionality tested and working, saving functionality not tested
	 */
	public static void save_matrices(){
		JSONArray matrices = new JSONArray();
		final int[] count = {0}; //Counter outside of lambda needs to be final because ???

		Data.ALL_MATRICES.forEach((i, um) -> {
			JSONObject o = new JSONObject();
			if (um.get_name().length() == 0) o.put("name", "");
			else o.put("name", um.get_name());
			o.put("relative_urgencies", relative_urgencies_to_JSON(um.get_U()));
			matrices.put(count[0], o);
			count[0]++;
		});

		try { saveFile(FILE_NAME, matrices.toString()); }
		catch (IOException e){e.printStackTrace();}
	}

	/**
	 * Converts the relative urgency matrix from double[][] to nested JSONArrays
	 * @param u the double[][] relative urgency matrix
	 * @return the relative urgency matrix in nested JSONArray form
	 */
	private static JSONArray relative_urgencies_to_JSON(double[][] u){
		JSONArray ru = new JSONArray();

		for (int i = 0; i < u.length; i++){
			JSONArray sub = new JSONArray();
			for (int j = 0; j < u.length; j++){
				sub.put(j, u[i][j]);
			}
			ru.put(i, sub);
		}

		return ru;
	}

	/**
	 * Attempts to save a file in the default directory. If the file does not exist, then that file will be created. This
	 * was written with the java.io package, as java.nio is not compatible with Android.
	 * @param fileName Name of the file to save
	 * @param content The content of the file in text format.
	 */
	public static void saveFile(String fileName, String content) throws IOException {
		File file = new File(DIR+File.separator+fileName);

		if (!file.exists()) file.createNewFile();

		PrintWriter writer =  new PrintWriter(file, "UTF-8");
		writer.print(content);
		writer.close();
	}

}
