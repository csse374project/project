package classRepresentation.designPaterns;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AdapterMethodVisitor extends MethodVisitor {

	private String fieldName;
	private MutableBoolean bool;
	private boolean fieldFound;
	
	public AdapterMethodVisitor(int opCode, MethodVisitor toDecorate,
			String field, String type, MutableBoolean bool) {
		super(opCode, toDecorate);
		fieldName = field;
		this.bool = bool;
	}
	
	@Override
	public void visitCode() {
		super.visitCode();
		fieldFound = false;
	}
	
	@Override
	public void visitEnd() {
		super.visitEnd();
		bool.value = bool.value && fieldFound;
	}
	
	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		if (name.equals(fieldName) && ((opcode & Opcodes.GETFIELD) != 0)) {
			fieldFound = true;
		}
	}
	
	

}
