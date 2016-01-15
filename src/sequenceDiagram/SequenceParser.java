package sequenceDiagram;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.MethodCalls;
import classRepresentation.SequenceClass;
import interfaces.IClass;
import umlDiagram.MethodDeclarationVisitor;
import umlDiagram.ClassMethodVisitor;

public class SequenceParser {
	
	public static MethodCalls calls = new MethodCalls();
	
	private static final int DEFAULT_DEPTH_LIMIT = 5;
	
	public static void main(String[] args) throws IOException {
		if(args.length > 2 || args.length == 0) {
			System.err.printf("incorrect argument number, expected 1 method name and recieved %d\n", args.length);
			return;
		}
		int depthLimit = DEFAULT_DEPTH_LIMIT;
		if(args.length == 2) {
			depthLimit = Integer.parseInt(args[1]);
		}
		String methodName = args[0];
		int lastIndex = methodName.lastIndexOf(".");
		String className = methodName.substring(0, lastIndex);
		System.out.println("DEBUG - method name: " + methodName);
		System.out.println("DEBUG - class name: " + className);
		
//		SequenceClass currentClass = new SequenceClass();
		ClassReader reader = new ClassReader(className);

		ClassVisitor singleMethodVisitor = new SingleMethodVisitor(Opcodes.ASM5, 0, depthLimit,
				methodName, className);

		reader.accept(singleMethodVisitor, ClassReader.EXPAND_FRAMES);
	}
}