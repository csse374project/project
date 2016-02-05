package classRepresentation.designPaterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import classRepresentation.Classes;
import classRepresentation.UMLClass;
import classRepresentation.decorators.CompositeComponentDecorator;
import classRepresentation.decorators.CompositeDecorator;
import classRepresentation.decorators.CompositeLeafDecorator;
import classRepresentation.decorators.IClassDecorator;
import interfaces.IClass;
import interfaces.IField;

public class CompositeDetector {

	private Map<String, IClass> classMap;
	private List<String> detectedComponents;
	private List<String> detectedComposites;
	private List<String> detectedLeaves;
	private Set<String> classesInPattern;
	private final static List<String> COLLECTIONS = new ArrayList<String>();

	public CompositeDetector(Classes classes) {
		COLLECTIONS.add("List");
		COLLECTIONS.add("[]");
		COLLECTIONS.add("Map");
		COLLECTIONS.add("Queue");
		COLLECTIONS.add("Stack");
		COLLECTIONS.add("SortedMap");
		COLLECTIONS.add("Vector");
		COLLECTIONS.add("Collection");
		COLLECTIONS.add("TreeSet");
		COLLECTIONS.add("Set");
		COLLECTIONS.add("ArrayList");

		detectedComponents = new ArrayList<String>();
		detectedComposites = new ArrayList<String>();
		detectedLeaves = new ArrayList<String>();
		classesInPattern = new TreeSet<String>();
		classMap = new HashMap<String, IClass>();
		this.classMap = classes.getClasses();
	}

	public void findCompositePattern() {
		// First, we find the "seed" composites
		findSeedComposites();

		// Then, we must decorate their Components
		decorateComponents();

		// Compile list of of classes in pattern
		getClassesInPattern();

		// Check the rest of the components
		identifyPatternClasses();
	}

	private void identifyPatternClasses() {
		for (String clsName : classesInPattern) {
			IClass cls = classMap.get(clsName);

			for (IField field : cls.getFields()) {
				int lastDot = field.getType().lastIndexOf('.');
				if (COLLECTIONS.contains(field.getType().substring(lastDot + 1))) {
					for (String interiorType : field.getInteriorTypes()) {
						if (classesInPattern.contains(interiorType) && !isComposite(cls)) {
							IClassDecorator decoratedClass = (IClassDecorator) cls;
							decoratedClass.decorate(new CompositeDecorator(interiorType));
						}
					}
				}
			}
			if (!isComposite(cls) && !isComponent(cls)) {
				IClassDecorator decoratedClass = (IClassDecorator) cls;
				decoratedClass.decorate(new CompositeLeafDecorator());
			}
		}
	}

	private void getClassesInPattern() {
		boolean foundSomething = false;
		for (IClass cls : classMap.values()) {
			if(classesInPattern.contains(cls.getName())) continue;
			if (classesInPattern.contains(cls.getSuperClass())) {
				classesInPattern.add(cls.getName());
				foundSomething = true;
			}
			for (String interfaze: cls.getInterfaces()){
				if(classesInPattern.contains(interfaze)){
					classesInPattern.add(cls.getName());
					foundSomething = true;
				}
			}
		}
		if (foundSomething)
			getClassesInPattern();
	}

	private void findSeedComposites() {
		for (IClass cls : classMap.values()) {
			if (isComposite(cls)) {
				continue;
			}
			for (IField field : cls.getFields()) {
				int lastDot = field.getType().lastIndexOf('.');
				if (COLLECTIONS.contains(field.getType().substring(lastDot + 1))) {
					checkInterfaces(cls, field);
					checkSupers(cls, field);
				}
			}
		}
	}

	private void checkInterfaces(IClass cls, IField field) {
		for (String type : field.getInteriorTypes()) {
			if (cls.getInterfaces().contains(type) && !isComposite(cls)) {
				IClassDecorator decoratedClass = (IClassDecorator)cls;
				decoratedClass.decorate(new CompositeDecorator(type));
				detectedComposites.add(cls.getName());
			}
		}
	}

	private void checkSupers(IClass cls, IField field) {
		List<String> supers = getSuperClasses(cls);
		for (String type : field.getInteriorTypes()) {
			if (supers.contains(type) && !isComposite(cls)) {
				IClassDecorator decoratedClass = (IClassDecorator)cls;
				decoratedClass.decorate(new CompositeDecorator(type));
				detectedComposites.add(cls.getName());
			}
		}
	}

	private List<String> getSuperClasses(IClass cls) {
		List<String> supers = new ArrayList<String>();

		String zuper = cls.getSuperClass();
		IClass newCls = cls;
		while (classMap.get(zuper) != null) {
			supers.add(zuper);
			newCls = classMap.get(zuper);
			zuper = newCls.getSuperClass();
		}

		return supers;
	}

	private void decorateComponents() {
		for (String composite : detectedComposites) {
			IClass compositeClass = classMap.get(composite);
			IClassDecorator castedComposite = (IClassDecorator) compositeClass;
			CompositeDecorator compositeDecorator = findComposite(castedComposite);

			String componentName = compositeDecorator.getComponent();
			IClass componentClass = classMap.get(componentName);
			if (componentClass != null) { // Class might not be in scope of what
											// we're parsing
				IClassDecorator castedComponent = (IClassDecorator) componentClass;
				if (!(isComponent(castedComponent))) { // Make sure it wasn't
														// already found
					castedComponent.decorate(new CompositeComponentDecorator());
					detectedComponents.add(castedComponent.getName());
					classesInPattern.add(castedComponent.getName());
				}
			}
		}
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

	/**
	 * Iterates down the decorator chain to find the composite decorator
	 * associated with
	 * 
	 * @param clazz
	 * @return CompositeDecorator of the given class
	 */
	private CompositeDecorator findComposite(IClass clazz) {
		IClassDecorator cls = (IClassDecorator) clazz;
		Object current = cls.getDecorates();
		while (true) {
			if (current instanceof UMLClass) {
				return null;
			}
			cls = (IClassDecorator) current;
			if (cls == null) {
				return null;
			}
			if (cls instanceof CompositeDecorator) {
				return (CompositeDecorator) cls;
			}
			current = cls.getDecorates();
		}
	}
}
