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
	
	private IClassDecorator arrayCompositeSample;
	private IClassDecorator arrayLeaf;
	private IClassDecorator arrayComponent;
	
	@Before
	public void setup() throws IOException {
		Classes classes = new Classes();
		
		compositeSampleInterface = new TopLevelDecorator(new UMLClass());
		leafInterface = new TopLevelDecorator(new UMLClass());
		componentInterface = new TopLevelDecorator(new UMLClass());
		compositeSampleAbstract = new TopLevelDecorator(new UMLClass());
		leafAbstractClass = new TopLevelDecorator(new UMLClass());
		componentAbstractClass = new TopLevelDecorator(new UMLClass());
		arrayCompositeSample = new TopLevelDecorator(new UMLClass());
		arrayLeaf = new TopLevelDecorator(new UMLClass());
		arrayComponent = new TopLevelDecorator(new UMLClass());
		
		setupHelper(classes, compositeSampleInterface, "testingData.compositePattern.CompositeSample");
		setupHelper(classes, leafInterface, "testingData.SampleInterface01");
		setupHelper(classes, componentInterface, "testingData.compositePattern.CompositeLeafSample");
		setupHelper(classes, compositeSampleAbstract, "testingData.compositePattern.AbstractCompositeSample");
		setupHelper(classes, leafAbstractClass, "testingData.compositePattern.AbstractCompositeComponent");
		setupHelper(classes, componentAbstractClass, "testingData.compositePattern.AbstractCompositeLeaf");
		setupHelper(classes, arrayCompositeSample, "testingData.compositePattern.CompositeWithArray");
		setupHelper(classes, arrayLeaf, "testingData.compositePattern.CompositeLeafWithArray");
		setupHelper(classes, arrayComponent, "testingData.compositePattern.CompositeComponentWithArray");
		
		CompositeDetector composite = new CompositeDetector(classes);
		composite.findCompositePattern();
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
	public void interfaceCompositeDecoratorHasCorrectComponent() throws Exception {
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
	public void interfaceCompositeSampleHasCompositeDecorator() {
		assertTrue(isComposite(compositeSampleInterface));
	}

	@Test
	public void interfaceComponentHasNoCompositeDecoratorInterface() {
		assertFalse(isComposite(componentInterface));
	}
	
	@Test
	public void interfaceCeafHasNoCompositeDecoratorInterface() {
		assertFalse(isComposite(leafInterface));
	}
	
	
	@Test
	public void interfaceCompositeSampleHasNoLeafDecoratorInterface() {
		assertFalse(isLeaf(compositeSampleInterface));
	}

	@Test
	public void interfaceComponentHasNoLeafDecoratorInterface() {
		assertFalse(isLeaf(componentInterface));
	}
	
	@Test
	public void interfaceCeafHasLeafDecoratorInterface() {
		assertTrue(isLeaf(leafInterface));
	}
	
	
	@Test
	public void interfaceCompositeSampleHasNoComponentDecoratorInterface() {
		assertFalse(isComponent(compositeSampleInterface));
	}

	@Test
	public void interfaceComponentHasComponentDecoratorInterface() {
		assertTrue(isComponent(componentInterface));
	}
	
	@Test
	public void interfaceCeafHasNoComponentDecoratorInterface() {
		assertFalse(isComponent(leafInterface));
	}
	

	@Test
	public void abstractCompositeDecoratorHasCorrectComponent() throws Exception {
		IClassDecorator current = compositeSampleAbstract;
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
		assertEquals("testingData/compositePattern/AbstractCompositeComponent", comp);
	}
	
	@Test
	public void abstractCompositeSampleHasCompositeDecoratorAbstractClass() {
		assertTrue(isComposite(compositeSampleAbstract));
	}

	@Test
	public void abstractComponentHasNoCompositeDecoratorAbstractClass() {
		assertFalse(isComposite(componentAbstractClass));
	}
	
	@Test
	public void abstractLeafHasNoCompositeDecoratorAbstractClass() {
		assertFalse(isComposite(leafAbstractClass));
	}
	
	
	@Test
	public void abstractCompositeSampleHasNoLeafDecoratorAbstractClass() {
		assertFalse(isLeaf(compositeSampleAbstract));
	}

	@Test
	public void abstractComponentHasNoLeafDecoratorAbstractClass() {
		assertFalse(isLeaf(componentAbstractClass));
	}
	
	@Test
	public void abstractLeafHasLeafDecoratorAbstractClass() {
		assertTrue(isLeaf(leafAbstractClass));
	}
	
	
	@Test
	public void abstractCompositeSampleHasNoComponentDecoratorAbstractClass() {
		assertFalse(isComponent(compositeSampleAbstract));
	}

	@Test
	public void abstractComponentHasComponentDecoratorAbstractClass() {
		assertTrue(isComponent(componentAbstractClass));
	}
	
	@Test
	public void abstractLeafHasNoComponentDecorator() {
		assertFalse(isComponent(leafAbstractClass));
	}

	@Test
	public void arrayCompositeDecoratorHasCorrectComponent() throws Exception {
		IClassDecorator current = compositeSampleAbstract;
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
		assertEquals("testingData/compositePattern/CompositeComponenetWithArray", comp);
	}
	

	@Test
	public void arrayCompositeSampleHasCompositeDecorator() {
		assertTrue(isComposite(arrayCompositeSample));
	}

	@Test
	public void arrayComponentHasNoCompositeDecoratorInterface() {
		assertFalse(isComposite(arrayComponent));
	}
	
	@Test
	public void arrayCeafHasNoCompositeDecoratorInterface() {
		assertFalse(isComposite(arrayLeaf));
	}
	
	
	@Test
	public void arrayCompositeSampleHasNoLeafDecoratorInterface() {
		assertFalse(isLeaf(arrayCompositeSample));
	}

	@Test
	public void arrayComponentHasNoLeafDecoratorInterface() {
		assertFalse(isLeaf(arrayComponent));
	}
	
	@Test
	public void arrayCeafHasLeafDecoratorInterface() {
		assertTrue(isLeaf(arrayLeaf));
	}
	
	
	@Test
	public void arrayCompositeSampleHasNoComponentDecoratorInterface() {
		assertFalse(isComponent(arrayCompositeSample));
	}

	@Test
	public void arrayComponentHasComponentDecoratorInterface() {
		assertTrue(isComponent(arrayComponent));
	}
	
	@Test
	public void arrayCeafHasNoComponentDecoratorInterface() {
		assertFalse(isComponent(arrayLeaf));
	}
	
}
