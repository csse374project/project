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

public class UnitTestsFieldVisitor {
	
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
	public void testCorrectNumberOfFields() {
		List<IField> fields = currentClass.getFields();
		assertEquals(1, fields.size());
	}
	@Test
	public void testCorrectlyNamedField() {
		List<IField> fields = currentClass.getFields();
		IField result =	fields.get(0);
		assertEquals("CASE_INSENSITIVE_ORDER", result.getName());
	}
	@Test
	public void testCorrectNonAccessModifiers() {
		IField result =	currentClass.getFields().get(0);
		List<String> mods = result.getNonAccessModifiers();
		assertEquals(1, mods.size());
		assertEquals("static", mods.get(0));
	}
	@Test
	public void testCorrectAccessModifiers() {
		IField result =	currentClass.getFields().get(0);
		char mod = result.getVisibility();
		assertEquals('+', mod);
	}
	@Test
	public void testCorrectType() {
		IField result =	currentClass.getFields().get(0);
		String type= result.getType();
		assertEquals("Comparator<String>", type);
	}
}
