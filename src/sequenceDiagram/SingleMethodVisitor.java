package sequenceDiagram;

import java.util.ArrayList;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import classRepresentation.SequenceClass;
import classRepresentation.SequenceMethodCall;

public class SingleMethodVisitor extends ClassVisitor {

	private int depth, depthLimit;
	private String fullMethodName;
	private SequenceMethodCall method;

	public SingleMethodVisitor(int opCode, int depth, int depthLimit, String owner, String methodName,
			String invokerName) {
		super(opCode);
		this.fullMethodName = methodName;
		this.depth = depth;
		this.depthLimit = depthLimit;
		this.method = new SequenceMethodCall();
		if (invokerName != null) {
			int dotIndex = invokerName.lastIndexOf(".");
			method.setInvoker(invokerName.substring(dotIndex+1));
		} else {
			method.setInvoker(null);
		}
		int dotIndex = owner.lastIndexOf(".");
		method.setOwner(owner.substring(dotIndex+1));
		
	}
	
//	public SingleMethodVisitor(int opCode, int depth, int depthLimit, String methodName, String className) {
//		super(opCode);
//		this.className = className;
//		this.fullMethodName = methodName;
//		this.depth = depth;
//		this.depthLimit = depthLimit;
//		this.method = new SequenceMethodCall();
//	}

	private String getMethodName() {
		if (fullMethodName.contains("<init>")) {
			return "<init>";
		} 
		int startIndex = fullMethodName.lastIndexOf('.');
		int endIndex = fullMethodName.lastIndexOf('(');
		return fullMethodName.substring(startIndex + 1, endIndex);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {		
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);

		String methodName = getMethodName();

		if (name.equals(methodName)) {
			MethodVisitor codeVisitor = new SequenceMethodCodeVisitor(Opcodes.ASM5, depth, depthLimit,
					toDecorate, method, method.getOwner());

			SequenceParser.calls.addMethodCall(method);
			method.setName(name);
			method.setReturnType(Type.getReturnType(desc).getClassName());

			Type[] argTypes = Type.getArgumentTypes(desc);
			ArrayList<String> parameterClassNames = new ArrayList<String>();
			for (int i = 0; i < argTypes.length; i++) {
				String parameterName = argTypes[i].getClassName();
				parameterClassNames.add(parameterName);
			}
			method.setParameters(parameterClassNames);

			return codeVisitor;
		}
		return toDecorate;
	}
}
