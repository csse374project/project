package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.After;
import org.objectweb.asm.Opcodes;

import classRepresentation.Class;
import classRepresentation.Field;
import classRepresentation.Method;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import org.junit.Test;


import csse374project.ClassDeclarationVisitor;
import csse374project.ClassFieldVisitor;
import csse374project.ClassMethodVisitor;
import csse374project.DesignParser;
import interfaces.IClass;
import interfaces.IField;
import interfaces.IMethod;

public class UnitTestsDeclarationSample {

	private static String className = "testingData.SampleClassForReadingInATest";
	private IClass currentClass;
	
	@Before
	public void setup() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		currentClass = new Class();
		java.lang.reflect.Field f = DesignParser.class.getDeclaredField("classesToAccept");
		f.setAccessible(true);
		f.set(null, new String[]{"testingData/SampleClassForReadingInATest"});
		ClassReader reader = null;
		reader = new ClassReader(className);
		ClassVisitor vis = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor vis2 = new ClassFieldVisitor(Opcodes.ASM5, vis, currentClass);
		ClassVisitor vis3 = new ClassMethodVisitor(Opcodes.ASM5, vis2, currentClass);
		reader.accept(vis3, ClassReader.EXPAND_FRAMES);
	}
	
	@Test
	public void testName() {
		assertEquals("testingData/SampleClassForReadingInATest", currentClass.getName());
	}
	@Test
	public void testFields() {
		List<IField> fields = currentClass.getFields();
		List<IField> expected = new ArrayList<IField>();
		
		Field field1 = new Field();
		field1.setType("int");
		field1.setName("SOME_CONSTANT");
		field1.setNonAccessModifiers(null); //TODO: implement method and change this test
		field1.setVisibility('-');
		
		Field field2 = new Field();
		field2.setType("java.lang.String");
		field2.setName("SOME_WORD");
		field2.setNonAccessModifiers(null); //TODO: implement method and change this test
		field2.setVisibility('+');
		
		Field field3 = new Field();
		field3.setType("char");
		field3.setName("aChar");
		field3.setNonAccessModifiers(null); //TODO: implement method and change this test
		field3.setVisibility('-');
		
		Field field4 = new Field();
		field4.setType("boolean");
		field4.setName("aBool");
		field4.setNonAccessModifiers(null); //TODO: implement method and change this test
		field4.setVisibility('+');
		
		Field field5 = new Field();
		field5.setType("testingData.SampleClassForInitializingTwo");
		field5.setName("sample");
		field5.setNonAccessModifiers(null); //TODO: implement method and change this test
		field5.setVisibility('+');
		
		expected.add(field1);
		expected.add(field2);
		expected.add(field3);
		expected.add(field4);
		expected.add(field5);
		
		assertEquals(expected.size(), fields.size());
		
		for (int i = 0; i < fields.size(); i++) {
			assertTrue(expected.contains(fields.get(i)));
		}
	}
	
	@Test
	public void testMethods() {
		List<IMethod> methods = currentClass.getMethods();
		List<IMethod> expected = new ArrayList<IMethod>();
		
		Method initMethod = new Method();
		initMethod.setName("<init>");
		initMethod.setNonAccessModifiers(new ArrayList<String>());
		initMethod.setParameters(new ArrayList<String>());
		initMethod.setReturnType("void");
		initMethod.setVisibility('+');
		
		Method method1 = new Method();
		method1.setName("useless");
		method1.setNonAccessModifiers(new ArrayList<String>()); //TODO: implement method and change this test
		method1.setParameters(new ArrayList<String>());
		method1.setReturnType("void");
		method1.setVisibility('-');
		
		Method method2 = new Method();
		method2.setName("identity");
		method2.setNonAccessModifiers(new ArrayList<String>()); //TODO: implement method and change this test
		ArrayList<String> params2 = new ArrayList<String>();
		params2.add("int");
		method2.setParameters(params2);
		method2.setReturnType("int");
		method2.setVisibility('+');
		
		Method method3 = new Method();
		method3.setName("finalStringMaker");
		method3.setNonAccessModifiers(new ArrayList<String>()); //TODO: implement method and change this test
		ArrayList<String> params3 = new ArrayList<String>();
		params3.add("java.lang.String[]");
		method3.setParameters(params3);
		method3.setReturnType("java.lang.String");
		method3.setVisibility('-');
		
		Method method4 = new Method();
		method4.setName("mutateSomething");
		method4.setNonAccessModifiers(new ArrayList<String>()); //TODO: implement method and change this test
		ArrayList<String> params4 = new ArrayList<String>();
		params4.add("int[]");
		method4.setParameters(params4);
		method4.setReturnType("void");
		method4.setVisibility('+');
		
		Method method5 = new Method();
		method5.setName("initializeClass");
		method5.setNonAccessModifiers(new ArrayList<String>()); //TODO: implement method and change this test
		ArrayList<String> params5 = new ArrayList<String>();
		method5.setParameters(params5);
		method5.setReturnType("void");
		method5.setVisibility('+');
		
		Method method6 = new Method();
		method6.setName("initializeList");
		method6.setNonAccessModifiers(new ArrayList<String>()); //TODO: implement method and change this test
		ArrayList<String> params6 = new ArrayList<String>();
		method6.setParameters(params6);
		method6.setReturnType("void");
		method6.setVisibility('+');
		
		Method method7 = new Method();
		method7.setName("initializeArray");
		method7.setNonAccessModifiers(new ArrayList<String>()); //TODO: implement method and change this test
		ArrayList<String> params7 = new ArrayList<String>();
		method7.setParameters(params7);
		method7.setReturnType("void");
		method7.setVisibility('+');
		
		Method method8 = new Method();
		method8.setName("whatever");
		method8.setNonAccessModifiers(new ArrayList<String>()); //TODO: implement method and change this test
		ArrayList<String> params8 = new ArrayList<String>();
		params8.add("testingData.SampleClassForInitializingThree");
		method8.setParameters(params8);
		method8.setReturnType("testingData.SampleClassForInitializingThree");
		method8.setVisibility('+');
		
		Method method9 = new Method();
		method9.setName("something");
		method9.setNonAccessModifiers(new ArrayList<String>()); //TODO: implement method and change this test
		ArrayList<String> params9 = new ArrayList<String>();
		params9.add("testingData.SampleClassForInitializingFour");
		method9.setParameters(params9);
		method9.setReturnType("void");
		method9.setVisibility('+');
		
		expected.add(initMethod);
		expected.add(method1);
		expected.add(method2);
		expected.add(method3);
		expected.add(method4);
		expected.add(method5);
		expected.add(method6);
		expected.add(method7);
		expected.add(method8);
		expected.add(method9);
		
		assertEquals(expected.size(), methods.size());
		
		for (int i = 0; i < methods.size(); i++) {
			assertTrue(expected.contains(methods.get(i)));
		}
	}
	
	@Test
	public void testInterfaces() {
		List<String> interfaces = currentClass.getInterfaces();
		List<String> expected = new ArrayList<String>();
		expected.add("testingData/SampleInterface01");
		expected.add("testingData/SampleInterface02");
		assertEquals(expected.size(), interfaces.size());
		
		List<String> interfaceNames = new ArrayList<String>();
		for(String name : interfaces)
			interfaceNames.add(name);
		for (int i = 0; i < expected.size(); i++) {
			assertTrue(interfaceNames.contains(expected.get(i)));
		}
	}
	
	@Test
	public void testSuperClass() {
		assertEquals("testingData/SampleSuperClass", currentClass.getSuperClass());
	}

}
