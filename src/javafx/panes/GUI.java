package javafx.panes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import io.Data;
import io.Reader;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.Task;
import logic.UrgencyMatrix;

public class GUI extends Application{
	public enum Device {
		PC			(768, 1024),
		IPAD2		(1024, 768),
		IPAD3		(2048, 1536),
		IPHONE3		(480, 320),
		IPHONE4		(960, 640),
		WINPHONE	(800, 480),
		GALAXYTAB7	(1024, 600),
		IPHONE5		(1136, 640);
		
		int res_h, res_w;
		Device(int res_h, int res_w){
			this.res_w = res_w;
			this.res_h = res_h;
		}
	};
	
	public static Color MAROON 	= Color.web("#8C4646");
	public static Color RED 	= Color.web("#D96459");
	public static Color ORANGE 	= Color.web("#F2AE72");
	public static Color YELLOW 	= Color.web("#F2E394");
	public static Color GREEN 	= Color.web("#588C7E");
	
	public static Device device = Device.PC;
	
	public final static double WIDTH, HEIGHT;
	
	Group root;
	Scene scene;
	
	static {
		WIDTH  = device.res_w;
		HEIGHT = device.res_h;
	}
	
	UrgencyMatrix um;
	
	@Override
	public void start(Stage ps) throws Exception {		
		ps.setTitle("ToDone");
		root = new Group();
		scene = new Scene(root, WIDTH, HEIGHT);
		ps.setScene(scene);
		ps.show();

		task_view_pane_test();
		show_splash_screen();
		

	}

	private void show_splash_screen(){
		SplashTitlePane p = new SplashTitlePane(WIDTH, HEIGHT);
		root.getChildren().add(p);
		p.run();
	}
	
	private void scroll_pane_text(){
		ScrollPane p = new ScrollPane();
		root.getChildren().add(p);
		
		TextArea area = new TextArea();
		p.setContent(area);
		
		int n = 100;
		String s = "0 precedes ";
		for(int i = 0; i < n; i++)
			s += i +" which precedes ";
		s += (n+1) +".";
		
		area.setText(s);
	}
	
	private void comp_pane_test(){
		ComparisonPane p = new ComparisonPane();
		root.getChildren().add(p);
		
		Task t1 = new Task("body disposal");
		Task t2 = new Task("clean blood");
		p.new_comparison(t1, t2);
	}
	
	private void task_box_test(){
		TaskBox p = new TaskBox(0, 0);
		root.getChildren().add(p);
	}
	
	private void task_view_pane_test(){
		TaskViewPane p = new TaskViewPane();
		root.getChildren().add(p);

		int n = Data.ALL_TASKS.size();
		for(int i = 0; i < n; i++)
			TaskViewPane.add(i);
	}
	
	private void task_creation_pane_test(){
		TaskCreatePane p = new TaskCreatePane();
		root.getChildren().add(p);
	}
	
	private void window_resize_listener() {
//		scene.widthProperty().addListener(new ChangeListener<Number>() {
//			@Override
//			public void changed(
//					ObservableValue<? extends Number> observableValue,
//					Number oldSceneWidth, Number newSceneWidth) {
//				boardWidth = (double) newSceneWidth;
//				CircuitPane.on_resize();
//				//LevelSelectPane.onResize();
//			}
//		});
//		scene.heightProperty().addListener(new ChangeListener<Number>() {
//			@Override
//			public void changed(
//					ObservableValue<? extends Number> observableValue,
//					Number oldSceneHeight, Number newSceneHeight) {
//				boardHeight = (double) newSceneHeight;
//				CircuitPane.on_resize();
//				//LevelSelectPane.onResize();
//			}
//		});
	}

	public static void open() {
		Application.launch();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
