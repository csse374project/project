package testing;

import static org.junit.Assert.*;

import java.util.List;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.UMLClass;
import classRepresentation.Classes;
import interfaces.IClass;
import umlDiagram.MetodDeclarationVisitor;
import umlDiagram.ClassDeclarationVisitor;
import umlDiagram.ClassFieldVisitor;
import umlDiagram.ClassMethodVisitor;
import umlDiagram.UMLParser;

public class UnitTestsMethodCodeVisitor {
	
	private static List<String> classNames;
	private Classes classes;
	
	private void createList() {
		classNames = new ArrayList<String>();
		classNames.add("testingData.SampleClassForReadingInATest");
		classNames.add("testingData.SampleInterface01");
		classNames.add("testingData.SampleInterface02");
		classNames.add("testingData.SampleSuperClass");
		classNames.add("testingData.SampleClassForInitializing");
		classNames.add("testingData.SampleClassForInitializingTwo");
		classNames.add("testingData.SampleClassForInitializingThree");
		classNames.add("testingData.SampleClassForInitializingFour");
	}
	
	@Before
	public void setup()
			throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// TODO fix this.
		createList();
		classes =  new Classes();
		Field f = UMLParser.class.getDeclaredField("classesToAccept");
		f.setAccessible(true);
		f.set(null, new String[]{"testingData/SampleClassForReadingInATest", "testingData/SampleInterface01", "testingData/SampleInterface02", "testingData/SampleSuperClass", "testingData/SampleClassForInitializing", "testingData/SampleClassForInitializingTwo", "testingData/SampleClassForInitializingThree", "testingData/SampleClassForInitializingFour"});
		for (String cls : classNames) {
			UMLClass currentClass = new UMLClass();
			ClassReader reader = new ClassReader(cls);
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, currentClass);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass);
			ClassVisitor methodCodeVisitor = new MetodDeclarationVisitor(Opcodes.ASM5, methodVisitor, currentClass);
			reader.accept(methodCodeVisitor, ClassReader.EXPAND_FRAMES);
			classes.addClass(currentClass);
		}
	}
	
	@Test
	public void hasCorrectAssociatedClasses() {
		IClass test = classes.getClasses().get("testingData/SampleClassForReadingInATest");
		assertEquals(1, test.getAssociatedClasses().size());
		assertTrue(test.getAssociatedClasses().contains("testingData/SampleClassForInitializingTwo"));
	}
	
	@Test
	public void hasCorrectUsedClasses() {
		IClass test = classes.getClasses().get("testingData/SampleClassForReadingInATest");
		assertEquals(3, test.getUsedClasses().size());
		assertTrue(test.getUsedClasses().contains("testingData/SampleClassForInitializing"));
		assertTrue(test.getUsedClasses().contains("testingData/SampleClassForInitializingThree"));
		assertTrue(test.getUsedClasses().contains("testingData/SampleClassForInitializingFour"));
	}
}
