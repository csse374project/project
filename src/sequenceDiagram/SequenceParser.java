package sequenceDiagram;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.SequenceClass;
import csse374project.ClassCodeVisitor;
import csse374project.ClassMethodVisitor;
import interfaces.IClass;

public class SequenceParser {
	
	public static void main(String[] args) throws IOException {
		for (String className : args) {
			IClass currentClass = new SequenceClass();

			ClassReader reader = new ClassReader(className);

			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, currentClass);
			
			ClassVisitor methodCodeVisitor = new ClassCodeVisitor(Opcodes.ASM5, methodVisitor, currentClass);

			reader.accept(methodCodeVisitor, ClassReader.EXPAND_FRAMES);
		}
	}
	
}
