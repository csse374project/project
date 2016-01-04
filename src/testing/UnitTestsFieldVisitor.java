package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.After;
import org.objectweb.asm.Opcodes;

import classRepresentation.Class;
import classRepresentation.Field;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import org.junit.Test;


import csse374project.ClassDeclarationVisitor;
import csse374project.ClassFieldVisitor;
import interfaces.IClass;
import interfaces.IField;
import interfaces.IMethod;

public class UnitTestsFieldVisitor {
	
	private static String className = "java.lang.String";
	private IClass currentClass;
	
	@Before
	public void setup() throws IOException {
		// TODO fix this.???
		currentClass = new Class();
		ClassReader reader = null;
		reader = new ClassReader(className);
		ClassVisitor decVis = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor fVis = new ClassFieldVisitor(Opcodes.ASM5, decVis, currentClass);
		reader.accept(fVis, ClassReader.EXPAND_FRAMES);
	}

	@Test
	public void testNumberOfFields() {
		List<IField> fields = currentClass.getFields();
		assertEquals(5, fields.size());
	}
	@Test
	public void testFields() {
		List<IField> fields = currentClass.getFields();
		List<String> allNames = new ArrayList(5);
		String[] expected = new String[]{"CASE_INSENSITIVE_ORDER", "value",
				"hash", "serialVersionUID", "serialPersistentFields"};
		for (int i = 0; i < fields.size(); i++) {
			allNames.add(fields.get(i).getName());
		}
		
		for (int i = 0; i < expected.length; i++) {
			assertTrue("missing field " + expected[i],
					allNames.contains(expected[i]));
		}
	}
//	@Test 
//	TODO reactivate this.
	public void testNonAccessModifiers() {
		HashMap<String, String[]> expectedMap = getExpectedNonAccessModifiers();
		List<IField> fields = currentClass.getFields();
		int size = fields.size();
		
		for (int i = 0; i < size; i++) {
			String[] expectedMods = expectedMap.get(fields.get(i).getName());
			List<String> mods = fields.get(i).getNonAccessModifiers();
			assertEquals(String.format("the field %s has too many fields", fields.get(i)),
					expectedMods.length, mods.size());
			
			for (int j = 0; j < expectedMods.length; i++) {
				assertTrue(String.format("field %s is missing the modifier '%s'",
						fields.get(i).getName(), expectedMods[j]),
						mods.contains(expectedMods[j]));
			}
		}
	}
	@Test
	public void testAccessModifiers() {
		HashMap<String, Character> expectedMap = getExpectedAccessModifiers();
		List<IField> result = currentClass.getFields();
		int size = result.size();
		for (int i = 0; i < size; i++) {
			char expected = expectedMap.get(result.get(i).getName());
			assertEquals(expected, result.get(i).getVisibility());
		}
	}

	@Test
	public void testType() {
		HashMap<String, String> expectedMap = getExpectedTypes();
		List<IField> result = currentClass.getFields();
		int size = result.size();
		for (int i = 0; i < size; i++) {
			String expected = expectedMap.get(result.get(i).getName());
			assertEquals(expected, result.get(i).getType());
		}
	}

	private static HashMap<String, Character> getExpectedAccessModifiers() {
		HashMap<String, Character> map = new HashMap<String, Character>();
		map.put("CASE_INSENSITIVE_ORDER", '+');
		map.put("value", '-');
		map.put("hash", '-');
		map.put("serialVersionUID", '-');
		map.put("serialPersistentFields", '-');
		return map;
	}
	private static HashMap<String, String> getExpectedTypes() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("CASE_INSENSITIVE_ORDER", "java.util.Comparator");
		map.put("value", "char[]");
		map.put("hash", "int");
		map.put("serialVersionUID", "long");
		map.put("serialPersistentFields", "java.io.ObjectStreamField[]");
		return map;
	}
	private static HashMap<String, String[]> getExpectedNonAccessModifiers() {
		HashMap<String, String[]> map = new HashMap<String, String[]>();
		map.put("CASE_INSENSITIVE_ORDER", new String[]{"static"});
		map.put("value", new String[]{});
		map.put("hash", new String[]{});
		map.put("serialVersionUID", new String[]{"static", "final"});
		map.put("serialPersistentFields", new String[]{"static", "final"});
		return map;
	}
}
