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
	public void testCorrectNumberOfFields() {
		List<IField> fields = currentClass.getFields();
		assertEquals(5, fields.size());
	}
	@Test
	public void testCorrectFields() {
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
	@Test
	public void testCorrectNonAccessModifiers() {
		IField result =	currentClass.getFields().get(0);
		
		fail("unimplimented");
	}
	@Test
	public void testCorrectAccessModifiersHash() {
		String expectedName = "hash";
		char expectedVisibility = '-';
		List<IField> result = currentClass.getFields();
		for (int i = 0; i < result.size(); i++) {
			if(result.get(i).getName().equals(expectedName)){
				assertEquals(expectedVisibility, result.get(i).getVisibility());
				return;
			}
		}
		fail("did not find expected field \"hash\"");
	}
	@Test
	public void testCorrectAccessModifiersValue() {
		String expectedName = "value";
		char expectedVisibility = '-';
		List<IField> result = currentClass.getFields();
		for (int i = 0; i < result.size(); i++) {
			if(result.get(i).getName().equals(expectedName)){
				assertEquals(expectedVisibility, result.get(i).getVisibility());
				return;
			}
		}
		fail("did not find expected field \"value\"");
	}
	@Test
	public void testCorrectAccessModifiersSerialUID() {
		String expectedName = "serialVersionUID";
		char expectedVisibility = '-';
		List<IField> result = currentClass.getFields();
		for (int i = 0; i < result.size(); i++) {
			if(result.get(i).getName().equals(expectedName)){
				assertEquals(expectedVisibility, result.get(i).getVisibility());
				return;
			}
		}
		fail("did not find expected field \"serialVersionUID\"");
	}
	@Test
	public void testCorrectAccessModifiersSerialPersistentFields() {
		String expectedName = "serialPersistentFields";
		char expectedVisibility = '-';
		List<IField> result = currentClass.getFields();
		for (int i = 0; i < result.size(); i++) {
			if(result.get(i).getName().equals(expectedName)){
				assertEquals(expectedVisibility, result.get(i).getVisibility());
				return;
			}
		}
		fail("did not find expected field \"serialPersistentFields\"");
	}
	@Test
	public void testCorrectAccessModifiersCaseOrder() {
		String expectedName = "CASE_INSENSITIVE_ORDER";
		char expectedVisibility = '+';
		List<IField> result = currentClass.getFields();
		for (int i = 0; i < result.size(); i++) {
			if(result.get(i).getName().equals(expectedName)){
				assertEquals(expectedVisibility, result.get(i).getVisibility());
				return;
			}
		}
		fail("did not find expected field \"serialPersistentFields\"");
	}
	@Test
	public void testCorrectType() {
		IField result =	currentClass.getFields().get(0);
		String type= result.getType();
		assertEquals("Comparator<String>", type);
	}
}
