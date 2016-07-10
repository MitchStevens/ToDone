package javafx.panes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.Data;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.Task;

public class TaskViewPane extends Pane {
	
	Parent root = null;
	
	private static List<Integer> task_ids = new ArrayList<>();
	private static List<TaskBox> task_boxes = new ArrayList<>();
	private static VBox task_housing;
	
	public TaskViewPane(){
		try {
			root = FXMLLoader.load(getClass().getResource("../../res/fxml/task_viewing.fxml"));
		} catch (IOException e) {
			System.out.println("error");
			throw new Error("Couldn't find the fxml file");
		}
		this.getChildren().add(root);
		
		init();
		//set_events();
	}
	
	private void init(){
		task_housing = new VBox();
		((ScrollPane)root.lookup("#scroll_pane")).setContent(task_housing);
	}	
	
	public static boolean add(int id){
		if(!Data.ALL_TASKS.keySet().contains(id) || task_ids.contains(id))
			return false;
		
		task_ids.add(id);
		
		TaskBox tb = new TaskBox(task_ids.size(), id);
		task_boxes.add(tb);
		
		task_housing.getChildren().add(tb);
		repaint_taskboxes();
		
		return true;
	}
	
	public static void remove(int rank){
		for(int i = rank; i < task_ids.size(); i++)
			task_boxes.get(i).set_rank(i);
		
		task_housing.getChildren().remove(task_boxes.get(rank-1));
		task_boxes.remove(rank-1);
		task_ids.remove(rank-1);
		
		repaint_taskboxes();
	}
	
	public static void swap(int i, int j){
		if(i >= 0 && i < task_ids.size() &&
		   j >= 0 && j < task_ids.size() && i != j){
			ObservableList<Node> workingCollection = FXCollections.observableArrayList(task_housing.getChildren());
			Collections.swap(workingCollection, i, j);
			task_housing.getChildren().setAll(workingCollection);
			
			task_boxes.get(i).set_rank(j+1);
			task_boxes.get(j).set_rank(i+1);
			Collections.swap(task_boxes, i, j);
			
			repaint_taskboxes();
		}
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













