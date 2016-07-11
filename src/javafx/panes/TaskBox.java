package javafx.panes;

import io.Data;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.Task;

public class TaskBox extends Pane{
	public static final double HEIGHT = 40;
	
	private Pane root;
	private EditTask edit_root = new EditTask();
	private Text rank, name, date;
	private Text edit, up, down, delete;
	
	private boolean is_editable = false;
	int id, rank_num;
	
	private static Transition edit_down, edit_up;
	
	public TaskBox(int rank_num, int id){
		try {
			root = FXMLLoader.load(getClass().getResource("../../res/fxml/task_box.fxml"));
		} catch (IOException e) {
			throw new Error("Couldn't find the fxml file");
		}
		this.getChildren().addAll(root, edit_root);
		
		//edit_root.setVisible(false);
		
		this.id = id;
		this.rank_num = rank_num;
		
		init();
		set_events();
	}
	
	private void init(){
		
		// MAIN PANE
		rank 	= (Text)root.lookup("#rank");
		name 	= (Text)root.lookup("#name");
		date 	= (Text)root.lookup("#date");
		edit 	= (Text)root.lookup("#edit_button");
		up 		= (Text)root.lookup("#up_button");
		down 	= (Text)root.lookup("#down_button");
		delete 	= (Text)root.lookup("#delete_button");
		
		Task t = Data.ALL_TASKS.get(id);
		rank.setText(Integer.toString(rank_num));
		name.setText(t.get_name());
		date.setText(t.due_string());
		edit.setText('\uf040' +"");
		up.setText('\uf0aa' +"");
		down.setText('\uf0ab' +"");
		delete.setText('\uf00d' +"");
		
		//edit_root.setMaxHeight(HEIGHT);
		
		// EDIT PANE TRANSITION
		TranslateTransition slide_down = new TranslateTransition(Duration.millis(500), edit_root);
		slide_down.setFromY(TaskBox.HEIGHT - EditTask.HEIGHT);
		slide_down.setToY(TaskBox.HEIGHT);
		
		FadeTransition fade_down = new FadeTransition(Duration.ZERO, edit_root);
		fade_down.setFromValue(0.0);
		fade_down.setToValue(1.0);
		fade_down.setDelay(Duration.millis(500));
		
		edit_down = new ParallelTransition(slide_down, fade_down);
		
		TranslateTransition slide_up = new TranslateTransition(Duration.millis(500), edit_root);
		slide_up.setFromY(TaskBox.HEIGHT);
		slide_up.setToY(TaskBox.HEIGHT - EditTask.HEIGHT);
		
		FadeTransition fade_up = new FadeTransition(Duration.ZERO, edit_root);
		fade_up.setFromValue(1.0);
		fade_up.setToValue(0.0);
		fade_up.setDelay(Duration.millis(500));
		
		edit_up = new ParallelTransition(slide_up, fade_up);
	}
	
	private void set_events(){
		up.setOnMouseClicked(e -> {
			TaskViewPane.swap(rank_num-1, rank_num-2);
		});
		
		down.setOnMouseClicked(e -> {
			TaskViewPane.swap(rank_num-1, rank_num);
		});
		
		delete.setOnMouseClicked(e -> {
			TaskViewPane.remove(rank_num);
		});
		
		edit.setOnMouseClicked(e -> {
			if(is_editable)
				edit_up.play();
			else
				edit_down.play();
			
			//my very favorite hack
			is_editable ^= true;
		});
	}
	
	public void set_rank(int rank){
		this.rank_num = rank;
		this.rank.setText(rank+"");
	}
	
	public void set_color(Color c){
		root.setBackground(new Background(new BackgroundFill(c, null, null)));
	}
	
	public void open_edit_pane(){
		
	}
	
	private void close_edit_pane(){
		
	}
	
}







