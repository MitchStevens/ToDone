package javafx.panes;

import java.util.ArrayList;
import java.util.List;

import io.Data;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.Task;

public class TaskViewPane extends FlowPane {
	private final static double TASK_WIDTH_PER = 0.65;
	private final static double NAV_WIDTH_PER  = 0.10;
	private final static double GAP			   = (1.0 - TASK_WIDTH_PER - NAV_WIDTH_PER)/3.0;
	
	private static HBox root = new HBox(GAP*GUI.WIDTH);
	private static List<Integer> task_ids = new ArrayList<>();
	private static VBox task_housing = new VBox(20);
	private static VBox sidebar = new VBox(20);
	
	public TaskViewPane(){
		this.getStylesheets().add("res/css/task_view_pane.css");
		this.setId("main");
		this.setPrefSize(GUI.WIDTH, GUI.HEIGHT);
		this.getChildren().add(root);
		this.setAlignment(Pos.TOP_CENTER);
		this.setPadding(new Insets(50));
		
		root.getChildren().addAll(task_housing, sidebar);
		root.setAlignment(Pos.CENTER);
		root.setId("root");
		
		init();
	}
	
	private void init(){
		task_housing.setId("task_housing");
		task_housing.setPrefWidth(GUI.WIDTH*TASK_WIDTH_PER);
		
		sidebar.setId("sidebar");
		//use FontAwesome to display these characters correctly.
		double size = GUI.WIDTH*NAV_WIDTH_PER;
		Button add = new Button("+");
		Button remove = new Button("-");
		Button edit = new Button("e");
		
		add.setPrefSize(size, size);
		remove.setPrefSize(size, size);
		edit.setPrefSize(size, size);
		
		sidebar.getChildren().addAll(add, remove, edit);
	
	}
	
	public static boolean add(int id){
		if(!Data.ALL_TASKS.keySet().contains(id) || task_ids.contains(id))
			return false;
		
		task_ids.add(id);
		task_housing.getChildren().add(new TaskBox(task_ids.size(), id));
		
		repaint_taskboxes();
		
		return true;
	}
	
	public static void clear(){
		
	}
	
	public static void repaint_taskboxes(){		
		double count = 0;
		double size = task_housing.getChildren().size();
		for(Node n : task_housing.getChildren()){
			if(n instanceof TaskBox){
				TaskBox tb = (TaskBox)n;
				
				tb.set_color(multi_interpolant(count/size, GUI.RED, GUI.YELLOW, GUI.GREEN));
			}
			count++;
		}
	}
	
	private static Color multi_interpolant(double val, Color... colors){
		int n = colors.length - 1;
		int i = (int)Math.floor(val*n);
		
		if(i >= n)
			return colors[n];
		else if(i < 0)
			return colors[0];
		else{
			return colors[i].interpolate(colors[i+1], n*val - i);
		}
	}
}

class TaskBox extends GridPane{
	
	//task row constants
	private final static double RANK_WIDTH_PER = 0.08;
	private final static double DATE_WIDTH_PER = 0.15;
	
	public final BooleanProperty is_highlighted = new SimpleBooleanProperty();
	Text rank, name, date;
	
	public TaskBox(int rank_num, int id){
		if(!Data.ALL_TASKS.containsKey(id))
			throw new Error("Couldn't find task with id "+ id +".");
		
		this.getStyleClass().add("task_box");
		Task t = Data.ALL_TASKS.get(id);
		
		rank = new Text(rank_num +"");
		name = new Text(t.get_name());
		date = new Text(t.due_string());
		
		this.add(rank, 0, 0);
		this.add(name, 1, 0);
		this.add(date, 2, 0);
		
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(RANK_WIDTH_PER * 100);
		col1.setHalignment(HPos.CENTER);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setHgrow(Priority.ALWAYS);
		ColumnConstraints col3 = new ColumnConstraints();
		col3.setPercentWidth(DATE_WIDTH_PER * 100);
		col3.setHalignment(HPos.CENTER);
		
		this.getColumnConstraints().addAll(col1, col2, col3);
		
		set_events();
	}
	
	private void set_events(){
		this.setOnMouseClicked(e -> {
			is_highlighted.set(!is_highlighted.get());
		});
		
		is_highlighted.addListener(e -> {
				this.pseudoClassStateChanged(PseudoClass.getPseudoClass("highlighted"), is_highlighted.get());
		});
	}
	
	public void set_color(Color c){
		this.setBackground(new Background(new BackgroundFill(c, null, null)));
		
	}
	
}













