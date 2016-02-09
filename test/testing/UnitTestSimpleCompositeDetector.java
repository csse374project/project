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
import classRepresentation.designPaterns.DesignPatternDetector;
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
		setupHelper(classes, interfaceLeaf, "testingData.compositePattern.CompositeLeafSample");
		setupHelper(classes, interfaceComponent, "testingData.SampleInterface01");
		setupHelper(classes, abstractCompositeSample, "testingData.compositePattern.AbstractCompositeSample");
		setupHelper(classes, abstractLeaf, "testingData.compositePattern.AbstractCompositeLeaf");
		setupHelper(classes, abstractComponent, "testingData.compositePattern.AbstractCompositeComponent");
		setupHelper(classes, arrayCompositeSample, "testingData.compositePattern.CompositeWithArray");
		setupHelper(classes, arrayLeaf, "testingData.compositePattern.CompositeLeafWithArray");
		setupHelper(classes, arrayComponent, "testingData.compositePattern.CompositeComponentWithArray");
		
		DesignPatternDetector composite = new CompositeDetector(classes);
		composite.detectPattern();
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
	public void interfaceComponentHasNoCompositeDecorator() {
		assertFalse(isComposite(interfaceComponent));
	}
	
	@Test
	public void interfaceCeafHasNoCompositeDecorato() {
		assertFalse(isComposite(interfaceLeaf));
	}
	
	
	@Test
	public void interfaceCompositeSampleHasNoLeafDecorator() {
		assertFalse(isLeaf(interfaceCompositeSample));
	}

	@Test
	public void interfaceComponentHasNoLeafDecorator() {
		assertFalse(isLeaf(interfaceComponent));
	}
	
	@Test
	public void interfaceCeafHasLeafDecorator() {
		assertTrue(isLeaf(interfaceLeaf));
	}
	
	
	@Test
	public void interfaceCompositeSampleHasNoComponentDecorator() {
		assertFalse(isComponent(interfaceCompositeSample));
	}

	@Test
	public void interfaceComponentHasComponentDecorator() {
		assertTrue(isComponent(interfaceComponent));
	}
	
	@Test
	public void interfaceCeafHasNoComponentDecorator() {
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
	public void abstractCompositeSampleHasCompositeDecorator() {
		assertTrue(isComposite(abstractCompositeSample));
	}

	@Test
	public void abstractComponentHasNoCompositeDecorator() {
		assertFalse(isComposite(abstractComponent));
	}
	
	@Test
	public void abstractLeafHasNoCompositeDecorator() {
		assertFalse(isComposite(abstractLeaf));
	}
	
	
	@Test
	public void abstractCompositeSampleHasNoLeafDecorator() {
		assertFalse(isLeaf(abstractCompositeSample));
	}

	@Test
	public void abstractComponentHasNoLeafDecorator() {
		assertFalse(isLeaf(abstractComponent));
	}
	
	@Test
	public void abstractLeafHasLeafDecorator() {
		assertTrue(isLeaf(abstractLeaf));
	}
	
	
	@Test
	public void abstractCompositeSampleHasNoComponentDecorator() {
		assertFalse(isComponent(abstractCompositeSample));
	}

	@Test
	public void abstractComponentHasComponentDecorator() {
		assertTrue(isComponent(abstractComponent));
	}
	
	@Test
	public void abstractLeafHasNoComponentDecorator() {
		assertFalse(isComponent(abstractLeaf));
	}

	@Test
	public void arrayCompositeDecoratorHasCorrectComponent() throws Exception {
		IClassDecorator current = arrayCompositeSample;
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
		assertEquals("testingData/compositePattern/CompositeComponentWithArray", comp);
	}
	

	@Test
	public void arrayCompositeSampleHasCompositeDecorator() {
		assertTrue(isComposite(arrayCompositeSample));
	}

	@Test
	public void arrayComponentHasNoCompositeDecorator() {
		assertFalse(isComposite(arrayComponent));
	}
	
	@Test
	public void arrayCeafHasNoCompositeDecorator() {
		assertFalse(isComposite(arrayLeaf));
	}
	
	
	@Test
	public void arrayCompositeSampleHasNoLeafDecorator() {
		assertFalse(isLeaf(arrayCompositeSample));
	}

	@Test
	public void arrayComponentHasNoLeafDecorator() {
		assertFalse(isLeaf(arrayComponent));
	}
	
	@Test
	public void arrayLeafHasLeafDecorator() {
		assertTrue(isLeaf(arrayLeaf));
	}
	
	
	@Test
	public void arrayCompositeSampleHasNoComponentDecorator() {
		assertFalse(isComponent(arrayCompositeSample));
	}

	@Test
	public void arrayComponentHasComponentDecorator() {
		assertTrue(isComponent(arrayComponent));
	}
	
	@Test
	public void arrayCeafHasNoComponentDecorator() {
		assertFalse(isComponent(arrayLeaf));
	}
	
}
