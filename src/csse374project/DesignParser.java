package csse374project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.Classes;
import classRepresentation.Class;
import interfaces.IClass;

public class DesignParser {
	
	
	private static String[] classesToAccept;
	
	//private static Map<Integer, List<String>> var = new HashMap<Integer, List<String>>();
	
	public static void main(String[] args) throws IOException {
		Classes classes = new Classes();

		setClassesToAccept(args);
		
		for (String className : args) {
			IClass currentClass = new Class();

			ClassReader reader = new ClassReader(className);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);

			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, currentClass);

			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
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
	
/*	private Integer testThing(){
		return -1;
	}*/
	
}
