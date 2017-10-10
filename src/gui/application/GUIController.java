package gui.application;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.interfaces.RMI_Projekt;
import model.interfaces.RMI_Projektmanager;
import model.interfaces.RMI_Task;

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
	private JFXButton buttonSaveNewTask;

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

	private Label activeLabel = null;
	private List<String[]> taskList = new ArrayList<>();

	public String STATUS_TODO = "To Do";
	public String STATUS_DOING = "Doing";
	public String STATUS_FINISHED = "Finished";
	
	private int usedScrollBarHeight_ToDo;
	private int usedScrollBarHeight_Doing;
	private int usedScrollBarHeight_Finished;

	private Color selectedColor = new Color(0, 0, 1, 0);

	/**
	 * Konstruktor.
	 */
	public GUIController() {
		
	}

	/**
	 * Init-Methode:
	 * Quasi erweiterter Konstruktor, der in der Main aufgerufen wird, da bspw.
	 * KeyListener nicht im Konstruktor angelegt werden können.
	 */
	public void init() {
		
		// Initialisierung von KeyListenern etc.
		textFieldTaskname.editableProperty().set(false);
		textAreaDescription.editableProperty().set(false);

		// Button Init
		buttonReturn.setVisible(false);
		buttonProceed.setVisible(false);
		buttonEditTaskName.setVisible(false);
		buttonEditDescription.setVisible(false);
		buttonAddTag.setVisible(false);
		buttonAddComment.setVisible(false);

		// Kanban-Columns Init
		labelToDo.setText(STATUS_TODO);
		labelDoing.setText(STATUS_DOING);
		labelFinished.setText(STATUS_FINISHED);
		mansoryPaneToDo.setAlignment(Pos.TOP_CENTER);
		mansoryPaneToDo.setSpacing(10);
		mansoryPaneDoing.setAlignment(Pos.TOP_CENTER);
		mansoryPaneDoing.setSpacing(10);
		mansoryPaneFinished.setAlignment(Pos.TOP_CENTER);
		mansoryPaneFinished.setSpacing(10);

		// EventHandler Init

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
		
		// Funktionalität - Laden & Setzen der Informationen
		
		labelUser.setText("    " + main.user.getNachname() + ", " + main.user.getVorname());
		labelProjectname.setText(main.aktuellesProjekt);
		
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(null);
			RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
			labelProjectinformation.setText(projekt.getBeschreibung());
			taskList = projekt.taskListe();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		for(String[] task : taskList)
		{
			Label lbl = new Label(task[0]);
			lbl.setPrefSize(200, 75);
			lbl.setMinSize(200, 75);
			lbl.setAlignment(Pos.CENTER);
			lbl.setStyle("-fx-background-color: white; -fx-padding: -20px; -fx-background-radius: 5px; width:40pt; height:10pt; display:inline-block;");

			if(task[1].equals(STATUS_TODO))
			{
				mansoryPaneToDo.getChildren().add(lbl);
				usedScrollBarHeight_ToDo = usedScrollBarHeight_ToDo + 90;
				checkScrollBarSpace();
			}
			else if(task[1].equals(STATUS_DOING))
			{
				mansoryPaneDoing.getChildren().add(lbl);
				usedScrollBarHeight_Doing = usedScrollBarHeight_Doing + 90;
				checkScrollBarSpace();
			}
			else if(task[1].equals(STATUS_FINISHED))
			{
				mansoryPaneFinished.getChildren().add(lbl);
				usedScrollBarHeight_Finished = usedScrollBarHeight_Finished + 90;
				checkScrollBarSpace();
			}
		}
	}

	/**
	 * Setter-Methode.
	 * 
	 * @param main - Die Main-App die gesetzt werden soll.
	 */
	public void setMainApp(Main main) {
		this.main = main;
	}
	
	/**
	 * Logout des Users und Weiterleitung an das Login-Fenster.
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
	void buttonLogOutPressed(ActionEvent event) {
		main.showLogin();
	}
	
	/**
	 * Öffnet das Fenster mit der Projektübersicht.
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
	void buttonProjectselectionPressed(ActionEvent event) {
		
		main.showProjectList();
	}
	
	/**
	 * Aktiviert die Textfelder, die zum Erstellen eines neuen Tasks ausgefüllt werden müssen und
	 * setzt den Button zum Speichern des Tasks sichtbar.
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
	void buttonNewTaskPressed(ActionEvent event) {
		
		buttonSaveNewTask.setVisible(true);
		textFieldTaskname.clear();
		textFieldTaskname.editableProperty().set(true);
		textFieldTaskname.setStyle("-fx-background-color: white;");
		buttonEditDescription.setVisible(false);
		textAreaDescription.clear();
		textAreaDescription.editableProperty().set(true);
		textAreaDescription.setStyle("text-area-background: white;");
		buttonAddComment.setVisible(false);
		textFieldComments.clear();
		buttonAddTag.setVisible(false);
		textFieldTags.clear();
		labelActualAuthor.setText("");
		labelActualStatus.setText("");
	}

	/**
	 * Speichert den neuen Task im Projekt auf dem Server.
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
	void buttonSaveNewTaskPressed(ActionEvent event) {
		
		boolean itWorked = false;
		
		try {

			Registry registry = LocateRegistry.getRegistry(null);
			RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
			itWorked = projekt.taskHinzufügen(textFieldTaskname.getText(), textAreaDescription.getText(), main.user);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (itWorked) {
			
			createTask(textFieldTaskname.getText());
			usedScrollBarHeight_ToDo = usedScrollBarHeight_ToDo + 90;
			checkScrollBarSpace();
			
			buttonSaveNewTask.setVisible(false);
			textFieldTaskname.editableProperty().set(false);
			textFieldTaskname.clear();
			textFieldTaskname.setStyle("-fx-background-color: orange;");
			textAreaDescription.editableProperty().set(false);
			textAreaDescription.clear();
			textAreaDescription.setStyle("text-area-background: orange;");
			
			labelBenachrichtigung.setText("");
			
		}
		else {
			
			main.log("Fehler", "Save new Task");
			labelBenachrichtigung.setText("Fehler beim Anlegen des Tasks!");
		}
	}
	
	/**
	 * Methode erstellt einen neuen Task und fügt ihn am ToDo-Pane an.
	 * Ein MouseEvent-Handler wird initialisiert und das erstellte Label wird zum aktiven Label.
	 * Die Buttons zum verschieben der Task werden sichtbar.
	 * 
	 * @param taskname - Der Name des Tasks.
	 */
	private void createTask(String taskname) {

		main.log("Add Task", "Button pressed");

		// Neues Label wird erzeugt und konfiguriert
		Label lbl = new Label(taskname);
		lbl.setPrefSize(200, 75);
		lbl.setMinSize(200, 75);
		lbl.setAlignment(Pos.CENTER);
		lbl.setStyle("-fx-background-color: white; -fx-padding: -20px; -fx-background-radius: 5px; width:40pt; height:10pt; display:inline-block;");

		mansoryPaneToDo.getChildren().add(lbl);
		taskList.add(new String[] {lbl.getText(), STATUS_TODO});

		// Erzeugt Eventhandler für das erzeugte Label
		lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			/**
			 * Eventhandler für das Label. 
			 * Wenn kein Label ausgewählt ist wird das ausgewählte Label aktiv.
			 * Ist ein Label und ein anderes Label wird angeklickt, wird das angeklickte Label aktiv.
			 * Wird das ausgewählte Label angeklickt, ist danach kein Label aktiv.
			 */
			@Override
			public void handle(MouseEvent e) {

				if (activeLabel != null) {
					activeLabel.setStyle("-fx-background-color: white"
							+ "; -fx-padding: -20px; -fx-background-radius: 5px; width:40pt; height:10pt; display:inline-block;");
				}

				if (activeLabel != lbl) {
					buttonReturn.setVisible(true);
					buttonProceed.setVisible(true);
					buttonEditDescription.setVisible(true);
					buttonAddComment.setVisible(true);
					buttonAddTag.setVisible(true);
					activeLabel = lbl;
					main.log(lbl.getId());
					lbl.setStyle("-fx-background-color: white"
							+ "; -fx-border-width: 2; -fx-border-color: orange; -fx-padding: -20px; -fx-background-radius: 5px; width:40pt; height:10pt; display:inline-block;");

				}
				else if (activeLabel == lbl) {
					activeLabel = null;
					lbl.setStyle(" -fx-background-color: white"
							+ "; -fx-padding: -20px; -fx-background-radius: 5px; width:40pt; height:10pt; display:inline-block;");
					buttonProceed.setVisible(false);
					buttonReturn.setVisible(false);
					buttonEditDescription.setVisible(false);
					buttonAddComment.setVisible(false);
					buttonAddTag.setVisible(false);
				}
				
				showTaskInfo();
			}
		});
	}
	
	/**
	 * Überprüft, ob noch genug Platz in den Spalten ist. 
	 * Sollte nicht genug Platz sein, wird die Größe der Panes angepasst.
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
	 * Zeigt die Informationen des aktiven Tasks an.
	 */
	public void showTaskInfo() {
		
		if (activeLabel != null) {
			
			try {

				Registry registry = LocateRegistry.getRegistry(null);
				RMI_Task task = (RMI_Task) registry.lookup(activeLabel.getText());
				
				textFieldTaskname.setText(activeLabel.getText());
				labelActualAuthor.setText(task.getLetzterNutzer().getNachname() + ", " + task.getLetzterNutzer().getVorname());
				textAreaDescription.setText(task.getBeschreibung());
				if (activeLabel.getParent() == mansoryPaneToDo) {
					labelActualStatus.setText(STATUS_TODO);
				} else if (activeLabel.getParent() == mansoryPaneDoing) {
					labelActualStatus.setText(STATUS_DOING);
				} else if (activeLabel.getParent() == mansoryPaneFinished) {
					labelActualStatus.setText(STATUS_FINISHED);
				}
				
				ArrayList<String> kommentare = task.getKommentar();
				for(String kommentar : kommentare)
				{
					createComment(kommentar);
				}
				
				ArrayList<String> tags = task.getTags();
				for(String tag : tags)
				{
					createTag(tag);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			textFieldTaskname.clear();
			labelActualAuthor.setText("");
			textAreaDescription.clear();
			labelActualStatus.setText("");
		}
	}
	
	/**
	 * Legt ein Label für einen Kommentar in der GUI an.
	 * 
	 * @param name - Der Name des Tags.
	 */
	private void createComment(String name) {
		Label lbl = new Label();

		this.mansoryPaneComments.setCellHeight(10);
		this.mansoryPaneComments.setCellWidth(290);
		this.mansoryPaneComments.setMaxWidth(300);
		lbl.setMaxWidth(290);

		lbl.setWrapText(true);

		name = formatComment(name, "-\n\t", 25);

		int additionalLength = (name.length() / 25 + 2) * 20;

		lbl.setText(name);
		textFieldComments.setText("");
		lbl.setAlignment(Pos.TOP_LEFT);
		lbl.setStyle("display:inline-block; -fx-padding: 0; -fx-background-color: orange;");
		mansoryPaneComments.getChildren().add(lbl);

		mansoryPaneComments.setPrefHeight(mansoryPaneComments.getHeight() + additionalLength);
		anchorPaneTaskInformation3.setPrefHeight(mansoryPaneComments.getPrefHeight() + 220);// +95

		anchorPaneTaskInformation.setPrefHeight(anchorPaneTaskInformation1.getHeight()
				+ anchorPaneTaskInformation2.getHeight() + anchorPaneTaskInformation3.getHeight());

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
	 * Legt einen neuen Tag an passende Stelle im Tag-Pane an. 
	 * 
	 * @param name - Der Name des Tags.
	 */
	private void createTag(String name) {
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
	 * Formatierung des Kommentars.
	 * 
	 * @param text - Der Ausgangstext des Kommentars.
	 * @param insert
	 * @param period
	 * 
	 * @return Der formatierte Kommentar.
	 */
	private static String formatComment(String text, String insert, int period) {
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
	 * Je nach Position des aktive Labels befindet wir es nach "rechts" bzw. in Prozessrichtung verschoben.
	 * Passt die benötigte Höhe der Scrollbar an.
	 * 
	 * @param event - Das Action-Event
	 */
	@FXML
	void buttonProceedPressed(ActionEvent event) {

		boolean itWorked = false;
		
		try {

			Registry registry = LocateRegistry.getRegistry(null);
			RMI_Task task = (RMI_Task) registry.lookup(activeLabel.getText());
			itWorked = task.taskNachVorneVerschieben();
			if (itWorked)
			{
				RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
				itWorked = projekt.speichereProjekt();
			}

		} catch (Exception e) {
			e.printStackTrace();
			labelBenachrichtigung.setText("Fehler beim Verschieben des Tasks!");
		}
		
		if (itWorked)
		{
			if (activeLabel.getParent() == mansoryPaneToDo) {
				mansoryPaneToDo.getChildren().remove(activeLabel);
				usedScrollBarHeight_ToDo = usedScrollBarHeight_ToDo - 90;
				mansoryPaneDoing.getChildren().add(activeLabel);
				usedScrollBarHeight_Doing = usedScrollBarHeight_Doing + 90;
			} else if (activeLabel.getParent() == mansoryPaneDoing) {
				mansoryPaneDoing.getChildren().remove(activeLabel);
				usedScrollBarHeight_Doing = usedScrollBarHeight_Doing - 90;
				mansoryPaneFinished.getChildren().add(activeLabel);
				usedScrollBarHeight_Finished = usedScrollBarHeight_Finished + 90;
			}
			
			labelBenachrichtigung.setText("");
		}
		else {
			
			main.log("Fehler", "Proceed Task");
			labelBenachrichtigung.setText("Task konnte nicht verschoben werden.");
		}
		
		showTaskInfo();
	}

	/**
	 * Je nach Position des aktive Labels befindet wir es nach "links" bzw. entgegen der Prozessrichtung verschoben.
	 * Passt die benötigte Höhe der Scrollbar an.
	 * 
	 * @param event
	 */
	@FXML
	void buttonReturnPressed(ActionEvent event) {
		
		boolean itWorked = false;
		
		try {

			Registry registry = LocateRegistry.getRegistry(null);
			RMI_Task task = (RMI_Task) registry.lookup(activeLabel.getText());
			itWorked = task.taskNachHintenVerschieben();
			if (itWorked)
			{
				RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
				itWorked = projekt.speichereProjekt();
			}

		} catch (Exception e) {
			e.printStackTrace();
			labelBenachrichtigung.setText("Fehler beim Verschieben des Tasks!");
		}
		
		if (itWorked)
		{
			if (activeLabel.getParent() == mansoryPaneDoing) {
				mansoryPaneDoing.getChildren().remove(activeLabel);
				usedScrollBarHeight_Doing = usedScrollBarHeight_Doing - 90;
				mansoryPaneToDo.getChildren().add(activeLabel);
				usedScrollBarHeight_ToDo = usedScrollBarHeight_ToDo + 90;
			} else if (activeLabel.getParent() == mansoryPaneFinished) {
				mansoryPaneFinished.getChildren().remove(activeLabel);
				usedScrollBarHeight_Finished = usedScrollBarHeight_Finished - 90;
				mansoryPaneDoing.getChildren().add(activeLabel);
				usedScrollBarHeight_Doing = usedScrollBarHeight_Doing + 90;
			}
			
			labelBenachrichtigung.setText("");
		}
		else {
			
			main.log("Fehler", "Return Task");
			labelBenachrichtigung.setText("Task konnte nicht verschoben werden.");
		}
		
		showTaskInfo();
	}

	/**
	 * Wenn eine Farbe ausgewählt wurde und ein Label aktiv ist,
	 * wird die Hintergrundfarbe des Labels dementspreched geändert.
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
	void ColorPickerSelectionChanged(ActionEvent event) {

		if (colorPicker != null) {
			selectedColor = colorPicker.getValue();
		}
		if (activeLabel != null) {
			activeLabel.setBackground(new Background(
					new BackgroundFill(Paint.valueOf(selectedColor.toString()), CornerRadii.EMPTY, Insets.EMPTY)));
		}
	}

	/**
	 * Aktiviert das Textfeld für die Task-Beschreibung, sodass diese verändert werden kann.
	 * Der Button wechselt zwischen "editieren" und "speichern".
	 * 
	 * @param event - Das Action Event.
	 */
	@FXML
	void buttonEditDescriptionPressed(ActionEvent event) {
		
		main.log("Button pressed", "Edit Description");

		if (textAreaDescription.editableProperty().get()) {
			textAreaDescription.editableProperty().set(false);
			saveEnteredDescription(textAreaDescription.getText());
			this.buttonEditDescriptionIcon.setImage(new Image(getClass().getResourceAsStream("compose.png")));
			textAreaDescription.setStyle("text-area-background: orange;");

		} else {
			
			this.textAreaDescription.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent ke) {
					if (ke.getCode().equals(KeyCode.ENTER)) {
						saveEnteredTaskname(textAreaDescription.getText());
						textAreaDescription.editableProperty().set(false);
						buttonEditDescriptionIcon.setImage(new Image(getClass().getResourceAsStream("compose.png")));
						textAreaDescription.setStyle("-fx-background-color: orange;");

					}
				}
			});

			textAreaDescription.editableProperty().set(true);
			textAreaDescription.requestFocus();
			this.buttonEditDescriptionIcon.setImage(new Image(getClass().getResourceAsStream("save.png")));
			textAreaDescription.setStyle("text-area-background: white;");

		}

	}

	/**
	 * Ändert die Beschreibung des aktiven Tasks. 
	 * 
	 * @param name - Die neue Beschreibung.
	 */
	void saveEnteredDescription(String name) {
		
		boolean itWorked = false;
		
		try {

			Registry registry = LocateRegistry.getRegistry(null);
			RMI_Task task = (RMI_Task) registry.lookup(activeLabel.getText());
			task.ändereBeschreibung(textAreaDescription.getText(), main.user);
			RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
			itWorked = projekt.speichereProjekt();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(itWorked)
		{
			showTaskInfo();
			labelBenachrichtigung.setText("");
		}
		else
		{
			main.log("Fehler", "Edit Description");
			labelBenachrichtigung.setText("Beschreibung konnte nicht geändert werden.");
		}
	}

	/**
	 * Ruft die Funktion createComment auf wenn das Textfeld nicht leer ist
	 * 
	 * @param event
	 */
	@FXML
	void buttonAddCommentPressed(ActionEvent event) {
		
		main.log("Button pressed", "Add Comment");
		
		if (!textFieldComments.getText().equals("")) {

			boolean itWorked = false;
			
			try {

				Registry registry = LocateRegistry.getRegistry(null);
				RMI_Task task = (RMI_Task) registry.lookup(activeLabel.getText());
				task.fügeKommentarHinzu(textFieldComments.getText(), main.user);
				RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
				itWorked = projekt.speichereProjekt();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(itWorked)
			{
				showTaskInfo();
				labelBenachrichtigung.setText("");
			}
			else
			{
				main.log("Fehler", "Add Comment");
				labelBenachrichtigung.setText("Kommentar konnte nicht hinzugefügt werden.");
			}
			
		} else {
			
			main.log("Fehler", "Add Comment");
			labelBenachrichtigung.setText("Kein Text eingegeben!");
		}
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
			
			boolean itWorked = false;
			
			try {

				Registry registry = LocateRegistry.getRegistry(null);
				RMI_Task task = (RMI_Task) registry.lookup(activeLabel.getText());
				task.fügeTagHinzu(textFieldTags.getText(), main.user);
				RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
				itWorked = projekt.speichereProjekt();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(itWorked)
			{
				showTaskInfo();
				labelBenachrichtigung.setText("");
			}
			else
			{
				main.log("Fehler", "Add Tag");
				labelBenachrichtigung.setText("Tag konnte nicht hinzugefügt werden.");
			}

		} else {
			
			main.log("Fehler", "Add Tag");
			labelBenachrichtigung.setText("Kein Text eingegeben!");
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
	 * TBD
	 * 
	 * @param event
	 */
	@FXML
	void buttonTagsPressed(ActionEvent event) {
		
	}
	
	/**
	 * Aktiviert das Textfeld für den Task-Name, sodass diese verändert werden kann.
	 * Diese Methode wird momentan nicht verwendet, da der Taskname nicht veränderbar ist (Button ist nicht sichtbar).
	 * Der Button wechselt zwischen "editieren" und "speichern".
	 * 
	 * @param event - Das Action-Event.
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
							textFieldTaskname.setText(activeLabel.getText());
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
	 * Speichert den geänderten Tasknamen.
	 * 
	 * @param name - Der neue Projektname.
	 */
	void saveEnteredTaskname(String name) {
		// nicht implementiert, da der Taskname momentan nicht veränderbar ist.
	}

	void guilog(String text) {
		labelBenachrichtigung.setText(text);
	}

	void guilog(int text) {
		labelBenachrichtigung.setText("" + text);
	}
}
