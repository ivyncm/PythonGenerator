package generator.pythonGenerator;

import java.io.File;

import generator.pythonGenerator.controller.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(new File("src/main/java/generator/pythonGenerator/view/MainWindow.fxml").toURI().toURL());
			Scene scene = new Scene(root);
			
			primaryStage.setTitle("Q-UML2Python");
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(event -> {
                Utils.deleteTempDir();
            });
			primaryStage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
