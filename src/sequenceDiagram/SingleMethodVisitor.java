package sequenceDiagram;

import java.util.ArrayList;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import classRepresentation.UMLMethod;
import interfaces.IClass;
import interfaces.IMethod;

public class SingleMethodVisitor extends ClassVisitor {

	private String methodName;
	private SequenceClass currentClass;
	
	public SingleMethodVisitor(int opCode, SequenceClass currentClass, String methodName) {
		super(opCode);
		this.currentClass = currentClass;
		this.methodName = methodName;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);

//		Type[] argTypes = Type.getArgumentTypes(desc);
//		ArrayList<String> parameterClassNames = new ArrayList<String>();
//		for (int i = 0; i < argTypes.length; i++) {
//			String parameterName = argTypes[i].getClassName();
//			parameterClassNames.add(parameterName);
//			currentClass.addUsedClass(parameterName.replace('.', '/'));
//		}

//		char vis = ' ';
//		if ((access & Opcodes.ACC_PUBLIC) != 0) {
//			vis = '+';
//		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
//			vis = '-';
//		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
//			vis = '#';
//		}
		if (name.equals(methodName)) {
			// TODO I'm not sure how this works or what it does, we should figure that out.
			MethodVisitor codeVisitor = new SequenceMethodCodeVisitor(Opcodes.ASM5, toDecorate, currentClass);
		
			SequenceMethod method = new SequenceMethod();
			method.setName(name);
			method.setInvoker(currentClass.getName());
			System.out.println("singleMethodVisitor - signature: " + signature);
			System.out.println("WARNING: still need to set the class this method is called from!");
			
			return codeVisitor;
		}
		return toDecorate;
	}
	

}
