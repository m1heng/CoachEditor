package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import view.MainViewController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			//loader UI stuff
			FXMLLoader mainloader = new FXMLLoader();
			mainloader.setLocation(
					getClass().getResource("/view/MainView.fxml"));
			AnchorPane mainroot = (AnchorPane)mainloader.load();
			
			Scene mainScene = new Scene(mainroot);
			
			MainViewController mcontroller = mainloader.getController();
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("CoachEditor");
			mcontroller.start(primaryStage);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
