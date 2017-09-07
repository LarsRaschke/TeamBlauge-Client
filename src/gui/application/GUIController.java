package gui.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.function.DoubleToIntFunction;

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
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

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
	private Label labelMasonryPaneOptic;

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
	private VBox mansoryPaneToDo;

	@FXML
	private VBox mansoryPaneDoing;

	@FXML
	private VBox mansoryPaneFinished;

	@FXML
	private AnchorPane anchorPaneMansory;

	@FXML
	private AnchorPane anchorPaneTaskInformation1;

	@FXML
	private AnchorPane anchorPaneTaskInformation2;

	@FXML
	private AnchorPane anchorPaneTaskInformation3;

	@FXML
	private ScrollPane scrollPaneMansory;

	@FXML
	private JFXMasonryPane mansoryPaneTags;

	@FXML
	private AnchorPane anchorPaneTaskInformation;

	private Main main;

	private JFXColorPicker colorPicker; // https://github.com/jfoenixadmin/JFoenix/issues/408

	private Label activeLabel;

	private ArrayList<Label> LabelList;

	private static int usedScrollBarHeight_ToDo;
	private static int usedScrollBarHeight_Doing;
	private static int usedScrollBarHeight_Finished;

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

	/**
	 * Quasi erweiterter Konstruktor, der in der Main aufgerufen wird, da bspw
	 * keylistener nicht im Konstruktor angelegt werden koeï¿½nnen
	 */
	public void initnshit() {
		// hier kÃ¶nnen keylistener und sowas initialisiert werden
		textFieldTaskname.editableProperty().set(false);
		textAreaDescription.editableProperty().set(false);

		// Button Init
		buttonReturn.setVisible(false);
		buttonProceed.setVisible(false);

		// Kanban columns Init
		mansoryPaneToDo.setAlignment(Pos.TOP_CENTER);
		mansoryPaneToDo.setSpacing(10);
		mansoryPaneDoing.setAlignment(Pos.TOP_CENTER);
		mansoryPaneDoing.setSpacing(10);
		mansoryPaneFinished.setAlignment(Pos.TOP_CENTER);
		mansoryPaneFinished.setSpacing(10);

		labelUser.setText(main.user.getNachname() + ", " + main.user.getVorname());

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

		this.textFieldTags.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					buttonAddTagPressed(null);
				}
			}
		});

		this.buttonAddTag.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					buttonAddTagPressed(null);
				}
			}
		});

	}

	public void setMainApp(Main main) {
		this.main = main;
	}

	/**
	 * TBD
	 * 
	 * @param event
	 */
	@FXML
	void ColorPickerSelectionChanged(ActionEvent event) {

	}

	/**
	 * TBD
	 * 
	 * @param event
	 */
	@FXML
	void buttonCommentsPressed(ActionEvent event) {

	}

	/**
	 * TBD
	 * 
	 * @param event
	 */
	@FXML
	void buttonInformationPressed(ActionEvent event) {

	}

	/**
	 * Logout des Users und Weiterleitung an das Login-Fenster
	 * 
	 * @param event
	 */
	@FXML
	void buttonLogOutPressed(ActionEvent event) {
		main.showLogin();
	}

	/**
	 * Methode wird beim drücken des Proceed-Buttons ausgeführt. Es wird überprüft
	 * in welchem Pane sich der aktuelle Task befindet. Passt die benötigte Höhe der
	 * Scrollbar an.
	 * 
	 * @param event
	 */
	@FXML
	void buttonProceedPressed(ActionEvent event) {

		if (activeLabel.getParent() == mansoryPaneToDo) {
			usedScrollBarHeight_ToDo = usedScrollBarHeight_ToDo - 90;
			mansoryPaneToDo.getChildren().remove(activeLabel);
			mansoryPaneDoing.getChildren().add(activeLabel);
		} else if (activeLabel.getParent() == mansoryPaneDoing) {
			usedScrollBarHeight_Doing = usedScrollBarHeight_Doing - 90;
			mansoryPaneDoing.getChildren().remove(activeLabel);
			mansoryPaneFinished.getChildren().add(activeLabel);
		} else if (activeLabel.getParent() == mansoryPaneFinished) {
			usedScrollBarHeight_Finished = usedScrollBarHeight_Finished - 90;
		}
	}

	/**
	 * Methode wird beim drücken des ProjectSelection-Buttons ausgeführt. Öffnet das
	 * Fenster mit der Projektübersicht.
	 * 
	 * @param event
	 */
	@FXML
	void buttonProjectselectionPressed(ActionEvent event) {
		main.showProjectList();
	}

	/**
	 * TBD
	 * 
	 * @param event
	 */
	@FXML
	void buttonReturnPressed(ActionEvent event) {
		if (activeLabel.getParent() == mansoryPaneToDo) {
			usedScrollBarHeight_ToDo = usedScrollBarHeight_ToDo - 90;

		} else if (activeLabel.getParent() == mansoryPaneDoing) {
			usedScrollBarHeight_Doing = usedScrollBarHeight_Doing - 90;
			mansoryPaneDoing.getChildren().remove(activeLabel);
			mansoryPaneToDo.getChildren().add(activeLabel);

		} else if (activeLabel.getParent() == mansoryPaneFinished) {
			usedScrollBarHeight_Finished = usedScrollBarHeight_Finished - 90;
			mansoryPaneFinished.getChildren().remove(activeLabel);
			mansoryPaneDoing.getChildren().add(activeLabel);
		}
	}

	/**
	 * TBD
	 * 
	 * @param event
	 */
	@FXML
	void buttonTagsPressed(ActionEvent event) {

	}

	/**
	 * Methode wird beim drücken des EditTask-Buttons ausgeführt. Der Button
	 * wechselt zwischen "editieren" und "speichern".
	 * 
	 * Wird der der Text geändert, wird der Name des Aktiven Tasks geändert.
	 * 
	 * @param event
	 */
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
						activeLabel.setText(textFieldTaskname.getText());
					}
				}
			});

			textFieldTaskname.editableProperty().set(true);
			this.buttonEditTaskNameIcon.setImage(new Image(getClass().getResourceAsStream("save.png")));
			textFieldTaskname.setStyle("-fx-background-color: white;");
		}
	}

	/**
	 * TBD
	 * 
	 * @param name
	 */
	void saveEnteredTaskname(String name) {
		// Nur zum Testen:
		labelActualAuthor.setText(name);
	}

	/**
	 * Methode wird beim Drücken des EditDescription-Button ausgeführt. Macht das
	 * Textfeld zur Task-Beschreibung editierbar, sofern es im Moment nicht
	 * editierbar ist. Ist es editierbar, wird es wieder gelocked.
	 * 
	 * Beim Klicken des Buttons wechselt das Icon entspechend seiner aktuellen
	 * Funktion.
	 * 
	 * @param event
	 */
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

	/**
	 * TBD
	 * 
	 * @param name
	 */
	void saveEnteredDescription(String name) {
		// Nur zum Testen:
		labelActualStatus.setText(name);
	}

	/**
	 * Methode wird beim Drücken des NewTask-Buttons ausgeführt. Führt die Methode
	 * createTask() aus und erhöht die benötigte Höhe im ToDo-Pane
	 * 
	 * @param event
	 */
	@FXML
	void buttonNewTaskPressed(ActionEvent event) {
		createTask();
		usedScrollBarHeight_ToDo = usedScrollBarHeight_ToDo + 90;

		main.log(anchorPaneMansory.getPrefHeight(), "anchorPaneMansory");
		main.log(mansoryPaneToDo.getPrefHeight(), "mansoryPaneToDo");

		checkScrollBarSpace();

	}

	/**
	 * Methode wird beim Drücken des AddTag-Buttons ausgeführt. Führt die Methode
	 * addTag(String name)
	 * 
	 * @param event
	 */
	@FXML
	void buttonAddTagPressed(ActionEvent event) {
		main.log("Button pressed", "Add Tag");
		if (!textFieldTags.getText().equals("")) {

			createTag(textFieldTags.getText());
		} else {
			main.log("Kein Text eingegeben!", "Add Tag");
		}
	}

	/**
	 * Methode erstellt einen neuen Tag mit dem übergebenen Namen und fügt ihn an
	 * passende Stelle im Tag-Pane an. Eine MouseEvent-Handle wird initialisiert
	 * 
	 * @param name
	 */
	public void createTag(String name) {
		Label lbl = new Label();

		this.mansoryPaneTags.setCellHeight(20);
		this.mansoryPaneTags.setCellWidth(40);

		lbl.setText(" " + name + " ");
		textFieldTags.setText("");
		lbl.setAlignment(Pos.CENTER);
		lbl.setStyle("-fx-background-color: #969696; -fx-background-radius: 15px; display:inline-block");
		mansoryPaneTags.setStyle("height:wrap-content");

		mansoryPaneTags.autosize();
		anchorPaneTaskInformation2.setPrefHeight(mansoryPaneTags.getHeight() + 130);// +95
		anchorPaneTaskInformation
				.setPrefHeight(400 + anchorPaneTaskInformation2.getHeight() + anchorPaneTaskInformation3.getHeight());
		mansoryPaneTags.getChildren().add(lbl);

		lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

			}
		});
	}

	/**
	 * Überprüft, ob noch genug Platz in den Spalten ist. Sollte nicht genug Platz
	 * sein, wird die Größte der Panes angepasst.
	 */
	public void checkScrollBarSpace() {
		if (mansoryPaneToDo.getPrefHeight() <= usedScrollBarHeight_ToDo) {

			mansoryPaneDoing.setPrefHeight(anchorPaneMansory.getPrefHeight() + 90);
			anchorPaneMansory.setPrefHeight(anchorPaneMansory.getPrefHeight() + 90);
		}
		if (mansoryPaneDoing.getPrefHeight() <= usedScrollBarHeight_Doing) {

			mansoryPaneDoing.setPrefHeight(anchorPaneMansory.getPrefHeight() + 90);
			anchorPaneMansory.setPrefHeight(anchorPaneMansory.getPrefHeight() + 90);
		}
		if (mansoryPaneToDo.getPrefHeight() <= usedScrollBarHeight_Finished) {

			mansoryPaneFinished.setPrefHeight(anchorPaneMansory.getPrefHeight() + 90);
			anchorPaneMansory.setPrefHeight(anchorPaneMansory.getPrefHeight() + 90);
		}
	}

	/**
	 * Methode erstellt einen neuen Task und fügt ihn am ToDo-Pane an. Eine
	 * MouseEvent-Handle wird initialisiert und das erstellte Label wird zum Aktiven
	 * Label. Die Buttons zum verschieben der Task werden sichtbar
	 */
	private void createTask() {
		this.taskCounter++;
		main.log("Add Task", "Button pressed");
		Label lbl = new Label();

		lbl.setId("Task " + taskCounter);
		lbl.setText("Task " + taskCounter);
		lbl.setPrefSize(200, 75);
		lbl.setMinSize(200, 75);
		lbl.setAlignment(Pos.CENTER);
		// lbl.setText(textFieldTaskname.getText());
		lbl.setStyle(
				"-fx-background-color: white; -fx-padding: -20px; -fx-background-radius: 5px; width:40pt; height:10pt; display:inline-block;");

		mansoryPaneToDo.getChildren().add(lbl);

		lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				buttonReturn.setVisible(true);
				buttonProceed.setVisible(true);
				activeLabel = lbl;
				main.log(lbl.getId());

				textFieldTaskname.setText(activeLabel.getText());
			}
		});
	}

	void getTaskInfoFromServer(String id) {
		/*
		 * textFieldTaskname.setText(String value); labelActualAuthor.setText(String
		 * value); labelActualStatus.setText(String value);
		 * textAreaDescription.setText(String value)
		 */
	}
}
