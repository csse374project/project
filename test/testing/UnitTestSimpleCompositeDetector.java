package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.Classes;
import classRepresentation.UMLClass;
import classRepresentation.decorators.AdapterDecorator;
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

	private IClassDecorator interfaceCompositeSample;
	private IClassDecorator interfaceLeaf;
	private IClassDecorator interfaceComponent;

	private IClassDecorator abstractCompositeSample;
	private IClassDecorator abstractLeaf;
	private IClassDecorator abstractComponent;
	
	private IClassDecorator arrayCompositeSample;
	private IClassDecorator arrayLeaf;
	private IClassDecorator arrayComponent;
	
	@Before
	public void setup() throws IOException {
		Classes classes = new Classes();
		
		interfaceCompositeSample = new TopLevelDecorator(new UMLClass());
		interfaceLeaf = new TopLevelDecorator(new UMLClass());
		interfaceComponent = new TopLevelDecorator(new UMLClass());
		abstractCompositeSample = new TopLevelDecorator(new UMLClass());
		abstractLeaf = new TopLevelDecorator(new UMLClass());
		abstractComponent = new TopLevelDecorator(new UMLClass());
		arrayCompositeSample = new TopLevelDecorator(new UMLClass());
		arrayLeaf = new TopLevelDecorator(new UMLClass());
		arrayComponent = new TopLevelDecorator(new UMLClass());
		
		setupHelper(classes, interfaceCompositeSample, "testingData.compositePattern.CompositeSample");
		setupHelper(classes, interfaceLeaf, "testingData.SampleInterface01");
		setupHelper(classes, interfaceComponent, "testingData.compositePattern.CompositeLeafSample");
		setupHelper(classes, abstractCompositeSample, "testingData.compositePattern.AbstractCompositeSample");
		setupHelper(classes, abstractLeaf, "testingData.compositePattern.AbstractCompositeComponent");
		setupHelper(classes, abstractComponent, "testingData.compositePattern.AbstractCompositeLeaf");
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
	private boolean isAdapter(IClass clazz) {
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
			if (cls instanceof AdapterDecorator) {
				return true;
			}
			current = cls.getDecorates();
		}
	}

	@Test
	public void interfaceCompositeSampleNotAdapter() {
		assertFalse(isAdapter(interfaceCompositeSample));
	}
	@Test
	public void interfaceCompositeComponentNotAdapter() {
		assertFalse(isAdapter(interfaceComponent));
	}
	@Test
	public void interfaceLeafNotAdapter() {
		assertFalse(isAdapter(interfaceLeaf));
	}
	
	@Test
	public void abstractCompositeSampleNotAdapter() {
		assertFalse(isAdapter(abstractCompositeSample));
	}
	@Test
	public void abstractCompositeComponentNotAdapter() {
		assertFalse(isAdapter(abstractComponent));
	}
	@Test
	public void abstractLeafNotAdapter() {
		assertFalse(isAdapter(abstractLeaf));
	}
	
	@Test
	public void arrayCompositeSampleNotAdapter() {
		assertFalse(isAdapter(arrayCompositeSample));
	}
	@Test
	public void arrayCompositeComponentNotAdapter() {
		assertFalse(isAdapter(arrayComponent));
	}
	@Test
	public void arrayLeafNotAdapter() {
		assertFalse(isAdapter(arrayLeaf));
	}
	
	@Test
	public void interfaceCompositeDecoratorHasCorrectComponent() throws Exception {
		IClassDecorator current = interfaceCompositeSample;
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
		assertTrue(isComposite(interfaceCompositeSample));
	}

	@Test
	public void interfaceComponentHasNoCompositeDecoratorInterface() {
		assertFalse(isComposite(interfaceComponent));
	}
	
	@Test
	public void interfaceCeafHasNoCompositeDecoratorInterface() {
		assertFalse(isComposite(interfaceLeaf));
	}
	
	
	@Test
	public void interfaceCompositeSampleHasNoLeafDecoratorInterface() {
		assertFalse(isLeaf(interfaceCompositeSample));
	}

	@Test
	public void interfaceComponentHasNoLeafDecoratorInterface() {
		assertFalse(isLeaf(interfaceComponent));
	}
	
	@Test
	public void interfaceCeafHasLeafDecoratorInterface() {
		assertTrue(isLeaf(interfaceLeaf));
	}
	
	
	@Test
	public void interfaceCompositeSampleHasNoComponentDecoratorInterface() {
		assertFalse(isComponent(interfaceCompositeSample));
	}

	@Test
	public void interfaceComponentHasComponentDecoratorInterface() {
		assertTrue(isComponent(interfaceComponent));
	}
	
	@Test
	public void interfaceCeafHasNoComponentDecoratorInterface() {
		assertFalse(isComponent(interfaceLeaf));
	}
	

	@Test
	public void abstractCompositeDecoratorHasCorrectComponent() throws Exception {
		IClassDecorator current = abstractCompositeSample;
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
		assertTrue(isComposite(abstractCompositeSample));
	}

	@Test
	public void abstractComponentHasNoCompositeDecoratorAbstractClass() {
		assertFalse(isComposite(abstractComponent));
	}
	
	@Test
	public void abstractLeafHasNoCompositeDecoratorAbstractClass() {
		assertFalse(isComposite(abstractLeaf));
	}
	
	
	@Test
	public void abstractCompositeSampleHasNoLeafDecoratorAbstractClass() {
		assertFalse(isLeaf(abstractCompositeSample));
	}

	@Test
	public void abstractComponentHasNoLeafDecoratorAbstractClass() {
		assertFalse(isLeaf(abstractComponent));
	}
	
	@Test
	public void abstractLeafHasLeafDecoratorAbstractClass() {
		assertTrue(isLeaf(abstractLeaf));
	}
	
	
	@Test
	public void abstractCompositeSampleHasNoComponentDecoratorAbstractClass() {
		assertFalse(isComponent(abstractCompositeSample));
	}

	@Test
	public void abstractComponentHasComponentDecoratorAbstractClass() {
		assertTrue(isComponent(abstractComponent));
	}
	
	@Test
	public void abstractLeafHasNoComponentDecorator() {
		assertFalse(isComponent(abstractLeaf));
	}

	@Test
	public void arrayCompositeDecoratorHasCorrectComponent() throws Exception {
		IClassDecorator current = abstractCompositeSample;
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
