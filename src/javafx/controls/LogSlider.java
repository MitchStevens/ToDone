package javafx.controls;

import static java.lang.Math.*;

import java.text.DecimalFormat;

import datatypes.Direction;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Creates a logarithmic slider to get input from the user in the range
 * {@code [1/max ..  max]} where {@code max} is specified as a constant.
 * The slider contains a subtitle that can be changed after
 * instantiation.
 * 
 * @author Mitch
 * */
public class LogSlider extends Pane{
	private double height;
	
	final public  static double SLIDER_WIDTH = 20;
	
	final private static double LABEL_GAP = 20;
	final private static double INITIAL_VALUE = 1.0;
	final private static double PERCENTAGE = 2.0/3.0;
	final public  static double MAX_VALUE = 8;
	
	final private static Color MAX_COLOR = Color.web("#d96459");
	final private static Color ONE_COLOR = Color.web("#f2e394");
	final private static Color MIN_COLOR = Color.web("#588c7e");
	
	private double x_off, y_off;
	
	final public  static DecimalFormat df = new DecimalFormat("#0.00");
	
	/**  */
	final public DoubleProperty curr_value = new SimpleDoubleProperty();
	final public BooleanProperty is_changing = new SimpleBooleanProperty();
	
	double mousey = 0.0;
	boolean dragging = false;
	
	private Pane parent;
	private Rectangle 	upper_piece, lower_piece;
	private SliderPiece	slider_group;
	private Direction direction;
	private Text[] labels;
	private Text subtitle;

	public LogSlider(Direction d, double height){		
		this.direction = d;
		this.height = height;
		this.setId("slider");
		this.getStylesheets().add("res/css/slider.css");
		
		parent = new Pane();
		this.getChildren().add(parent);
		
		slider_group = new SliderPiece(INITIAL_VALUE, d);
		slider_group.setLayoutX(0.0);
		slider_group.setTranslateY(f_inv(INITIAL_VALUE));
		parent.getChildren().add(slider_group);
		slider_group.toFront();
		
		slider_group.setTranslateX(0);
		slider_group.setTranslateY(f_inv(INITIAL_VALUE));
		
		upper_piece = new Rectangle(0.0, f_inv(MAX_VALUE), SLIDER_WIDTH, height*PERCENTAGE);
		upper_piece.setId("upper_piece");
		parent.getChildren().add(upper_piece);
		
		lower_piece = new Rectangle(0.0, f_inv(1), SLIDER_WIDTH, height*(1- PERCENTAGE));
		lower_piece.setId("lower_piece");
		parent.getChildren().add(lower_piece);
		
		subtitle = new Text("untitled");
		subtitle.setId("subtitle");
		parent.getChildren().add(subtitle);
		subtitle.applyCss();
		double text_width = subtitle.getBoundsInParent().getWidth();
		double text_height = subtitle.getBoundsInParent().getHeight();
		subtitle.setTranslateX(-(text_width - SLIDER_WIDTH)*0.5);
		subtitle.setTranslateY(height + text_height);
		
		display_labels();
		set_value(1.0);
		curr_value.set(1.0);
		is_changing.set(false);
		set_events();
	}
	
	/**
	 * Creates ActionListeners for events.
	 * */
	private void set_events(){
		slider_group.piece.setOnMousePressed(e -> {
			mousey = e.getSceneY();
			toFront();
			e.consume();
		});

		slider_group.piece.setOnMouseDragged(e -> {
			dragging = true;
			double ypos = slider_group.getTranslateY() + e.getSceneY() - mousey;
			if(f(ypos) > MAX_VALUE){
				slider_group.setTranslateY(f_inv(MAX_VALUE));
				this.set_value(MAX_VALUE);
			} else if(f(ypos) <= 1) {
				slider_group.setTranslateY(f_inv(1));
				this.set_value(1.0);
			} else {
				slider_group.setTranslateY(ypos);
				this.set_value(f(ypos));
			}
			
			mousey = e.getSceneY();
			e.consume();
		});

		slider_group.piece.setOnMouseReleased(e -> {
			if (dragging)
				dragging = false;
			e.consume();
		});
		
		curr_value.addListener(e -> {
			slider_group.set_value(curr_value.get());
			repaint_piece();
		});
		
		upper_piece.setOnMouseClicked(e -> { 
			if(slider_group.contains(e.getX(), e.getY()))
				return;
			this.set_value(f(e.getY()));
		});
	}
	
	/**
	 * Repaints the color of the slider piece and the label piece.
	 * */
	private void repaint_piece(){
		double fx = curr_value.get();
		Color c = null;
		if(fx > 1)
			c = MAX_COLOR.interpolate(ONE_COLOR, (fx - MAX_VALUE)/(1 - MAX_VALUE));
		else
			c = MIN_COLOR.interpolate(ONE_COLOR, (MAX_VALUE*fx-1)/(MAX_VALUE -1));
		slider_group.set_color(c);
	}
	
	/**
	 * Gets subtitle text.
	 * @return subtitle text.
	 * */
	public String get_subtitle(){
		return subtitle.getText();
	}
	
	/**
	 * Sets subtitle text. In doing this the value of {@code x_off}
	 * 
	 * */
	public void set_subtitle(String s){
		subtitle.setText(s);
		
		double text_width = subtitle.getBoundsInParent().getWidth();
		double text_height = subtitle.getBoundsInParent().getHeight();
		subtitle.setTranslateX(-(text_width - SLIDER_WIDTH)*0.5);
		subtitle.setTranslateY(height + text_height);
		
		calc_offset();
	}
	
	/**
	 * Get value that this slider represents.
	 * @return The current value
	 * */
	public double get_value(){
		return curr_value.get();
	}
	
	/**
	 * Set the value this slider displays. Animate the transition
	 * between the previous value and the new {@code value}.
	 * @param value The value in the range [1/max .. max] that this
	 * slider is displaying.
	 * */
	public void set_value(double value){
		if(value == curr_value.get())
			return;
		
		is_changing.set(true);
		
		double dist = min(abs(f_inv(value) - f_inv(curr_value.get())), 200);
		TranslateTransition tt = new TranslateTransition(Duration.millis(2*(int)dist), slider_group);
		tt.setInterpolator(Interpolator.EASE_OUT);
		tt.setToY(f_inv(value));
		tt.setOnFinished(e -> {
			is_changing.set(false);
		});
		tt.play();
		
		curr_value.set(value);
		this.slider_group.set_value(value);
		repaint_piece();
	}
	
	/**
	 * Display the dashes and the text labels on the side of the slider.
	 * There is no current way to disable the labels.
	 * */
	public void display_labels(){
		double x = 0, y = 0;
		Bounds b;
		
		double[] label_vals = new double[]{MAX_VALUE, 1.0, 1/MAX_VALUE};
		double[] label_widths = new double[3];
		double[] dash_vals = new double[2* (int)MAX_VALUE];
		for(int i = 0; i < MAX_VALUE; i++){
			dash_vals[2*i]   = (double)(i+1);
			dash_vals[2*i+1] = 1/((double)i+1);
		}
			
		labels = new Text[label_vals.length];
		Line[] dashes = new Line[(int) (2*MAX_VALUE)];
		
		//create dashes
		for(int i = 0; i < 2*MAX_VALUE; i++){
			dashes[i] = new Line(0, 0, 5.0, 0);
			if(direction == Direction.LEFT)
				x = -10.0;
			else if(direction == Direction.RIGHT)
				x = SLIDER_WIDTH + 5.0;
			y = f_inv(dash_vals[i]);
			dashes[i].setLayoutX(x);
			dashes[i].setTranslateY(y);
			parent.getChildren().add(dashes[i]);
		}
		
		//create labels
		for(int i = 0; i < label_vals.length; i ++){
			labels[i] = new Text(df.format(label_vals[i]));
			labels[i].setId("marker");
			parent.getChildren().add(labels[i]);
			labels[i].applyCss();
			
			b = labels[i].getLayoutBounds();
			if(direction == Direction.LEFT)
				x = -LABEL_GAP - b.getWidth();
			else if(direction == Direction.RIGHT)
				x = SLIDER_WIDTH + LABEL_GAP;
			y = f_inv(label_vals[i]) + b.getHeight()*0.2;
			labels[i].setLayoutX(x);
			labels[i].setTranslateY(y);
			label_widths[i] = labels[i].getBoundsInParent().getWidth();
		}
		
		calc_offset();
		slider_group.toFront();
	}
	
	/**
	 * Setting height and width of slider. These dimensions have to 
	 * be set at some point and to calculate the dimensions
	 * Accurately, we need the dimensions of the label.
	 * */
	public void calc_offset(){
		double max_width = 0, max_height = 0;
		for(Text t : labels){
			max_width  = max(max_width, t.getBoundsInParent().getWidth());
			max_height = max(max_height,t.getBoundsInParent().getHeight());
		}
		Bounds sub_bounds = subtitle.getBoundsInParent();
		this.setPrefHeight(height + max_height*0.5 + sub_bounds.getHeight());
		this.setPrefWidth(SLIDER_WIDTH + LABEL_GAP + max_width);
		
		this.x_off = max((sub_bounds.getWidth() - SLIDER_WIDTH)*0.5, max_width + LABEL_GAP);
		this.y_off = max_height*0.5;
		this.parent.setLayoutX(x_off);
		this.parent.setLayoutY(y_off);
		
		double pref_h = height + sub_bounds.getHeight() + max_height*0.5;
		double pref_w = SLIDER_WIDTH;
		pref_w += 2*max((sub_bounds.getWidth() - SLIDER_WIDTH)*0.5, max_width + LABEL_GAP);
		
		this.setPrefSize(pref_w, pref_h);
	}
	
	/**
	 * The function f maps positional data {@code x} to the 
	 * corresponding value.
	 * <p>f_inv : [0 .. HEIGHT] -> [1/max .. max]
	 * 
	 * @param pos	A double in the range [0 .. HEIGHT]. 					(pos)
	 * @return		The value that this position would represent.			(val)
	 * */
	public double f(double pos){
		if(pos<=PERCENTAGE*height)
			return Math.pow(MAX_VALUE, 1 - (pos)/(PERCENTAGE*height));
		else	
			return Math.pow(MAX_VALUE, 2 - (pos)/((1-PERCENTAGE)*height));
	}
	
	/**
	 * The function f_inv maps a value {@code value} to the corresponding 
	 * position on the y axis relative to the slider.
	 * <p>f : [1/max .. max] -> [0 .. HEIGHT]
	 * 
	 * @param value		A double in the range [1/max_value .. max_value].	(val)
	 * @return		The corresponding position.								(pos)
	 * */
	public double f_inv(double value){
		if(value>=1)
			return    PERCENTAGE *height*(1 - log(value)/log(MAX_VALUE));
		else
			return (1-PERCENTAGE)*height*(2 - log(value)/log(MAX_VALUE));
	}
}