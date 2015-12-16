package csse374project;

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;

import interfaces.IClass;

public class ClassDeclarationVisitor extends ClassVisitor {

	public ClassDeclarationVisitor(int arg0) {
		super(arg0);
	}
	
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces, IClass class2construct){
		System.out.println("Class: "+ name+" extends "+superName+" implements "+Arrays.toString(interfaces));
		super.visit(version, access, name, signature, superName, interfaces);
	}

}
