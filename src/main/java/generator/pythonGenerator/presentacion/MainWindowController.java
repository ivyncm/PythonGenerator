/**
 * Sample Skeleton for 'MainWindow.fxml' Controller Class
 */

package generator.pythonGenerator.presentacion;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import generator.pythonGenerator.Generator;
import generator.pythonGenerator.Utils;
import generator.pythonGenerator.presentacion.model.InputFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


    public class MainWindowController {
    	
    	private Preferences preferences;

        @FXML // ResourceBundle that was given to the FXMLLoader
        private ResourceBundle resources;

        @FXML // URL location of the FXML file that was given to the FXMLLoader
        private URL location;

        @FXML // fx:id="InputTable"
        private TableView<InputFile> InputTable; // Value injected by FXMLLoader

        @FXML // fx:id="NombreCol"
        private TableColumn<InputFile, String> NombreCol; // Value injected by FXMLLoader

        @FXML // fx:id="RutaCol"
        private TableColumn<InputFile, String> RutaCol; // Value injected by FXMLLoader

        @FXML // fx:id="ActividadCol"
        private TableColumn<InputFile, Boolean> ActividadCol; // Value injected by FXMLLoader

        @FXML // fx:id="ClaseCol"
        private TableColumn<InputFile, Boolean> ClaseCol; // Value injected by FXMLLoader
        
        @FXML // fx:id="OutputLabel"
        private TextField OutputLabel; // Value injected by FXMLLoader

        @FXML // fx:id="btnAnadir"
        private Button btnAnadir; // Value injected by FXMLLoader

        @FXML // fx:id="btnBrowse"
        private Button btnBrowse; // Value injected by FXMLLoader

        @FXML // fx:id="btnEliminar"
        private Button btnEliminar; // Value injected by FXMLLoader

        @FXML // fx:id="btnGenerar"
        private Button btnGenerar; // Value injected by FXMLLoader

        @FXML // fx:id="progressTextLabel"
        private TextArea progressTextLabel; // Value injected by FXMLLoader
        
        @FXML // fx:id="progressBar"
        private ProgressBar progressBar; // Value injected by FXMLLoader

        @FXML // fx:id="btnOpenOutDir"
        private Button btnOpenOutDir; // Value injected by FXMLLoader

        @FXML
        void clickAnadir(ActionEvent event) {
        	ObservableList<InputFile> fileList = FXCollections.observableArrayList();
        	FileChooser chooser = new FileChooser();
        	String lastDir = preferences.get("lastDir", System.getProperty("user.home"));
            File lastDirFile = new File(lastDir);
            if (lastDirFile.exists()) {
            	chooser.setInitialDirectory(lastDirFile);
            }
            chooser.setTitle("Open File");
            chooser.getExtensionFilters().addAll(new ExtensionFilter("Uml Files", "*.uml"));
            List<File> list = chooser.showOpenMultipleDialog(new Stage());
            
            if (!list.isEmpty()) {
	            preferences.put("lastDir", list.get(0).getParent());
	            Boolean exists;
	            for(File file : list) {
	            	exists = false;
	            	if (!InputTable.getItems().isEmpty()) {
		            	for(InputFile row : InputTable.getItems()) {
		            		if(row.getName().equals(file.getName())) {
		            			exists = true;
		            			if(!row.getRuta().equals(file.getAbsolutePath())) {
			            			// Crear una alerta
				            		Alert alert = new Alert(AlertType.CONFIRMATION);
				            		alert.setTitle("Confirmación");
				            		alert.setHeaderText("El elemento ya existe en la tabla");
				            		alert.setContentText("¿Desea quedarse con el elemento anterior o con el nuevo?");
		
				            		// Crear botones de opción
				            		ButtonType buttonTypeAnterior = new ButtonType("Anterior");
				            		ButtonType buttonTypeNuevo = new ButtonType("Nuevo");
		
				            		// Agregar botones a la alerta
				            		alert.getButtonTypes().setAll(buttonTypeAnterior, buttonTypeNuevo);
		
				            		// Mostrar la alerta y esperar a que el usuario seleccione una opción
				            		Optional<ButtonType> result = alert.showAndWait();
		
				            		// Verificar la opción seleccionada por el usuario
				            		if (result.get() == buttonTypeAnterior) {
				            		    // El usuario seleccionó quedarse con el elemento anterior
				            		} else if (result.get() == buttonTypeNuevo) {
				            		    // El usuario seleccionó quedarse con el elemento nuevo
				            			row.setName(file.getName());
				            			row.setRuta(file.getAbsolutePath());
				            			row.setActividad(false);
				            			row.setClase(false);
				            			InputTable.refresh();
				            		} else {
				            		    // El usuario cerró la alerta sin seleccionar ninguna opción
				            		}
		            			} else {
		            				// Mostrar un mensaje de error si no se seleccionó ninguna fila
		                            Alert alert = new Alert(AlertType.ERROR);
		                            alert.setTitle("Warning");
		                            alert.setHeaderText(null);
		                            alert.setContentText("El elemento ".concat(file.getName()).concat(" ya está insertado."));
		                            alert.showAndWait();
		            			}
			            	}
		            	}
	            	}
	            	if(exists == false) {
	            		fileList.add(new InputFile(file.getName(), file.getAbsolutePath()));
	            	}
	            }
	            InputTable.getItems().addAll(fileList);
            }
        }

        @FXML
        void clickBrowse(ActionEvent event) {
        	DirectoryChooser directoryChooser = new DirectoryChooser();
        	String lastDir = preferences.get("lastDir", System.getProperty("user.home"));
            File lastDirFile = new File(lastDir);
            if (lastDirFile.exists()) {
            	directoryChooser.setInitialDirectory(lastDirFile);
            }
            File selectedDirectory = directoryChooser.showDialog(new Stage());
            if (selectedDirectory != null) {
	            preferences.put("lastDir", selectedDirectory.getAbsolutePath());
            	OutputLabel.setText(selectedDirectory.getAbsolutePath());
            }
        }

        @FXML
        void clickEliminar(ActionEvent event) {
        	ObservableList<InputFile> selectedItems = InputTable.getSelectionModel().getSelectedItems();
            if (!selectedItems.isEmpty()) {
            	InputTable.getItems().removeAll(selectedItems);
            	InputTable.getSelectionModel().clearSelection();
            } else {
                // Mostrar un mensaje de error si no se seleccionó ninguna fila
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Debes seleccionar una fila para eliminar.");
                alert.showAndWait();
            }
        }

        @FXML
        void clickGennerar(ActionEvent event) {
        	progressBar.setProgress(0.0);
        	Generator generator = new Generator(getClassesFromTable(), getActivitiesFromTable());
    		// Inicia la tarea en un hilo separado
    	    Task<Void> task = new Task<Void>() {
    	        @Override
    	        protected Void call() throws Exception {
    	        	updateProgress(0.0, 1.0);
    	        	updateMessage("Starting... \n");
    	        	int n = generator.getumlActivityFiles().size();
    	        	int m = generator.getumlClassFiles().size();
    	        	int totalIterations = n + m;
    	        	List<String> activities = new ArrayList<String>();
    	            // Bucle que realiza un total de n iteraciones
    	            for (int i = 0; i < n; i++) {
    	            	updateMessage("Executing activity: ".concat(generator.getumlActivityFiles().get(i).toString().replace('\\', '/')));
    	                // Realiza una iteración de la tarea
    	            	activities.add(generator.executeGeneratorActivity(generator.getumlActivityFiles().get(i)));

    	                // Calcula el progreso actual y actualiza la barra de progreso
    	                double progress = (i + 1.0) / totalIterations;
    	                updateProgress(progress, 1.0);
    	            }
    	            for (int j = 0; j < m; j++) {
    	            	updateMessage("Executing activity: ".concat(generator.getumlClassFiles().get(j).toString().replace('\\', '/')));
    	                // Realiza una iteración de la tarea
    	            	generator.executeGeneratorClass(generator.getumlClassFiles().get(j), activities);

    	                // Calcula el progreso actual y actualiza la barra de progreso
    	                double progress = (n + j + 1.0) / totalIterations;
    	                updateProgress(progress, 1.0);
    	            }
    	            updateMessage("Moviendo resultados a: ".concat(OutputLabel.getText()));
    				Utils.copyCircuits(new File("temp"), new File(OutputLabel.getText()));
    				Utils.deleteTempDir();
    				updateMessage("Done!");

    	            return null;
    	        }
    	    };
    	    // Vincula la barra de progreso con la propiedad de progreso de la tarea
    	    progressBar.progressProperty().bind(task.progressProperty());
    	    progressTextLabel.textProperty().bind(task.messageProperty());

    	    // Inicia la tarea en un nuevo hilo
    	    new Thread(task).start();
    	    
        }
        
        @FXML
        void clickOpenOutDir(ActionEvent event) {
        	Desktop desktop = Desktop.getDesktop();
        	String directory = OutputLabel.getText();
            try {
                desktop.open(new File(directory));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @FXML // This method is called by the FXMLLoader when initialization is complete
        void initialize() {
        	assert ActividadCol != null : "fx:id=\"ActividadCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert ClaseCol != null : "fx:id=\"ClaseCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert InputTable != null : "fx:id=\"InputTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert NombreCol != null : "fx:id=\"NombreCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert OutputLabel != null : "fx:id=\"OutputLabel\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert RutaCol != null : "fx:id=\"RutaCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert btnAnadir != null : "fx:id=\"btnAnadir\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert btnBrowse != null : "fx:id=\"btnBrowse\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert btnEliminar != null : "fx:id=\"btnEliminar\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert btnGenerar != null : "fx:id=\"btnGenerar\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert btnOpenOutDir != null : "fx:id=\"btnOpenOutDir\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert progressTextLabel != null : "fx:id=\"progressTextLabel\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'MainWindow.fxml'.";
        	InputTable.setEditable(true);
        	ActividadCol.setEditable(true);
        	ClaseCol.setEditable(true);
            ActividadCol.setCellFactory(CheckBoxTableCell.forTableColumn(ActividadCol));
            ClaseCol.setCellFactory(CheckBoxTableCell.forTableColumn(ClaseCol));
            InputTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            preferences = Preferences.userRoot().node("myApp");
        }
        
        public List<String> getActivitiesFromTable() {
        	ObservableList<InputFile> items = InputTable.getItems();
        	List<String> filteredItems = new ArrayList<String>();

        	for (InputFile item : items) {
        	    if (item.getActividad()) {
        	        filteredItems.add(item.getRuta());
        	    }
        	}
        	return filteredItems;
        }
        public List<String> getClassesFromTable() {
        	ObservableList<InputFile> items = InputTable.getItems();
        	List<String> filteredItems = new ArrayList<String>();

        	for (InputFile item : items) {
        	    if (item.getClase()) {
        	        filteredItems.add(item.getRuta());
        	    }
        	}
        	return filteredItems;
        }

    }
  
