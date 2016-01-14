package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import interfaces.IClass;
import umlDiagram.ClassDeclarationVisitor;
import umlDiagram.ClassFieldVisitor;
import umlDiagram.ClassMethodVisitor;
import classRepresentation.UMLClass;
import classRepresentation.Classes;

public class TestSampleClassForReadingInATest {

	private static String className = "testing.SampleClassForReadingInATest";
	private PrintStream stdout;
	
	@Before
	public void setup() {
		stdout = System.out;
	}
	
	@After
	public void tearDown() {
		System.setOut(stdout);
	}

	@Test
	public void testHas_mutateSomething() throws IOException {
		Classes classes = new Classes();
		
		IClass currentClass = new UMLClass();
		ClassReader reader = new ClassReader(className);
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, currentClass);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		classes.addClass(currentClass);
		
		String result = classes.toGraphViz();
		System.out.println(result);
		assertTrue(result.contains("+ void mutateSomething(int[])"));
	}
	
	@Test
	public void testHas_finalStringMaker() throws IOException {
		Classes classes = new Classes();
		
		IClass currentClass = new UMLClass();
		ClassReader reader = new ClassReader(className);
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, currentClass);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		classes.addClass(currentClass);
		
		String result = classes.toGraphViz();
		System.out.println(result);
		assertTrue(result.contains("- String finalStringMaker(java.lang.String[])"));
	}
	
	@Test
	public void testHas_identity() throws IOException {
		Classes classes = new Classes();
		
		IClass currentClass = new UMLClass();
		ClassReader reader = new ClassReader(className);
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, currentClass);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		classes.addClass(currentClass);
		
		String result = classes.toGraphViz();
		assertTrue(result.contains("+ int identity(int)"));
	}

	@Test
	public void testHas_useless() throws IOException  {
		Classes classes = new Classes();
		
		IClass currentClass = new UMLClass();
		ClassReader reader = new ClassReader(className);
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, currentClass);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		classes.addClass(currentClass);
		
		String result = classes.toGraphViz();
		System.out.println(result);
		assertTrue(result.contains("- void useless()"));
	}

	@Test
	public void testHas_aBool() throws IOException  {
		Classes classes = new Classes();
		
		IClass currentClass = new UMLClass();
		ClassReader reader = new ClassReader(className);
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, currentClass);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		classes.addClass(currentClass);
		
		String result = classes.toGraphViz();
		System.out.println(result);
		assertTrue(result.contains("+ aBool: boolean"));
	}
	@Test
	public void testHas_aChar() throws IOException  {
		Classes classes = new Classes();
		
		IClass currentClass = new UMLClass();
		ClassReader reader = new ClassReader(className);
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, currentClass);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		classes.addClass(currentClass);
		
		String result = classes.toGraphViz();
		assertTrue(result.contains("- aChar: char"));
	}
	@Test
	public void testHasSOME_WORD()  throws IOException {
		Classes classes = new Classes();
		
		IClass currentClass = new UMLClass();
		ClassReader reader = new ClassReader(className);
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, currentClass);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		classes.addClass(currentClass);
		
		String result = classes.toGraphViz();
		assertTrue(result.contains("+ SOME_WORD: java.lang.String"));
	}
	@Test
	public void testHasSOME_CONSTANT()  throws IOException  {
		Classes classes = new Classes();
		
		IClass currentClass = new UMLClass();
		ClassReader reader = new ClassReader(className);
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, currentClass);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		classes.addClass(currentClass);
		
		String result = classes.toGraphViz();
		assertTrue(result.contains("- SOME_CONSTANT: int"));
	}
}