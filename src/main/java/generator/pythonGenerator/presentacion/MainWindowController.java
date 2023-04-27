/**
 * Sample Skeleton for 'MainWindow.fxml' Controller Class
 */

package generator.pythonGenerator.presentacion;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;

import generator.pythonGenerator.Generator;
import generator.pythonGenerator.Utils;
import generator.pythonGenerator.presentacion.model.InputFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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

        @FXML
        void clickAnadir(ActionEvent event) {
        	ObservableList<InputFile> fileList = FXCollections.observableArrayList();
        	FileChooser chooser = new FileChooser();
            chooser.setTitle("Open File");
            chooser.getExtensionFilters().addAll(new ExtensionFilter("Uml Files", "*.uml"));
            List<File> list = chooser.showOpenMultipleDialog(new Stage());
            for(File file : list) {
            	fileList.add(new InputFile(file.getName(), file.getAbsolutePath()));
            }
            InputTable.getItems().addAll(fileList);
        }

        @FXML
        void clickBrowse(ActionEvent event) {
        	DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(new Stage());
            if (selectedDirectory != null) {
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
        	Generator generator = new Generator(getClassesFromTable(), getActivitiesFromTable());
        	try {
				generator.execute();
			} catch (EolModelElementTypeNotFoundException e) {
				e.printStackTrace();
			} finally {
				Utils.copyCircuits(new File("temp"), new File(OutputLabel.getText()));
				Utils.deleteTempDir();
				System.out.println("Done!");
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
            assert progressTextLabel != null : "fx:id=\"progressTextLabel\" was not injected: check your FXML file 'MainWindow.fxml'.";
        	InputTable.setEditable(true);
        	ActividadCol.setEditable(true);
        	ClaseCol.setEditable(true);
            ActividadCol.setCellFactory(CheckBoxTableCell.forTableColumn(ActividadCol));
            ClaseCol.setCellFactory(CheckBoxTableCell.forTableColumn(ClaseCol));
            InputTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        
         // Crea un nuevo OutputStream personalizado que escribe en el TextArea
            OutputStream out = new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                	progressTextLabel.appendText(String.valueOf((char) b));
                }
            };

            // Redirige la salida estándar a este OutputStream personalizado
            System.setOut(new PrintStream(out, true));
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
  
