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

import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.uml.UmlModel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

import generator.pythonGenerator.Generator;
import generator.pythonGenerator.Utils;
import generator.pythonGenerator.presentacion.model.InputFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
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

        @FXML // fx:id="ResultadoCol"
        private TableColumn<InputFile, Button> ResultadoCol; // Value injected by FXMLLoader

        @FXML // fx:id="RutaCol"
        private TableColumn<InputFile, String> RutaCol; // Value injected by FXMLLoader

        @FXML // fx:id="VerCol"
        private TableColumn<InputFile, Button> VerCol; // Value injected by FXMLLoader

        @FXML // fx:id="ActividadCol"
        private TableColumn<InputFile, Boolean> ActividadCol; // Value injected by FXMLLoader

        @FXML // fx:id="ClaseCol"
        private TableColumn<InputFile, Boolean> ClaseCol; // Value injected by FXMLLoader
        
        @FXML // fx:id="OutputLabel"
        private TextField OutputLabel; // Value injected by FXMLLoader

        @FXML // fx:id="btnAnadir"
        private Button btnAnadir; // Value injected by FXMLLoader

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
            
            if (!(list == null)) {
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
				            		alert.setTitle("ConfirmaciÃ³n");
				            		alert.setHeaderText("El elemento ya existe en la tabla");
				            		alert.setContentText("Â¿Desea quedarse con el elemento anterior o con el nuevo?");
		
				            		// Crear botones de opciÃ³n
				            		ButtonType buttonTypeAnterior = new ButtonType("Anterior");
				            		ButtonType buttonTypeNuevo = new ButtonType("Nuevo");
		
				            		// Agregar botones a la alerta
				            		alert.getButtonTypes().setAll(buttonTypeAnterior, buttonTypeNuevo);
		
				            		// Mostrar la alerta y esperar a que el usuario seleccione una opciÃ³n
				            		Optional<ButtonType> result = alert.showAndWait();
		
				            		// Verificar la opciÃ³n seleccionada por el usuario
				            		if (result.get() == buttonTypeAnterior) {
				            		    // El usuario seleccionÃ³ quedarse con el elemento anterior
				            		} else if (result.get() == buttonTypeNuevo) {
				            		    // El usuario seleccionÃ³ quedarse con el elemento nuevo
				                    	ResultadoCol.setVisible(false);
				            			row.setName(file.getName());
				            			row.setRuta(file.getAbsolutePath());
				            			row.setActividad(false);
				            			row.setClase(false);
				            			InputTable.refresh();
				            		} else {
				            		    // El usuario cerrÃ³ la alerta sin seleccionar ninguna opciÃ³n
				            		}
		            			} else {
		            				// Mostrar un mensaje de error si no se seleccionÃ³ ninguna fila
		                            Alert alert = new Alert(AlertType.WARNING);
		                            alert.setTitle("Warning");
		                            alert.setHeaderText(null);
		                            alert.setContentText("El elemento ".concat(file.getName()).concat(" ya estÃ¡ insertado."));
		                            alert.showAndWait();
		            			}
			            	}
		            	}
	            	}
	            	if(exists == false) {
	                	ResultadoCol.setVisible(false);
	            		fileList.add(new InputFile(file.getName(), file.getAbsolutePath()));
	            	}
	            }
	            InputTable.getItems().addAll(fileList);
            }
        }

        @FXML
        void clickEliminar(ActionEvent event) {
        	ObservableList<InputFile> selectedItems = InputTable.getSelectionModel().getSelectedItems();
            if (!selectedItems.isEmpty()) {
            	InputTable.getItems().removeAll(selectedItems);
            	InputTable.getSelectionModel().clearSelection();
            } else {
                // Mostrar un mensaje de error si no se seleccionÃ³ ninguna fila
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText(null);
                alert.setContentText("Debes seleccionar una fila para eliminar.");
                alert.showAndWait();
            }
        }

        @FXML
        void clickGennerar(ActionEvent event) {
        	Utils.deleteTempDir();
        	if(checkOutputDirectory() == true && checkTableSelection() == true && checkEmptyTable() == false) {
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
	    	            	updateMessage("Executing activity: ".concat(generator.getumlActivityFiles().get(i).toString().replace('/', '/')));
	    	                // Realiza una iteraciÃ³n de la tarea
	    	            	activities.add(generator.executeGeneratorActivity(generator.getumlActivityFiles().get(i)));
	
	    	                // Calcula el progreso actual y actualiza la barra de progreso
	    	                double progress = (i + 1.0) / totalIterations;
	    	                updateProgress(progress, 1.0);
	    	            }
	    	            for (int j = 0; j < m; j++) {
	    	            	updateMessage("Executing activity: ".concat(generator.getumlClassFiles().get(j).toString().replace('/', '/')));
	    	                // Realiza una iteraciÃ³n de la tarea
	    	            	generator.executeGeneratorClass(generator.getumlClassFiles().get(j), activities);
	
	    	                // Calcula el progreso actual y actualiza la barra de progreso
	    	                double progress = (n + j + 1.0) / totalIterations;
	    	                updateProgress(progress, 1.0);
	    	            }
	    	            updateMessage("Moviendo resultados a: ".concat(OutputLabel.getText()));
	    				Utils.copyCircuits(new File("temp/RESULTS"), new File(OutputLabel.getText() + "/RESULTS"));
	    				updateMessage("Done!");
	
	    	            return null;
	    	        }
	    	    };
	    	    // Vincula la barra de progreso con la propiedad de progreso de la tarea
	    	    progressBar.progressProperty().bind(task.progressProperty());
	    	    progressTextLabel.textProperty().bind(task.messageProperty());
	
	    	    // Inicia la tarea en un nuevo hilo
	    	    Thread thread = new Thread(task);
	    	    thread.start();
	    	    
	    	    try {
	    	    	thread.join(); // Esperar a que el hilo finalice
		            ResultadoCol.setVisible(true);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }

        	}
        }
        
        @FXML
        void clickOpenOutDir(ActionEvent event) {
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

        @FXML // This method is called by the FXMLLoader when initialization is complete
        void initialize() {
        	assert ActividadCol != null : "fx:id=\"ActividadCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert ClaseCol != null : "fx:id=\"ClaseCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert InputTable != null : "fx:id=\"InputTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert NombreCol != null : "fx:id=\"NombreCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert OutputLabel != null : "fx:id=\"OutputLabel\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert ResultadoCol != null : "fx:id=\"ResultadoCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert RutaCol != null : "fx:id=\"RutaCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert VerCol != null : "fx:id=\"VerCol\" was not injected: check your FXML file 'MainWindow.fxml'.";
            assert btnAnadir != null : "fx:id=\"btnAnadir\" was not injected: check your FXML file 'MainWindow.fxml'.";
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
            ResultadoCol.setVisible(false);
            
            VerCol.setCellFactory(col -> new TableCell<InputFile, Button>() {
            	private final Button viewButton =  new Button("👁");
                	{
                		viewButton.setOnAction((ActionEvent event) -> {
                			InputTable.refresh();
                			viewButton.setVisible(false);
                			File file = null;
                			InputFile item = (InputFile) getTableRow().getItem();
                                if (item != null) {
                                	EgxModule module = null;
                                	// Generate .puml
                                	if(item.getClase() == true) {
                                    	module = Utils.parseEgxFile("EGLtemplates/classGenPUML.egx"); // Parse egxFilePath.egx
                                	}else if(item.getActividad() == true) {
                                		module = Utils.parseEgxFile("EGLtemplates/activityGenPUML.egx"); // Parse egxFilePath.egx
                                	}
                                	if(module != null) {
                                		UmlModel umlModel = Utils.loadUml(item.getRuta()); // Load UmlModel
	                            		
	                            		module.getContext().getModelRepository().addModel(umlModel); // Make the document visible to the EGX program
	                            		try {
	                            			module.execute(); // Execute module egxFilePath.egx
	                            			Utils.crearDirectorio("./temp/images");
											file = new File(item.generateImageFromPuml());
	                            		} catch (EolRuntimeException | IOException e) {
	                            			e.printStackTrace();
	                            			System.out.printf("[ERROR] Failed to execute %s%n", item.getRuta());
	                            		}
	                                	// Crear una nueva ventana para mostrar la imagen
	                                    Stage stage = new Stage();
	                                    stage.setTitle(item.getName().replace(".uml", ""));
	                                    stage.initModality(Modality.APPLICATION_MODAL);
	
	                                    Image image = new Image(file.toURI().toString());

										// Create an ImageView with the Image object
										ImageView imageView = new ImageView(image);

										// Wrap the ImageView in a ScrollPane
										ScrollPane scrollPane = new ScrollPane(imageView);

										// Create a StackPane to hold the ScrollPane
										StackPane root = new StackPane(scrollPane);

										// Create a Scene with the StackPane
										Scene scene = new Scene(root, 640, 480);

										// Set the Scene and show the Stage
										stage.setScene(scene);
										stage.show();
                                	} else {
                                		Alert alert = new Alert(AlertType.ERROR);
                                        alert.setTitle("Error!");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Debe seleccionar al menos una opción(actividad o clase).");
                                        alert.showAndWait();
                                	}
										
                                }
                    			viewButton.setVisible(true);
                            });
                        }
                	
                    @Override
                    public void updateItem(Button file, boolean empty) {
                        super.updateItem(file, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(viewButton);
                            setAlignment(Pos.CENTER);
                        }
                    }
            });
            
            ResultadoCol.setCellFactory(col -> new TableCell<InputFile, Button>() {
            	private final Button resButton =  new Button("📂");
                	{	
                		resButton.setOnAction((ActionEvent event) -> {
                			InputTable.refresh();
                			resButton.setVisible(false);
                			InputFile item = (InputFile) getTableRow().getItem();
                            if (item != null) {
                            	String directoryPath = item.getRutaResultado(); // Specify the directory path here

                                if(item.getActividad()) {
                                	if (directoryPath != null) {
                                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.EDIT)) {
                                            try {
                                                Desktop.getDesktop().edit(new File(directoryPath + "/" + item.getName().replace(".uml", ".py").replace(' ', '_').replace('-', '_')));
                                            } catch (IOException e) {
                                            	String os = System.getProperty("os.name").toLowerCase();

                                                if (os.contains("win")) {
                                                    try {
														Runtime.getRuntime().exec("notepad.exe " + (new File(directoryPath + "/" + item.getName().replace(".uml", ".py").replace(' ', '_').replace('-', '_'))).getAbsolutePath());
													} catch (IOException e1) {
														e1.printStackTrace();
													}
                                                } else {
                                                	Alert alert = new Alert(AlertType.ERROR);
            	                                    alert.setTitle("Error!");
            	                                    alert.setHeaderText(null);
            	                                    alert.setContentText("Opening with Notepad is supported only on Windows.");
            	                                    alert.showAndWait();
                                                }
                                            }
                                        } else {
                                        	Alert alert = new Alert(AlertType.ERROR);
    	                                    alert.setTitle("Error!");
    	                                    alert.setHeaderText(null);
    	                                    alert.setContentText("No se pudo abrir el fichero.");
    	                                    alert.showAndWait();
                                        }
                                    }
                                } else if(item.getClase()) {
                                    File directory = new File(directoryPath + item.getName().replace(".uml", ""));
	                                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
	                                    try {
	                                        Desktop.getDesktop().open(directory);
	                                    } catch (IOException e) {
	                                        e.printStackTrace();
	                                        // Handle the exception appropriately
	                                    }
	                                } else {
	                                	Alert alert = new Alert(AlertType.ERROR);
	                                    alert.setTitle("Error!");
	                                    alert.setHeaderText(null);
	                                    alert.setContentText("No se pudo abrir el directorio.");
	                                    alert.showAndWait();
	                                }
                                }
                            }
                            resButton.setVisible(true);
                        });
                    }
                	
                    @Override
                    public void updateItem(Button file, boolean empty) {
                        super.updateItem(file, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(resButton);
                            setAlignment(Pos.CENTER);
                        }
                    }
            });
        }
        
        public List<String> getActivitiesFromTable() {
        	ObservableList<InputFile> items = InputTable.getItems();
        	List<String> filteredItems = new ArrayList<String>();

        	for (InputFile item : items) {
        	    if (item.getActividad()) {
        	    	item.setRutaResultado(OutputLabel.getText() + "/RESULTS/quantumCircuits/");
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
        	    	item.setRutaResultado(OutputLabel.getText() + "/RESULTS/");
        	        filteredItems.add(item.getRuta());
        	    }
        	}
        	return filteredItems;
        }
        public Boolean checkOutputDirectory() {
        	Boolean validOutput = true;
        	File directorio = new File(OutputLabel.getText());
        	if(!(directorio.exists() && directorio.isDirectory())) {
        		validOutput = false;
        	}
        	if (validOutput == false) {
        		Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText(null);
                alert.setContentText("El directorio destino no es válido.");
                alert.showAndWait();
        	}
        	return validOutput;
        }
        public Boolean checkTableSelection() {
        	Boolean validTable = true;
        	for(InputFile row : InputTable.getItems()) {
        		if(!row.getActividad() && !row.getClase()) {
        			validTable = false;
        		}
        	}
        	if (validTable == false) {
        		Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText(null);
                alert.setContentText("Debes seleccionar al menos una opción (clase o actividad) para cada uml.");
                alert.showAndWait();
        	}
        	return validTable;
        	
        }
        public Boolean checkEmptyTable() {
        	Boolean emptyTable = false;
        	if(InputTable.getItems().size() == 0) {
        		emptyTable = true;
        	}
        	if (emptyTable == true) {
        		Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText(null);
                alert.setContentText("La tabla está vacía. Debes añadir al menos un fichero uml.");
                alert.showAndWait();
        	}
        	return emptyTable;
        }
    }
  
