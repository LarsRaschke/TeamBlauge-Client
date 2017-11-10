package gui.application;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.ZonedDateTime;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import communication.ldap.Abfragen;
import communication.ldap.LdapConnectionClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.User;
import model.interfaces.RMI_Projekt;

/*
 * TODO:
 * Schriftart anpassen Roboto Light
 */

public class ProjectFrameController {

	@FXML
	private JFXButton buttonLogOut;
	
	@FXML
	private JFXButton buttonBack;
	
	@FXML
	private Label labelUser;
	
	@FXML
	private JFXButton buttonEditProjectDescription;

	@FXML
	private ImageView buttonEditProjectDescriptionIcon;
	
	@FXML
	private JFXTextArea textAreaProjectDescription;
	
	@FXML
	private Label labelProjectname;
	
	@FXML
	private JFXListView<Label> listStatus;

	@FXML
	private JFXButton buttonAddStatus;
	
	@FXML
	private ImageView buttonAddStatusIcon;

	@FXML
	private JFXListView<Label> listProjectMember;
	
	@FXML
	private JFXTextField textFieldStatusName;
	
	@FXML
	private JFXTextField textFieldStatusBefore;

	@FXML
	private JFXButton buttonAddMember;
	
	@FXML
	private ImageView buttonAddMemberIcon;
	
	@FXML
	private Label labelLastChange;
	
	@FXML
	private Label labelCreationDate;
	
	@FXML
	private Label labelProjectCreator;
	
	@FXML
	private JFXTextField textFieldMember;

	private Main main;

	public ProjectFrameController() {

	}

	/**
	 * Init-Methode:
	 * Quasi erweiterter Konstruktor, der in der Main aufgerufen wird, da bspw.
	 * KeyListener nicht im Konstruktor angelegt werden können.
	 */
	public void init() {
		
		// Initialisierung von KeyListenern etc.
		textAreaProjectDescription.editableProperty().set(false);
		textFieldMember.editableProperty().set(false);
		textFieldStatusName.editableProperty().set(false);
		textFieldStatusBefore.editableProperty().set(false);
		
		// Funktionalität - Laden & Setzen der Informationen

		labelUser.setText(main.user.getNachname() + ", " + main.user.getVorname());
		labelProjectname.setText(main.aktuellesProjekt);
		
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(null);
			RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
			
			User ersteller = projekt.getErsteller();
			ZonedDateTime erstellungsDatum = projekt.getErstellungsDatum();
			ZonedDateTime letzteÄnderung = projekt.getLetzteAenderung();
			
			labelProjectCreator.setText(ersteller.getNachname() + ", " + ersteller.getVorname());
			labelCreationDate.setText(erstellungsDatum.getDayOfWeek().toString() + ", " + 
					erstellungsDatum.getDayOfMonth() + "." + erstellungsDatum.getMonth().toString() + " " + erstellungsDatum.getYear());
			labelLastChange.setText(letzteÄnderung.getDayOfWeek().toString() + ", " + 
					letzteÄnderung.getDayOfMonth() + "." + letzteÄnderung.getMonth().toString() + " " + letzteÄnderung.getYear());
			
			textAreaProjectDescription.setText(projekt.getBeschreibung());
			
			List<User> userListe = projekt.userListe();
			for (User user : userListe)
			{
				Image image = new Image(getClass().getResource("../resources/Icons/ios7-chatbubble.png").toExternalForm());
				ImageView view = new ImageView();
				view.setImage(image);
				view.setFitHeight(20.0);
				view.setFitWidth(20.0);
				
				Label lbl = new Label();
				lbl.setGraphicTextGap(15);
				lbl.setGraphic(view);
				lbl.setStyle("-fx-font-size: 15px; -fx-font-family: \"Candara\";");
				lbl.setText(user.getNachname() + ", " + user.getVorname() + "   |   " + user.getNutzername());
				
				listProjectMember.getItems().add(lbl);
			}
			
			List<String> statusliste = projekt.statusListe();
			for(String status : statusliste)
			{
				Image image = new Image(getClass().getResource("../resources/Icons/minus.png").toExternalForm());
				ImageView view = new ImageView();
				view.setImage(image);
				view.setFitHeight(20.0);
				view.setFitWidth(20.0);
				
				Label lbl = new Label();
				lbl.setGraphicTextGap(15);
				lbl.setGraphic(view);
				lbl.setStyle("-fx-font-size: 15px; -fx-font-family: \"Candara\";");
				lbl.setText(status);
				
				listStatus.getItems().add(lbl);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
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
		main.showProjectList();
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
			this.buttonEditProjectDescriptionIcon.setImage(new Image(getClass().getResourceAsStream("../resources/Icons/compose.png")));
			textAreaProjectDescription.setStyle("text-area-background: orange;");
			
			saveEnteredProjectDescription(textAreaProjectDescription.getText());
			
		} else {

			textAreaProjectDescription.editableProperty().set(true);
			this.buttonEditProjectDescriptionIcon.setImage(new Image(getClass().getResourceAsStream("../resources/Icons/save.png")));
			textAreaProjectDescription.setStyle("text-area-background: white;");
		}
	}

	/**
	 * Speichert die geänderte Projektbeschreibung.
	 * 
	 * @param description - Die neue Projektbeschreibung.
	 */
	private void saveEnteredProjectDescription(String description) {
		
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(null);
			RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
			projekt.ändereBeschreibung(description);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Aktiviert das Textfeld für das Hinzufügen eines Benutzers.
	 * Der Button wechselt zwischen "editieren" und "speichern".
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
	void buttonAddMemberPressed(ActionEvent event) {
		
		if (textFieldMember.editableProperty().get()) {
			
			textFieldMember.editableProperty().set(false);
			this.buttonAddMemberIcon.setImage(new Image(getClass().getResourceAsStream("../resources/Icons/plus-circled.png")));
			textFieldMember.setStyle("-fx-background-color: orange;");
			
			saveAddedMember(textFieldMember.getText());
			textFieldMember.clear();
			
		} else {

			textFieldMember.editableProperty().set(true);
			this.buttonAddMemberIcon.setImage(new Image(getClass().getResourceAsStream("../resources/Icons/save.png")));
			textFieldMember.setStyle("-fx-background-color: white;");
		}
		
	}
	
	/**
	 * Speichert den hinzugefügten User.
	 * 
	 * @param username - Der Username.
	 */
	private void saveAddedMember(String username) {
		
		User neu = null;	
		if (username.equals("testuser")){
			neu = new User("testuser", true, "Testuser", "Neu");
		}
		else if (main.getLDAPConnection()) {
			neu = Abfragen.erstelleUser(username);
		}
		 
		if(neu != null)
		{
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry(null);
				RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
				projekt.userHinzufügen(neu);
				
				Image image = new Image(getClass().getResource("../resources/Icons/ios7-chatbubble.png").toExternalForm());
				ImageView view = new ImageView();
				view.setImage(image);
				view.setFitHeight(20.0);
				view.setFitWidth(20.0);
				
				Label lbl = new Label();
				lbl.setGraphicTextGap(15);
				lbl.setGraphic(view);
				lbl.setStyle("-fx-font-size: 15px; -fx-font-family: \"Candara\";");
				lbl.setText(neu.getNachname() + ", " + neu.getVorname() + "   |   " + neu.getNutzername());
				
				listProjectMember.getItems().add(lbl);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Aktiviert das Textfeld für das Hinzufügen eines Status.
	 * Der Button wechselt zwischen "editieren" und "speichern".
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
	void buttonAddStatusPressed(ActionEvent event) {
		
		if (textFieldStatusName.editableProperty().get()) {
			
			textFieldStatusName.editableProperty().set(false);
			textFieldStatusBefore.editableProperty().set(false);
			this.buttonAddStatusIcon.setImage(new Image(getClass().getResourceAsStream("../resources/Icons/plus-circled.png")));
			textFieldStatusName.setStyle("-fx-background-color: orange;");
			textFieldStatusBefore.setStyle("-fx-background-color: orange;");
			
			saveAddedStatus(textFieldStatusName.getText(), textFieldStatusBefore.getText());
			textFieldStatusName.clear();
			textFieldStatusBefore.clear();
			
		} else {

			textFieldStatusName.editableProperty().set(true);
			textFieldStatusBefore.editableProperty().set(true);
			this.buttonAddStatusIcon.setImage(new Image(getClass().getResourceAsStream("../resources/Icons/save.png")));
			textFieldStatusName.setStyle("-fx-background-color: white;");
			textFieldStatusBefore.setStyle("-fx-background-color: white;");
		}

	}
	
	/**
	 * Speichert den hinzugefügten Status.
	 * 
	 * @param statusname - Der Statusname.
	 * @param vorgänger - Der Vorgänger.
	 */
	private void saveAddedStatus(String statusname, String vorgänger) {
		
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(null);
			RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
			projekt.statusHinzufügen(statusname, vorgänger);
			
			listStatus.getItems().clear();
			
			List<String> statusliste = projekt.statusListe();
			for(String status : statusliste)
			{
				Image image = new Image(getClass().getResource("../resources/Icons/minus.png").toExternalForm());
				ImageView view = new ImageView();
				view.setImage(image);
				view.setFitHeight(20.0);
				view.setFitWidth(20.0);
				
				Label lbl = new Label();
				lbl.setGraphicTextGap(15);
				lbl.setGraphic(view);
				lbl.setStyle("-fx-font-size: 15px; -fx-font-family: \"Candara\";");
				lbl.setText(status);
				
				listStatus.getItems().add(lbl);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
