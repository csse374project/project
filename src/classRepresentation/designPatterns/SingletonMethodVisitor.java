package classRepresentation.designPatterns;

import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import classRepresentation.decorators.IClassDecorator;
import classRepresentation.decorators.SingletonDecorator;
import jdk.internal.org.objectweb.asm.Type;
import umlDiagram.UMLParser;

public class SingletonMethodVisitor extends ClassVisitor {

	private IClassDecorator currentClass;
	private boolean requireGetInstance;
	
	public SingletonMethodVisitor(int opCode, IClassDecorator currentClass) {
		super(opCode);
		this.currentClass = currentClass;
		requireGetInstance = false;
	}
	
	public SingletonMethodVisitor(int opCode, IClassDecorator currentClass, String[] args) {
		super(opCode);
		this.currentClass = currentClass;
		List<String> tempArgs = Arrays.asList(args);
		if (tempArgs.contains("requireGetInstance")) {
			requireGetInstance = true;
		}
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		Type returnType = Type.getReturnType(desc);
		if (currentClass.getName().equals(UMLParser.replaceDotsWithSlashes(returnType.getClassName()))) {
			if (!requireGetInstance || name.equals("getInstance")) {
				currentClass.decorate(new SingletonDecorator());
				currentClass.addAssociatedClass(currentClass.getName());
			}
		}
		return toDecorate;
	}

}
