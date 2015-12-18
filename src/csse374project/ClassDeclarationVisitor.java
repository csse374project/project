package csse374project;

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import interfaces.IClass;

public class ClassDeclarationVisitor extends ClassVisitor {

	private IClass currentClass;

	public ClassDeclarationVisitor(int arg0, IClass curClass) {
		super(arg0);
		this.currentClass = curClass;
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
		//Add needed info to Class
		currentClass.setName(name);
		currentClass.setSuperClass(superName);
		currentClass.setInterfaces(Arrays.asList(interfaces));
		
		if((access & Opcodes.ACC_INTERFACE) != 0){
			currentClass.setIsInterface(true);
		}
		
		
		//System.out.println("Class: "+ name+" extends "+superName+" implements "+Arrays.asList(interfaces));
		super.visit(version, access, name, signature, superName, interfaces);
	}
}
