package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.UMLClass;
import classRepresentation.decorators.TopLevelDecorator;
import interfaces.IClass;
import interfaces.IMethod;
import umlDiagram.ClassDeclarationVisitor;
import umlDiagram.ClassFieldVisitor;
import umlDiagram.ClassMethodVisitor;
import umlDiagram.UMLParser;

public class UnitTestMethodVisitor {

	private static String className = "java.lang.String";
	private IClass currentClass;
	private TopLevelDecorator topDecorator;
	
	@Before
	public void setup()
			throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException,
				IllegalAccessException {
		// TODO fix this.
		currentClass = new UMLClass();
		topDecorator = new TopLevelDecorator(currentClass);
		java.lang.reflect.Field f = UMLParser.class.getDeclaredField("classesToAccept");
		f.setAccessible(true);
		f.set(null, new String[]{"java/lang/String"});
		ClassReader reader = new ClassReader(className);
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, topDecorator);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, topDecorator);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, topDecorator);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
	}

	@Test
	public void testNumberOfMethods() {
		List<IMethod> methods = currentClass.getMethods();
		int numbMethods = 94;
		assertEquals(numbMethods, methods.size());
	}
	@Test
	public void testCorrectMethods() {
		List<IMethod> methods = currentClass.getMethods();
		List<String> methodNames = methodListToNameList(methods);
		List<String> expectedNames = getExpectedMethodNames();
		int size = expectedNames.size();
		for (int i = 0; i < size; i++) {
			assertTrue(methodNames.contains(expectedNames.get(i)));
		}
	}
	private List<String> getExpectedMethodNames() {
		List<String> names = new ArrayList<String>();
		names.add("charAt");
		names.add("compareToIgnoreCase");
		names.add("contains");
		names.add("format");
		names.add("getBytes");
		names.add("indexOf");
		names.add("intern");
		names.add("isEmpty");
		names.add("length");
		names.add("toString");
		names.add("trim");
		names.add("toUpperCase");
		names.add("valueOf");
		return names;
	}
	
	private List<String> methodListToNameList(List<IMethod> methods) {
		List<String> names = new ArrayList<String>();
		int size = methods.size();
		for (int i = 0; i < size; i++) {
			names.add(methods.get(i).getName());
		}
		return names;
	}
}
