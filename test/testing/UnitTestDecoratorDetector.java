package testing;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.Classes;
import classRepresentation.UMLClass;
import classRepresentation.decorators.ComponentDecorator;
import classRepresentation.decorators.DecoratorDecorator;
import classRepresentation.decorators.IClassDecorator;
import classRepresentation.decorators.TopLevelDecorator;
import classRepresentation.designPaterns.DecoratorDetector;
import interfaces.IClass;
import umlDiagram.ClassDeclarationVisitor;
import umlDiagram.ClassFieldVisitor;

public class UnitTestDecoratorDetector {

	private Classes classes;
	private IClassDecorator comp, decorator, child, singleton;

	@Before
	public void setUp() throws Exception {
		classes = new Classes();
		IClass clazz = new UMLClass();
		comp = new TopLevelDecorator(clazz);
		clazz = new UMLClass();
		decorator = new TopLevelDecorator(clazz);
		clazz = new UMLClass();
		child = new TopLevelDecorator(clazz);
		clazz = new UMLClass();
		singleton = new TopLevelDecorator(clazz);

		ClassReader reader = new ClassReader("interfaces.IClass");
		ClassVisitor vis1 = new ClassDeclarationVisitor(Opcodes.ASM5, comp);
		ClassVisitor vis2 = new ClassFieldVisitor(Opcodes.ASM5, vis1, comp);
		reader.accept(vis2, ClassReader.EXPAND_FRAMES);
		classes.addClass(comp);

		reader = new ClassReader("classRepresentation.decorators.IClassDecorator");
		vis1 = new ClassDeclarationVisitor(Opcodes.ASM5, decorator);
		vis2 = new ClassFieldVisitor(Opcodes.ASM5, vis1, decorator);
		reader.accept(vis2, ClassReader.EXPAND_FRAMES);
		classes.addClass(decorator);

		reader = new ClassReader("classRepresentation.decorators.DecoratorDecorator");
		vis1 = new ClassDeclarationVisitor(Opcodes.ASM5, child);
		vis2 = new ClassFieldVisitor(Opcodes.ASM5, vis1, child);
		reader.accept(vis2, ClassReader.EXPAND_FRAMES);
		
		reader = new ClassReader("classRepresentation.decorators.ComponentDecorator");
		vis1 = new ClassDeclarationVisitor(Opcodes.ASM5, child);
		vis2 = new ClassFieldVisitor(Opcodes.ASM5, vis1, child);
		reader.accept(vis2, ClassReader.EXPAND_FRAMES);
		classes.addClass(child);

		reader = new ClassReader("testingData.SampleSingletonClass");
		vis1 = new ClassDeclarationVisitor(Opcodes.ASM5, singleton);
		vis2 = new ClassFieldVisitor(Opcodes.ASM5, vis1, singleton);
		reader.accept(vis2, ClassReader.EXPAND_FRAMES);
		classes.addClass(singleton);

		DecoratorDetector detector = new DecoratorDetector(classes);
		detector.findDecorators();
	}

	@Test
	public void interfaceMarkedAsComponent() throws Exception {
		assertTrue(isComponent(comp));
	}

	@Test
	public void decoratorMarkedAsDecorator() throws Exception {
		assertTrue(isDecorator(decorator));
	}

	@Test
	public void childMarkedAsDecorator() throws Exception {
		assertTrue(isDecorator(child));
	}

	@Test
	public void interfaceNotMarkedAsDecorator() throws Exception {
		assertFalse(isDecorator(comp));
	}

	@Test
	public void decoratorNotMarkedAsComponent() throws Exception {
		assertFalse(isComponent(decorator));
	}

	@Test
	public void childNotMarkedAsComponent() throws Exception {
		assertFalse(isComponent(child));
	}

	@Test
	public void singletonNotMarkedAsComponent() throws Exception {
		assertFalse(isComponent(singleton));
	}

	@Test
	public void singtonNotMarkedAsDecorator() throws Exception {
		assertFalse(isDecorator(singleton));
	}
	
	@Test
	public void testMulitpleDecorators() throws Exception { 
		//Testing this because we had a problem were DecoratorDecorator would be added multiple times (once for each of its children)
		assertFalse(containsMultipleDecorators(comp));
	}
	
	private boolean containsMultipleDecorators(IClassDecorator clazz){
		int numDecorators = 0;
		IClassDecorator tmpClass = clazz;
		while(true){
			if(tmpClass.getDecorates() instanceof UMLClass) break;
			if(tmpClass.getDecorates() instanceof DecoratorDecorator) numDecorators++;
			tmpClass = (IClassDecorator) tmpClass.getDecorates();
		}
		return (numDecorators == 1);
	}

	private boolean isDecorator(IClass clazz) throws Exception {
		Field f = IClassDecorator.class.getDeclaredField("decorates");
		f.setAccessible(true);
		IClassDecorator cls;
		Object current = f.get(clazz);
		while (true) {
			if (current instanceof UMLClass) {
				return false;
			}
			cls = (IClassDecorator) current;
			if (cls == null) {
				return false;
			}
			if (cls instanceof DecoratorDecorator) {
				return true;
			}
			current = f.get(cls);
		}
	}

	private boolean isComponent(IClass clazz) throws Exception {
		Field f = IClassDecorator.class.getDeclaredField("decorates");
		f.setAccessible(true);
		IClassDecorator cls;
		Object current = f.get(clazz);
		while (true) {
			if (current instanceof UMLClass) {
				return false;
			}
			cls = (IClassDecorator) current;
			if (cls == null) {
				return false;
			}
			if (cls instanceof ComponentDecorator) {
				return true;
			}
			current = f.get(cls);
		}
	}

}
