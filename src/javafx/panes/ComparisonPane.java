package javafx.panes;

import datatypes.Direction;
import javafx.controls.Slider;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.Task;

public class ComparisonPane extends BorderPane {
	private final static double DEF_HEIGHT = 400;
	private final static double DEF_WIDTH  = 400;
	
	private Slider slider_left, slider_right;
	private boolean ignore_left, ignore_right;
	private Text message;
	
	private Task task_left, task_right;
	
	public ComparisonPane(){
		this.getStylesheets().add("res/css/comparison_pane.css");
		this.setHeight(DEF_HEIGHT);
		this.setWidth(DEF_WIDTH);
		
		init();
		set_events();
	}
	
	private void init(){
		Text title = new Text("Compare Tasks");
		title.setId("title");
		this.setTop(title);
		
		VBox left = new VBox();
		left.setAlignment(Pos.CENTER);
		this.setLeft(left);
		
		slider_left = new Slider(Direction.RIGHT, 400);
		GridPane.setHalignment(slider_left, HPos.LEFT);
		left.getChildren().add(slider_left);	
		
		Button next = new Button("string");
		next.setAlignment(Pos.CENTER_LEFT);
		this.setCenter(next);
		
		VBox right = new VBox();
		right.setAlignment(Pos.CENTER);
		this.setRight(right);
		
		slider_right = new Slider(Direction.LEFT, 400);
		GridPane.setHalignment(slider_right, HPos.RIGHT);
		right.getChildren().add(slider_right);
		
		message = new Text("message");
		message.setId("message");
		message.setWrappingWidth(DEF_WIDTH);
		this.setBottom(message);
	}
	
	private void set_events(){
		slider_left.is_changing.addListener(e -> {
			ignore_right = slider_left.is_changing.get();
		});
		
		slider_right.is_changing.addListener(e -> {
			ignore_left = slider_right.is_changing.get();
		});
		
		slider_left.curr_value.addListener(e -> {
			if(!ignore_left){
				slider_right.set_value(1/slider_left.get_value());
				rewrite_message();
			}
		});
		
		slider_right.curr_value.addListener(e -> {
			if(!ignore_right){
				slider_left.set_value(1/slider_right.get_value());
				rewrite_message();
			}
		});
	}
	
	public void new_comparison(Task task1, Task task2){
		ignore_left = ignore_right = true;
		slider_left.set_value(1.0);
		slider_right.set_value(1.0);
		ignore_left = ignore_right = false;
		
		task_left = task1;
		task_right = task2;
		slider_left.set_subtitle(task_left.get_name());
		slider_right.set_subtitle(task_right.get_name());
		
		rewrite_message();
	}
	
	private void rewrite_message(){
		double val = slider_left.get_value();
		String str;
		if(val > 1)
			str = task_left.get_name() +" is "+ Slider.df.format(val) + 
					" times more important than "+ task_right.get_name();
		else if(val < 1)
			str = task_right.get_name() +" is "+ Slider.df.format(1/val) + 
				" times more important than "+ task_left.get_name();
		else
			str = task_left.get_name() +" is as important as "+ task_right.get_name();
		
		message.setText(str);
	}
	
	
	
}
