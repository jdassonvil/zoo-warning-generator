package zoo.generator.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import zoo.generator.enums.EventType;
import zoo.generator.model.EventTrame;
import zoo.generator.service.TrameService;

public class NewEvtTrame {

	private Stage stage;

	@FXML
	private ChoiceBox<EventType> choices;

	@FXML
	private TextField cageId;

	private TrameService trameService;

	public NewEvtTrame() {
		
		this.trameService = TrameService.getInstance();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/TrameEvt.fxml"));
		fxmlLoader.setController(this);

		try {
			BorderPane borderPane = fxmlLoader.load();
			stage = new Stage();
			stage.setScene(new Scene(borderPane));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Nouvelle trame");
			choices.getItems().addAll(EventType.values());			
			stage.showAndWait();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	@FXML
	public void handleCancel(MouseEvent event) {
		stage.close();
	}

	@FXML
	public void handleSubmit(MouseEvent event) {
		
		int cageIdValue = Integer.parseInt(cageId.getText());
		
		// TODO Implement controls
		EventType evt = choices.getValue();
		
		trameService.notifyNewTrame(new EventTrame((short)cageIdValue, evt));
		stage.close();
	}

}
