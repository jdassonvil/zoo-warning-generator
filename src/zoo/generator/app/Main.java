package zoo.generator.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import zoo.generator.controller.MainPanelController;
import zoo.generator.service.RandomGenerationService;

public class Main extends Application {

	private static Stage primaryStage;
	private static BorderPane rootLayout;

	public static void main(String args[]) {
		Application.launch(Main.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;

		primaryStage.setTitle("Générateur de trames de surveillances");

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../../../zoo/generator/view/RootLayout.fxml"));

		try {
			rootLayout = loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// Main panel
			FXMLLoader loader2 = new FXMLLoader();
			loader2.setLocation(Main.class.getResource("../../../zoo/generator/view/MainPanel.fxml"));
			BorderPane o = loader2.load();

			scene.setRoot(o);

			MainPanelController controller = loader2.getController();
			controller.setRandomGenerationService(RandomGenerationService.getInstance());

			primaryStage.show();

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					System.out.println("Arrêt des threads");
					Thread[] threads = new Thread[100];

					int n = Thread.enumerate(threads);

					for (int i = 0; i < n; i++) {
						System.out.println(threads[i]);
					}

					// 1. Stop random generation
					RandomGenerationService.getInstance().stop();

					// 2. Stop server thread
					Server.getInstance().stop();

					// 3. Stop client threads
					ClientHandler.stopAll();

					System.out.println("Après l'arrêt des threads");
				}

			});

			Server.getInstance().start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
