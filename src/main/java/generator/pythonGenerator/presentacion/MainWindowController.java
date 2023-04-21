/**
 * Sample Skeleton for 'MainWindow.fxml' Controller Class
 */

package generator.pythonGenerator.presentacion;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainWindowController {
	final FileChooser fileChooser = new FileChooser();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAnadir"
    private Button btnAnadir; // Value injected by FXMLLoader

    @FXML // fx:id="btnBrowse"
    private Button btnBrowse; // Value injected by FXMLLoader

    @FXML // fx:id="btnEditar"
    private Button btnEditar; // Value injected by FXMLLoader

    @FXML // fx:id="btnEliminar"
    private Button btnEliminar; // Value injected by FXMLLoader

    @FXML // fx:id="btnGenerar"
    private Button btnGenerar; // Value injected by FXMLLoader

    @FXML
    void clickAnadir(ActionEvent event) {
    	FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        chooser.getExtensionFilters().addAll(
                new ExtensionFilter("Uml Files", "*.uml"));
        List<File> list = chooser.showOpenMultipleDialog(new Stage());
        System.out.println(list.toString());
    }

    @FXML
    void clickBrowse(ActionEvent event) {

    }

    @FXML
    void clickEditar(ActionEvent event) {

    }

    @FXML
    void clickEliminar(ActionEvent event) {

    }

    @FXML
    void clickGennerar(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAnadir != null : "fx:id=\"btnAnadir\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert btnBrowse != null : "fx:id=\"btnBrowse\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert btnEditar != null : "fx:id=\"btnEditar\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert btnEliminar != null : "fx:id=\"btnEliminar\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert btnGenerar != null : "fx:id=\"btnGenerar\" was not injected: check your FXML file 'MainWindow.fxml'.";

    }

}
