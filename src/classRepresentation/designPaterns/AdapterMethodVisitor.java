package classRepresentation.designPaterns;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import interfaces.IClass;
import interfaces.IField;

public class AdapterMethodVisitor extends MethodVisitor {

	private String fieldName, fieldType;
	private MutableBoolean bool;
	
	public AdapterMethodVisitor(int opCode, MethodVisitor toDecorate,
			String field, String type, MutableBoolean bool) {
		super(opCode, toDecorate);
		fieldName = field;
		fieldType = type;
		this.bool = bool;
	}
	
	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		System.out.printf("Opcode: %d,\nowner: %s,\nname: %s,\ndesc: %s\n",
				opcode, owner, name, desc);
		if ((opcode & Opcodes.GETFIELD) != 0) {
			// TODO
		}
	}
	
	

}
