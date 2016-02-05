package umlDiagram;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureVisitor;

import classRepresentation.UMLField;
import interfaces.IClass;
import interfaces.IField;
import org.objectweb.asm.signature.SignatureReader;

public class ClassFieldVisitor extends ClassVisitor {

	private IClass currentClass;
	private IField field;

	public ClassFieldVisitor(int arg0, ClassVisitor arg1, IClass currentClass) {
		super(arg0, arg1);
		this.currentClass = currentClass;
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();

		char vis = ' ';
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			vis = '+';
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			vis = '-';
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			vis = '#';
		}

		IField field = new UMLField();
		field.setName(name);
		field.setType(type);
		field.setVisibility(vis);
		this.field = field;

		currentClass.addField(field);
		currentClass.addAssociatedClass(type.replace('.', '/'));
		handleSignature(signature, type);
		return toDecorate;
	}

	public void handleSignature(String signature, String type) {
		if (signature == null)
			return;
		SignatureVisitor sigVis = new SigVisitor(Opcodes.ASM5, type);
		SignatureReader sigReader = new SignatureReader(signature);
		sigReader.accept(sigVis);

	}

	class SigVisitor extends SignatureVisitor {

		String type;
		
		public SigVisitor(int opcode, String type) {
			super(opcode);
			this.type = type;
		}

		@Override
		public void visitClassType(String name) {
			currentClass.addAssociatedClass(name);
			
			if(!name.equals(type.replace('.', '/')))
				field.addInteriorType(name);
		}
	}
}
