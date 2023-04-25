package generator.pythonGenerator.presentacion.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

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
