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

import classRepresentation.UMLClass;
import umlDiagram.MethodDeclarationVisitor;
import umlDiagram.ClassDeclarationVisitor;
import umlDiagram.ClassFieldVisitor;
import umlDiagram.ClassMethodVisitor;
import umlDiagram.UMLParser;
import classRepresentation.Classes;
import classRepresentation.TopLevelDecorator;

public class UnitTestsToGraphViz {

	private static List<String> classNames;
	private Classes classes;
	private String graphViz;
	
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
		createList();
		classes =  new Classes();
		Field f = UMLParser.class.getDeclaredField("classesToAccept");
		f.setAccessible(true);
		f.set(null, new String[]{"testingData/SampleClassForReadingInATest", "testingData/SampleInterface01", "testingData/SampleInterface02", "testingData/SampleSuperClass", "testingData/SampleClassForInitializing", "testingData/SampleClassForInitializingTwo", "testingData/SampleClassForInitializingThree", "testingData/SampleClassForInitializingFour"});
		for (String cls : classNames) {
			UMLClass currentClass = new UMLClass();
			TopLevelDecorator topDecorator = new TopLevelDecorator();
			topDecorator.setDecorates(currentClass);
			ClassReader reader = new ClassReader(cls);
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, topDecorator);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, topDecorator);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, topDecorator);
			ClassVisitor methodCodeVisitor = new MethodDeclarationVisitor(Opcodes.ASM5, methodVisitor, topDecorator);
			reader.accept(methodCodeVisitor, ClassReader.EXPAND_FRAMES);
			classes.addClass(topDecorator);
		}
		graphViz = classes.printGraphVizInput();
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
	
	@Test
	public void hasRightAssociationArrows() {
		int startBreak = graphViz.indexOf("arrowhead = \"normal\" style = \"solid\"");
		int stopBreak = graphViz.indexOf("arrowhead = \"normal\" style = \"dashed\"");
		String testThis = graphViz.substring(startBreak, stopBreak);
		assertTrue(testThis.contains("SampleClassForReadingInATest -> SampleClassForInitializingTwo\n"));
	}
	
	@Test
	public void hasRightUseArrows() {
		int startBreak = graphViz.indexOf("arrowhead = \"normal\" style = \"dashed\"");
		String testThis = graphViz.substring(startBreak);
		assertTrue(testThis.contains("SampleClassForReadingInATest -> SampleClassForInitializing\n"));
		assertTrue(testThis.contains("SampleClassForReadingInATest -> SampleClassForInitializingThree\n"));
		assertTrue(testThis.contains("SampleClassForReadingInATest -> SampleClassForInitializingFour\n"));
	}
	
}
