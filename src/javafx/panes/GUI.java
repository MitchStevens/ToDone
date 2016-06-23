package javafx.panes;

import io.Reader;
import datatypes.Direction;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.controls.Slider;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.Task;

public class GUI extends Application{
	private enum Device {PC, ANDROID};
	public Device device = Device.PC;
	
	public static double screen_width, screen_height;
	
	Group root;
	Scene scene;
	
	{
		switch(device){
		case PC:
			screen_width = 1024;
			screen_height = 768;
		case ANDROID:
			//somthing else
		}
	}
	
	@Override
	public void start(Stage ps) throws Exception {		
		Reader.read_all();
		ps.setTitle("ToDone");
		root = new Group();
		scene = new Scene(root, screen_width, screen_height);
		ComparisonPane p = new ComparisonPane();
		root.getChildren().add(p);
		
		Task t1 = new Task(0, "Lift Heavy Shit");
		Task t2 = new Task(1, "Cardio");
		p.new_comparison(t1, t2);
		
		ps.setScene(scene);
		ps.show();
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
