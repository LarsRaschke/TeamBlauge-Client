package gui.application;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.User;
import model.interfaces.RMI_Projekt;
import model.interfaces.RMI_Projektmanager;

public class ProjectListController {

	@FXML
	private JFXButton buttonLogOut;
	
	@FXML
	private JFXButton buttonBack;

	@FXML
	private JFXButton buttonAddProject;
	
	@FXML
    private JFXButton buttonOpenProject;
	
	@FXML
    private JFXButton buttonRefresh;
	
	@FXML
	private Label labelUser;
	
	@FXML
    private Label labelNotification;

	@FXML
    private JFXListView<Label> tableProjectList;
	
	@FXML
    private JFXButton buttonMore;

	@FXML
	private JFXButton buttonSaveNewProject;

	@FXML
	private JFXTextField textFieldProjectName;
	
	@FXML
	private JFXTextArea textAreaProjectDescription;

	@FXML
    private Label labelLastChange;
	
	@FXML
    private Label labelCreationDate;
	
	@FXML
    private Label labelProjectCreator;
	

	private Main main;

	public ProjectListController() {

	}

	/**
	 * Init-Methode:
	 * Quasi erweiterter Konstruktor, der in der Main aufgerufen wird, da bspw.
	 * KeyListener nicht im Konstruktor angelegt werden können.
	 */
	public void init() {
		
		// Initialisierung von Button-States etc.
		
		textFieldProjectName.editableProperty().set(false);
		textAreaProjectDescription.editableProperty().set(false);
		
		buttonMore.setVisible(false);
		buttonSaveNewProject.setVisible(false);
		buttonOpenProject.setVisible(false);
		
		buttonRefresh.setStyle("-fx-background-color: transparent;");
		
		// Funktionalität - Laden & Setzen der Informationen

		labelUser.setText(main.user.getNachname() + ", " + main.user.getVorname());
		tableProjectList.getItems().clear();
		
		ArrayList<String> projektliste = new ArrayList<>();
		
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(null);
			RMI_Projektmanager manager = (RMI_Projektmanager) registry.lookup("manager");
			projektliste = manager.ladeProjekte(main.user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(String projekt : projektliste)
		{
			Image image = new Image(getClass().getResource("../resources/Icons/pricetags.png").toExternalForm());
			ImageView view = new ImageView();
			view.setImage(image);
			view.setFitHeight(25.0);
			view.setFitWidth(25.0);
			
			Label lbl = new Label();
			lbl.setGraphicTextGap(15);
			lbl.setGraphic(view);
			lbl.setStyle("-fx-font-size: 20px; -fx-font-family: \"Candara\";");
			lbl.setText(projekt);
			
			tableProjectList.getItems().add(lbl);
			
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
		main.showMainFrame();
	}
	
	/**
	 * Öffnet das selektierte Projekt aus der Projektliste im MainFrame.
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
    void buttonOpenProjectPressed(ActionEvent event) {
		
		main.aktuellesProjekt = tableProjectList.getSelectionModel().getSelectedItem().getText();
		main.showMainFrame();
    }
	
	/**
	 * Aktualisiert die Seite
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
    void buttonRefreshPressed(ActionEvent event) {
		
		buttonRefresh.setStyle("-fx-background-color: transparent;");
		main.showProjectList();
    }
	
	/**
	 * Benachrichtigt den User über Änderungen.
	 */
	public void notifyUserProjekt()
	{
		buttonRefresh.setStyle("-fx-background-color: #800000;");
	}
	
	/**
	 * Öffnet den ProjectFrame für das ausgewählte Projekt.
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
    void buttonMorePressed(ActionEvent event) {
		
		main.aktuellesProjekt = tableProjectList.getSelectionModel().getSelectedItem().getText();
		main.showProjectFrame();
    }

	/**
	 * Aktiviert die Textfelder, die zum Erstellen eines neuen Projektes ausgefüllt werden müssen und
	 * setzt den Button zum Speichern des Projekts sichtbar.
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
	void buttonAddProjectPressed(ActionEvent event) {
		
		buttonMore.setVisible(false);
		buttonOpenProject.setVisible(false);
		buttonSaveNewProject.setVisible(true);
		tableProjectList.getSelectionModel().clearSelection();
		
		labelCreationDate.setText(null);
		labelLastChange.setText(null);
		labelProjectCreator.setText(null);
		
		textFieldProjectName.clear();
		textFieldProjectName.editableProperty().set(true);
		textFieldProjectName.setStyle("-fx-background-color: white;");
		textAreaProjectDescription.editableProperty().set(true);
		textAreaProjectDescription.clear();
		textAreaProjectDescription.setStyle("text-area-background: white;");
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
			
			try {
				main.client.somethingChanged("Projektuebersicht", "");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			Image image = new Image(getClass().getResource("../resources/Icons/pricetags.png").toExternalForm());
			ImageView view = new ImageView();
			view.setImage(image);
			view.setFitHeight(25.0);
			view.setFitWidth(25.0);
			
			Label lbl = new Label();
			lbl.setGraphicTextGap(15);
			lbl.setGraphic(view);
			lbl.setStyle("-fx-font-size: 20px; -fx-font-family: \"Candara\";");
			lbl.setText(textFieldProjectName.getText());
			
			tableProjectList.getItems().add(lbl);
			
			buttonSaveNewProject.setVisible(false);
			textFieldProjectName.editableProperty().set(false);
			textFieldProjectName.clear();
			textFieldProjectName.setStyle("-fx-background-color: orange;");
			textAreaProjectDescription.editableProperty().set(false);
			textAreaProjectDescription.clear();
			textAreaProjectDescription.setStyle("text-area-background: orange;");
		}
		else {
			
			main.log("Fehler beim Anlegen des Projekts!");
		}
	}
	
	/**
	 * Aktualisiert die Projektübersicht bei Mausklick in der Tabelle.
	 * 
	 * @param event - Das Mouse-Event.
	 */
	@FXML
    void projectTableListMouseClicked(MouseEvent event) {

		if(event.getButton() == MouseButton.PRIMARY)
		{
			if(tableProjectList.getSelectionModel().getSelectedItem() != null)
			{
				textFieldProjectName.setText(tableProjectList.getSelectionModel().getSelectedItem().getText());
				
				Registry registry;
				try {
					registry = LocateRegistry.getRegistry(null);
					RMI_Projekt projekt = (RMI_Projekt) registry.lookup(textFieldProjectName.getText());
					
					User ersteller = projekt.getErsteller();
					ZonedDateTime erstellungsDatum = projekt.getErstellungsDatum();
					ZonedDateTime letzteÄnderung = projekt.getLetzteAenderung();
					
					labelProjectCreator.setText(ersteller.getNachname() + ", " + ersteller.getVorname());
					labelCreationDate.setText(erstellungsDatum.getDayOfWeek().toString() + ", " + 
							erstellungsDatum.getDayOfMonth() + "." + erstellungsDatum.getMonth().toString() + " " + erstellungsDatum.getYear());
					labelLastChange.setText(letzteÄnderung.getDayOfWeek().toString() + ", " + 
							letzteÄnderung.getDayOfMonth() + "." + letzteÄnderung.getMonth().toString() + " " + letzteÄnderung.getYear());
					textAreaProjectDescription.setText(projekt.getBeschreibung());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				buttonOpenProject.setVisible(true);
				buttonMore.setVisible(true);
			}
		}
    }
	
}
