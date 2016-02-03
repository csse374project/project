package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.objectweb.asm.Opcodes;

import classRepresentation.UMLClass;
import classRepresentation.decorators.TopLevelDecorator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import org.junit.Test;

import interfaces.IClass;
import umlDiagram.ClassDeclarationVisitor;
import umlDiagram.UMLParser;

public class UnitTestsDeclarationString {

	private static String className = "java.lang.String";
	private IClass currentClass;
	private TopLevelDecorator topDecorator;
	
	@Before
	public void setup() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		currentClass = new UMLClass();
		topDecorator = new TopLevelDecorator(currentClass);
		java.lang.reflect.Field f = UMLParser.class.getDeclaredField("classesToAccept");
		f.setAccessible(true);
		f.set(null, new String[]{"java/lang/String"});
		ClassReader reader = null;
		reader = new ClassReader(className);
		ClassVisitor vis = new ClassDeclarationVisitor(Opcodes.ASM5, topDecorator);
		reader.accept(vis, ClassReader.EXPAND_FRAMES);
	}
	
	@Test
	public void testName() {
		assertEquals("java/lang/String", topDecorator.getName());
	}
	@Test
	public void testNumberOfInterfaces() {
		// Strings implement: Serializable, Comparable<String>, CharSequence
		List<String> interfaces = topDecorator.getInterfaces();
		assertEquals(3, interfaces.size());
	}
	
	@Test
	public void testInterfaces() {
		// Strings implement: Serializable, Comparable<String>, CharSequence
		List<String> interfaces = topDecorator.getInterfaces();
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
		assertEquals("java/lang/Object", topDecorator.getSuperClass());
	}

}
