package umlDiagram;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import classRepresentation.decorators.IClassDecorator;

public class SingletonFieldVisitor extends ClassVisitor {

	private IClassDecorator currentClass;
	private String[] arguments;

	public SingletonFieldVisitor(int opCode, ClassVisitor toDecorate, IClassDecorator currentClass) {
		super(opCode, toDecorate);
		this.currentClass = currentClass;
		arguments = new String[0];
	}

	public SingletonFieldVisitor(int opCode, ClassVisitor toDecorate, IClassDecorator currentClass, String[] args) {
		super(opCode, toDecorate);
		this.currentClass = currentClass;
		arguments = args;
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);

		if ((access & Opcodes.ACC_STATIC) != 0) {
			Type type = Type.getType(desc);
			if (currentClass.getName().equals(UMLParser.replaceDotsWithSlashes(type.getClassName()))) {
				lookForGetter();
			}
		}
		return toDecorate;
	}

	private void lookForGetter() {
		SingletonMethodVisitor visitor = new SingletonMethodVisitor(Opcodes.ASM5, currentClass, arguments);
		ClassReader reader = null;
		try {
			reader = new ClassReader(currentClass.getName());
		} catch (IOException e) {
			System.err.println("YOU BROKE THE NAME YOU DUMMY!!!");
			e.printStackTrace();
		}
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
	}

}
