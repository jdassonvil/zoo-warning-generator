package zoo.generator.app;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import zoo.generator.behavior.INotifiable;
import zoo.generator.enums.LogLevel;
import zoo.generator.model.Trame;
import zoo.generator.service.LogService;
import zoo.generator.service.TrameService;

public class ClientHandler implements Runnable, INotifiable {

	private Socket socket;

	private TrameService trameService = TrameService.getInstance();
	private LogService logService = LogService.getInstance();

	private BlockingQueue<Trame> trames = new LinkedBlockingQueue<>();

	private static List<Thread> instances = new ArrayList<Thread>();
	private static boolean isClosing = false;

	public ClientHandler(Socket socket) {
		this.socket = socket;

		Thread thread = new Thread(this);
		thread.setName("Client handler thread " + thread.getId());

		synchronized (instances) {
			if (!isClosing) {
				instances.add(thread);
				thread.start();
			}
		}
	}

	public static void stopAll() {

		synchronized (instances) {
			isClosing = true;
		}

		for (Thread thread : instances) {
			thread.interrupt();
		}
	}

	@Override
	public void run() {

		trameService.subscribe(Thread.currentThread().getId() + "", this);

		BufferedOutputStream out;

		try {
			out = new BufferedOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}

		try {
			while (!socket.isClosed()) {
				Trame trame = trames.take();
				out.write(trame.getBytes());
				out.flush();
			}
		} catch (InterruptedException e) {
			System.out.println("Arrêt du thread "  + Thread.currentThread().getName());
		} catch (IOException e) {
			logService.log(LogLevel.IMPORTANT, "Un client s'est déconnecté");
		} finally {
			trameService.unsubscribe(Thread.currentThread().getId() + "");
			
			/*if(out != null){
				try{
					out.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/
		}
	}

	@Override
	public void notify(Trame t) {
		try {
			trames.add(t);
		} catch (IllegalStateException e) {
			System.err.println("Trame perdue " + e.getMessage());
		}

	}

}
