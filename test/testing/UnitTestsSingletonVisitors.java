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

public class UnitTestsSingletonVisitors {

//	private static String className = "java.lang.String";
	private IClass currentClass;
	
	@Before
	public void setup()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException,
				IllegalAccessException, IOException {
		currentClass = new UMLClass();
//		java.lang.reflect.Field f = UMLParser.class.getDeclaredField("classesToAccept");
//		f.setAccessible(true);
//		f.set(null, new String[]{"java/lang/String"});
//		ClassReader reader = new ClassReader(className);
//		ClassVisitor vis1 = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
//		ClassVisitor vis2 = new SingletonFieldVisitor(Opcodes.ASM5, vis1, currentClass);
	}
	
	@Test
	public void testNegative() throws IOException {
		String className = "testingData.SampleClassForReadingInATest";
		ClassReader reader = new ClassReader(className);
		ClassVisitor vis1 = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor vis2 = new SingletonFieldVisitor(Opcodes.ASM5, vis1, currentClass);
		reader.accept(vis2, ClassReader.EXPAND_FRAMES);
		assertFalse(currentClass.isSingleton());
	}
	
	@Test
	public void testPossitive() throws IOException {
		String className = "testingData.SampleSingletonClass";
		ClassReader reader = new ClassReader(className);
		ClassVisitor vis1 = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor vis2 = new SingletonFieldVisitor(Opcodes.ASM5, vis1, currentClass);
		reader.accept(vis2, ClassReader.EXPAND_FRAMES);
		assertTrue(currentClass.isSingleton());
	}
	
	@Test
	public void testChocolateBoilerEager() throws IOException {
		String className = "testingData.ChocolateBoilerEager";
		ClassReader reader = new ClassReader(className);
		ClassVisitor vis1 = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor vis2 = new SingletonFieldVisitor(Opcodes.ASM5, vis1, currentClass);
		reader.accept(vis2, ClassReader.EXPAND_FRAMES);
		assertTrue(currentClass.isSingleton());
	}
	
	@Test
	public void testChocolateBoilerLazy() throws IOException {
		String className = "testingData.ChocolateBoilerLazy";
		ClassReader reader = new ClassReader(className);
		ClassVisitor vis1 = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor vis2 = new SingletonFieldVisitor(Opcodes.ASM5, vis1, currentClass);
		reader.accept(vis2, ClassReader.EXPAND_FRAMES);
		assertTrue(currentClass.isSingleton());
	}

}
