package zoo.generator.app;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import zoo.generator.enums.LogLevel;
import zoo.generator.service.LogService;

public class Server {

	private ServerSocket serverSocket;
	private int PORT = 5128;

	private LogService logService;

	private static Server instance;

	private Thread thread;

	private Server() {

		this.logService = LogService.getInstance();

		try {
			serverSocket = new ServerSocket(PORT);
			serverSocket.setReuseAddress(true);
			logService.log(LogLevel.IMPORTANT, "Le serveur ecoute sur le port " + PORT);

		} catch (BindException e) {
			logService.log(LogLevel.ERROR, "Le port " + PORT + " est déjà utilisé par une autre application");
			logService.log(LogLevel.ERROR, "Fermez l'application utilisant ce port puis redémarrez l'application");
		} catch (IOException e) {
			logService.log(LogLevel.ERROR, e.getMessage());
		}
	}

	public static Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}

		return instance;
	}

	public void start() {

		if (serverSocket == null) {
			return;
		}

		thread = new Thread(new Runnable() {
			public void run() {
				try {
					while (true) {
						Socket s = serverSocket.accept();
						logService.log(LogLevel.IMPORTANT, "Un nouveau client s'est connecté");
						new ClientHandler(s);
					}
				} catch (IOException e) {
					logService.log(LogLevel.ERROR, e.getMessage());
				}
			}
		});

		thread.setName("Server thread");
		thread.start();
	}

	public void stop() {
		if (thread != null && !thread.isInterrupted()) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
				thread.interrupt();
			}
		}
	}

}
