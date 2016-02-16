package umlDiagram;

import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import classRepresentation.decorators.IClassDecorator;
import classRepresentation.decorators.SingletonDecorator;
import jdk.internal.org.objectweb.asm.Type;

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
		System.out.println("Howdy!  Looks like the variable requireGetInstance is " + requireGetInstance + "!");
		if (currentClass.getName().equals(UMLParser.replaceDotsWithSlashes(returnType.getClassName()))) {
			if (!requireGetInstance || name.equals("getInstance")) {
				System.out.println("You have added a class as a singleton!");
				currentClass.decorate(new SingletonDecorator());
				currentClass.addAssociatedClass(currentClass.getName());
			}
		}
		return toDecorate;
	}

}
