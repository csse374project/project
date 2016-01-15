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
	private String className;

	public SingleMethodVisitor(int opCode, String methodName, String className) {
		super(opCode);
		this.className = className;
		this.fullMethodName = methodName;
	}

	private String getMethodName() {
		if (fullMethodName.equals("java.lang.StringBuilder.append(java.lang.String)")) {
			System.out.print("");
		}
		
		if (fullMethodName.contains("<init>")) {
			return "<init>";
		} 
		int startIndex = fullMethodName.lastIndexOf('.');
		int endIndex = fullMethodName.lastIndexOf('(');
		System.out.println("fullmethodname: " + fullMethodName);
		return fullMethodName.substring(startIndex + 1, endIndex);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);

		String methodName = getMethodName();

		if (name.equals(methodName)) {
			// TODO I'm not sure how this works or what it does, we should
			// figure that out.
			SequenceMethodCall method = new SequenceMethodCall();
			SequenceParser.calls.addMethodCall(method);
			MethodVisitor codeVisitor = new SequenceMethodCodeVisitor(Opcodes.ASM5, toDecorate, method);
			System.out.println("visit");

			method.setName(name);
			method.setInvoker(className);
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
