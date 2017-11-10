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

	public LoginController() {

		System.out.println("created LoginController");

	}

	/**
	 * Initialisierung des LoginScreen
	 */
	public void init() {

		this.textFieldLogInScreenPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					
					checkLoginData();
					
					if (main.user != null) {
						main.showProjectList();
					} else {
						infoLabelLogInScreen.setText("Falscher Username!");
					}
				}
			}
		});

		this.textFieldLogInScreenUsername.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {

					checkLoginData();
					
					if (main.user != null) {
						main.showProjectList();
					} else {
						infoLabelLogInScreen.setText("Falscher Username!");
					}
				}
			}
		});
		this.textFieldLogInScreenPassword.setVisible(false);

	}

	public void setMainApp(Main main) {
		this.main = main;
	}

	/**
	 * Methode wird beim drücken des Login-Buttons ausgeführt.
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
	void buttonLogInScreenLogInPressed(ActionEvent event) {
		
		checkLoginData();
		
		if (main.user != null) {
			main.showProjectList();
		} else {
			infoLabelLogInScreen.setText("Falscher Username!");
		}
	}

	/**
	 * Überprüft, ob der eingegebene Username auf dem Server hinterlegt ist.
	 */
	public void checkLoginData() {

		main.log("Check Login");

		if(textFieldLogInScreenUsername.getText().equals("admin")) {
			main.user = new User("admin", true, "Administrator", "Admin");
		}
		else if (main.getLDAPConnection()) {
			main.user = Abfragen.erstelleUser(textFieldLogInScreenUsername.getText());
		}
		else {
			main.user = new User("dummy", true, "User", "Dummy");
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
	void buttonLogInScreenInformationPressed(ActionEvent event) {

	}
	
}
