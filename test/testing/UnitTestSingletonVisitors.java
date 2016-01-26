package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Field;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.UMLClass;
import classRepresentation.decorators.IClassDecorator;
import classRepresentation.decorators.SingletonDecorator;
import classRepresentation.decorators.TopLevelDecorator;
import interfaces.IClass;
import umlDiagram.ClassDeclarationVisitor;
import umlDiagram.SingletonFieldVisitor;

public class UnitTestSingletonVisitors {

//	private static String className = "java.lang.String";
	private IClass currentClass;
	private TopLevelDecorator topDecorator;
	
	public void setup(String className) throws IOException {
		currentClass = new UMLClass();
		topDecorator = new TopLevelDecorator();
		topDecorator.setDecorates(currentClass);
		ClassReader reader = new ClassReader(className);
		ClassVisitor vis1 = new ClassDeclarationVisitor(Opcodes.ASM5, topDecorator);
		ClassVisitor vis2 = new SingletonFieldVisitor(Opcodes.ASM5, vis1, topDecorator);
		reader.accept(vis2, ClassReader.EXPAND_FRAMES);
	}
	
	@Test
	public void testNegative() 
			throws IOException, NoSuchFieldException, SecurityException,
					IllegalArgumentException, IllegalAccessException {
		String className = "testingData.SampleClassForReadingInATest";
		setup(className);
		assertFalse(isSingleton(topDecorator));
	}
	
	@Test
	public void testPossitive() 
			throws IOException, NoSuchFieldException, SecurityException,
					IllegalArgumentException, IllegalAccessException{
		String className = "testingData.SampleSingletonClass";
		setup(className);
		assertTrue(isSingleton(topDecorator));
	}
	
	@Test
	public void testChocolateBoilerEager() 
			throws IOException, NoSuchFieldException, SecurityException,
					IllegalArgumentException, IllegalAccessException {
		String className = "testingData.ChocolateBoilerEager";
		setup(className);
		assertTrue(isSingleton(topDecorator));
	}

	@Test
	public void testChocolateBoilerLazy() 
			throws IOException, NoSuchFieldException, SecurityException,
					IllegalArgumentException, IllegalAccessException {
		String className = "testingData.ChocolateBoilerLazy";
		setup(className);
		assertTrue(isSingleton(topDecorator));
	}

	@Test
	public void testRuntime() 
			throws IOException, NoSuchFieldException, SecurityException,
					IllegalArgumentException, IllegalAccessException {
		String className = "java.lang.Runtime";
		setup(className);
		assertTrue(isSingleton(topDecorator));
	}
	
	@Test
	public void testDesktop() 
			throws IOException, NoSuchFieldException, SecurityException,
					IllegalArgumentException, IllegalAccessException {
		String className = "java.awt.Desktop";
		setup(className);
		assertTrue(isSingleton(topDecorator));
	}
	
	@Test
	public void testCalendar() throws IOException, NoSuchFieldException, SecurityException,
	IllegalArgumentException, IllegalAccessException {
		String className = "java.util.Calendar";
		setup(className);
		assertFalse(isSingleton(topDecorator));
	}
	
	@Test
	public void testFilterInputStream() 
			throws IOException, NoSuchFieldException, SecurityException,
					IllegalArgumentException, IllegalAccessException {
		String className = "java.io.FilterInputStream";
		setup(className);
		assertFalse(isSingleton(topDecorator));
	}
	
	private boolean isSingleton(TopLevelDecorator clazz)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Field f = clazz.getClass().getDeclaredField("decorates");
		f.setAccessible(true);
		IClassDecorator cls = (IClassDecorator) f.get(clazz);
		while(true) {
			if (cls == null) {
				return false;
			}
			
			
			if (cls instanceof SingletonDecorator) {
				return true;
			}
			Object current = f.get(cls);
			if (current instanceof UMLClass) {
				return false;
			}
			cls = (IClassDecorator) current;
		}
	}

}
