package zoo.generator.controller;

import java.util.Observable;
import java.util.Observer;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import zoo.generator.service.RandomGenerationService;

public class MainPanelController implements Observer {

	@FXML private ToggleButton autoBtn;
	
	@FXML private TextField numeroMin;
	
	@FXML private TextField numeroMax;
	
	@FXML private TextField tempMin;
	
	@FXML private TextField tempMax;
	
	@FXML private TextField humidityMin;
	
	@FXML private TextField humidityMax;
	
	@FXML private TextField soundMin;
	
	@FXML private TextField soundMax;

	private RandomGenerationService randomGenerationService;
	
	// Event Listener on ToggleButton.onMouseClicked
	@FXML
	public void handleClick(MouseEvent event) {
		
		if(autoBtn.getText().equals("OFF")){
			autoBtn.setText("ON");
			autoBtn.setStyle("-fx-background-color: #90EE90; -fx-background-radius: 10.0;");
			
			numeroMin.setDisable(true);
			numeroMax.setDisable(true);
			humidityMin.setDisable(true);
			humidityMax.setDisable(true);
			tempMax.setDisable(true);
			tempMin.setDisable(true);
			soundMin.setDisable(true);
			soundMax.setDisable(true);
			
			//TODO Control input
			int cageIdMin = Integer.parseInt(numeroMin.getText());
			int cageIdMax = Integer.parseInt(numeroMax.getText());
			
			int temperatureMin = Integer.parseInt(tempMin.getText());
			int temperatureMax = Integer.parseInt(tempMax.getText());
			
			int humMin = Integer.parseInt(humidityMin.getText());
			int humMax = Integer.parseInt(humidityMax.getText());
			
			int soundLevelMin = Integer.parseInt(soundMin.getText());
			int soundLevelMax = Integer.parseInt(soundMax.getText());
			
			randomGenerationService.start(cageIdMin, cageIdMax, temperatureMin, temperatureMax, humMin, humMax, soundLevelMin, soundLevelMax);
		}else{
			autoBtn.setText("OFF");
			autoBtn.setStyle("-fx-background-color: #D3D3D3; -fx-background-radius: 10.0;");
			
			numeroMin.setDisable(false);
			numeroMax.setDisable(false);
			humidityMin.setDisable(false);
			humidityMax.setDisable(false);
			tempMax.setDisable(false);
			tempMin.setDisable(false);
			soundMin.setDisable(false);
			soundMax.setDisable(false);
			
			
			randomGenerationService.stop();
		}
		
	}
	
	@FXML void handleMouseOver(MouseEvent event){
		if(autoBtn.getText().equals("OFF")){
			autoBtn.setStyle("-fx-background-color: #e6e6e6; -fx-background-radius: 10.0;");
		}else{
			autoBtn.setStyle("-fx-background-color: #d3f0d3; -fx-background-radius: 10.0;");
		}
	}
	
	@FXML void handleMouseExit(MouseEvent event){
		if(autoBtn.getText().equals("OFF")){
			autoBtn.setStyle("-fx-background-color: #D3D3D3; -fx-background-radius: 10.0;");
		}else{
			autoBtn.setStyle("-fx-background-color: #90EE90; -fx-background-radius: 10.0;");
		}		
	}
	
	@FXML
	public void handleNewInfoTrame(MouseEvent event){
		new NewInfoTrame();		
	}

	@FXML
	public void handleNewEvtTrame(MouseEvent event){
		new NewEvtTrame();		
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	public void setRandomGenerationService(RandomGenerationService randomGenerationService) {
		this.randomGenerationService = randomGenerationService;
	}
}