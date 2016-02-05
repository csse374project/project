package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.Classes;
import classRepresentation.UMLClass;
import classRepresentation.decorators.CompositeComponentDecorator;
import classRepresentation.decorators.CompositeDecorator;
import classRepresentation.decorators.CompositeLeafDecorator;
import classRepresentation.decorators.IClassDecorator;
import classRepresentation.decorators.TopLevelDecorator;
import classRepresentation.designPaterns.CompositeDetector;
import classRepresentation.designPaterns.CompositeVisitor;
import interfaces.IClass;
import umlDiagram.ClassDeclarationVisitor;
import umlDiagram.ClassFieldVisitor;

public class UnitTestCompositeDetector {

	private IClassDecorator compositeSample;
	private IClassDecorator leaf;
	private IClassDecorator component;
	
	@Before
	public void setup() throws IOException {
		Classes classes = new Classes();
		
		compositeSample = new TopLevelDecorator(new UMLClass());
		leaf = new TopLevelDecorator(new UMLClass());
		component = new TopLevelDecorator(new UMLClass());
		
		setupHelper(classes, compositeSample, "testingData.compositePattern.CompositeSample");
		setupHelper(classes, leaf, "testingData.compositePattern.CompositeLeafSample");
		setupHelper(classes, component, "testingData.SampleInterface01");
		
		CompositeDetector composite = new CompositeDetector(classes);
		composite.findCompositePattern();
		System.out.println(isLeaf(leaf));
	}
	
	private static void setupHelper(Classes classes, IClassDecorator top, String className) throws IOException {
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, top);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, top);

		ClassReader reader = new ClassReader(className);
		reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
		classes.addClass(top);
	}
	
	private boolean isComposite(IClass clazz) {
		IClassDecorator cls = (IClassDecorator) clazz;
		Object current = cls.getDecorates();
		while (true) {
			if (current instanceof UMLClass) {
				return false;
			}
			cls = (IClassDecorator) current;
			if (cls == null) {
				return false;
			}
			if (cls instanceof CompositeDecorator) {
				return true;
			}
			current = cls.getDecorates();
		}
	}
	private boolean isComponent(IClass clazz) {
		IClassDecorator cls = (IClassDecorator) clazz;
		Object current = cls.getDecorates();
		while (true) {
			if (current instanceof UMLClass) {
				return false;
			}
			cls = (IClassDecorator) current;
			if (cls == null) {
				return false;
			}
			if (cls instanceof CompositeComponentDecorator) {
				return true;
			}
			current = cls.getDecorates();
		}
	}
	private boolean isLeaf(IClass clazz) {
		IClassDecorator cls = (IClassDecorator) clazz;
		Object current = cls.getDecorates();
		while (true) {
			if (current instanceof UMLClass) {
				return false;
			}
			cls = (IClassDecorator) current;
			if (cls == null) {
				return false;
			}
			if (cls instanceof CompositeLeafDecorator) {
				return true;
			}
			current = cls.getDecorates();
		}
	}
	
	@Test
	public void compositeDecoratorHasCorrectComponent() throws Exception {
		IClassDecorator current = compositeSample;
		while(true) {
			if (current instanceof CompositeDecorator) {
				break;
			}
			IClass next = current.getDecorates();
			if (! (next instanceof IClassDecorator)) {
				throw new Exception("did not detect composite decorator");
			}
			current = (IClassDecorator) next;
		}
		CompositeDecorator decorator = (CompositeDecorator) current;
		Field f = CompositeDecorator.class.getDeclaredField("component");
		f.setAccessible(true);
		String comp = (String) f.get(decorator);
		assertEquals("testingData/SampleInterface01", comp);
	}
	
	@Test
	public void compositeSampleHasCompositeDecorator() {
		assertTrue(isComposite(compositeSample));
	}

	@Test
	public void componentHasNoCompositeDecorator() {
		assertFalse(isComposite(component));
	}
	
	@Test
	public void leafHasNoCompositeDecorator() {
		assertFalse(isComposite(leaf));
	}
	
	
	@Test
	public void compositeSampleHasNoLeafDecorator() {
		assertFalse(isLeaf(compositeSample));
	}

	@Test
	public void componentHasNoLeafDecorator() {
		assertFalse(isLeaf(component));
	}
	
	@Test
	public void leafHasLeafDecorator() {
		assertTrue(isLeaf(leaf));
	}
	
	
	@Test
	public void compositeSampleHasNoComponentDecorator() {
		assertFalse(isComponent(compositeSample));
	}

	@Test
	public void componentHasComponentDecorator() {
		assertTrue(isComponent(component));
	}
	
	@Test
	public void leafHasNoComponentDecorator() {
		assertFalse(isComponent(leaf));
	}
	
}
