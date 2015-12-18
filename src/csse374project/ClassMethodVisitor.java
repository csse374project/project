package csse374project;

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import classRepresentation.Method;
import interfaces.IClass;
import interfaces.IMethod;
import classRepresentation.Parameter;
import java.util.ArrayList;

public class ClassMethodVisitor extends ClassVisitor {

	private IClass currentClass;

	public ClassMethodVisitor(int arg0, ClassVisitor arg1, IClass currentClass) {
		super(arg0, arg1);
		this.currentClass = currentClass;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions){
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		
		Type[] argTypes = Type.getArgumentTypes(desc);
		ArrayList<String> classNames = new ArrayList<String>();
		for(int i=0; i<argTypes.length; i++){
			classNames.add(argTypes[i].getClassName());
		}
		String symbol = "";
		if((access & Opcodes.ACC_PUBLIC) != 0){
			symbol = "+";
		}
		
		char vis = ' ';
		if((access & Opcodes.ACC_PUBLIC) != 0){
			vis = '+';
		}
		else if((access & Opcodes.ACC_PRIVATE) != 0){
			vis = '-';
		}
		else if((access & Opcodes.ACC_PROTECTED) != 0){
			vis = '#';
		}
		
		IMethod method = new Method();
		method.setName(name);
		method.setParameters(classNames);
		method.setReturnType(Type.getReturnType(desc).getClassName());
		method.setVisibility(vis);
		
		currentClass.addMethod(method);
		
		System.out.println("     method "+symbol+name+" "+classNames.toString()+" "+Type.getReturnType(desc).getClassName());
		
		return toDecorate;
	}

}
