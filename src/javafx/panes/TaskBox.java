package javafx.panes;

import io.Data;

import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.Task;

public class TaskBox extends Pane{
	Pane root;
	Text rank, name, date;
	Text edit, up, down, delete;
	
	int id, rank_num;
	
	public TaskBox(int rank_num, int id){
		try {
			root = FXMLLoader.load(getClass().getResource("../../res/fxml/task_box.fxml"));
		} catch (IOException e) {
			throw new Error("Couldn't find the fxml file");
		}
		this.getChildren().add(root);
		
		this.id = id;
		this.rank_num = rank_num;
		
		init();
		set_events();
	}
	
	private void init(){
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
	
}