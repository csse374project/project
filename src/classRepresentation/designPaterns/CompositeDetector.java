package classRepresentation.designPaterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import classRepresentation.Classes;
import classRepresentation.UMLClass;
import classRepresentation.decorators.CompositeComponentDecorator;
import classRepresentation.decorators.CompositeDecorator;
import classRepresentation.decorators.CompositeLeafDecorator;
import classRepresentation.decorators.IClassDecorator;
import interfaces.IClass;

public class CompositeDetector {

	private Map<String, IClass> classMap;
	private List<String> detectedComponents;
	private List<String> detectedComposites;
	private List<String> detectedLeaves;

	public CompositeDetector(Classes classes) {
		detectedComponents = new ArrayList<String>();
		detectedComposites = new ArrayList<String>();
		detectedLeaves = new ArrayList<String>();
		classMap = new HashMap<String, IClass>();
		this.classMap = classes.getClasses();
	}

	public void findCompositePattern() {
		// First, we find the composites that our Visitor has found
		findComposites();

		// Then, we must decorate their Components
		decorateComponents();
		
		//Then we find children of the components, which are not composites, and decorate them as leaves
		decorateFirstLevelLeaves();
		
		findLeafChildren();
		
		findCompositeChildren();
	}

	private void findComposites() {
		for (IClass cls : classMap.values()) {
			if (isComposite(cls))
				detectedComposites.add(cls.getName());
		}
	}

	private void decorateComponents() {
		for (String composite : detectedComposites) {
			IClass compositeClass = classMap.get(composite);
			IClassDecorator castedComposite = (IClassDecorator) compositeClass;
			CompositeDecorator compositeDecorator = findComposite(castedComposite);
			
			String componentName = compositeDecorator.getComponent();
			IClass componentClass = classMap.get(componentName);
			if(componentClass != null){ //Class might not be in scope of what we're parsing
				IClassDecorator castedComponent = (IClassDecorator)componentClass;
				if(!(isComponent(castedComponent))){ //Make sure it wasn't already found
					castedComponent.decorate(new CompositeComponentDecorator());
					detectedComponents.add(castedComponent.getName());
				}
			}
		}
	}

	private void findLeafChildren() {
		boolean foundLeaf = false;
		for (IClass cls : classMap.values()) {
			if (detectedLeaves.contains(cls.getSuperClass()) && !isLeaf(cls) && !isComposite(cls)) {
				IClassDecorator decoratedClass = (IClassDecorator)cls;
				decoratedClass.decorate(new CompositeLeafDecorator());
				foundLeaf = true;
			}
		}
		if (foundLeaf) {
			findLeafChildren();
		}
	}

	private void findCompositeChildren() {
		boolean foundComposite = false;
		for (IClass cls : classMap.values()) {
			if (detectedComposites.contains(cls.getSuperClass()) && !isLeaf(cls) && !isComposite(cls)) {
				IClassDecorator decoratedClass = (IClassDecorator)cls;
				decoratedClass.decorate(new CompositeDecorator(null));
				foundComposite = true;
			}
		}
		if (foundComposite) {
			findCompositeChildren();
		}
	}
	
	private void decorateFirstLevelLeaves(){
		for (IClass cls : classMap.values()){
			if (detectedComponents.contains(cls.getSuperClass()) && !isComposite(cls) && !isLeaf(cls)) {
				IClassDecorator decoratedClass = (IClassDecorator)cls;
				decoratedClass.decorate(new CompositeLeafDecorator());
				detectedLeaves.add(cls.getName());
			}
			
			for (String interfaze : cls.getInterfaces()){
				if (detectedComponents.contains(interfaze) && !isComposite(cls) && !isLeaf(cls)) {
					IClassDecorator decoratedClass = (IClassDecorator)cls;
					decoratedClass.decorate(new CompositeLeafDecorator());
					detectedLeaves.add(cls.getName());
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
	
	/**
	 * Iterates down the decorator chain to find the composite decorator associated with
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
