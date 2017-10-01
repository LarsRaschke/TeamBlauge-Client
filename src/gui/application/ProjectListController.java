package gui.application;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

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
    private JFXListView<Label> tableProjectList;

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
	private JFXListView<?> listProjectMember;

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
	
	@FXML
    private JFXButton buttonOpenProject;
	
	@FXML
    private Label labelNotification;

	private Main main;

	public ProjectListController() {

	}

	/**
	 * Init-Methode:
	 * Quasi erweiterter Konstruktor, der in der Main aufgerufen wird, da bspw.
	 * KeyListener nicht im Konstruktor angelegt werden können.
	 */
	public void init() {
		
		// Initialisierung von KeyListenern etc.
		textFieldProjectName.editableProperty().set(false);
		textAreaProjectDescription.editableProperty().set(false);
		
		// EventHandler Init
		
		((Scene) labelProjectList.getScene()).setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ESCAPE)) {
					main.showGUI();
				}
			}
		});
		
		// Funktionalität - Laden & Setzen der Informationen

		labelUser.setText("    " + main.user.getNachname() + ", " + main.user.getVorname());
		
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(null);
			RMI_Projektmanager manager = (RMI_Projektmanager) registry.lookup("manager");
			main.projektliste = manager.ladeProjekte(main.user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(String projekt : main.projektliste)
		{
			tableProjectList.getItems().add(new Label(projekt));
		}
		
	}

	/**
	 * Setter-Methode.
	 * 
	 * @param main - Die Main-App, die gesetzt wird.
	 */
	public void setMainApp(Main main) {
		this.main = main;
	}

	/**
	 * Loggt den Benutzer aus und öffnet den LoginScreen.
	 * 
	 * @param event - Das Action Event.
	 */
	@FXML
	void buttonLogOutPressed(ActionEvent event) {
		main.showLogin();
	}

	/**
	 * Öffnet das Hauptfenster.
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
	void buttonBackPressed(ActionEvent event) {
		main.showGUI();
	}

	/**
	 * Aktiviert die Textfelder, die zum Erstellen eines neuen Projektes ausgefüllt werden müssen und
	 * setzt den Button zum Speichern des Projekts sichtbar.
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
	void buttonAddProjectPressed(ActionEvent event) {
		
		buttonSaveNewProject.setVisible(true);
		textFieldProjectName.clear();
		textFieldProjectName.editableProperty().set(true);
		textFieldProjectName.setStyle("-fx-background-color: white;");
		buttonEditProjectDescription.setVisible(false);
		textAreaProjectDescription.editableProperty().set(true);
		textAreaProjectDescription.clear();
		textAreaProjectDescription.setStyle("text-area-background: white;");
		buttonAddMember.setVisible(false);
		listProjectMember.getItems().clear();
		listProjectMember.setStyle("-fx-background-color: orange;");
	}
	
	/**
	 * Speichert das neue Projekt auf dem Server.
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
	void buttonSaveNewProjectPressed(ActionEvent event) {
		
		boolean itWorked = false;
		
		try {

			Registry registry = LocateRegistry.getRegistry(null);
			RMI_Projektmanager manager = (RMI_Projektmanager) registry.lookup("manager");
			itWorked = manager.erstelleProjekt(main.user, textFieldProjectName.getText(), textAreaProjectDescription.getText());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (itWorked) {
			
			main.projektliste.add(textFieldProjectName.getText());
			tableProjectList.getItems().add(new Label(textFieldProjectName.getText()));
			
			buttonSaveNewProject.setVisible(false);
			textFieldProjectName.editableProperty().set(false);
			textFieldProjectName.clear();
			textFieldProjectName.setStyle("-fx-background-color: orange;");
			buttonEditProjectDescription.setVisible(true);
			textAreaProjectDescription.editableProperty().set(false);
			textAreaProjectDescription.clear();
			textAreaProjectDescription.setStyle("text-area-background: orange;");
			buttonAddMember.setVisible(true);
			listProjectMember.setStyle("-fx-background-color: white;");
		}
		else {
			
			main.log("Fehler beim Anlegen des Projekts!");
		}
	}
	
	/**
	 * Öffnet das selektierte Projekt aus der Projektliste im MainFrame.
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
    void buttonOpenProjectPressed(ActionEvent event) {
		
		main.aktuellesProjekt = tableProjectList.getSelectionModel().getSelectedItem().getText();
		main.showGUI();
    }
	
	/**
	 * Aktiviert das Textfeld für die Projekt-Beschreibung, sodass diese verändert werden kann.
	 * Der Button wechselt zwischen "editieren" und "speichern".
	 * 
	 * @param event - Das Action-Event.
	 */
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

	/**
	 * Speichert die geänderte Projektbeschreibung.
	 * 
	 * @param name - Die neue Projektbeschreibung.
	 */
	void saveEnteredProjectDescription(String name) {

	}

	/**
	 * Aktiviert das Textfeld für den Projekt-Name, sodass diese verändert werden kann.
	 * Diese Methode wird momentan nicht verwendet, da der Projektname nicht veränderbar ist (Button ist nicht sichtbar).
	 * Der Button wechselt zwischen "editieren" und "speichern".
	 * 
	 * @param event - Das Action Event.
	 */
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

	/**
	 * Speichert den geänderten Projektnamen.
	 * 
	 * @param name - Der neue Projektname.
	 */
	void saveEnteredProjectName(String name) {
		// nicht implementiert, da der Projektname momentan nicht veränderbar ist.
	}
	
}
