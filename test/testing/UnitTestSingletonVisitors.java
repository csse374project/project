package testing;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.UMLClass;
import interfaces.IClass;
import umlDiagram.ClassDeclarationVisitor;
import umlDiagram.SingletonFieldVisitor;
import umlDiagram.UMLParser;

public class UnitTestSingletonVisitors {

//	private static String className = "java.lang.String";
	private IClass currentClass;
	
	public void setup(String className) throws IOException {
		currentClass = new UMLClass();
		ClassReader reader = new ClassReader(className);
		ClassVisitor vis1 = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor vis2 = new SingletonFieldVisitor(Opcodes.ASM5, vis1, currentClass);
		reader.accept(vis2, ClassReader.EXPAND_FRAMES);
	}
	
	@Test
	public void testNegative() throws IOException {
		String className = "testingData.SampleClassForReadingInATest";
		setup(className);
		assertFalse(currentClass.isSingleton());
	}
	
	@Test
	public void testPossitive() throws IOException {
		String className = "testingData.SampleSingletonClass";
		setup(className);
		assertTrue(currentClass.isSingleton());
	}
	
	@Test
	public void testChocolateBoilerEager() throws IOException {
		String className = "testingData.ChocolateBoilerEager";
		setup(className);
		assertTrue(currentClass.isSingleton());
	}

	@Test
	public void testChocolateBoilerLazy() throws IOException {
		String className = "testingData.ChocolateBoilerLazy";
		setup(className);
		assertTrue(currentClass.isSingleton());
	}

	@Test
	public void testRuntime() throws IOException {
		String className = "java.lang.Runtime";
		setup(className);
		assertTrue(currentClass.isSingleton());
	}
	
	@Test
	public void testDesktop() throws IOException {
		String className = "java.awt.Desktop";
		setup(className);
		assertFalse(currentClass.isSingleton());
	}
	
	@Test
	public void testCalendar() throws IOException {
		String className = "java.util.Calendar";
		setup(className);
		assertFalse(currentClass.isSingleton());
	}
	
	@Test
	public void testFilterInputStream() throws IOException {
		String className = "java.io.FilterInputStream";
		setup(className);
		assertFalse(currentClass.isSingleton());
	}

}
