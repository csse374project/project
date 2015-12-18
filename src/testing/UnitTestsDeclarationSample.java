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

public class UnitTestsDeclarationSample {

	private static String className = "SampleClassForReadingInATest";
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
		assertEquals("SampleClassForReadingInATest", currentClass.getName());
	}
	@Test
	public void testFields() {
		List<IField> fields = currentClass.getFields();
		List<IField> expected = new ArrayList<IField>();
		fail("unimplimented");
	}
	
	@Test
	public void testMethods() {
		List<IMethod> methods = currentClass.getMethods();
		List<IMethod> expected = new ArrayList<IMethod>();
		
		fail("unimplemented");
	}
	
	@Test
	public void testInterfaces() {
		List<String> interfaces = currentClass.getInterfaces();
		List<String> expected = new ArrayList<String>();
		expected.add("SampleInterface01");
		expected.add("SampleInterface02");
		assertEquals(expected.size(), interfaces.size());
		for (int i = 0; i < expected.size(); i++) {
			assertTrue(interfaces.contains(expected.get(i)));
		}
	}
	
	@Test
	public void testSuperClass() {
		assertEquals("SampleSuperClass", currentClass.getSuperClass());
	}

}
