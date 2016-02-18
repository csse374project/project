package classRepresentation.designPatterns;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import classRepresentation.decorators.AdapterDecorator;
import classRepresentation.decorators.IClassDecorator;
import gui.DesignPatternInstance;

public class AdapterClassVisitor extends ClassVisitor {

	IClassDecorator currentClass;
	List<DesignPatternInstance> designPatternInstances;

	public AdapterClassVisitor(int opCode, ClassVisitor toDecorate, IClassDecorator clazz, List<DesignPatternInstance> designPatternInstances) {
		super(opCode, toDecorate);
		currentClass = clazz;
		this.designPatternInstances = designPatternInstances;
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		if (currentClass.getInterfaces().size() != 1) {
			return toDecorate;
		}
		try {
			String type = Type.getType(desc).getClassName();
			ClassReader reader = new ClassReader(currentClass.getName());
			MutableBoolean bool = new MutableBoolean();
			bool.value = true;
			ClassVisitor visitor = new AdapterFieldVisitor(Opcodes.ASM5, name, type, bool);
			reader.accept(visitor, ClassReader.EXPAND_FRAMES);
			if (bool.value) {
				String interfaze = currentClass.getInterfaces().get(0);
				List<String> classes = new ArrayList<String>();
				classes.add(currentClass.getName());
				classes.add(type);
				classes.add(interfaze);
				DesignPatternInstance instance = new DesignPatternInstance(currentClass.getName(), "Adapter", classes);
				designPatternInstances.add(instance);
				currentClass.decorate(new AdapterDecorator(type, interfaze));
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.printf("FAILED TO FIND CLASS '%s' INSIDE 'visitField' INSIDE CLASS 'AdapterClassVisitor'\n",
					name);
		}
		return toDecorate;
	}

}
