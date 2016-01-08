package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.After;
import org.objectweb.asm.Opcodes;

import classRepresentation.Class;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import org.junit.Test;


import csse374project.ClassDeclarationVisitor;
import csse374project.DesignParser;
import interfaces.IClass;
import interfaces.IField;
import interfaces.IMethod;

public class UnitTestsDeclarationString {

	private static String className = "java.lang.String";
	private IClass currentClass;
	
	@Before
	public void setup() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		currentClass = new Class();
		java.lang.reflect.Field f = DesignParser.class.getDeclaredField("classesToAccept");
		f.setAccessible(true);
		f.set(null, new String[]{"java/lang/String"});
		ClassReader reader = null;
		reader = new ClassReader(className);
		ClassVisitor vis = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		reader.accept(vis, ClassReader.EXPAND_FRAMES);
	}
	
	@Test
	public void testName() {
		assertEquals("java/lang/String", currentClass.getName());
	}
	@Test
	public void testNumberOfInterfaces() {
		// Strings implement: Serializable, Comparable<String>, CharSequence
		List<String> interfaces = currentClass.getInterfaces();
		assertEquals(3, interfaces.size());
	}
	
	@Test
	public void testInterfaces() {
		// Strings implement: Serializable, Comparable<String>, CharSequence
		List<String> interfaces = currentClass.getInterfaces();
		List<String> expected = new ArrayList<String>();
		expected.add("java/io/Serializable");
		expected.add("java/lang/Comparable");
		expected.add("java/lang/CharSequence");
		for (int i = 0; i < expected.size(); i++) {
			assertTrue(String.format("expected %s, interface %d/%d",
					 expected.get(i), i+1, expected.size()),
					interfaces.contains(expected.get(i)));
		}
	}
	
	@Test
	public void testSuperClass() {
		assertEquals("java/lang/Object", currentClass.getSuperClass());
	}

}
