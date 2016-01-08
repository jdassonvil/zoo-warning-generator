package zoo.generator.service;

import java.util.Observable;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import zoo.generator.enums.LogLevel;

public class LogService extends Observable {
	
	private static LogService singletonInstance;
	
	private LogService(){
		
	}
	
	public static LogService getInstance(){
		if(singletonInstance == null){
			singletonInstance = new LogService();
		}
		
		return singletonInstance;
	}
	
	public void log(LogLevel logLevel, String str){
		

		
		Text text = new Text(str + "\n");
		
		switch(logLevel){
		case ERROR:
			text.setFill(Color.RED);
			break;
		case IMPORTANT:
			text.setFill(Color.GREEN);
			break;
		case NORMAL:
			text.setFill(Color.BLUE);
			break;
		}
		
		this.setChanged();
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				notifyObservers(text);			
			}
		});
	}
	
	

}
