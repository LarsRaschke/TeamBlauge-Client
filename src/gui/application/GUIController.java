package gui.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.prism.paint.Color;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;

public class GUIController {
	@FXML
	private Label labelProjectname;

	@FXML
	private Label labelProjectinformation;

	@FXML
	private Label labelUser;

	@FXML
	private MenuButton menuButtonFilter;

	@FXML
	private Label labelTaskname;

	@FXML
	private Label labelAuthor;

	@FXML
	private Label labelStatus;

	@FXML
	private Label labelColor;

	@FXML
	private Label labelDescription;

	@FXML
	private Label labelTags;

	@FXML
	private Label labelActualAuthor;

	@FXML
	private Label labelActualStatus;

	@FXML
	private JFXTextField textFieldTaskname;

	@FXML
	private JFXColorPicker ColorPicker;

	@FXML
	private JFXTextArea textAreaDescription;

	@FXML
	private JFXListView<?> listViewTags;

	@FXML
	private Label labelComments;

	@FXML
	private JFXListView<?> listViewComments;

	@FXML
	private JFXTextField textFieldTags;

	@FXML
	private JFXButton buttonAddTag;

	@FXML
	private JFXTextField textFieldComments;

	@FXML
	private JFXButton buttonAddComment;

	@FXML
	private JFXButton buttonEditTaskName;

	@FXML
	private ImageView buttonEditTaskNameIcon;

	@FXML
	private JFXButton buttonEditDescription;

	@FXML
	private ImageView buttonEditDescriptionIcon;

	@FXML
	private JFXButton buttonProjectselection;

	@FXML
	private JFXButton buttonLogOut;

	@FXML
	private JFXButton buttonProceed;

	@FXML
	private JFXButton buttonReturn;

	@FXML
	private JFXButton buttonInformation;

	@FXML
	private JFXButton buttonTags;

	@FXML
	private JFXButton buttonComments;

	@FXML
	private JFXButton buttonNewTask;

	@FXML
	private Label labelToDo;

	@FXML
	private Label labelDoing;

	@FXML
	private Label labelFinished;

	@FXML
	private JFXMasonryPane mansoryPaneToDo;

	@FXML
	private JFXMasonryPane mansoryPaneDoing;

	@FXML
	private JFXMasonryPane mansoryPaneFinished;

	@FXML
	private AnchorPane anchorPaneMansory;

	@FXML
	private ScrollPane scrollPaneMansory;

	private Main main;

	private JFXColorPicker colorPicker; // https://github.com/jfoenixadmin/JFoenix/issues/408

	private Label activeLabel;

	private ArrayList<Label> LabelList;

	private static int taskCounter;

	public GUIController() {
		this.colorPicker = new JFXColorPicker();
		// this.colorPicker.editableProperty().bind(column.editableProperty());
		// this.colorPicker.disableProperty().bind(column.editableProperty().not());
		this.colorPicker.setOnShowing(event -> {
			// nothing yet
		});
		this.colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {

			// newValue is selected color

		});

	}

	/*
	 * Quasi erweiterter Konstruktor, der in der Main aufgerufen wird, da bspw
	 * keylistener nicht im Konstruktor angelegt werden koe�nnen
	 */
	public void initnshit() {
		// hier können keylistener und sowas initialisiert werden
		textFieldTaskname.editableProperty().set(false);
		textAreaDescription.editableProperty().set(false);
		buttonReturn.setVisible(false);
		buttonProceed.setVisible(false);

		// labelUser.setText(main.user.getNachname() + ", " + main.user.getVorname());

		((Scene) labelProjectname.getScene()).setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ESCAPE)) {
					main.showLogin();
				}
			}
		});

		this.buttonEditTaskName.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					buttonEditTaskNamePressed(null);
				}
			}
		});

	}

	public void setMainApp(Main main) {
		this.main = main;
	}

	@FXML
	void ColorPickerSelectionChanged(ActionEvent event) {

	}

	@FXML
	void buttonCommentsPressed(ActionEvent event) {

	}

	@FXML
	void buttonInformationPressed(ActionEvent event) {

	}

	@FXML
	void buttonLogOutPressed(ActionEvent event) {
		main.showLogin();
	}

	@FXML
	void buttonProceedPressed(ActionEvent event) {

	}

	@FXML
	void buttonProjectselectionPressed(ActionEvent event) {
		main.showProjectList();
	}

	@FXML
	void buttonReturnPressed(ActionEvent event) {

	}

	@FXML
	void buttonTagsPressed(ActionEvent event) {

	}

	@FXML
	void buttonEditTaskNamePressed(ActionEvent event) {
		if (textFieldTaskname.editableProperty().get()) {
			textFieldTaskname.editableProperty().set(false);
			saveEnteredTaskname(textFieldTaskname.getText());
			this.buttonEditTaskNameIcon.setImage(new Image(getClass().getResourceAsStream("compose.png")));
			textFieldTaskname.setStyle("-fx-background-color: orange;");

		} else {
			this.textFieldTaskname.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent ke) {
					if (ke.getCode().equals(KeyCode.ENTER)) {
						saveEnteredTaskname(textFieldTaskname.getText());
						textFieldTaskname.editableProperty().set(false);
						buttonEditTaskNameIcon.setImage(new Image(getClass().getResourceAsStream("compose.png")));
						textFieldTaskname.setStyle("-fx-background-color: orange;");
					}
				}
			});
			textFieldTaskname.editableProperty().set(true);
			this.buttonEditTaskNameIcon.setImage(new Image(getClass().getResourceAsStream("save.png")));
			textFieldTaskname.setStyle("-fx-background-color: white;");
		}
	}

	void saveEnteredTaskname(String name) {
		// Nur zum Testen:
		labelActualAuthor.setText(name);
	}

	@FXML
	void buttonEditDescriptionPressed(ActionEvent event) {
		if (textAreaDescription.editableProperty().get()) {
			textAreaDescription.editableProperty().set(false);
			saveEnteredDescription(textAreaDescription.getText());
			this.buttonEditDescriptionIcon.setImage(new Image(getClass().getResourceAsStream("compose.png")));
			textAreaDescription.setStyle("text-area-background: orange;");

		} else {

			textAreaDescription.editableProperty().set(true);
			this.buttonEditDescriptionIcon.setImage(new Image(getClass().getResourceAsStream("save.png")));
			textAreaDescription.setStyle("text-area-background: white;");
		}
	}

	void saveEnteredDescription(String name) {
		// Nur zum Testen:
		labelActualStatus.setText(name);
	}

	@FXML
	void buttonNewTaskPressed(ActionEvent event) {
		Label lbl = new Label("Task " + taskCounter);
		lbl.setId("Task " + taskCounter);
		lbl.setAlignment(Pos.CENTER);

		lbl.setPrefSize(100, 100);
		lbl.setStyle(
				"-fx-background-color: white; -fx-background-color: white; -fx-padding: 10px; -fx-background-radius: 10px;");

		mansoryPaneToDo.setPrefHeight(mansoryPaneToDo.getPrefHeight() + 180);
		mansoryPaneDoing.setPrefHeight(mansoryPaneToDo.getPrefHeight() + 180);
		mansoryPaneFinished.setPrefHeight(mansoryPaneToDo.getPrefHeight() + 180);
		mansoryPaneToDo.getChildren().add(lbl);
		if (mansoryPaneToDo.getPrefHeight() > anchorPaneMansory.getPrefHeight()) {
			anchorPaneMansory.setPrefHeight(anchorPaneMansory.getPrefHeight() + 180);
		}

		lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				buttonReturn.setVisible(true);
				buttonProceed.setVisible(true);
				getTaskInfoFromServer(lbl.getId());
			}
		});
	}
	
	@FXML
	private JFXMasonryPane mansoryPaneTags;
	
	@FXML
	private AnchorPane anchorPaneTaskInformation;

		@FXML
		void buttonAddTagPressed(ActionEvent event) {
			main.log("Add Tag", "Button pressed");
			Label lbl = new Label();

			lbl.setPrefSize(30, 10);
			lbl.setMaxHeight(5);
			lbl.setMaxWidth(40);
			
			lbl.setText(textFieldTags.getText());
					
			lbl.setStyle("-fx-background-color: green; -fx-padding: 2px; -fx-background-radius: 15px; width:40pt; height:10pt; display:inline-block");

			mansoryPaneTags.setPrefHeight(mansoryPaneTags.getPrefHeight() + 180);
			
			mansoryPaneTags.getChildren().add(lbl);
			if (mansoryPaneTags.getPrefHeight() > anchorPaneTaskInformation.getPrefHeight()) {
				anchorPaneTaskInformation.setPrefHeight(anchorPaneTaskInformation.getPrefHeight() + 180);
			}

			lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					
				}
			});
			//LabelList.add(lbl);
			
		}

	void getTaskInfoFromServer(String id) {
		
	}
}
