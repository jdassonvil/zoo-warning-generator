package zoo.generator.service;

import java.util.Random;

import zoo.generator.enums.EventType;
import zoo.generator.enums.LogLevel;
import zoo.generator.model.EventTrame;
import zoo.generator.model.InfoTrame;
import zoo.generator.model.Trame;

public class RandomGenerationService {

	private static RandomGenerationService instance;

	private LogService logService;
	private TrameService trameService;
	private Thread thread;

	private RandomGenerationService() {
		this.logService = LogService.getInstance();
		this.trameService = TrameService.getInstance();
	}
	
	private int getRandomValue(int min, int max){
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}

	public static RandomGenerationService getInstance() {
		if (instance == null) {
			instance = new RandomGenerationService();
		}

		return instance;
	}

	public void start(int cageMin, int cageMax, int tempMin, int tempMax, int humidityMin, int humidityMax,
			int soundMin, int soundMax) {
		
		thread = new Thread(new Runnable() {
			
			public void run() {
				logService.log(LogLevel.IMPORTANT, "Mode aléatoire activé");
				try {
					int count = 0;
					
					while (true) {
						Thread.sleep(1000);
						
						Trame trame;
						
						if(count%10 == 0){
							EventType event = EventType.values()[getRandomValue(0, 4)];
							trame = new EventTrame((short) getRandomValue(cageMin, cageMax) , event);
						}else{
							trame = new InfoTrame((short) getRandomValue(cageMin, cageMax) , (byte) getRandomValue(tempMin, tempMax), (byte) getRandomValue(humidityMin, humidityMax), (byte) getRandomValue(soundMin, soundMax));
						}
						
						count++;
						
						trameService.notifyNewTrame(trame);
					}
				} catch (InterruptedException e) {
					logService.log(LogLevel.IMPORTANT, "Mode aléatoire désactivé");
				}
			}
		});

		thread.setName("Random Generator thread");
		thread.start();
	}

	public void stop() {
		if (this.thread != null && !this.thread.isInterrupted()) {
			thread.interrupt();
		}
	}

}
