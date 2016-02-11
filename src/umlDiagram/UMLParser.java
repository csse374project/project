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
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.Classes;
import classRepresentation.UMLClass;
import classRepresentation.decorators.IClassDecorator;
import classRepresentation.decorators.TopLevelDecorator;
import classRepresentation.designPaterns.AdapterClassVisitor;
import classRepresentation.designPaterns.AdapterDetector;
import classRepresentation.designPaterns.CompositeDetector;
import classRepresentation.designPaterns.DecoratorDetector;
import classRepresentation.designPaterns.DesignPatternDetector;
import interfaces.IClass;

public class UMLParser {


	public static void main(String[] args) throws IOException {
		setClassesToAccept(args);
		UMLParser parser = new UMLParser(Arrays.asList(args), "", "", "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe");
		parser.parseByteCode();
		parser.detectPatterns();
	}
	
	private static String[] classesToAccept = new String[0];
	@SuppressWarnings("unused") //Will be used once the UI gets farther along
	private String inputFolder, outputDir, dotPath;
	private List<String> inputClasses;
	private Classes classes;
	
	
	public UMLParser(List<String> argClasses, String inputFolder, String outputDirectory, String dotPath){
		classes = new Classes();
		inputClasses = argClasses;
		this.inputFolder = inputFolder;
		this.outputDir = outputDirectory;
		this.dotPath = dotPath;
	}

	
	private void parseByteCode() throws IOException{
		for(String className : inputClasses){	
			IClass currentClass = new UMLClass();
			IClassDecorator topLevelDecorator = new TopLevelDecorator(currentClass);
			ClassReader reader = new ClassReader(className);
	
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, topLevelDecorator);
	
			ClassVisitor singletonVisitor = new SingletonFieldVisitor(Opcodes.ASM5, declVisitor, topLevelDecorator);
	
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, singletonVisitor, topLevelDecorator);
	
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, topLevelDecorator);
	
			ClassVisitor classCodeVisitor = new MethodDeclarationVisitor(Opcodes.ASM5, methodVisitor,
					topLevelDecorator);
	
			ClassVisitor adapterVisitor = new AdapterClassVisitor(Opcodes.ASM5, classCodeVisitor, topLevelDecorator);
	
			reader.accept(adapterVisitor, ClassReader.EXPAND_FRAMES);
			classes.addClass(topLevelDecorator);
		}
	}
	
	private void detectPatterns(){
		DesignPatternDetector decDet = new DecoratorDetector(classes);
		decDet.detectPattern();

		DesignPatternDetector adapterizer = new AdapterDetector(classes);
		adapterizer.detectPattern();

		DesignPatternDetector composite = new CompositeDetector(classes);
		composite.detectPattern();

		String digraph = classes.printGraphVizInput();
		createGraph(digraph);
		//System.out.println(digraph);
	}

	private void createGraph(String digraph) {
		// Temp file to write digraph string to
		// Will be deleted once complete
		Path path = Paths.get("temp.dot");

		try (final BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8,
				StandardOpenOption.CREATE);) {
			writer.write(digraph);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//TODO this will need to be replaced with dotPath once we recieve this argument from the UI
		ProcessBuilder pb = new ProcessBuilder(this.dotPath, "-Tpng", "temp.dot", "-o", "out.png");

		try {
			// Process p = pb.start();
			File log = new File("errorLog.txt");
			pb.redirectErrorStream(true);
			pb.redirectOutput(Redirect.appendTo(log));
			pb.start();
			//Files.delete(path); //intended to be used to clean up temp file...breaks it
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

	public static boolean classIsUsed(String className) {
		for (int i = 0; i < classesToAccept.length; i++) {
			if (classesToAccept[i].equals(className)) {
				return true;
			}
		}
		return false;
	}

	public static String replaceDotsWithSlashes(String string) {
		return string.replace('.', '/');
	}
}
