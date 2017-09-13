package gui.application;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import model.interfaces.RMI_Projektmanager;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GUIController {

	@FXML
	private Label labelProjectname;

	@FXML
	private Label labelProjectinformation;

	@FXML
	private Label labelUser;

	@FXML
	private Label labelBenachrichtigung;

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
	private JFXTextArea textAreaDescription;

	@FXML
	private JFXListView<?> listViewTags;

	@FXML
	private Label labelComments;

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
	private JFXMasonryPane mansoryPaneComments;

	@FXML
	private AnchorPane anchorPaneTaskInformation;

	private Main main;

	@FXML
	private JFXColorPicker colorPicker; // https://github.com/jfoenixadmin/JFoenix/issues/408

	private GuiTask activeLabel = null;

	// private ArrayList<String[]> labelList;

	private ArrayList<Label> taskList;

	private static int usedScrollBarHeight_ToDo;
	private static int usedScrollBarHeight_Doing;
	private static int usedScrollBarHeight_Finished;

	private static int taskCounter;

	private String usernameVN = "";
	private String usernameNV = "";

	private Color selectedColor = new Color(0, 0, 1, 0);

	public GUIController() {
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
		buttonEditTaskName.setVisible(false);
		buttonEditDescription.setVisible(false);

		// Kanban columns Init
		mansoryPaneToDo.setAlignment(Pos.TOP_CENTER);
		mansoryPaneToDo.setSpacing(10);
		mansoryPaneDoing.setAlignment(Pos.TOP_CENTER);
		mansoryPaneDoing.setSpacing(10);
		mansoryPaneFinished.setAlignment(Pos.TOP_CENTER);
		mansoryPaneFinished.setSpacing(10);

		if (main.getLDAPConnection()) {
			usernameVN = main.user.getVorname() + " " + main.user.getNachname();
			usernameNV = main.user.getNachname() + ", " + main.user.getVorname();
		} else {
			guilog("Keine Verbindung zum LDAP Server");
			usernameVN = "Hans Dampf";
			usernameNV = "Dampf, Hans";
		}

		labelUser.setText("    " + usernameNV);
		labelProjectname.setText("    Testprojekt");
		labelProjectinformation.setText("    Erstelldatum: 07.07.2017, Ersteller: Fiete Schmidt");

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

		this.buttonAddComment.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					buttonAddCommentPressed(null);
				}
			}
		});

		this.textFieldComments.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					buttonAddCommentPressed(null);
				}
			}
		});

	}

	public void setMainApp(Main main) {
		this.main = main;
	}

	/**
	 * Wenn eine Farbe ausgewählt wurde und ein Label aktiv ist wird die
	 * Hintergrundfarbe des Labels dementspreched geändert
	 * 
	 * @param event
	 */
	@FXML
	void ColorPickerSelectionChanged(ActionEvent event) {

		if (colorPicker != null) {
			selectedColor = colorPicker.getValue();
		}
		if (activeLabel != null) {
			activeLabel.setBackground(new Background(
					new BackgroundFill(Paint.valueOf(selectedColor.toString()), CornerRadii.EMPTY, Insets.EMPTY)));
			activeLabel.setColor(selectedColor);

		}

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
	 * Methode wird beim drï¿½cken des Proceed-Buttons ausgefï¿½hrt. Es wird
	 * ï¿½berprï¿½ft in welchem Pane sich der aktuelle Task befindet. Passt die
	 * benï¿½tigte Hï¿½he der Scrollbar an.
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
		showTaskInfo();
	}

	/**
	 * Methode wird beim drï¿½cken des ProjectSelection-Buttons ausgefï¿½hrt.
	 * ï¿½ffnet das Fenster mit der Projektï¿½bersicht.
	 * 
	 * @param event
	 */
	@FXML
	void buttonProjectselectionPressed(ActionEvent event) {
		try {

			Registry registry = LocateRegistry.getRegistry(null);
			RMI_Projektmanager manager = (RMI_Projektmanager) registry.lookup("manager");
			main.projektliste = manager.ladeProjekte(main.user.getNutzername());

		} catch (Exception e) {
			e.printStackTrace();
		}
		main.showProjectList();
	}

	/**
	 * Je nach dem wo sich das aktive Label befindet wir es nach "links"/ entgegen
	 * der Richtung verschoben
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
		showTaskInfo();
	}

	/**
	 * Ruft die Methode createMoreTags auf
	 * 
	 * @param event
	 */
	@FXML
	void buttonTagsPressed(ActionEvent event) {
		createMoreTags(25);
	}

	/**
	 * Methode wird beim drï¿½cken des EditTask-Buttons ausgefï¿½hrt. Der Button
	 * wechselt zwischen "editieren" und "speichern".
	 * 
	 * Wird der der Text geï¿½ndert, wird der Name des Aktiven Tasks geï¿½ndert.
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

					}
				}
			});

			this.textFieldTaskname.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (!newValue) {
						textFieldTaskname.editableProperty().set(false);
						buttonEditTaskNameIcon.setImage(new Image(getClass().getResourceAsStream("compose.png")));
						textFieldTaskname.setStyle("-fx-background-color: orange;");
						if (activeLabel != null) {
							textFieldTaskname.setText(activeLabel.getName());
						}
					}
				}
			});

			textFieldTaskname.editableProperty().set(true);
			textFieldTaskname.requestFocus();
			this.buttonEditTaskNameIcon.setImage(new Image(getClass().getResourceAsStream("save.png")));
			textFieldTaskname.setStyle("-fx-background-color: white;");
		}
	}

	/**
	 * Ändert den Text des aktiven Labels
	 * 
	 * @param name
	 */
	void saveEnteredTaskname(String name) {
		activeLabel.setName(textFieldTaskname.getText());
		showTaskInfo();
	}

	/**
	 * Methode wird beim Drï¿½cken des EditDescription-Button ausgefï¿½hrt. Macht
	 * das Textfeld zur Task-Beschreibung editierbar, sofern es im Moment nicht
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
			/*
			 * Changelistener der aktiv wird wenn textarea den fokus verliert funktioniert
			 * nicht, obwohl genau so wie bei editTaskname......
			 * 
			 * this.textAreaDescription.focusedProperty().addListener(new
			 * ChangeListener<Boolean>() {
			 * 
			 * @Override public void changed(ObservableValue<? extends Boolean> observable,
			 * Boolean oldValue, Boolean newValue) { if(!newValue) {
			 * textAreaDescription.editableProperty().set(false);
			 * buttonEditDescriptionIcon.setImage(new
			 * Image(getClass().getResourceAsStream("compose.png")));
			 * textAreaDescription.setStyle("-fx-background-color: orange;"); if(activeLabel
			 * != null) { textAreaDescription.setText(activeLabel.getDescription()); } } }
			 * });
			 */

			textAreaDescription.editableProperty().set(true);
			textAreaDescription.requestFocus();
			this.buttonEditDescriptionIcon.setImage(new Image(getClass().getResourceAsStream("save.png")));
			textAreaDescription.setStyle("text-area-background: white;");

		}

	}

	/**
	 * Ändert den Text der textArea auf dem aktivel Label
	 * 
	 * @param name
	 */
	void saveEnteredDescription(String name) {
		activeLabel.setDescription(textAreaDescription.getText());
		showTaskInfo();
	}

	/**
	 * Methode wird beim Drï¿½cken des NewTask-Buttons ausgefï¿½hrt. Fï¿½hrt die
	 * Methode createTask() aus und erhï¿½ht die benï¿½tigte Hï¿½he im ToDo-Pane
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
	 * Methode wird beim Drï¿½cken des AddTag-Buttons ausgefï¿½hrt. Fï¿½hrt die
	 * Methode addTag(String name)
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
	 * Methode erstellt einen neuen Tag mit dem ï¿½bergebenen Namen und fï¿½gt ihn
	 * an passende Stelle im Tag-Pane an. Eine MouseEvent-Handle wird initialisiert
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

		anchorPaneTaskInformation3
				.setLayoutY(anchorPaneTaskInformation1.getHeight() + anchorPaneTaskInformation2.getHeight());

		lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

			}
		});
	}

	/**
	 * Testmethode von Fiete
	 * 
	 * @param name
	 */
	public void createMoreTags(String[] name) {
		anchorPaneTaskInformation2.setPrefHeight(100 + name.length * 200);
		anchorPaneTaskInformation
				.setPrefHeight(400 + anchorPaneTaskInformation2.getHeight() + anchorPaneTaskInformation3.getHeight());
		for (int i = 0; i < name.length; i++) {
			createTag(name[i]);
		}
	}

	/**
	 * Erzeugt Zufällige Tags und zeigt diese an Testmethode
	 * 
	 * @param name
	 */
	public void createMoreTags(int name) {
		anchorPaneTaskInformation2.setPrefHeight(100 + name * 200);
		anchorPaneTaskInformation
				.setPrefHeight(400 + anchorPaneTaskInformation2.getHeight() + anchorPaneTaskInformation3.getHeight());
		for (int i = 0; i < name - 2; i++) {
			String tag = "";
			int b = (int) (2 + Math.random() * 10);
			for (int a = 0; a < b; a++) {
				tag = tag + (char) (97 + Math.random() * 26);

			}

			createTag(tag);
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		// TimeUnit.SECONDS.sleep(1);
		textFieldTags.setText("sdfsdfs");
		buttonAddTagPressed(null);
		textFieldTags.setText("");
		textFieldTags.setText("nsvjlkv");
		buttonAddTagPressed(null);
		textFieldTags.setText("");

		anchorPaneTaskInformation3
				.setLayoutY(anchorPaneTaskInformation1.getHeight() + anchorPaneTaskInformation2.getHeight() + 200);
		anchorPaneTaskInformation.setPrefHeight(anchorPaneTaskInformation.getPrefHeight() + 200);

	}

	/**
	 * Ruft die Funktion createComment auf wenn das Textfeld nicht leer ist
	 * 
	 * @param event
	 */
	@FXML
	void buttonAddCommentPressed(ActionEvent event) {
		main.log("Button pressed", "Comment");
		if (!textFieldComments.getText().equals("")) {

			createComment(textFieldComments.getText(), usernameVN);
		} else {
			main.log("Kein Text eingegeben!", "Comment");
		}
	}

	/**
	 * Testmethode von Fiete
	 * 
	 * @param name
	 */
	public void createMoreComment(String[] name) {
		anchorPaneTaskInformation3.setPrefHeight(100 + name.length * 200);
		anchorPaneTaskInformation
				.setPrefHeight(400 + anchorPaneTaskInformation2.getHeight() + anchorPaneTaskInformation3.getHeight());
		for (int i = 0; i < name.length; i++) {
			createComment(name[i], "Christian Hopp");
		}
	}

	/**
	 * Fügt einen Kommentar in der Liste hinzu, wenn der Button gedrückt wird
	 * 
	 * @param name
	 * @param author
	 */
	public void createComment(String name, String author) {
		Label lbl = new Label();

		this.mansoryPaneComments.setCellHeight(10);
		this.mansoryPaneComments.setCellWidth(290);
		this.mansoryPaneComments.setMaxWidth(300);
		lbl.setMaxWidth(290);

		lbl.setWrapText(true);

		name = insertPeriodically(name, "-\n\t", 25);
		System.out.println(name);

		int additionalLength = (name.length() / 25 + 2) * 20;

		lbl.setText(author + ":\n\n\t" + name);
		textFieldComments.setText("");
		lbl.setAlignment(Pos.TOP_LEFT);
		lbl.setStyle("display:inline-block; -fx-padding: 0; -fx-background-color: orange;");
		mansoryPaneComments.getChildren().add(lbl);

		mansoryPaneComments.setPrefHeight(mansoryPaneComments.getHeight() + additionalLength);
		anchorPaneTaskInformation3.setPrefHeight(mansoryPaneComments.getPrefHeight() + 220);// +95

		anchorPaneTaskInformation.setPrefHeight(anchorPaneTaskInformation1.getHeight()
				+ anchorPaneTaskInformation2.getHeight() + anchorPaneTaskInformation3.getHeight());
		/*
		 * System.out.println("AnchorPane1: "+anchorPaneTaskInformation1. getHeight());
		 * System.out.println("AnchorPane2: "+anchorPaneTaskInformation2. getHeight());
		 * System.out.println("AnchorPane3: "+anchorPaneTaskInformation3. getHeight());
		 * System.out.println("MasonaryPaneComment: "+mansoryPaneComments. getHeight());
		 * System.out.println("AnchorPaneRoot: "+anchorPaneTaskInformation.
		 * getHeight());
		 */

		lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				anchorPaneTaskInformation3.setPrefHeight(mansoryPaneComments.getPrefHeight() + 220);
				anchorPaneTaskInformation.setPrefHeight(anchorPaneTaskInformation1.getHeight()
						+ anchorPaneTaskInformation2.getHeight() + anchorPaneTaskInformation3.getHeight());
			}
		});

	}

	/**
	 * Formatierung des Kommentars
	 * 
	 * @param text
	 * @param insert
	 * @param period
	 * @return
	 */
	public static String insertPeriodically(String text, String insert, int period) {
		StringBuilder builder = new StringBuilder(
				// text.length() + 2 * (text.length()/period)+1);
				text.length() + insert.length() * (text.length() / period) + 1);

		int index = 0;
		String prefix = "";
		while (index < text.length()) {
			// Don't put the insert in the very first iteration.
			// This is easier than appending it *after* each substring
			builder.append(prefix);
			prefix = insert;
			builder.append(text.substring(index, Math.min(index + period, text.length())));
			index += period;
		}
		return builder.toString();
	}

	/**
	 * ï¿½berprï¿½ft, ob noch genug Platz in den Spalten ist. Sollte nicht genug
	 * Platz sein, wird die Grï¿½ï¿½te der Panes angepasst.
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
	 * Methode erstellt einen neuen Task und fï¿½gt ihn am ToDo-Pane an. Eine
	 * MouseEvent-Handle wird initialisiert und das erstellte Label wird zum Aktiven
	 * Label. Die Buttons zum verschieben der Task werden sichtbar
	 */
	private void createTask(String name) {
		taskCounter++;
		main.log("Add Task", "Button pressed");

		// Neues Label wird erzeugt und konfiguriert
		Label lbl = new Label();
		lbl.setId(name);
		lbl.setText(name);
		lbl.setPrefSize(200, 75);
		lbl.setMinSize(200, 75);
		lbl.setAlignment(Pos.CENTER);
		lbl.setStyle(
				"-fx-background-color: white; -fx-padding: -20px; -fx-background-radius: 5px; width:40pt; height:10pt; display:inline-block;");

		mansoryPaneToDo.getChildren().add(lbl);

		// Erzeugt Eventhandler für das erzeugte Label
		lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			/**
			 * Eventhandler für das Label.
			 * Wenn kein Label ausgewählt ist wird das ausgeählte Label aktiv. Ist ein Label
			 * und ein andere Label wird geklickt, wird das geklickte Label aktiv. Wird das
			 * ausgewählte Label angeklickt, ist danach kein Label aktiv.
			 * 
			 */
			@Override
			public void handle(MouseEvent e) {

				if (activeLabel != null) {
					activeLabel.setStyle(
							"-fx-background-color: white; -fx-padding: -20px; -fx-background-radius: 5px; width:40pt; height:10pt; display:inline-block;");
				}

				buttonReturn.setVisible(true);
				buttonProceed.setVisible(true);
				// activeLabel = lbl;
				main.log(lbl.getId());

				textFieldTaskname.setText(activeLabel.getText());

				lbl.setStyle(
						"-fx-border-width: 2; -fx-border-color: red; -fx-background-color: white; -fx-padding: -20px; -fx-background-radius: 5px; width:40pt; height:10pt; display:inline-block;");

			}
		});
		taskList.add(lbl);
	}

	/**
	 * Überladene Methode, ohne einen Namen angeben zu müssen
	 */
	private void createTask() {
		taskCounter++;
		main.log("Add Task", "Button pressed");
		GuiTask lbl = new GuiTask();

		lbl.setId("Task " + taskCounter);
		// lbl.setText("Task " + taskCounter);
		lbl.setName("Task " + taskCounter);
		lbl.setAuthor(usernameVN);
		lbl.setDescription("Dies ist eine Beschreibung");
		lbl.showText();
		lbl.setPrefSize(200, 75);
		lbl.setMinSize(200, 75);
		lbl.setAlignment(Pos.CENTER);
		// lbl.setText(textFieldTaskname.getText());
		lbl.setStyle("-fx-background-color:" + lbl.getColorString()
				+ "; -fx-padding: -20px; -fx-background-radius: 5px; width:40pt; height:10pt; display:inline-block;");

		mansoryPaneToDo.getChildren().add(lbl);

		lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				if (activeLabel != null) {
					activeLabel.setStyle("-fx-background-color:" + activeLabel.getColorString()
							+ "; -fx-padding: -20px; -fx-background-radius: 5px; width:40pt; height:10pt; display:inline-block;");
				}

				if (activeLabel != lbl) {
					buttonReturn.setVisible(true);
					buttonProceed.setVisible(true);
					buttonEditTaskName.setVisible(true);
					buttonEditDescription.setVisible(true);
					activeLabel = lbl;
					main.log(lbl.getId());
					lbl.setStyle("-fx-background-color:" + lbl.getColorString()
							+ "; -fx-border-width: 2; -fx-border-color: orange; -fx-padding: -20px; -fx-background-radius: 5px; width:40pt; height:10pt; display:inline-block;");

				}

				else if (activeLabel == lbl) {
					activeLabel = null;
					lbl.setStyle(" -fx-background-color:" + lbl.getColorString()
							+ "; -fx-padding: -20px; -fx-background-radius: 5px; width:40pt; height:10pt; display:inline-block;");
					buttonProceed.setVisible(false);
					buttonReturn.setVisible(false);
					buttonEditTaskName.setVisible(false);
					buttonEditDescription.setVisible(false);

				}
				showTaskInfo();
			}
		});
		showTaskInfo();
	}

	public void showTaskInfo() {
		if (activeLabel != null) {
			textFieldTaskname.setText(activeLabel.getName());
			labelActualAuthor.setText(activeLabel.getAuthor());
			textAreaDescription.setText(activeLabel.getDescription());
			if (activeLabel.getParent() == mansoryPaneToDo) {
				labelActualStatus.setText("To Do");
			} else if (activeLabel.getParent() == mansoryPaneDoing) {
				labelActualStatus.setText("Doing");
			} else if (activeLabel.getParent() == mansoryPaneFinished) {
				labelActualStatus.setText("Finished");
			}
			activeLabel.showText();
		} else {
			textFieldTaskname.setText(" ");
			labelActualAuthor.setText(" ");
			textAreaDescription.setText(" ");
			labelActualStatus.setText(" ");
		}
	}

	/**
	 * Ruft die Informationen eines Task ï¿½ber seine ID auf und schreibt sie
	 * 
	 * @param id
	 */
	void getTaskInfoFromServer(String id) {
		/*
		 * textFieldTaskname.setText(String value); labelActualAuthor.setText(String
		 * value); labelActualStatus.setText(String value);
		 * textAreaDescription.setText(String value)
		 */
	}

	void guilog(String text) {
		labelBenachrichtigung.setText(text);
	}

	void guilog(int text) {
		labelBenachrichtigung.setText("" + text);
	}
}
