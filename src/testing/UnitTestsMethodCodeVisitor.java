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

import classRepresentation.Class;
import classRepresentation.Classes;
import csse374project.ClassDeclarationVisitor;
import csse374project.ClassFieldVisitor;
import csse374project.ClassMethodVisitor;
import csse374project.DesignParser;
import interfaces.IClass;

public class UnitTestsMethodCodeVisitor {
	
	private static List<String> classNames;
	private Classes classes;
	
	private void createList() {
		classNames = new ArrayList<String>();
		classNames.add("testing.SampleClassForReadingInATest");
		classNames.add("testing.SampleInterface01");
		classNames.add("testing.SampleInterface02");
		classNames.add("testing.SampleSuperClass");
		classNames.add("testing.SampleClassForInitializing");
		classNames.add("testing.SampleClassForInitializingTwo");
		classNames.add("testing.SampleClassForInitializingThree");
		classNames.add("testing.SampleClassForInitializingFour");
	}
	
	@Before
	public void setup()
			throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// TODO fix this.
		createList();
		classes =  new Classes();
		Field f = DesignParser.class.getDeclaredField("classesToAccept");
		f.setAccessible(true);
		f.set(null, new String[]{"testing/SampleClassForReadingInATest", "testing/SampleInterface01", "testing/SampleInterface02", "testing/SampleSuperClass", "testing/SampleClassForInitializing", "testing/SampleClassForInitializingTwo", "testing.SampleClassForInitializingThree", "testing.SampleClassForInitializingFour"});
		for (String cls : classNames) {
			Class currentClass = new Class();
			ClassReader reader = new ClassReader(cls);
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, currentClass);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass);
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			classes.addClass(currentClass);
		}
	}
	
	@Test
	public void hasCorrectAssociatedClasses() {
		IClass test = classes.getClasses().get("testing/SampleClassForReadingInATest");
		assertEquals(2, test.getAssociatedClasses().size());
		assertTrue(test.getAssociatedClasses().contains("testing/SampleClassForInitializing"));
		assertTrue(test.getAssociatedClasses().contains("testing/SampleClassForInitializingTwo"));
	}
	
	@Test
	public void hasCorrectUsedClasses() {
		IClass test = classes.getClasses().get("testing/SampleClassForReadingInATest");
		assertEquals(2, test.getUsedClasses().size());
		assertTrue(test.getUsedClasses().contains("testing/SampleClassForInitializingThree"));
		assertTrue(test.getUsedClasses().contains("testing/SampleClassForInitializingFour"));
	}
}
