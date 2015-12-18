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

import classRepresentation.Class;
import csse374project.ClassDeclarationVisitor;
import interfaces.IClass;
import interfaces.IField;
import interfaces.IMethod;

public class UnitTestsMethodVisitor {

	private static String className = "java.lang.String";
	private IClass currentClass;
	
	@Before
	public void setup() throws IOException {
		// TODO fix this.
		currentClass = new Class();
		ClassReader reader = null;
		reader = new ClassReader(className);
		ClassVisitor vis = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		reader.accept(vis, ClassReader.EXPAND_FRAMES);
	}

	@Test
	public void testNumberOfMethods() {
		List<IMethod> methods = currentClass.getMethods();
		assertEquals(67, methods.size());
	}
	@Test
	public void testCorrectMethods() {
		List<IMethod> methods = currentClass.getMethods();
		List<IMethod> expected = new ArrayList<IMethod>();
		// TODO add methods
		for (int i = 0; i < expected.size(); i++) {
			// TODO implement a proper comparison.
			assertTrue(methods.contains(expected.get(i)));
		}
	}
	
}
