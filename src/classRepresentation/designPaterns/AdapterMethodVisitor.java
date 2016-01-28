package classRepresentation.designPaterns;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.decorators.TopLevelDecorator;
import interfaces.IField;

public class AdapterMethodVisitor extends MethodVisitor {

	TopLevelDecorator currentClass;
	IField currentField;
	
	public AdapterMethodVisitor(int opCode, MethodVisitor toDecorate, TopLevelDecorator clazz, IField field) {
		super(opCode, toDecorate);
		currentClass = clazz;
		currentField = field;
	}
	
	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		System.out.printf("Opcode: %d,\nowner: %s,\nname: %s,\ndesc: %s\n",
				opcode, owner, name, desc);
		if ((opcode & Opcodes.GETFIELD) != 0) {
			
		}
		
	}
	
	

}
