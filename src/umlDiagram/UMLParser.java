package umlDiagram;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.Classes;
import classRepresentation.UMLClass;
import classRepresentation.decorators.IClassDecorator;
import classRepresentation.decorators.TopLevelDecorator;
import classRepresentation.designPaterns.AdapterDetector;
import classRepresentation.designPaterns.DecoratorDetector;
import interfaces.IClass;

public class UMLParser {

	private static String[] classesToAccept = new String[0];

	public static void main(String[] args) throws IOException {
		Classes classes = new Classes();

		setClassesToAccept(args);

		for (String className : args) {
			IClassDecorator topLevelDecorator = new TopLevelDecorator();
			IClass currentClass = new UMLClass();
			topLevelDecorator.setDecorates(currentClass);

			ClassReader reader = new ClassReader(className);
			
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, topLevelDecorator);
			
			ClassVisitor singletonVisitor = new SingletonFieldVisitor(Opcodes.ASM5, declVisitor, topLevelDecorator);

			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, singletonVisitor, topLevelDecorator);

			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, topLevelDecorator);
			
			ClassVisitor classCodeVisitor = new MethodDeclarationVisitor(Opcodes.ASM5, methodVisitor, topLevelDecorator);
			
			reader.accept(classCodeVisitor, ClassReader.EXPAND_FRAMES);
			classes.addClass(topLevelDecorator);
		}
		DecoratorDetector decDet = new DecoratorDetector(classes);
		decDet.findDecorators();
		AdapterDetector adapterizer = new AdapterDetector(classes); 
		adapterizer.findAdapters();

		System.out.println(classes.printGraphVizInput());
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

	/*
	 * private Integer testThing(){ return -1; }
	 */

}
