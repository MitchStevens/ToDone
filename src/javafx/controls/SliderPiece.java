package javafx.controls;

import java.text.DecimalFormat;

import com.sun.javafx.geom.transform.Affine3D;
import com.sun.javafx.geom.transform.BaseTransform;

import datatypes.Direction;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolatable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class SliderPiece extends Pane{
	public double h, w;
	
	final public  static double PIECE_HEIGHT = 10;
	final public  static double PIECE_WIDTH = Slider.SLIDER_WIDTH;
//	final private static double LABEL_HEIGHT = 25;
//	final private static double LABEL_WIDTH	 = 40;
	final public  static double GAP = 10;
	
	public  Rectangle piece;
	
	private Pane	  label;
	private Polygon	  label_background;
	private Text      label_text;
	
	private double    value;
	private Direction label_dir;
	private boolean   label_visability = false;
	private Color	  color;
	
	public SliderPiece(double value, Direction d){
		this.value = value;
		this.label_dir = d;
		this.getStylesheets().add("res/css/slider.css");
		init();
	}
	
	private void init(){
		// init piece
		piece = new Rectangle(0, -PIECE_HEIGHT*0.5, PIECE_WIDTH, PIECE_HEIGHT);
		piece.setId("piece");
		this.getChildren().add(piece);
		
		// inint label stackpane
		label = new Pane();
		this.getChildren().add(label);
		
		// init label text
		label_text = new Text(Slider.df.format(Slider.MAX_VALUE));
		label_text.setId("label_text");
		label.getChildren().add(label_text);
		label_text.applyCss();
		w = label_text.getLayoutBounds().getWidth() + 7.0;
		h = label_text.getLayoutBounds().getHeight();
		
		label_text.setLayoutX(5);
		label_text.setLayoutY(h*0.75);
		
		switch(label_dir){
		case LEFT:  this.label.setLayoutX(-w-GAP); break;
		case RIGHT: this.label.setLayoutX( w+GAP); break;
		default:break;
		}
		
		// init background
		label_background = new Polygon();
		label_background.setId("label_background");
		switch(label_dir){
		case LEFT:
			label_background.getPoints().addAll(
				0.0, 	0.0,
				w-5, 	0.0,
				w,		h*0.5,
				w-5,	h,
				0.0,	h
			);
			break;
		case RIGHT:
			label_background.getPoints().addAll(
				5.0, 	0.0,
				w,		0.0,
				w,		h,
				5.0,	h,
				0.0,	h*0.5
			);
			break;
		default: break;
		}
		label.getChildren().add(label_background);
		label_text.toFront();
		
		label.setLayoutY(-h*0.5);
		label.setOpacity(1.0);
	}
	
	public Color get_color(){
		return color;
	}
	
	public void set_color(Color c){
		this.color = c;
		piece.setFill(color);
		label_background.setFill(color);
	}
	
	public double get_value(){
		return value;
	}
	
	public void set_value(double value){
		this.value = value;
		this.label_text.setText(Slider.df.format(value));
	}
}













