package javafx.controls;

import datatypes.Direction;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SliderPiece extends Pane{
	public double h, w;
	
	final public  static double PIECE_HEIGHT = 10;
	final public  static double PIECE_WIDTH = LogSlider.SLIDER_WIDTH;
	final public  static double GAP = 10;
	
	public  Rectangle piece;
	
	private Pane	  label;
	private Polygon	  label_background;
	private Text      label_text;
	
	private double    value;
	private Direction label_dir;
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
		label_text = new Text(LogSlider.df.format(LogSlider.MAX_VALUE));
		label_text.setId("label_text");
		label.getChildren().add(label_text);
		label_text.applyCss();
		w = label_text.getLayoutBounds().getWidth() + 7.0;
		h = label_text.getLayoutBounds().getHeight();
		
		label_text.setLayoutX(5);
		switch(label_dir){
		case LEFT:  label_text.setLayoutX(2); break;
		case RIGHT: label_text.setLayoutX(5); break;
		default:break;
		}
		label_text.setLayoutY(h*0.75);
		
		switch(label_dir){
		case LEFT:  this.label.setLayoutX(-w-GAP); break;
		case RIGHT: this.label.setLayoutX(PIECE_WIDTH + GAP); break;
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
		this.label_text.setText(LogSlider.df.format(value));
	}
}













