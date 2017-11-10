package gui.application;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.User;
import model.interfaces.RMI_Projekt;
import model.interfaces.RMI_Task;

public class GUIController {

	@FXML
	private JFXButton buttonLogOut;

	@FXML
	private JFXButton buttonNewTask;
	
	@FXML
	private JFXButton buttonRefresh;

	@FXML
	private JFXButton buttonProceed;

	@FXML
	private JFXButton buttonReturn;
	
	@FXML
	private JFXButton buttonProjectselection;

	@FXML
	private Label labelUser;
	
	@FXML
	private Label labelProjectname;

	@FXML
	private Label labelBenachrichtigung;
	
	@FXML
	private JFXButton buttonMore;
	
	@FXML
	private JFXButton buttonSaveNewTask;

	@FXML
	private JFXTextField textFieldTaskname;

	@FXML
	private JFXTextArea textAreaDescription;
	
	@FXML
	private Label labelLastChange;
	
	@FXML
	private TableView<?> tableViewKanbanBoard;
	
	@FXML
	private Label labelLastUser;
	
	@FXML
	private AnchorPane anchor;
	
	@FXML
	private GridPane gridKanban;

	private Main main;

	@FXML
	private JFXColorPicker colorPicker; // https://github.com/jfoenixadmin/JFoenix/issues/408

	private Label activeLabel = null;
	
	public List<String> statusliste = new ArrayList<>();

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
		buttonMore.setVisible(false);
		buttonSaveNewTask.setVisible(false);
		
		buttonRefresh.setStyle("-fx-background-color: transparent;");

		// EventHandler Init

		((Scene) labelProjectname.getScene()).setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ESCAPE)) {
					main.showLogin();
				}
			}
		});
		
		// Funktionalität - Laden & Setzen der Informationen
		
		labelUser.setText(main.user.getNachname() + ", " + main.user.getVorname());
		labelProjectname.setText(main.aktuellesProjekt);
		
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(null);
			RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
			statusliste = projekt.statusListe();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		// Init Kanban Columns
		
		while( this.getColumnCount(gridKanban) < statusliste.size()) {
			
			anchor.setMinWidth(anchor.getMinWidth() + 260);
			
			Node[] nodes = new Node[2];
			AnchorPane pane = new AnchorPane();
			pane.setPrefWidth(250);
			pane.setMinWidth(250);
			pane.setStyle("-fx-background-color: #737373");
			VBox box = new VBox();
			box.setPrefWidth(250);
			box.setMinWidth(250);
			box.setAlignment(Pos.TOP_CENTER);
			box.setStyle("-fx-background-color: #737373");
			nodes[0] = pane;
			nodes[1] = box;
			
			gridKanban.addColumn(this.getColumnCount(gridKanban), nodes);
		}
		
		for (Node node : gridKanban.getChildren()) {
			
			if(GridPane.getRowIndex(node) == null || GridPane.getRowIndex(node) == 0) {
				
				int col = 0;
				if(GridPane.getColumnIndex(node) != null)
				{
					col = GridPane.getColumnIndex(node);
				}
				
				AnchorPane pane = (AnchorPane) node;
				
				Label label = new Label("");
				label.setAlignment(Pos.CENTER);
				label.setStyle("-fx-font-size: 17px; -fx-font-family: \"Candara\"; -fx-font-weight: bold;");
				label.setText(statusliste.get(col));
				pane.getChildren().add(label);
				
				AnchorPane.setBottomAnchor(label, 0.0);
				AnchorPane.setTopAnchor(label, 0.0);
				AnchorPane.setRightAnchor(label, 0.0);
				AnchorPane.setLeftAnchor(label, 0.0);
			}
		}
		
		// Funktionalität - Laden & Setzen der Informationen
		
		List<String[]> taskList = new ArrayList<>();
		
		try {
			registry = LocateRegistry.getRegistry(null);
			RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
			taskList = projekt.taskListe();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		for (String[] task : taskList) {
			
			for( int cnt = 0; cnt < statusliste.size(); cnt++) {
				
				if(task[1].equals(statusliste.get(cnt))) {
					
					// Neues Label wird erzeugt und konfiguriert
					Label lbl = new Label(task[0]);
					lbl.setPrefSize(200, 75);
					lbl.setMinSize(200, 75);
					lbl.setAlignment(Pos.CENTER);
					lbl.setStyle("-fx-background-color: white; -fx-padding: -20px; -fx-background-radius: 5px; width:40pt; height:10pt; display:inline-block;");
					this.mouseHandlerLabel(lbl);
					
					VBox box = (VBox) this.getNodeFromGridPane(gridKanban, cnt, 1);
					box.getChildren().add(lbl);
				}
			}
		}
		
		this.checkScrollBarSpace();
		
	}

	/**
	 * Counts the number of columns in a GridPane.
	 * 
	 * @param pane - The gridPane
	 * 
	 * @return The number of columns.
	 */
	private int getColumnCount(GridPane pane) {
		
        int numCols = pane.getColumnConstraints().size();
        
        for (Node child : pane.getChildren()) {
            if (child.isManaged()) {
                Integer colIndex = GridPane.getColumnIndex(child);
                if(colIndex != null){
                	numCols = Math.max(numCols, colIndex + 1);
                }
            }
        }
        
        return numCols;
    }
	
	/**
	 * Gets the node at a specific cell in a gridpane.
	 * 
	 * @param gridPane - The gridpane.
	 * @param col - The column index.
	 * @param row - The row index.
	 * 
	 * @return The node at the cell.
	 */
	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
		
	    for (Node node : gridPane.getChildren()) {
	    	
	    	int colIndex = 0;
	    	if(GridPane.getColumnIndex(node) != null)
	    	{
	    		colIndex = GridPane.getColumnIndex(node);
	    	}
	    	
	    	int rowIndex = 0;
	    	if(GridPane.getRowIndex(node) != null)
	    	{
	    		rowIndex = GridPane.getRowIndex(node);
	    	}
	    	
	        if (colIndex == col && rowIndex == row) {
	            return node;
	        }
	    }
	    
	    return null;
	}
	
	/**
	 * Initialisiert den MouseHandler für das Label.
	 * 
	 * @param lbl - The Label.
	 */
	private void mouseHandlerLabel(Label lbl)
	{
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
					buttonMore.setVisible(true);
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
					buttonMore.setVisible(false);
				}
				
				showTaskInfo();
			}
		});
	}
	
	/**
	 * Zeigt die Informationen des aktiven Tasks an.
	 */
	private void showTaskInfo() {
		
		if (activeLabel != null) {
			
			try {

				Registry registry = LocateRegistry.getRegistry(null);
				RMI_Task task = (RMI_Task) registry.lookup(activeLabel.getText());
				
				User letzerUser = task.getLetzterNutzer();
				ZonedDateTime letzteÄnderung = task.getLetzteAenderung();
				
				textFieldTaskname.setText(activeLabel.getText());
				labelLastUser.setText(letzerUser.getNachname() + ", " + letzerUser.getVorname());
				labelLastChange.setText(letzteÄnderung.getDayOfWeek().toString() + ", " + 
						letzteÄnderung.getDayOfMonth() + "." + letzteÄnderung.getMonth().toString() + " " + letzteÄnderung.getYear());
				textAreaDescription.setText(task.getBeschreibung());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			
			textFieldTaskname.clear();
			labelLastUser.setText("");
			labelLastChange.setText("");
			textAreaDescription.clear();
		}
	}
	
	/**
	 * Überprüft, ob noch genug Platz in den Spalten ist. 
	 * Sollte nicht genug Platz sein, wird die Größe der Panes angepasst.
	 */
	private void checkScrollBarSpace() {
		
		if(anchor.getHeight() < gridKanban.getHeight() + 90)
		{
			anchor.setMinHeight(gridKanban.getHeight() + 90);
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
	 * Aktualisiert die Seite
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
    void buttonRefreshPressed(ActionEvent event) {
		
		buttonRefresh.setStyle("-fx-background-color: transparent;");
		main.showMainFrame();
    }
	
	/**
	 * Benachrichtigt den User über Änderungen.
	 */
	public void notifyUser()
	{
		buttonRefresh.setStyle("-fx-background-color: #ff2d37;");
	}
	
	/**
	 * Öffnet den TaskFrame.
	 * 
	 * @param event - Das Action-Event.
	 */
	@FXML
    void buttonMorePressed(ActionEvent event) {
		
		main.aktuellerTask = activeLabel.getText();
		main.showTaskFrame();
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
		buttonMore.setVisible(false);
		labelLastChange.setText(null);
		labelLastUser.setText(null);
		
		textFieldTaskname.clear();
		textFieldTaskname.editableProperty().set(true);
		textFieldTaskname.setStyle("-fx-background-color: white;");
		textAreaDescription.clear();
		textAreaDescription.editableProperty().set(true);
		textAreaDescription.setStyle("text-area-background: white;");
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

		VBox box = (VBox) this.getNodeFromGridPane(gridKanban, 0, 1);
		box.getChildren().add(lbl);
		this.mouseHandlerLabel(lbl);
		
	}
	
	/**
	 * Je nach Position des aktive Labels befindet wir es nach "rechts" bzw. in Prozessrichtung verschoben.
	 * Passt die benötigte Höhe der Scrollbar an.
	 * 
	 * @param event - Das Action-Event
	 */
	@FXML
	void buttonProceedPressed(ActionEvent event) {

		try {

			Registry registry = LocateRegistry.getRegistry(null);
			RMI_Task task = (RMI_Task) registry.lookup(activeLabel.getText());
			boolean verschiebbar = task.taskNachVorneVerschieben();
			RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
			projekt.speichereProjekt();
			
			if(verschiebbar)
			{
				int col = 0;
				if(GridPane.getColumnIndex(activeLabel.getParent()) != null) {
					col = GridPane.getColumnIndex(activeLabel.getParent());
				}
				
				VBox boxOld = (VBox) activeLabel.getParent();
				boxOld.getChildren().remove(activeLabel);
				
				VBox boxNew = (VBox) this.getNodeFromGridPane(gridKanban, col + 1, 1);
				boxNew.getChildren().add(activeLabel);
				
				labelBenachrichtigung.setText("");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			main.log("Fehler", "Proceed Task");
			labelBenachrichtigung.setText("Fehler beim Verschieben des Tasks!");
		}
	}

	/**
	 * Je nach Position des aktive Labels befindet wir es nach "links" bzw. entgegen der Prozessrichtung verschoben.
	 * Passt die benötigte Höhe der Scrollbar an.
	 * 
	 * @param event
	 */
	@FXML
	void buttonReturnPressed(ActionEvent event) {

		try {
			
			Registry registry = LocateRegistry.getRegistry(null);
			RMI_Task task = (RMI_Task) registry.lookup(activeLabel.getText());
			boolean verschiebbar = task.taskNachHintenVerschieben();
			RMI_Projekt projekt = (RMI_Projekt) registry.lookup(main.aktuellesProjekt);
			projekt.speichereProjekt();
			
			if(verschiebbar)
			{
				int col = 0;
				if(GridPane.getColumnIndex(activeLabel.getParent()) != null) {
					col = GridPane.getColumnIndex(activeLabel.getParent());
				}
				
				VBox boxOld = (VBox) activeLabel.getParent();
				boxOld.getChildren().remove(activeLabel);
				
				VBox boxNew = (VBox) this.getNodeFromGridPane(gridKanban, col - 1, 1);
				boxNew.getChildren().add(activeLabel);
				
				labelBenachrichtigung.setText("");
			}

		} catch (Exception e) {
			e.printStackTrace();
			labelBenachrichtigung.setText("Fehler beim Verschieben des Tasks!");
		}
		
		showTaskInfo();
	}

	void guilog(String text) {
		labelBenachrichtigung.setText(text);
	}

	void guilog(int text) {
		labelBenachrichtigung.setText("" + text);
	}
}
