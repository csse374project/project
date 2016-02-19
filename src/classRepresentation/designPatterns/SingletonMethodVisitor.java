package classRepresentation.designPatterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import classRepresentation.decorators.IClassDecorator;
import classRepresentation.decorators.SingletonDecorator;
import gui.DesignPatternInstance;
import jdk.internal.org.objectweb.asm.Type;
import umlDiagram.UMLParser;

public class SingletonMethodVisitor extends ClassVisitor {

	private IClassDecorator currentClass;
	private boolean requireGetInstance;
	private List<DesignPatternInstance> designPatternInstances;
	
	public SingletonMethodVisitor(int opCode, IClassDecorator currentClass, String[] args, List<DesignPatternInstance> designPatternInstances) {
		super(opCode);
		this.currentClass = currentClass;
		this.designPatternInstances = designPatternInstances;
		List<String> tempArgs = Arrays.asList(args);
		if (tempArgs.contains("requireGetInstance")) {
			requireGetInstance = true;
		}
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		Type returnType = Type.getReturnType(desc);
		if (currentClass.getName().equals(UMLParser.replaceDotsWithSlashes(returnType.getClassName()))) {
			if (!requireGetInstance || name.equals("getInstance")) {
				List<String> classes = new ArrayList<String>();
				classes.add(currentClass.getName());
				DesignPatternInstance instance = new DesignPatternInstance(currentClass.getName(), "Singleton", classes);
				designPatternInstances.add(instance);
				currentClass.decorate(new SingletonDecorator());
				currentClass.addAssociatedClass(currentClass.getName());
				System.out.println(currentClass.getAssociatedClasses());
			}
		}
		return toDecorate;
	}

}
