package umlDiagram;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import classRepresentation.Classes;
import classRepresentation.UMLClass;
import classRepresentation.decorators.IClassDecorator;
import classRepresentation.decorators.TopLevelDecorator;
import classRepresentation.designPaterns.AdapterDetector;
import classRepresentation.designPaterns.CompositeDetector;
import classRepresentation.designPaterns.DecoratorDetector;
import classRepresentation.designPaterns.DesignPatternDetector;
import interfaces.IClass;

public class UMLParser {

	//main shouldn't be used to run from UI. Construct a UMLParser object
	//and call the appropriate methods.
	public static void main(String[] args) throws IOException {
		setClassesToAccept(args);
		Map<String, String[]> phaseAtt = new HashMap<String, String[]>();
		phaseAtt.put("Singleton", new String[]{"requireGetInstance"});
		List<String> phases = new LinkedList<String>();
		phases.add("Decorator");
		phases.add("Singleton");
		phases.add("Adapter");
		phases.add("Composite");
		UMLParser parser = new UMLParser(Arrays.asList(args), "", "",
				"C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe", phases, phaseAtt);
		parser.parseByteCode();
		parser.detectPatterns();
		parser.createGraph();
	}

	private static String[] classesToAccept = new String[0];
	@SuppressWarnings("unused") // Will be used once the UI gets farther along
	private String inputFolder, outputDir, dotPath;
	private Map<String, DesignPatternDetector> detectors;
	private Map<String, String[]> phaseAttributes;
	private List<String> inputClasses, inputPhases;
	private Classes classes;

	public UMLParser(List<String> argClasses, String inputFolder, String outputDirectory, String dotPath,
			List<String> phases, Map<String, String[]> phaseAttributes) {
		classes = new Classes();
		inputClasses = argClasses;
		this.inputFolder = inputFolder;
		this.outputDir = outputDirectory;
		this.dotPath = dotPath;
		this.inputPhases = phases;
		
		this.detectors = new HashMap<String, DesignPatternDetector>();
		detectors.put("Decorator", new DecoratorDetector(this.classes));
		detectors.put("Adapter", new AdapterDetector(this.classes));
		detectors.put("Composite", new CompositeDetector(this.classes));
		
		this.phaseAttributes = phaseAttributes;
	}

	/**
	 * Adds a detector to the system. The detector can then be used to detect patterns
	 * within the provided java bytecode.
	 * 
	 * @param name	name of the DecoratorDetector
	 * @param detector	DecoratorDetector object
	 */
	public void addDetectorPhase(String name, DesignPatternDetector detector) {
		this.inputPhases.add(name);
		this.detectors.put(name, detector);
	}
	
	public void addPhaseAttribute(String phaseName, String[] att){
		this.phaseAttributes.put(phaseName, att);
	}

	/**
	 * Parses the provided java classes and creates the class representation objects.
	 * 
	 * @throws IOException
	 */
	public void parseByteCode() throws IOException {
		for (String className : inputClasses) {
			IClass currentClass = new UMLClass();
			IClassDecorator topLevelDecorator = new TopLevelDecorator(currentClass);
			ClassReader reader = new ClassReader(className);

			ClassVisitor visitor = VisitorFactory.generateVisitors(this.inputPhases, topLevelDecorator, this.phaseAttributes);

			reader.accept(visitor, ClassReader.EXPAND_FRAMES);
			classes.addClass(topLevelDecorator);
		}
	}

	/**
	 * Detects various design patterns in the class representation objects created by 
	 * parseByteCode()
	 */
	public void detectPatterns() {
		for (String pattern : inputPhases) {
			DesignPatternDetector detector = detectors.get(pattern);
			//Check for patterns that were not added
			String[] args = this.phaseAttributes.get(pattern); 
			if (detector != null) detector.detectPattern(args);
		}
	}

	/**
	 * Creates the string to pass into graphViz and generates the graph.
	 */
	public void createGraph() {
		String digraph = this.classes.printGraphVizInput();
		// Temp file to write digraph string to
		Path path = Paths.get("./input_output/temp.dot");
		File f = path.toFile();
		f.delete();

		try (final BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8,
				StandardOpenOption.CREATE);) {
			writer.write(digraph);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ProcessBuilder pb = new ProcessBuilder(this.dotPath, "-Tpng", "temp.dot", "-o", ".\\input_output\\out.png");

		try {
			File log = new File(".\\input_output\\errorLog.txt");
			pb.redirectErrorStream(true);
			pb.redirectOutput(Redirect.appendTo(log));
			pb.start();
			// Files.delete(path); //intended to be used to clean up temp
			// file...breaks it
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void setClassesToAccept(String[] args) {
		classesToAccept = new String[args.length];
		for (int i = 0; i < classesToAccept.length; i++) {
			classesToAccept[i] = args[i].replace('.', '/');
		}
	}

	/**
	 * Checks to see if the given class name was provided as input
	 * 
	 * @param className	name of the class to check
	 * @return	true if the class was found in the input, false otherwise
	 */
	public static boolean classIsUsed(String className) {
		for (int i = 0; i < classesToAccept.length; i++) {
			if (classesToAccept[i].equals(className)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Replaces all dots, '.', in the given string with slashes, '/'.
	 * Helps to fix ASM's seeming arbitrary naming scheme.
	 * 
	 * @param string	String to modify
	 * @return	modified string
	 */
	public static String replaceDotsWithSlashes(String string) {
		return string.replace('.', '/');
	}
}
