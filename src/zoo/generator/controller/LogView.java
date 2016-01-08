package zoo.generator.controller;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import zoo.generator.service.LogService;

public class LogView extends VBox implements Observer{

	@FXML
	private TextFlow textWindow;
	
	@FXML
	private ScrollPane scroll;

	public LogView() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/LogView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		LogService.getInstance().addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Text){
			textWindow.getChildren().add((Text)arg);
			scroll.setVvalue(1.0); 
		}		
	}
}
