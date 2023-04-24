package generator.pythonGenerator.presentacion.model;

import javafx.beans.property.SimpleStringProperty;

public class InputFile {
	private SimpleStringProperty Name;
    private SimpleStringProperty Ruta;
    private Boolean Actividad;
    private Boolean Clase;

    public InputFile(String Name, String Ruta, Boolean Actividad, Boolean Clase) {
        this.Name = new SimpleStringProperty(Name);
        this.Ruta = new SimpleStringProperty(Ruta);
        this.Actividad = Actividad;
        this.Clase = Clase;
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
        return this.Actividad;
    }

    public void setActividad(Boolean Actividad) {
    	this.Actividad = Actividad;
    }
    public Boolean getClase() {
        return this.Clase;
    }

    public void setClase(Boolean Clase) {
    	this.Clase = Clase;
    }
}
