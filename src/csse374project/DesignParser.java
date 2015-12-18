package csse374project;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.Classes;
import classRepresentation.Class;
import interfaces.IClass;

public class DesignParser {
	public static void main(String[] args) throws IOException{
		Classes classes = new Classes();
		
		for(String className : args){
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
}
