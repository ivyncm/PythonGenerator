package generator.pythonGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.uml.UmlModel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;

public class Generator {
	private static String egxClassFilePath = "templates/classGen.egx"; // Path to egx class file
	private static String egxActivityFilePath = "templates/activityGen.egx"; // Path to egx activity file
	private static String umlClassDirectoryPath = "umlDiagrams/classes"; // Path to uml classes directory
	private static String umlActivityDirectoryPath = "umlDiagrams/activities"; // Path to uml activities directory

	/**
	 * Main method for the complete execution of the transformations.
	 * 
	 * @param args
	 * @throws EolModelElementTypeNotFoundException
	 */
	public static void main(String[] args) throws EolModelElementTypeNotFoundException {
		List<String> activities = iterateDirectoryAndExecuteActivity(); // Iterate activity diagrams from directory and save name of circuits on List
		iterateDirectoryAndExecuteClass(activities); // Iterate class diagrams from directory
		
		Utils.deleteTempDir();  // Delete temp directory
	}
	
	/**
	 * Method that iterates through a directory looking for uml activity files and execute egx/egl transformation on them.
	 * 
	 * @return Returns List containing name of circuits.
	 * @throws EolModelElementTypeNotFoundException
	 */
	public static List<String> iterateDirectoryAndExecuteActivity() throws EolModelElementTypeNotFoundException {
		List<String> activities = new ArrayList<String>();
		File[] files = new File(umlActivityDirectoryPath).listFiles();
		for (File filename : files) {
			if (!filename.isDirectory()) {
				System.out.println("Executing activity: ".concat(filename.toString().replace('\\', '/')));
				activities.add(executeGeneratorActivity(filename.toString().replace('\\', '/')));
			}
		}
		return activities;
	}
	
	/**
	 * Method that iterates through a directory looking for uml class files and execute egx/egl transformation on them.
	 * 
	 * @throws EolModelElementTypeNotFoundException
	 */
	public static void iterateDirectoryAndExecuteClass(List<String> activities) throws EolModelElementTypeNotFoundException {
		File[] files = new File(umlClassDirectoryPath).listFiles();
		for (File filename : files) {
			if (!filename.isDirectory()) {
				System.out.println("Executing class: ".concat(filename.toString().replace('\\', '/')));
				executeGeneratorClass(filename.toString().replace('\\', '/'), activities);
			}
		}
	}

	/**
	 * Method that executes an egx file on uml activity file.
	 * 
	 * @param umlFilePath Path to uml activity file.
	 * @return Returns name of activity (used for class transformation).
	 * @throws EolModelElementTypeNotFoundException
	 */
	public static String executeGeneratorActivity(String umlFilePath) throws EolModelElementTypeNotFoundException {
		EgxModule module = Utils.parseEgxFile(egxActivityFilePath); // Parse egxFilePath.egx
		UmlModel umlModel = Utils.loadUml(umlFilePath); // Load UmlModel
		
		module.getContext().getModelRepository().addModel(umlModel); // Make the document visible to the EGX program
		try {
			module.execute(); // Execute module egxFilePath.egx
		} catch (EolRuntimeException e) {
			e.printStackTrace();
			System.out.printf("Failed to execute %s%n", umlFilePath);
		}
		return umlModel.getAllOfType("Activity").toString().split("name: ")[1].split(",")[0].toLowerCase().replace(' ', '_').replace('-', '_');
	}
	
	
	/**
	 * Method that executes egx file on uml class file.
	 * 
	 * @param umlFilePath Path to uml class file.
	 * @param activities List contains name of activities.
	 * @throws EolModelElementTypeNotFoundException
	 */
	public static void executeGeneratorClass(String umlFilePath, List<String> activities) throws EolModelElementTypeNotFoundException {
		EgxModule module = Utils.parseEgxFile(egxClassFilePath); // Parse egxFilePath.egx
		UmlModel umlModel = Utils.loadUml(umlFilePath); // Load UmlModel
		
		// Add umlModel and parameters to egx file
		module.getContext().getModelRepository().addModel(umlModel); // Make the document visible to the EGX program
		module.getContext().getFrameStack().put("Parameters", activities.toString()); // Make the activity names visible to the EGX program
		try {
			module.execute(); // Execute egxFileName.egx
		} catch (EolRuntimeException e) {
			e.printStackTrace();
			System.out.printf("Failed to execute %s%n", umlFilePath);
		}
		
		// Copy circuits(activities) to class generated directory
		File destDir = new File("RESULTS/".concat(umlModel.getAllOfType("Model").toString().split("name: ")[1].split(",")[0].concat("/quantumCircuits")));
		Utils.copyCircuits(new File("temp/quantumCircuits"), destDir);
	}
	
}