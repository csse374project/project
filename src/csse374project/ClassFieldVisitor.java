package csse374project;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import classRepresentation.Field;
import interfaces.IClass;
import interfaces.IField;
import jdk.internal.org.objectweb.asm.signature.SignatureReader;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class ClassFieldVisitor extends ClassVisitor {

	private IClass currentClass;

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

		IField field = new Field();
		field.setName(name);
		field.setType(type);
		field.setVisibility(vis);

		currentClass.addField(field);
		// System.out.println(" "+type+" "+name);
		handleSignature(signature);
		return toDecorate;
	}
	
	private void handleSignature(String signature) {
		if (signature == null) {
			return;
		}
		SignatureVisitor sigVis = new MySigVisitor(Opcodes.ASM5);
		SignatureReader sigReader = new SignatureReader(signature);
		sigReader.accept(sigVis);
		
	}
	
	class MySigVisitor extends SignatureVisitor {

		public MySigVisitor(int opcode) {
			super(opcode);
		}
		@Override
		public void visitClassType(String name) {
			currentClass.addAssociatedClass(name);
		}
	}

}
