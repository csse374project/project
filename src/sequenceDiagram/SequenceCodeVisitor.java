package sequenceDiagram;

import interfaces.IClass;
import jdk.internal.org.objectweb.asm.MethodVisitor;

public class SequenceCodeVisitor extends MethodVisitor {

	private IClass currentClass;
	
	public SequenceCodeVisitor(int arg0, MethodVisitor toDecorate, IClass currentClass) {
		super(arg0, toDecorate);
		this.currentClass = currentClass;
	}
	
//    opcode - the opcode of the type instruction to be visited. This opcode is either INVOKEVIRTUAL, INVOKESPECIAL, INVOKESTATIC or INVOKEINTERFACE.
//    owner - the internal name of the method's owner class (see getInternalName).
//    name - the method's name.
//    desc - the method's descriptor (see Type).
//    itf - if the method's owner class is an interface.
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		if(name.equals("<init>")) {
			
		} else {
			
		}
	}
}