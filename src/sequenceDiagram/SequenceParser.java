package sequenceDiagram;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import interfaces.IClass;
import umlDiagram.MethodDeclarationVisitor;
import umlDiagram.ClassMethodVisitor;

public class SequenceParser {
	
	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			System.err.printf("incorrect argument number, expected 1 method name and recieved %d\n", args.length);
			System.exit(1);
		}
		String methodName = args[0];
		int lastIndex = methodName.lastIndexOf(".");
		String className = methodName.substring(0, lastIndex);
		System.out.println("DEBUG - method name: " + methodName);
		System.out.println("DEBUG - class name: " + className);
		
		IClass currentClass = new SequenceClass();
		ClassReader reader = new ClassReader(className);

		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor methodCodeVisitor = new MethodDeclarationVisitor(Opcodes.ASM5, methodVisitor, currentClass);

		reader.accept(methodCodeVisitor, ClassReader.EXPAND_FRAMES);
	}
}
