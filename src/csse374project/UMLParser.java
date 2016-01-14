package csse374project;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.Classes;
import classRepresentation.UMLClass;
import interfaces.IClass;

public class UMLParser {

	private static String[] classesToAccept;

	public static void main(String[] args) throws IOException {
		Classes classes = new Classes();

		setClassesToAccept(args);

		for (String className : args) {
			IClass currentClass = new UMLClass();

			ClassReader reader = new ClassReader(className);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);

			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, currentClass);

			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass);
			
			ClassVisitor classCodeVisitor = new ClassCodeVisitor(Opcodes.ASM5, methodVisitor, currentClass);

			reader.accept(classCodeVisitor, ClassReader.EXPAND_FRAMES);
			classes.addClass(currentClass);
		}

		System.out.println(classes.toGraphViz());
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

	/*
	 * private Integer testThing(){ return -1; }
	 */

}
