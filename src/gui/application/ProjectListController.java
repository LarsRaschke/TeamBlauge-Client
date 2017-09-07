package gui.application;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.interfaces.RMI_Projektmanager;

/*
 * TODO:
 * Schriftart anpassen Roboto Light
 */

public class ProjectListController {

	@FXML
	private Label labelUser;

	@FXML
	private JFXButton buttonLogOut;

	@FXML
	private JFXTreeTableView<?> tableProjectList;

	@FXML
	private JFXTextField textFieldProjectName;

	@FXML
	private JFXButton buttonEditProjectName;

	@FXML
	private ImageView buttonEditProjectNameIcon;

	@FXML
	private JFXTextArea textAreaProjectDescription;

	@FXML
	private JFXButton buttonEditProjectDescription;

	@FXML
	private ImageView buttonEditProjectDescriptionIcon;

	@FXML
	private JFXListView<String> listProjectMember;

	@FXML
	private ImageView buttonAddMember;

	@FXML
	private JFXButton buttonAddProject;

	@FXML
	private JFXButton buttonBack;

	@FXML
	private Label labelProjectList;

	@FXML
	private JFXButton buttonSaveNewProject;

	private Main main;

	public ProjectListController() {

	}

	public void init() {
		textFieldProjectName.editableProperty().set(false);
		textAreaProjectDescription.editableProperty().set(false);
		labelUser.setText(main.user.getNachname() + ", " + main.user.getVorname());
		
		ObservableList<String> items = FXCollections.observableArrayList(main.projektliste);
		listProjectMember = new JFXListView<>();
		listProjectMember.setItems(items);
		
		((Scene) labelProjectList.getScene()).setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ESCAPE)) {
					main.showGUI();
				}
			}
		});
	}

	public void setMainApp(Main main) {
		this.main = main;
	}

	@FXML
	void buttonLogOutPressed(ActionEvent event) {
		main.showLogin();
	}

	@FXML
	void buttonBackPressed(ActionEvent event) {
		main.showGUI();
	}

	@FXML
	void buttonAddProjectPressed(ActionEvent event) {

	}

	@FXML
	void buttonEditProjectNamePressed(ActionEvent event) {
		if (textFieldProjectName.editableProperty().get()) {
			textFieldProjectName.editableProperty().set(false);
			saveEnteredProjectName(textFieldProjectName.getText());
			this.buttonEditProjectNameIcon.setImage(new Image(getClass().getResourceAsStream("compose.png")));
			textFieldProjectName.setStyle("-fx-background-color: orange;");

		} else {
			this.textFieldProjectName.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent ke) {
					if (ke.getCode().equals(KeyCode.ENTER)) {
						saveEnteredProjectName(textFieldProjectName.getText());
						textFieldProjectName.editableProperty().set(false);
						buttonEditProjectNameIcon.setImage(new Image(getClass().getResourceAsStream("compose.png")));
						textFieldProjectName.setStyle("-fx-background-color: orange;");
					}
				}
			});
			textFieldProjectName.editableProperty().set(true);
			this.buttonEditProjectNameIcon.setImage(new Image(getClass().getResourceAsStream("save.png")));
			textFieldProjectName.setStyle("-fx-background-color: white;");
		}
	}

	void saveEnteredProjectName(String name) {

	}

	@FXML
	void buttonEditProjectDescriptionPressed(ActionEvent event) {
		if (textAreaProjectDescription.editableProperty().get()) {
			textAreaProjectDescription.editableProperty().set(false);
			saveEnteredProjectDescription(textAreaProjectDescription.getText());
			this.buttonEditProjectDescriptionIcon.setImage(new Image(getClass().getResourceAsStream("compose.png")));
			textAreaProjectDescription.setStyle("text-area-background: orange;");

		} else {

			textAreaProjectDescription.editableProperty().set(true);
			this.buttonEditProjectDescriptionIcon.setImage(new Image(getClass().getResourceAsStream("save.png")));
			textAreaProjectDescription.setStyle("text-area-background: white;");

		}
	}

	void saveEnteredProjectDescription(String name) {

	}

	@FXML
	void buttonSaveNewProjectPressed(ActionEvent event) {
		
		try {

			Registry registry = LocateRegistry.getRegistry(null);
			RMI_Projektmanager manager = (RMI_Projektmanager) registry.lookup("manager");
			manager.erstelleProjekt(main.user, textFieldProjectName.getText(), "");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
