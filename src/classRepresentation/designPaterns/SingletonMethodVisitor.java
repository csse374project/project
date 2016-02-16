package classRepresentation.designPaterns;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import classRepresentation.decorators.IClassDecorator;
import classRepresentation.decorators.SingletonDecorator;
import jdk.internal.org.objectweb.asm.Type;
import umlDiagram.UMLParser;

public class SingletonMethodVisitor extends ClassVisitor {

	private IClassDecorator currentClass;

	public SingletonMethodVisitor(int opCode, IClassDecorator currentClass) {
		super(opCode);
		this.currentClass = currentClass;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		Type returnType = Type.getReturnType(desc);
		if (currentClass.getName().equals(UMLParser.replaceDotsWithSlashes(returnType.getClassName()))) {
			currentClass.decorate(new SingletonDecorator());
			currentClass.addAssociatedClass(currentClass.getName());
		}
		return toDecorate;
	}

}
