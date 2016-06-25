package javafx.panes;

import java.util.ArrayList;
import java.util.List;

import io.Data;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import logic.Task;

public class TaskViewPane extends BorderPane {
	private final static double TASK_WIDTH_PER = 0.65;
	private final static double NAV_WIDTH_PER  = 0.10;
	private final static double GAP			   = (1.0 - TASK_WIDTH_PER - NAV_WIDTH_PER)/3.0;
	
	static List<Integer> task_ids = new ArrayList<>();
	static GridPane task_housing = new GridPane();
	
	public TaskViewPane(){
		this.setCenter(task_housing);
	}
	
	private void init(){
		this.getStylesheets().add("res/css/task_view_pane.css");
		task_housing.setId("task_housing");
		task_housing.setPrefWidth(GUI.WIDTH*TASK_WIDTH_PER);
	}
	
	public static boolean add(int task_id){
		if(!Data.ALL_TASKS.keySet().contains(task_id))
			return false;
		
		if(task_ids.contains(task_id))
			return false;
		
		Task t = Data.ALL_TASKS.get(task_id);
		Text rank = new Text(Integer.toString(task_ids.size()+1));
		rank.setId("rank_label");
		
		Text name = new Text(t.get_name());
		name.setId("name_label");
		
		Text date = new Text(t.due_string());
		date.setId("date_label");
		
		int row = task_ids.size();
		task_housing.add(rank, 0, row);
		task_housing.setAlignment(Pos.CENTER);
		
		task_housing.add(name, 1, row);	
		task_housing.setAlignment(Pos.CENTER_LEFT);
		
		task_housing.add(date, 2, row);
		task_housing.setAlignment(Pos.CENTER);
		
		task_ids.add(task_id);
		
		return true;
	}
	
	public static void clear(){
		
	}
}