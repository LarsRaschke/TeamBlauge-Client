package gui.application;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.User;
import model.interfaces.RMI_Projekt;
import model.interfaces.RMI_Task;

public class TaskFrameController {

	@FXML
	private JFXButton buttonLogOut;

	@FXML
	private JFXButton buttonBack;

	@FXML
	private Label labelUser;

	@FXML
	private JFXTextArea textAreaDescription;

	@FXML
	private JFXButton buttonEditDescription;

	@FXML
	private ImageView buttonEditDescriptionIcon;

	@FXML
	private Label labelTaskname;

	@FXML
	private Label labelStatus;
	
	@FXML
	private AnchorPane anchorPaneTags;
	
	@FXML
	private JFXTextField textFieldTags;
	
	@FXML
	private JFXButton buttonAddTag;
	
	@FXML
	private JFXMasonryPane mansoryPaneTags;
	
	@FXML
	private AnchorPane anchorPaneComments;

	@FXML
	private JFXMasonryPane mansoryPaneComments;

	@FXML
	private JFXTextField textFieldComments;

	@FXML
	private JFXButton buttonAddComment;
	
	@FXML
	private Label labelLastUser;

	@FXML
	private Label labelLastChange;
	
	@FXML
	private AnchorPane anchor;

	@FXML
	private JFXColorPicker colorPicker; // https://github.com/jfoenixadmin/JFoenix/issues/408

	private Main main;
	
	private Color selectedColor = new Color(0, 0, 1, 0);

	/**
	 * Konstruktor.
	 */
	public TaskFrameController() {
		
	}

	/**
	 * Init-Methode:
	 * Quasi erweiterter Konstruktor, der in der Main aufgerufen wird, da bspw.
	 * KeyListener nicht im Konstruktor angelegt werden können.
	 */
	public void init() {
		
		// Initialisierung von KeyListenern etc.
		textAreaDescription.editableProperty().set(false);

		// EventHandler Init

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
		labelTaskname.setText(main.aktuellerTask);
		
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(null);
			RMI_Task task = (RMI_Task) registry.lookup(main.aktuellerTask);
			
			User lastUser = task.getLetzterNutzer();
			ZonedDateTime letzteÄnderung = task.getLetzteAenderung();
			
			labelLastUser.setText(lastUser.getNachname() + ", " + lastUser.getVorname());
			labelLastChange.setText(letzteÄnderung.getDayOfWeek().toString() + ", " + 
					letzteÄnderung.getDayOfMonth() + "." + letzteÄnderung.getMonth().toString() + " " + letzteÄnderung.getYear());
			textAreaDescription.setText(task.getBeschreibung());
			labelStatus.setText(task.getStatusname());
			
			ArrayList<String> tagList = task.getTags();
			for(String tag : tagList)
			{
				this.createTag(tag);
			}
			
			ArrayList<String> commentList = task.getKommentar();
			for(String comment : commentList)
			{
				this.createComment(comment);
			}
			
			// TODO: Comments
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
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
	void buttonBackPressed(ActionEvent event) {
		
		main.showMainFrame();
	}
	
	/**
	 * Legt ein Label für einen Kommentar in der GUI an.
	 * 
	 * @param name - Der Name des Tags.
	 * @param user
	 */
	private void createComment(String name) {
		
		Label lbl = new Label();
		lbl.setPadding(new Insets(0, 5, 0, 5));

		this.mansoryPaneComments.setCellHeight(10);
		this.mansoryPaneComments.setCellWidth(270);
		
		lbl.setWrapText(true);
		lbl.setMaxWidth(270);

		name = formatComment(name, "-\n\t", 25);

		lbl.setText(name);
		System.out.println(lbl.getHeight());
		textFieldComments.setText("");
		lbl.setAlignment(Pos.CENTER);
		lbl.setStyle("display:inline-block; -fx-background-radius: 15px; -fx-background-color: #969696;");
		mansoryPaneComments.setStyle("height:wrap-content");
		
		mansoryPaneComments.autosize();
		mansoryPaneComments.getChildren().add(lbl);

		anchorPaneComments.setMinHeight(mansoryPaneComments.getHeight() + 300);
		if(anchor.getHeight() < 100 + anchorPaneComments.getHeight() + anchorPaneTags.getHeight())
		{
			anchor.setMinHeight(100 + anchorPaneComments.getHeight() + anchorPaneTags.getHeight());
		}
		
		lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

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
		lbl.setPadding(new Insets(0, 5, 0, 5));
		
		this.mansoryPaneTags.setCellHeight(20);
		this.mansoryPaneTags.setCellWidth(40);

		lbl.setText(name);
		textFieldTags.setText("");
		lbl.setAlignment(Pos.CENTER);
		lbl.setStyle("-fx-background-color: #969696; -fx-background-radius: 15px; display:inline-block");
		mansoryPaneTags.setStyle("height:wrap-content");

		mansoryPaneTags.autosize();
		mansoryPaneTags.getChildren().add(lbl);
		
		anchorPaneTags.setMinHeight(mansoryPaneTags.getHeight() + 135);
		AnchorPane.setTopAnchor(anchorPaneComments, anchorPaneTags.getHeight() + 70);
		if(anchor.getHeight() < 100 + anchorPaneComments.getHeight() + anchorPaneTags.getHeight())
		{
			anchor.setMinHeight(100 + anchorPaneComments.getHeight() + anchorPaneTags.getHeight());
		}
		
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
	 * @param user
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
		
		// TODO: Parse to int and save in Task, to be shown in MainFrame
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
			this.buttonEditDescriptionIcon.setImage(new Image(getClass().getResourceAsStream("../resources/Icons/compose.png")));
			textAreaDescription.setStyle("text-area-background: orange;");

		} else {
			
			this.textAreaDescription.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent ke) {
					if (ke.getCode().equals(KeyCode.ENTER)) {
						saveEnteredDescription(textAreaDescription.getText());
						textAreaDescription.editableProperty().set(false);
						buttonEditDescriptionIcon.setImage(new Image(getClass().getResourceAsStream("../resources/Icons/compose.png")));
						textAreaDescription.setStyle("-fx-background-color: orange;");

					}
				}
			});

			textAreaDescription.editableProperty().set(true);
			textAreaDescription.requestFocus();
			this.buttonEditDescriptionIcon.setImage(new Image(getClass().getResourceAsStream("../resources/Icons/save.png")));
			textAreaDescription.setStyle("text-area-background: white;");

		}

	}

	/**
	 * Ändert die Beschreibung des aktiven Tasks. 
	 * 
	 * @param name - Die neue Beschreibung.
	 */
	void saveEnteredDescription(String name) {
		
		try {

			Registry registry = LocateRegistry.getRegistry(null);
			RMI_Task task = (RMI_Task) registry.lookup(main.aktuellerTask);
			task.ändereBeschreibung(textAreaDescription.getText(), main.user);
			RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
			projekt.speichereProjekt();

		} catch (Exception e) {
			e.printStackTrace();
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

			try {

				Registry registry = LocateRegistry.getRegistry(null);
				RMI_Task task = (RMI_Task) registry.lookup(main.aktuellerTask);
				task.fügeKommentarHinzu(textFieldComments.getText(), main.user);
				RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
				projekt.speichereProjekt();
				
				this.createComment(textFieldComments.getText() + " | " + main.user.getNachname() + ", " + main.user.getVorname());

			} catch (Exception e) {
				e.printStackTrace();
			}
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
			
			try {

				Registry registry = LocateRegistry.getRegistry(null);
				RMI_Task task = (RMI_Task) registry.lookup(main.aktuellerTask);
				task.fügeTagHinzu(textFieldTags.getText(), main.user);
				RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
				projekt.speichereProjekt();
				
				this.createTag(textFieldTags.getText());

			} catch (Exception e) {
				e.printStackTrace();
			}

		} 
	}
	
}
