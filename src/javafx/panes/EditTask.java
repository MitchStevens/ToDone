package javafx.panes;

import java.io.IOException;
import java.time.LocalDate;

import io.Data;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import logic.Task;

public class EditTask extends Pane {
	private final static String OK = '\uf14a'+"", NOK = '\uf0c8'+"";
	public  final static double HEIGHT = 155;
	
	private Pane root;
	private Text confirm_name, confirm_date, confirm_time;
	
	private BooleanProperty valid_name = new SimpleBooleanProperty(false);
	private BooleanProperty valid_date = new SimpleBooleanProperty(false);
	private BooleanProperty valid_time = new SimpleBooleanProperty(false);
	
	public EditTask(){
		
		try {
			root = FXMLLoader.load(getClass().getResource("../../res/fxml/edit_task.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("Couldn't load the fxml file");
		}
		this.getChildren().add(root);
		
		init();
		set_events();
		
		valid_time.set(true);
	}
	
	private void init(){
		confirm_name = (Text)root.lookup("#confirm_name");
		confirm_date = (Text)root.lookup("#confirm_date");
		confirm_time = (Text)root.lookup("#confirm_time");
		
		confirm_name.setText(NOK);
		confirm_date.setText(NOK);
		confirm_time.setText(NOK);
	}
	
	private void set_events(){
		root.lookup("#name_textfield").setOnKeyReleased(e -> {
			String name = ((TextField)root.lookup("#name_textfield")).getText();
			
			for(Task t : Data.ALL_TASKS.values())
				if(t.get_name().equals(name)){
					valid_name.set(false);
					return;
				}
			
			valid_name.set(!"".equals(name));
		});
		
		DatePicker dp = (DatePicker)root.lookup("#date_datepicker");
		dp.setOnAction(e -> {
			if(dp.getValue() == null)
				valid_date.set(false);
			else
				valid_date.set(LocalDate.now().isBefore(dp.getValue()));
		});
		
		root.lookup("#time_textfield").setOnKeyReleased(e -> {
			String time = ((TextField)root.lookup("#time_textfield")).getText();
			
			boolean b = "".equals(time) || time.toLowerCase().matches("((2[0-3])|([0-1]*\\d)):[0-5]\\d( ?(a|p)m)?");
			valid_time.set(b);
		});
		
		valid_name.addListener(e -> {
			boolean b = valid_name.get();
			confirm_name.pseudoClassStateChanged(PseudoClass.getPseudoClass("pos"), b);
			confirm_name.setText(b ? OK : NOK);
		});
		
		valid_date.addListener(e -> {
			boolean b = valid_date.get();
			confirm_date.pseudoClassStateChanged(PseudoClass.getPseudoClass("pos"), b);
			confirm_date.setText(b ? OK : NOK);
		});
		
		valid_time.addListener(e -> {
			boolean b = valid_time.get();
			confirm_time.pseudoClassStateChanged(PseudoClass.getPseudoClass("pos"), b);
			confirm_time.setText(b ? OK : NOK);
		});
	}
	
//	@FXML
//	private void save(){
//		System.out.println("saved");
//	}
//	
//	@FXML
//	private void cancel(){
//		
//	}
	
}
