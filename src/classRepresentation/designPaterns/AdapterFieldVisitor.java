package classRepresentation.designPaterns;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AdapterFieldVisitor extends ClassVisitor {

	private String fieldName, fieldType;
	private MutableBoolean bool;
	
	public AdapterFieldVisitor(int opcode, String field, String type, MutableBoolean bool) {
		super(opcode);
		fieldType = type;
		this.fieldName = field;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature,
			String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		MethodVisitor codeVisitor = new AdapterMethodVisitor(Opcodes.ASM5, toDecorate,
				fieldName, fieldType, bool);
		return codeVisitor;
	}
		
}
