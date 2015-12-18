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
import interfaces.IClass;
import interfaces.IField;
import interfaces.IMethod;

public class UnitTestsDeclarationString {

	private static String className = "java.lang.String";
	private IClass currentClass;
	
	@Before
	public void setup() throws IOException {
		currentClass = new Class();
		ClassReader reader = null;
		reader = new ClassReader(className);
		ClassVisitor vis = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		reader.accept(vis, ClassReader.EXPAND_FRAMES);
	}
	
	@Test
	public void testName() {
		assertEquals("String", currentClass.getName());
	}
	@Test
	public void testInterfaces() {
		// Strings implement: Serializable, Comparable<String>, CharSequence
		// TODO add Comparable<String> to the test
		List<String> interfaces = currentClass.getInterfaces();
		List<String> expected = new ArrayList<String>();
		expected.add("Serializable");
		expected.add("Comparable<String>");
		expected.add("CharSequence");
		assertEquals(expected.size(), interfaces.size());
		for (int i = 0; i < expected.size(); i++) {
			assertTrue(interfaces.contains(expected.get(i)));
		}
	}
	
	@Test
	public void testSuperClass() {
		assertEquals("java.lang.Object", currentClass.getSuperClass());
	}

}
