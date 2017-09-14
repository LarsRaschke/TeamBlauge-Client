package gui.application;

import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import communication.ldap.Abfragen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.User;

public class LoginController {

	private Main main;

	@FXML
	private Label infoLabelLogInScreen;

	@FXML
	private JFXTextField textFieldLogInScreenUsername;

	@FXML
	JFXPasswordField textFieldLogInScreenPassword;

	@FXML
	private JFXButton buttonLogInScreenLogIn;

	@FXML
	private JFXButton buttonLogInScreenSettings;

	@FXML
	private JFXButton buttonLogInScreenInformation;

	@FXML
	private JFXButton buttonSaveNewProject;

	@FXML
	private JFXButton buttonNewProject;

	@FXML
	void buttonLogInScreenInformationPressed(ActionEvent event) {

	}

	public LoginController() {

		System.out.println("created LoginController");

	}

	/**
	 * Initialisierung des LoginScreen
	 */
	public void initnshit() {

		this.textFieldLogInScreenPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					checkLoginData();
				}
			}
		});

		this.textFieldLogInScreenUsername.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					checkLoginData();
				}
			}
		});
		this.textFieldLogInScreenPassword.setVisible(false);

	}

	public void setMainApp(Main main) {
		this.main = main;
	}

	/**
	 * Methode wird beim dr�cken des Login-Buttons ausgef�hrt. Ruft die Methode
	 * checkLoginData() aus
	 * 
	 * @param event
	 */
	@FXML
	void buttonLogInScreenLogInPressed(ActionEvent event) {
		checkLoginData();
	}

	/**
	 * �berpr�ft, ob der eingegebene Username auf dem Server hinterlegt ist.
	 * 
	 */
	public void checkLoginData() {

		main.log("Check Login");

		if (main.getLDAPConnection()) {
			main.user = Abfragen.erstelleUser(textFieldLogInScreenUsername.getText());
		}
		else
		{
			main.user = new User("admin", true, "Administrator", "Admin");
		}

		if (main.user != null) {
			main.projektliste = new ArrayList<String>();
			main.showProjectList();;
		} else {
			infoLabelLogInScreen.setText("Falscher Username!");
		}

	}

	/**
	 * TBD
	 * 
	 * @param event
	 */
	@FXML
	void buttonLogInScreenSettingsPressed(ActionEvent event) {

	}

	/**
	 * TBD
	 * 
	 * @param event
	 */
	@FXML
	void buttonSaveNewProjectPressed(ActionEvent event) {

	}

	/**
	 * TBD
	 * 
	 * @param event
	 */
	@FXML
	void buttonNewProjectPressed(ActionEvent event) {

	}
}
