package csse374project;

import org.objectweb.asm.MethodVisitor;

import interfaces.IClass;

public class MethodCodeVisitor extends MethodVisitor {

	private IClass currentClass;

	public MethodCodeVisitor(int asm5, MethodVisitor toDecorate, IClass currentClass) {
		super(asm5, toDecorate);
		this.currentClass = currentClass;
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		if (!currentClass.getSuperClass().equals(owner)) {
			currentClass.addUsedClass(owner);
		}
	}

}
