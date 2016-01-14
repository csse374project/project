package sequenceDiagram;

import java.io.IOException;
import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import classRepresentation.SequenceMethodCall;
import interfaces.IClass;

public class SequenceMethodCodeVisitor extends MethodVisitor {

	private IClass currentClass;
	private SequenceMethodCall currentMethod;
	
	public SequenceMethodCodeVisitor(int arg0, MethodVisitor toDecorate, IClass currentClass, SequenceMethodCall method) {
		super(arg0, toDecorate);
		this.currentClass = currentClass;
		this.currentMethod = method;
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		
		if(name.equals("<init>")) {
			this.currentMethod.setInit(true);
		}
		System.out.println("desc: " + desc );
		System.out.println("ownser: " + owner);
		
		String className = owner.replace('/', '.');
		ClassReader reader = null;
		try {
			reader = new ClassReader(className);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String qualifiedMethodName = className + name + "(";
		Type[] argTypes = Type.getArgumentTypes(desc);
		ArrayList<String> parameterClassNames = new ArrayList<String>();
		for (int i = 0; i < argTypes.length; i++) {
			String parameterName = argTypes[i].getClassName();
			qualifiedMethodName += parameterName + ",";
		}
		String qualifiedMethodName2 = qualifiedMethodName.substring(0, qualifiedMethodName.length()-1); 
		qualifiedMethodName2+=")";

		ClassVisitor singleMethodVisitor = new SingleMethodVisitor(Opcodes.ASM5, qualifiedMethodName2);

		reader.accept(singleMethodVisitor, ClassReader.EXPAND_FRAMES);
		
	}
}
