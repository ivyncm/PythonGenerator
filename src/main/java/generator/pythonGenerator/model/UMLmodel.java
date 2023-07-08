package generator.pythonGenerator.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.uml.UmlModel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

import generator.pythonGenerator.controller.Utils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class UMLmodel {
	private SimpleStringProperty Name;
    private SimpleStringProperty Ruta;
    private SimpleStringProperty RutaResultado;
    public BooleanProperty Actividad;
    public BooleanProperty Clase;

    public UMLmodel(String Name, String Ruta) {
        this.Name = new SimpleStringProperty(Name);
        this.Ruta = new SimpleStringProperty(Ruta);
        this.RutaResultado = new SimpleStringProperty("");
        this.Actividad = new SimpleBooleanProperty(false);
        this.Clase = new SimpleBooleanProperty(false);
    }
    
    //Return path to image
	public String generateImageFromPuml() throws IOException{
    	Utils.crearDirectorio("temp/images/");
		Path filePath = Path.of("temp/plantuml/" + this.getName().replace(".uml", ".puml"));

        // Read the file content into a byte array
        byte[] fileBytes = Files.readAllBytes(filePath);

        // Convert the byte array to a string using the specified character set
        String plantUmlCode = new String(fileBytes, StandardCharsets.UTF_8);

        // Create a SourceStringReader object with our PlantUML source code
        SourceStringReader reader = new SourceStringReader(plantUmlCode);

        // Export the diagram to a PNG file
        FileFormatOption option = new FileFormatOption(FileFormat.PNG);
        File outputFile = new File("temp/images/" + this.getName().replace(".uml", ".png"));
        reader.outputImage(new FileOutputStream(outputFile), option);
        
        return "temp/images/" + this.getName().replace(".uml", ".png");
	}
	public String generateImageFromPython() throws IOException{
    	EgxModule module = Utils.parseEgxFile("EGLtemplates/activityGenPng.egx"); // Parse egxFilePath.egx
		UmlModel umlModel = Utils.loadUml(this.getRuta()); // Load UmlModel
		
		module.getContext().getModelRepository().addModel(umlModel); // Make the document visible to the EGX program
		try {
			module.execute(); // Execute module egxFilePath.egx
			Utils.crearDirectorio("./temp/images");
		} catch (EolRuntimeException e) {
			e.printStackTrace();
			System.out.printf("[ERROR] Failed to execute %s%n", this.getRuta());
		}
		// GENERAR IMAGEN FROM PYTHON FILE
		try {
            ProcessBuilder pb = new ProcessBuilder(Arrays.asList("python", "temp/qiskit/" + this.getName().replace(".uml", ".py").toLowerCase().replace('-', '_')));
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return "temp/images/" + this.getName().replace(".uml", ".png").replace('-', '_').toLowerCase();
	}
    
    public String getName() {
        return Name.get();
    }

    public void setName(String Name) {
    	this.Name.set(Name);
    }
    public String getRuta() {
    	return Ruta.get();
    }

    public void setRuta(String Ruta) {
    	this.Ruta.set(Ruta);
    }
    public String getRutaResultado() {
    	return RutaResultado.get();
    }

    public void setRutaResultado(String RutaResultado) {
    	this.RutaResultado.set(RutaResultado);
    }
    public Boolean getActividad() {
        return this.Actividad.get();
    }

    public void setActividad(Boolean Actividad) {
    	this.Actividad.set(Actividad);
    }
    public Boolean getClase() {
        return this.Clase.get();
    }

    public void setClase(Boolean Clase) {
    	this.Clase.set(Clase);
    }
    
    public BooleanProperty ActividadProperty() { return Actividad; }
    public BooleanProperty ClaseProperty() { return Clase; }
}
