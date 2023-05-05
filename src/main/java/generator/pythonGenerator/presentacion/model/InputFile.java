package generator.pythonGenerator.presentacion.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import generator.pythonGenerator.Utils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class InputFile {
	private SimpleStringProperty Name;
    private SimpleStringProperty Ruta;
    public BooleanProperty Actividad;
    public BooleanProperty Clase;

    public InputFile(String Name, String Ruta) {
        this.Name = new SimpleStringProperty(Name);
        this.Ruta = new SimpleStringProperty(Ruta);
        this.Actividad = new SimpleBooleanProperty(false);
        this.Clase = new SimpleBooleanProperty(false);
    }
    
    //Return path to image
	public String generateImage() throws IOException{
    	Utils.crearDirectorio("temp/images/");
		Path filePath = Path.of("temp/plantuml/" + this.getName().replace(".uml", ".puml"));

        // Read the file content into a byte array
        byte[] fileBytes = Files.readAllBytes(filePath);

        // Convert the byte array to a string using the specified character set
        String plantUmlCode = new String(fileBytes, StandardCharsets.UTF_8);
        System.out.println(plantUmlCode);

        // Create a SourceStringReader object with our PlantUML source code
        SourceStringReader reader = new SourceStringReader(plantUmlCode);

        // Export the diagram to a PNG file
        FileFormatOption option = new FileFormatOption(FileFormat.PNG);
        File outputFile = new File("temp/images/" + this.getName().replace(".uml", ".png"));
        reader.outputImage(new FileOutputStream(outputFile), option);
        
        return "temp/images/" + this.getName().replace(".uml", ".png");
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
