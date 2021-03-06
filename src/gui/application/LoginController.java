package gui.application;

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

    // currently Invisible
    @FXML JFXPasswordField textFieldLogInScreenPassword;

    @FXML
    private JFXButton buttonLogInScreenLogIn;

    @FXML
    private JFXButton buttonLogInScreenSettings;

    @FXML
    private JFXButton buttonLogInScreenInformation;

    @FXML
    void buttonLogInScreenInformationPressed(ActionEvent event) {

    }
    
    public LoginController() {
    	
    	System.out.println("created LoginController");
    	
    	
        
    }
    
    public void initnshit() {
    	this.textFieldLogInScreenPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                	checkLoginData();
                }
            }
        });
    	
    	this.textFieldLogInScreenUsername.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                	checkLoginData();
                }
            }
        });
    	this.textFieldLogInScreenPassword.setVisible(false);
    	//main.showGUI();
    }
    
    
    public void setMainApp(Main main) {
		this.main = main;
	}

    
    
    @FXML
    void buttonLogInScreenLogInPressed(ActionEvent event) {
    	checkLoginData();
    }
    
    public void checkLoginData() {
    	main.log("Check Login");
    	
    	main.user = Abfragen.erstelleUser(textFieldLogInScreenUsername.getText());
    	
	    if(main.user != null) {
			main.showGUI();
		}
	    else {
	    	infoLabelLogInScreen.setText("Falscher Username!");
	    }
    }

    @FXML
    void buttonLogInScreenSettingsPressed(ActionEvent event) {

    }

}
