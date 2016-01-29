package classRepresentation.designPaterns;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import classRepresentation.decorators.AdapterDecorator;
import classRepresentation.decorators.IClassDecorator;

public class AdapterClassVisitor extends ClassVisitor {

	IClassDecorator currentClass;

	public AdapterClassVisitor(int opCode, IClassDecorator clazz) {
		super(opCode);
		currentClass = clazz;
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		try {
			String type = Type.getType(desc).getClassName();
			ClassReader reader = new ClassReader(name);
			MutableBoolean bool = new MutableBoolean();
			bool.value = true;
			ClassVisitor visitor = new AdapterFieldVisitor(Opcodes.ASM5, name, type, bool);
			reader.accept(visitor, ClassReader.EXPAND_FRAMES);
			if (bool.value) {
				 currentClass.decorate(new AdapterDecorator(type));
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.printf("FAILED TO FIND CLASS '%s' INSIDE 'visitField' INSIDE CLASS 'AdapterClassVisirot'\n",
					name);
		}
		return toDecorate;
	}

}
