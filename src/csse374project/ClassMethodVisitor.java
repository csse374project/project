package csse374project;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import classRepresentation.Method;
import csse374project.ClassFieldVisitor.SigVisitor;
import interfaces.IClass;
import interfaces.IMethod;
import jdk.internal.org.objectweb.asm.signature.SignatureReader;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

import java.util.ArrayList;

public class ClassMethodVisitor extends ClassVisitor {

	private IClass currentClass;

	public ClassMethodVisitor(int arg0, ClassVisitor arg1, IClass currentClass) {
		super(arg0, arg1);
		this.currentClass = currentClass;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
	
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		MethodVisitor codeVisitor = new MethodCodeVisitor(Opcodes.ASM5, toDecorate, currentClass);

		Type[] argTypes = Type.getArgumentTypes(desc);
		ArrayList<String> parameterClassNames = new ArrayList<String>();
		for (int i = 0; i < argTypes.length; i++) {
			String parameterName = argTypes[i].getClassName();
			parameterClassNames.add(parameterName);
			currentClass.addUsedClass(parameterName.replace('.', '/'));
		}
		
		char vis = ' ';
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			vis = '+';
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			vis = '-';
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			vis = '#';
		}

		IMethod method = new Method();
		method.setName(name);
		method.setParameters(parameterClassNames);
		method.setReturnType(Type.getReturnType(desc).getClassName());
		method.setVisibility(vis);

		currentClass.addMethod(method);
		
		currentClass.addUsedClass(Type.getReturnType(desc).getClassName().replace('.', '/'));
		handleSignature(signature);
		
		return codeVisitor;
	}
	
	public void handleSignature(String signature) {
		if (signature == null) return;
		SignatureVisitor sigVis = new SigVisitor(Opcodes.ASM5);
		SignatureReader sigReader = new SignatureReader(signature);
		sigReader.accept(sigVis);

	}

	class SigVisitor extends SignatureVisitor {
		
		public SigVisitor(int opcode) {
			super(opcode);
		}

		@Override
		public void visitClassType(String name) {
			currentClass.addUsedClass(name);
		}
	}
}
