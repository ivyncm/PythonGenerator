package generator.pythonGenerator.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.uml.UmlModel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;

public class Generator {
	private static String egxClassFilePath = "EGLtemplates/classGenJava.egx"; // Path to egx class file
	private static String egxActivityFilePath = "EGLtemplates/activityGen.egx"; // Path to egx activity file
	private List<String> umlClassFiles; // Path to uml classes directory
	private List<String> umlActivityFiles; // Path to uml activities directory
	
	public Generator(List<String> umlClassFiles, List<String> umlActivityFiles) {
		this.umlClassFiles = umlClassFiles;
		this.umlActivityFiles = umlActivityFiles;
	}
	
	/**
	 * Main method for the complete execution of the transformations.
	 * 
	 * @param args
	 * @throws EolModelElementTypeNotFoundException
	 */
	public void execute() throws EolModelElementTypeNotFoundException {
		List<String> activities = iterateListAndExecuteActivity(); // Iterate activity diagrams from directory and save name of circuits on List
		iterateListAndExecuteClass(activities); // Iterate class diagrams from directory
	}
	
	/**
	 * Method that iterates through a list looking for uml activity files and execute egx/egl transformation on them.
	 * 
	 * @return Returns List containing name of circuits.
	 * @throws EolModelElementTypeNotFoundException
	 */
	public List<String> iterateListAndExecuteActivity() throws EolModelElementTypeNotFoundException {
		List<String> activities = new ArrayList<String>();
		for (String filename : this.umlActivityFiles) {
			File file = new File(filename);
			if (!file.isDirectory()) {
				System.out.println("Executing activity: ".concat(file.toString().replace('\\', '/')));
				activities.add(executeGeneratorActivity(file.toString().replace('\\', '/')));
			}
		}
		return activities;
	}
	
	/**
	 * Method that iterates through a list looking for uml class files and execute egx/egl transformation on them.
	 * 
	 * @throws EolModelElementTypeNotFoundException
	 */
	public void iterateListAndExecuteClass(List<String> activities) throws EolModelElementTypeNotFoundException {
		for (String filename : this.umlClassFiles) {
			File file = new File(filename);
			if (!file.isDirectory()) {
				System.out.println("Executing class: ".concat(file.toString().replace('\\', '/')));
				executeGeneratorClass(file.toString().replace('\\', '/'), activities);
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
	public String executeGeneratorActivity(String umlFilePath) throws EolModelElementTypeNotFoundException {
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
	public void executeGeneratorClass(String umlFilePath, List<String> activities) throws EolModelElementTypeNotFoundException {
		EgxModule module = Utils.parseEgxFile(egxClassFilePath); // Parse egxFilePath.egx
		UmlModel umlModel = Utils.loadUml(umlFilePath); // Load UmlModel
		
		// Add umlModel and parameters to egx file
		module.getContext().getModelRepository().addModel(umlModel); // Make the document visible to the EGX program
		if(activities.size() == 0) {
			module.getContext().getFrameStack().put("ListActivities", activities.toString()); // Make the activity names visible to the EGX program
		}else {
			module.getContext().getFrameStack().put("ListActivities", activities.toString().substring(1, activities.toString().length()-1)); // Make the activity names visible to the EGX program
		}
		try {
			module.execute(); // Execute egxFileName.egx
		} catch (EolRuntimeException e) {
			e.printStackTrace();
			System.out.printf("Failed to execute %s%n", umlFilePath);
		}
		
		// Copy circuits(activities) to class generated directory
		File destDir = new File("temp/RESULTS/".concat(umlModel.getAllOfType("Model").toString().split("name: ")[1].split(",")[0].concat("/quantumCircuits")));
		File circuitsDir = new File("temp/RESULTS/quantumCircuits");
		if (circuitsDir.exists() && circuitsDir.isDirectory()) {
			Utils.copyCircuits(circuitsDir, destDir);
        } 
	}
	
    public List<String> getumlClassFiles() {
        return this.umlClassFiles;
    }
    public void setumlClassFiles(List<String> umlClassFiles) {
    	this.umlClassFiles = umlClassFiles;
    }
    public List<String> getumlActivityFiles() {
        return this.umlActivityFiles;
    }
    public void setumlActivityFiles(List<String> umlActivityFiles) {
    	this.umlActivityFiles = umlActivityFiles;
    }
    public String getegxClassFilePath() {
        return egxClassFilePath;
    }
    public String getegxActivityFilePath() {
        return egxActivityFilePath;
    }
	
}