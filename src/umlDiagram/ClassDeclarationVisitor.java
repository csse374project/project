package umlDiagram;

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.decorators.IClassDecorator;
import classRepresentation.decorators.InterfaceDecorator;

public class ClassDeclarationVisitor extends ClassVisitor {

	private IClassDecorator currentClass;

	public ClassDeclarationVisitor(int arg0, IClassDecorator curClass) {
		super(arg0);
		this.currentClass = curClass;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		// Add needed info to Class
		currentClass.setName(name);
		currentClass.setSuperClass(superName);
		currentClass.setInterfaces(Arrays.asList(interfaces));

		if ((access & Opcodes.ACC_INTERFACE) != 0) {
			currentClass.decorate(new InterfaceDecorator());
		}

		super.visit(version, access, name, signature, superName, interfaces);
	}
}
