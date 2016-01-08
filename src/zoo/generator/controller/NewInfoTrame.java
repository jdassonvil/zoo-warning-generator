package zoo.generator.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import zoo.generator.model.InfoTrame;
import zoo.generator.service.TrameService;

public class NewInfoTrame {

	private Stage stage;

	@FXML
	private TextField cageId;

	@FXML
	private TextField humidity;

	@FXML
	private TextField sound;

	@FXML
	private TextField temperature;
	
	private TrameService trameService;

	public NewInfoTrame() {
		
		this.trameService = TrameService.getInstance();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/TrameInfo.fxml"));
		fxmlLoader.setController(this);

		try {
			BorderPane borderPane = fxmlLoader.load();
			stage = new Stage();
			stage.setScene(new Scene(borderPane));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Nouvelle trame");
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
		
		int humidityValue = Integer.parseInt(humidity.getText());
		int soundValue = Integer.parseInt(sound.getText());
		int temperatureValue = Integer.parseInt(temperature.getText());
		int cageIdValue = Integer.parseInt(cageId.getText());
		
		// TODO Implement controls
		
		trameService.notifyNewTrame(new InfoTrame(cageIdValue, temperatureValue, humidityValue, soundValue));
		stage.close();
	}

}
