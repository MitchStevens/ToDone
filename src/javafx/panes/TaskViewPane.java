package javafx.panes;

import java.util.ArrayList;
import java.util.List;

import io.Data;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.Task;

public class TaskViewPane extends HBox {
	private final static double TASK_WIDTH_PER = 0.65;
	private final static double NAV_WIDTH_PER  = 0.10;
	private final static double GAP			   = (1.0 - TASK_WIDTH_PER - NAV_WIDTH_PER)/3.0;
	
	//task row constants
	private final static double RANK_WIDTH_PER = 0.08;
	private final static double DATE_WIDTH_PER = 0.15;
	private final static double NAME_WIDTH_PER = 1.0 - RANK_WIDTH_PER - DATE_WIDTH_PER;
	
	private static List<Integer> task_ids = new ArrayList<>();
	private static GridPane task_housing = new GridPane();
	private static VBox sidebar = new VBox();
	
	public TaskViewPane(){
		this.getChildren().addAll(task_housing, sidebar);
		this.setPadding(new Insets(GUI.WIDTH * GAP));
		this.getStylesheets().add("res/css/task_view_pane.css");
		this.setId("main");
		init();
	}
	
	private void init(){
		task_housing.setId("task_housing");
		task_housing.setPrefWidth(GUI.WIDTH*TASK_WIDTH_PER);
		ColumnConstraints col1 = new ColumnConstraints();
		ColumnConstraints col2 = new ColumnConstraints();
		ColumnConstraints col3 = new ColumnConstraints();
		col1.setPercentWidth(RANK_WIDTH_PER*100);
		col1.setHalignment(HPos.CENTER);
		col2.setPercentWidth(NAME_WIDTH_PER*100);
		col2.setHalignment(HPos.LEFT);
		col2.setHgrow(Priority.ALWAYS);
		col3.setPercentWidth(DATE_WIDTH_PER*100);
		col3.setHalignment(HPos.CENTER);
		task_housing.getColumnConstraints().addAll(col1, col2, col3);
		
		sidebar.setId("sidebar");
		//use FontAwesome to display these characters correctly.
		Pane add = new Pane(new Button('\uf067'+""));
		add.setId("icon_button");
		
		add.setPrefSize(GUI.WIDTH*NAV_WIDTH_PER, GUI.WIDTH*NAV_WIDTH_PER);
		
		sidebar.getChildren().add(add);
	
	}
	
	public static boolean add(int task_id){
		if(!Data.ALL_TASKS.keySet().contains(task_id) || task_ids.contains(task_id))
			return false;
		
		int row = task_ids.size();
		Task t = Data.ALL_TASKS.get(task_id);
		
		Text rank = new Text(Integer.toString(row +1));
		Text name = new Text(t.get_name());
		Text date = new Text(t.due_string());
		rank.setId("rank_text");
		name.setId("name_text");
		date.setId("date_text");
		task_housing.add(rank, 0, row);
		task_housing.add(name, 1, row);
		task_housing.add(date, 2, row);
		task_ids.add(task_id);
		
		return true;
	}
	
	public static void clear(){
		
	}
}














