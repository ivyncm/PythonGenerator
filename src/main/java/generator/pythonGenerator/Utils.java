package generator.pythonGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.uml.UmlModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

public class Utils {
	private static String umlProfileFilePath = "umlDiagrams/profiles/QuantumUMLProfile.profile.uml"; // Path to uml profile file
	
	/**
	 * Method that loads uml file into UmlModel.
	 * 
	 * @param umlFilePath Path to uml file.
	 * @return Returns UmlModel type corresponding to uml file.
	 */
	public static UmlModel loadUml(String umlFilePath) {
		UmlModel umlModel = new UmlModel();
		umlModel.setModelFile(umlFilePath);
		umlModel.setMetamodelFile(umlProfileFilePath); // Add the UML profile as a metamodel reference
		try {
			umlModel.load();
		} catch (EolModelLoadingException e) {
			e.printStackTrace();
			System.out.printf("Failed to load %s%n", umlFilePath);
		}
		return umlModel;
	}
	
	/**
	 * Method that parse egx file into EgxModule.
	 * 
	 * @param egxFilePath Path to egx file.
	 * @return  Returns EgxModule type corresponding to egx file.
	 */
	public static EgxModule parseEgxFile(String egxFilePath) {
		EgxModule module = new EgxModule(new EglFileGeneratingTemplateFactory());
		try {
			module.parse(new File(egxFilePath).getAbsoluteFile());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.printf("Syntax errors found at %s%n", egxFilePath);
		}
		return module;
	}
	
	/**
	 * Method that copies directory from srcDir to destDir.
	 * 
	 * @param srcDir Source directory.
	 * @param destDir Destiny directory.
	 */
	public static void copyCircuits(File srcDir, File destDir) {
		try {
			FileUtils.copyDirectory(srcDir, destDir, true);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.printf("Failed to copy circuits to %s%n", destDir);
		}
	}
	
	/**
	 * Method that deletes temp directory
	 */
	public static void deleteTempDir() {
		try {
			FileUtils.deleteDirectory(new File("temp"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to delete temp directory");
		}
	}
    public static void crearDirectorio(String rutaDirectorio) {
        // Crea un objeto Path con la ruta del directorio
        Path directorio = Paths.get(rutaDirectorio);

        // Comprueba si el directorio ya existe
        if (!Files.exists(directorio)) {
            // Intenta crear el directorio
            try {
                Files.createDirectories(directorio);
                System.out.println("Directorio creado con éxito.");
            } catch (Exception e) {
                System.out.println("No se pudo crear el directorio.");
            }
        } else {
            System.out.println("El directorio ya existe.");
        }
    }
}
