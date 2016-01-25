package umlDiagram;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import interfaces.IClass;
import jdk.internal.org.objectweb.asm.Type;

public class SingletonMethodVisitor extends ClassVisitor {

	private IClass currentClass;
	
	public SingletonMethodVisitor(int opCode, IClass currentClass) {
		super(opCode);
		this.currentClass = currentClass;
	}
	
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		Type returnType = Type.getReturnType(desc);
		if (currentClass.getName().equals(UMLParser.replaceDotsWithSlashes(returnType.getClassName()))) {
			currentClass.setIsSingleton(true);
			currentClass.addAssociatedClass(currentClass.getName());
		}
		return toDecorate;
	}

}
