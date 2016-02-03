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

public class UnitTestSimpleCompositeDetector {

	private IClassDecorator compositeSampleInterface;
	private IClassDecorator leafInterface;
	private IClassDecorator componentInterface;

	private IClassDecorator compositeSampleAbstract;
	private IClassDecorator leafAbstractClass;
	private IClassDecorator componentAbstractClass;
	
	@Before
	public void setup() throws IOException {
		Classes classes = new Classes();
		
		compositeSampleInterface = new TopLevelDecorator(new UMLClass());
		leafInterface = new TopLevelDecorator(new UMLClass());
		componentInterface = new TopLevelDecorator(new UMLClass());
		compositeSampleAbstract = new TopLevelDecorator(new UMLClass());
		leafAbstractClass = new TopLevelDecorator(new UMLClass());
		componentAbstractClass = new TopLevelDecorator(new UMLClass());
		
		setupHelper(classes, compositeSampleInterface, "testingData.CompositeSample");
		setupHelper(classes, leafInterface, "testingData.SampleInterface01");
		setupHelper(classes, componentInterface, "testingData.CompositeLeafSample");
		setupHelper(classes, compositeSampleAbstract, "testingData.AbstractCompositeSample");
		setupHelper(classes, leafAbstractClass, "testingData.AbstractCompositeComponent");
		setupHelper(classes, componentAbstractClass, "testingData.AbstractCompositeLeaf");
		
		CompositeDetector composite = new CompositeDetector(classes);
		composite.findComposites();
	}
	
	private static void setupHelper(Classes classes, IClassDecorator top, String className) throws IOException {
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, top);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, top);
		ClassVisitor compositeVisitor = new CompositeVisitor(Opcodes.ASM5, fieldVisitor, top);

		ClassReader reader = new ClassReader(className);
		reader.accept(compositeVisitor, ClassReader.EXPAND_FRAMES);
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
		IClassDecorator current = compositeSampleInterface;
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
	public void compositeSampleHasCompositeDecoratorInterface() {
		assertTrue(isComposite(compositeSampleInterface));
	}

	@Test
	public void componentHasNoCompositeDecoratorInterface() {
		assertFalse(isComposite(componentInterface));
	}
	
	@Test
	public void leafHasNoCompositeDecoratorInterface() {
		assertFalse(isComposite(leafInterface));
	}
	
	
	@Test
	public void compositeSampleHasNoLeafDecoratorInterface() {
		assertFalse(isLeaf(compositeSampleInterface));
	}

	@Test
	public void componentHasNoLeafDecoratorInterface() {
		assertFalse(isLeaf(componentInterface));
	}
	
	@Test
	public void leafHasLeafDecoratorInterface() {
		assertTrue(isLeaf(leafInterface));
	}
	
	
	@Test
	public void compositeSampleHasNoComponentDecoratorInterface() {
		assertFalse(isComponent(compositeSampleInterface));
	}

	@Test
	public void componentHasComponentDecoratorInterface() {
		assertTrue(isComponent(componentInterface));
	}
	
	@Test
	public void leafHasNoComponentDecoratorInterface() {
		assertFalse(isComponent(leafInterface));
	}
	

	@Test
	public void compositeSampleHasCompositeDecoratorAbstractClass() {
		assertTrue(isComposite(compositeSampleAbstract));
	}

	@Test
	public void componentHasNoCompositeDecoratorAbstractClass() {
		assertFalse(isComposite(componentAbstractClass));
	}
	
	@Test
	public void leafHasNoCompositeDecoratorAbstractClass() {
		assertFalse(isComposite(leafAbstractClass));
	}
	
	
	@Test
	public void compositeSampleHasNoLeafDecoratorAbstractClass() {
		assertFalse(isLeaf(compositeSampleAbstract));
	}

	@Test
	public void componentHasNoLeafDecoratorAbstractClass() {
		assertFalse(isLeaf(componentAbstractClass));
	}
	
	@Test
	public void leafHasLeafDecoratorAbstractClass() {
		assertTrue(isLeaf(leafAbstractClass));
	}
	
	
	@Test
	public void compositeSampleHasNoComponentDecoratorAbstractClass() {
		assertFalse(isComponent(compositeSampleAbstract));
	}

	@Test
	public void componentHasComponentDecoratorAbstractClass() {
		assertTrue(isComponent(componentAbstractClass));
	}
	
	@Test
	public void leafHasNoComponentDecoratorAbstractClass() {
		assertFalse(isComponent(leafAbstractClass));
	}
	
	
}
