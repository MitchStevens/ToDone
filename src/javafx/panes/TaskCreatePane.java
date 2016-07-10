package javafx.panes;

import io.Data;














import java.io.IOException;






import java.time.LocalDate;



import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;
import logic.Task;


public class TaskCreatePane extends Pane {
	private static String OK = '\uf14a'+"", NOK = '\uf0c8'+"";
	private static String date_pattern = "dd/MM/yyyy";
	
	private static StringConverter<LocalDate> converter = new StringConverter<LocalDate>(){
	     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(date_pattern);
	     
	     public String toString(LocalDate date) {
	         if (date != null) {
	             return dateFormatter.format(date);
	         } else {
	             return "";
	         }
	     }

	     @Override public LocalDate fromString(String string) {
	         if (string != null && !string.isEmpty()) {
	             return LocalDate.parse(string, dateFormatter);
	         } else {
	             return null;
	         }
	     }

	};
	
	Parent root = null;
	
	private BooleanProperty valid_name = new SimpleBooleanProperty(false);
	private BooleanProperty valid_due  = new SimpleBooleanProperty(false);
	
	public TaskCreatePane(){
		try {
			root = FXMLLoader.load(getClass().getResource("../../res/fxml/task_creation.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getChildren().add(root);
		
		init();
		set_events();
	}
	
	private void init(){
		DatePicker dp = (DatePicker)root.lookup("#due_datepicker");
		dp.setPromptText(date_pattern.toLowerCase());
		dp.setConverter(converter);
		
		((Text)root.lookup("#confirm_name")).setText(NOK);
		((Text)root.lookup("#confirm_due")).setText(NOK);
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
		
		DatePicker dp = (DatePicker)root.lookup("#due_datepicker");
		dp.setOnAction(e -> {
			if(dp.getValue() == null)
				valid_due.set(false);
			else
				valid_due.set(LocalDate.now().isBefore(dp.getValue()));
		});
		
		valid_name.addListener(e -> {
			boolean b = valid_name.get();
			root.lookup("#confirm_name").pseudoClassStateChanged(PseudoClass.getPseudoClass("pos"), b);
			((Text)root.lookup("#confirm_name")).setText(b ? OK : NOK);
		});
		
		valid_due.addListener(e -> {
			boolean b = valid_due.get();
			root.lookup("#confirm_due").pseudoClassStateChanged(PseudoClass.getPseudoClass("pos"), b);
			((Text)root.lookup("#confirm_due")).setText(b ? OK : NOK);
		});
	}
	
}
