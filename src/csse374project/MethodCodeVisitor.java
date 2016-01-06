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
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf){
//		System.out.println("Opcode: " + opcode);
//		System.out.println("Owner: " + owner);
//		System.out.println("Name: " + name);
//		System.out.println("Description: " + desc);
//		System.out.println("\n");
		
		if (name.equals("<init>")) {
			currentClass.addAssociatedClass(owner);
		}
	}

}
