package gui.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;

public class Main extends Application {

	BorderPane root = new BorderPane();
	Stage primaryStage = new Stage();
	
	public User user;

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("BLAUGE Kanban");
			initRoot();
			showLogin();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initRoot() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("rootLayout.fxml"));
			root = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showGUI() {
		try {
			// Load GUI fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("mainFrame.fxml"));
			AnchorPane GUI = (AnchorPane) loader.load();
			GUIController controller = loader.getController();
			controller.setMainApp(this);
			root.setCenter(GUI);
			controller.initnshit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showLogin() {
		try {
			// Load Loginscreen fxml.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("loginScreen.fxml"));
			AnchorPane GUI = (AnchorPane) loader.load();
			LoginController controller = loader.getController();
			controller.setMainApp(this);
			root.setCenter(GUI);
			controller.initnshit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showProjectList() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("projectList.fxml"));
			AnchorPane GUI = (AnchorPane) loader.load();
			ProjectListController controller = loader.getController();
			controller.setMainApp(this);
			root.setCenter(GUI);
			controller.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Logfunktion, Ueberladen um mit und ohne Label loggen zu koennen
	 */

	public void log(String text, String label) {
		System.out.print("*** LOG: ");
		if (label != "") {
			System.out.print(label);
		} else {
			System.out.print(">No Label<");
		}
		System.out.print(":\t");
		System.out.print(text + "\n");
	}

	public void log(String text) {
		System.out.print("*** LOG: >No Label<:\t");
		System.out.print(text + "\n");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
