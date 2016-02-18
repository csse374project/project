package testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.Classes;
import classRepresentation.UMLClass;
import classRepresentation.decorators.AdapteeDecorator;
import classRepresentation.decorators.AdapterDecorator;
import classRepresentation.decorators.AdaptionTargetDecorator;
import classRepresentation.decorators.IClassDecorator;
import classRepresentation.decorators.TopLevelDecorator;
import classRepresentation.designPatterns.AdapterClassVisitor;
import classRepresentation.designPatterns.AdapterDetector;
import classRepresentation.designPatterns.DesignPatternDetector;
import gui.DesignPatternInstance;
import interfaces.IClass;
import umlDiagram.ClassDeclarationVisitor;

public class UnitTestAdapterDecorator {
	
	private TopLevelDecorator adapter, adaptee, target, singleton;
	private List<DesignPatternInstance> designPattern;
	
	@Before 
	public void setup() throws IOException {
		Classes classes = new Classes();
		IClass toDecorate = new UMLClass();
		adapter = new TopLevelDecorator(toDecorate);
		designPattern = new ArrayList<DesignPatternInstance>();
		ClassReader reader = new ClassReader("testingData.AdapterSample");
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, adapter);
		ClassVisitor adapterVisitor = new AdapterClassVisitor(Opcodes.ASM5, declVisitor, adapter, designPattern);
		reader.accept(adapterVisitor, ClassReader.EXPAND_FRAMES);
		classes.addClass(adapter);
		
		toDecorate = new UMLClass();
		adaptee = new TopLevelDecorator(toDecorate);
		reader = new ClassReader("testingData.AdapteeSample");
		declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, adaptee);
		adapterVisitor = new AdapterClassVisitor(Opcodes.ASM5, declVisitor, adaptee, designPattern);
		reader.accept(adapterVisitor, ClassReader.EXPAND_FRAMES);
		classes.addClass(adaptee);
		
		toDecorate = new UMLClass();
		target = new TopLevelDecorator(toDecorate);
		reader = new ClassReader("testingData.AdapterTargetSample");
		declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, target);
		adapterVisitor = new AdapterClassVisitor(Opcodes.ASM5, declVisitor, target, designPattern);
		reader.accept(adapterVisitor, ClassReader.EXPAND_FRAMES);
		classes.addClass(target);
		
		toDecorate = new UMLClass();
		singleton = new TopLevelDecorator(toDecorate);
		reader = new ClassReader("testingData.SampleSingletonClass");
		declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, singleton);
		adapterVisitor = new AdapterClassVisitor(Opcodes.ASM5, declVisitor, singleton, designPattern);
		reader.accept(adapterVisitor, ClassReader.EXPAND_FRAMES);
		classes.addClass(singleton);
		
		DesignPatternDetector detector = new AdapterDetector(classes);
		detector.detectPattern(new String[]{});
		
	}

	@Test
	public void adapterHasAdapterDecorator() {
		assertTrue(isAdapter(adapter));
	}
	@Test
	public void adapteeIsNotAdapter() {
		assertFalse(isAdapter(adaptee));
	}
	@Test
	public void targetIsNotAdapter() {
		assertFalse(isAdapter(target));
	}
	@Test
	public void singletonNotAdapter() {
		assertFalse(isAdapter(singleton));
	}

	@Test
	public void adapterIsNotAdaptee() {
		assertFalse(isAdaptee(adapter));
	}
	@Test
	public void adapteeHasAdapteeDecorator() {
		assertTrue(isAdaptee(adaptee));
	}
	@Test
	public void targetIsNotAdaptee() {
		assertFalse(isAdaptee(target));
	}
	@Test
	public void singletonNotAdaptee() {
		assertFalse(isAdaptee(singleton));
	}
	
	@Test
	public void adapterIsNotTarget() {
		assertFalse(isTarget(adapter));
	}
	@Test
	public void adapteeIsNotTarget() {
		assertFalse(isTarget(adaptee));
	}
	@Test
	public void targetHasTargetDecorator() {
		assertTrue(isTarget(target));
	}
	@Test
	public void singletonNotTarget() {
		assertFalse(isTarget(singleton));
	}
	
	
	private boolean isAdapter(IClassDecorator clazz) {
		while (true) {
			IClass current = clazz.getDecorates();
			if (current instanceof UMLClass) {
				return false;
			}
			clazz = (IClassDecorator) current;
			if (clazz == null) {
				return false;
			}
			if (clazz instanceof AdapterDecorator) {
				return true;
			}
		}
	}
	
	private boolean isAdaptee(IClassDecorator clazz) {
		while (true) {
			IClass current = clazz.getDecorates();
			if (current instanceof UMLClass) {
				return false;
			}
			clazz = (IClassDecorator) current;
			if (clazz == null) {
				return false;
			}
			if (clazz instanceof AdapteeDecorator) {
				return true;
			}
		}
	}
	private boolean isTarget(IClassDecorator clazz) {
		while (true) {
			IClass current = clazz.getDecorates();
			if (current instanceof UMLClass) {
				return false;
			}
			clazz = (IClassDecorator) current;
			if (clazz == null) {
				return false;
			}
			if (clazz instanceof AdaptionTargetDecorator) {
				return true;
			}
		}
	}

}
