package classRepresentation.designPaterns;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.decorators.TopLevelDecorator;
import interfaces.IField;

public class AdapterClassVisitor extends ClassVisitor{

	TopLevelDecorator currentClass;
	IField currentField;
	
	public AdapterClassVisitor(int opCode, TopLevelDecorator clazz, IField field) {
		super(opCode);
		currentClass = clazz;
		currentField = field;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		MethodVisitor methVisitor = new AdapterMethodVisitor(Opcodes.ASM5, toDecorate,
				currentClass, currentField);
		
		return methVisitor;
	}

}
