package sequenceDiagram;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;
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
	
//    opcode - the opcode of the type instruction to be visited. This opcode is either INVOKEVIRTUAL, INVOKESPECIAL, INVOKESTATIC or INVOKEINTERFACE.
//    owner - the internal name of the method's owner class (see getInternalName).
//    name - the method's name.
//    desc - the method's descriptor (see Type).
//    itf - if the method's owner class is an interface.
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		
		if(name.equals("<init>")) {
			this.currentMethod.setInit(true);
		}
		// TODO DO STUFF HERE
		
	}
}
