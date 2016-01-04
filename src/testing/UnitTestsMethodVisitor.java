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
import classRepresentation.Method;
import csse374project.ClassDeclarationVisitor;
import csse374project.ClassFieldVisitor;
import csse374project.ClassMethodVisitor;
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
		ClassReader reader = new ClassReader(className);
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, currentClass);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass);
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
		List<IMethod> expected = new ArrayList<IMethod>();
		addExpectedMethods(expected);
		for (int i = 0; i < expected.size(); i++) {
			// TODO implement a proper comparison.
			assertTrue(String.format("does not contain method '%s'", expected.get(i).getName()),
					methods.contains(expected.get(i)));
		}
	}
	private void addExpectedMethods(List<IMethod> expected) {
		add_codePointBefore(expected);
		add_hashCode(expected);
		add_copyValueOf(expected);
	}
	
	private void add_codePointBefore(List<IMethod> expected) {
		IMethod method = new Method();
		
		method.setName("codePointBefore");
		method.setNonAccessModifiers(new ArrayList<String>());
		method.setReturnType("int");
		method.setVisibility('+');
		
		List<String> list = new ArrayList<String>();
		list.add("int");
		method.setParameters(list);
		
		expected.add(method);
	}
	private void add_hashCode(List<IMethod> expected) {
		IMethod method = new Method();
		
		method.setName("hashCode");
		method.setNonAccessModifiers(new ArrayList<String>());
		method.setReturnType("int");
		method.setVisibility('+');
		method.setParameters(new ArrayList<String>());
		
		expected.add(method);
	}
	private void add_copyValueOf(List<IMethod> expected) {
		IMethod method = new Method();
		
		method.setName("copyValueOf");
		method.setReturnType("String");
		method.setVisibility('+');
		
		List<String> nonAccessMods = new ArrayList<String>();
		nonAccessMods.add("static");
		method.setNonAccessModifiers(nonAccessMods);
		
		List<String> params = new ArrayList<String>();
		params.add("char[]");		
		method.setParameters(params);
		
		expected.add(method);
	}
	
	
	
}
