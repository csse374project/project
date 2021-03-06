package sequenceDiagram;

import java.io.IOException;
//import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import classRepresentation.SequenceMethodCall;

public class SequenceMethodCodeVisitor extends MethodVisitor {

	private int depth, depthLimit;
	private String invoker;
	private SequenceMethodCall currentMethod;

	public SequenceMethodCodeVisitor(int opCode, int depth, int depthLimit,
			MethodVisitor toDecorate, SequenceMethodCall method, String invoker) {
		super(opCode, toDecorate);
		this.currentMethod = method;
		this.depth = depth;
		this.depthLimit = depthLimit;
		this.invoker = invoker;
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {

		if (depth > depthLimit) {
			return;
		}
		String className = owner.replace('/', '.');
		ClassReader reader = null;
		try {
			reader = new ClassReader(className);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String qualifiedMethodName = className + "." + name + "(";
		Type[] argTypes = Type.getArgumentTypes(desc);
		// ArrayList<String> parameterClassNames = new ArrayList<String>();
		for (int i = 0; i < argTypes.length; i++) {
			String parameterName = getParamName(argTypes[i]);
			qualifiedMethodName += parameterName + ",";
		}
		if (argTypes.length > 0) {
			qualifiedMethodName = qualifiedMethodName.substring(0, qualifiedMethodName.length() - 1);
		}

		qualifiedMethodName += ")";
		String invokerName = invoker.replace('/', '.');
//		int classNameDot = className.lastIndexOf('.');
//		String shortClassName = className.substring(classNameDot + 1);
		ClassVisitor singleMethodVisitor = new SingleMethodVisitor(Opcodes.ASM5, depth+1, depthLimit,
				className, qualifiedMethodName, invokerName);
		
		if (name.equals("<init>")) {
			this.currentMethod.setInit(true);
//			return;
		}

		reader.accept(singleMethodVisitor, ClassReader.EXPAND_FRAMES);
	}
	
//	@Override
//	public void visitTypeInsn(int opcode, String type) {
//		super.visitTypeInsn(opcode, type);
//		if (opcode == Opcodes.NEW) {
//			this.currentMethod.setInit(true);
//		}
//	}
	
	private String getParamName(Type type) {
		String fullParamPath = type.getClassName();
		int dotIndex = fullParamPath.lastIndexOf('.');
		return fullParamPath.substring(dotIndex+1);
	}
}
