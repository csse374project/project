package sequenceDiagram;

import java.util.ArrayList;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import classRepresentation.SequenceClass;
import classRepresentation.SequenceMethodCall;

public class SingleMethodVisitor extends ClassVisitor {

	private String fullMethodName;
	private SequenceClass currentClass;
	
	public SingleMethodVisitor(int opCode, SequenceClass currentClass, String methodName) {
		super(opCode);
		this.currentClass = currentClass;
		this.fullMethodName = methodName;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);

		if(name.equals("<init>")) return toDecorate;
		int lastDot = fullMethodName.lastIndexOf('.');
		int lastOpenParen = fullMethodName.lastIndexOf('(');
		String methodName = fullMethodName.substring(lastDot+1, lastOpenParen);

		if (name.equals(methodName)) {
			// TODO I'm not sure how this works or what it does, we should figure that out.
			MethodVisitor codeVisitor = new SequenceMethodCodeVisitor(Opcodes.ASM5, toDecorate, currentClass);
			System.out.println("visit");
		
			SequenceMethodCall method = new SequenceMethodCall();
			method.setName(name);
			method.setInvoker(currentClass.getName());
			method.setReturnType(Type.getReturnType(desc).getClassName());
			
			Type[] argTypes = Type.getArgumentTypes(desc);
			ArrayList<String> parameterClassNames = new ArrayList<String>();
			for (int i = 0; i < argTypes.length; i++) {
				String parameterName = argTypes[i].getClassName();
				parameterClassNames.add(parameterName);
			}
			method.setParameters(parameterClassNames);
			
			System.out.println("singleMethodVisitor - signature: " + signature);
			System.out.println("WARNING: still need to set the class this method is called from!");
			
			return codeVisitor;
		}
		return toDecorate;
	}
	

}
