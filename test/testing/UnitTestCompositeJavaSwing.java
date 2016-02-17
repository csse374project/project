package testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

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
import classRepresentation.decorators.DecoratorDecorator;
import classRepresentation.decorators.IClassDecorator;
import classRepresentation.decorators.TopLevelDecorator;
import classRepresentation.designPaterns.CompositeDetector;
import classRepresentation.designPaterns.CompositeVisitor;
import classRepresentation.designPaterns.DesignPatternDetector;
import interfaces.IClass;
import umlDiagram.ClassDeclarationVisitor;
import umlDiagram.ClassFieldVisitor;

public class UnitTestCompositeJavaSwing {

	private IClassDecorator window, comp, panel, button;
	
	@Before
	public void setup() throws Exception {
		Classes classes = new Classes();
		window = new TopLevelDecorator(new UMLClass());
		comp = new TopLevelDecorator(new UMLClass());
		panel = new TopLevelDecorator(new UMLClass());
		button = new TopLevelDecorator(new UMLClass());
		
		setupHelper(classes, new TopLevelDecorator(new UMLClass()), "javax.swing.JComponent");
		setupHelper(classes, comp, "java.awt.Component");
		setupHelper(classes, new TopLevelDecorator(new UMLClass()), "javax.swing.AbstractButton");
		setupHelper(classes, button, "javax.swing.JButton");
		setupHelper(classes, panel, "javax.swing.JPanel");
		setupHelper(classes, new TopLevelDecorator(new UMLClass()), "java.awt.Container");
		setupHelper(classes, window, "java.awt.Window");

		DesignPatternDetector composite = new CompositeDetector(classes);
		composite.detectPattern(new String[]{});
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
	
	private boolean isDecorator(IClass clazz) {
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
			if (cls instanceof DecoratorDecorator) {
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
	public void buttonIsNotComposite() {
		assertFalse(isComposite(button));
	}
	@Test 
	public void buttonIsNotComponent() {
		assertFalse(isComponent(button));
	}
	@Test 
	public void buttonIstLeaf() {
		assertTrue(isLeaf(button));
	}
	@Test 
	public void buttonIsNotAdapter() {
		assertFalse(isAdapter(button));
	}
	@Test
	public void buttonIsNotDecorator() {
		assertFalse(isDecorator(button));
	}
	

	@Test 
	public void panelIsComposite() {
		assertTrue(isComposite(panel));
	}
	@Test 
	public void panelIsNotComponent() {
		assertFalse(isComponent(panel));
	}
	@Test 
	public void panelIsNotLeaf() {
		assertFalse(isLeaf(panel));
	}
	@Test 
	public void panelIsNotAdapter() {
		assertFalse(isAdapter(panel));
	}
	@Test
	public void panelIsNotDecorator() {
		assertFalse(isDecorator(panel));
	}

	
	@Test 
	public void compIsNotComposite() {
		assertFalse(isComposite(comp));
	}
	@Test 
	public void compIsComponent() {
		assertTrue(isComponent(comp));
	}
	@Test 
	public void compIsNotLeaf() {
		assertFalse(isLeaf(comp));
	}
	@Test 
	public void compIsNotAdapter() {
		assertFalse(isAdapter(comp));
	}
	@Test
	public void compIsNotDecorator() {
		assertFalse(isDecorator(comp));
	}


	@Test 
	public void windowIsComposite() {
		assertTrue(isComposite(window));
	}
	@Test 
	public void windowIsNotComponent() {
		assertFalse(isComponent(window));
	}
	@Test 
	public void windowIsNotLeaf() {
		assertFalse(isLeaf(window));
	}
	@Test 
	public void windowIsNotAdapter() {
		assertFalse(isAdapter(window));
	}
	@Test
	public void windowIsNotDecorator() {
		assertFalse(isDecorator(window));
	}
}
