package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

public class UnitTestsToGraphViz {

	private static List<String> classNames;
	private Classes classes;
	private String graphViz;
	
	private void createList() {
		classNames = new ArrayList<String>();
		classNames.add("testing.SampleClassForReadingInATest");
		classNames.add("testing.SampleInterface01");
		classNames.add("testing.SampleInterface02");
		classNames.add("testing.SampleSuperClass");
	}
	
	@Before
	public void setup()
			throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		createList();
		classes =  new Classes();
		Field f = DesignParser.class.getDeclaredField("classesToAccept");
		f.setAccessible(true);
		f.set(null, new String[]{"testing/SampleClassForReadingInATest", "testing/SampleInterface01", "testing/SampleInterface02", "testing/SampleSuperClass"});
		for (String cls : classNames) {
			Class currentClass = new Class();
			ClassReader reader = new ClassReader(cls);
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, currentClass);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, currentClass);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, currentClass);
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			classes.addClass(currentClass);
		}
		graphViz = classes.toGraphViz();
	}
	
	@Test
	public void hasRightExtensionArrows() {
		int startBreak = graphViz.indexOf("arrowhead = \"empty\"");
		int stopBreak = graphViz.indexOf("style = \"dashed\"");
		String testThis = graphViz.substring(startBreak, stopBreak);
		assertTrue(testThis.contains("SampleClassForReadingInATest -> SampleSuperClass"));
	}
	
	@Test
	public void hasRightImplementArrows() {
		int startBreak = graphViz.indexOf("style = \"dashed\"");
		int stopBreak = graphViz.indexOf("arrowhead = \"normal\" style = \"solid\"");
		String testThis = graphViz.substring(startBreak, stopBreak);
		assertTrue(testThis.contains("SampleClassForReadingInATest -> SampleInterface01"));
		assertTrue(testThis.contains("SampleClassForReadingInATest -> SampleInterface02"));
	}
	
}
