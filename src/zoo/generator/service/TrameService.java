package zoo.generator.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import zoo.generator.behavior.INotifiable;
import zoo.generator.enums.LogLevel;
import zoo.generator.model.Trame;

/**
 * 
 * @author a547889
 * Trame service receive fresh trames and notify its subscribers
 */
public class TrameService {
	
	private Map<String, INotifiable> subscribers = new HashMap<>();
	
	private static TrameService instance;
	
	private LogService logService;
	
	public TrameService(){
		this.logService =  LogService.getInstance();
	}
	
	public static TrameService getInstance(){
		if(instance == null){
			instance = new TrameService();
		}
		return instance;
	}
	
	public synchronized void subscribe(String id, INotifiable subscriber){
		subscribers.put(id, subscriber);
	}

	public synchronized void unsubscribe(String id){
		subscribers.remove(id);
	}
	
	public void notifyNewTrame(Trame t){
		
		Collection<INotifiable> toNotify;
		synchronized (this) {
			toNotify = subscribers.values();
		}
		
		logService.log(LogLevel.NORMAL, "Envoi d'une trame " + t);
		
		for(INotifiable notifiable: toNotify){
			notifiable.notify(t);
		}
	}
}
