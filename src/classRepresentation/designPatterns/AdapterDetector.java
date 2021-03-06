package classRepresentation.designPatterns;

import java.util.List;
import java.util.Map;

import classRepresentation.Classes;
import classRepresentation.UMLClass;
import classRepresentation.decorators.AdapteeDecorator;
import classRepresentation.decorators.AdapterDecorator;
import classRepresentation.decorators.AdaptionTargetDecorator;
import classRepresentation.decorators.IClassDecorator;
import gui.DesignPatternInstance;
import interfaces.IClass;

public class AdapterDetector implements DesignPatternDetector{

	private Map<String, IClass> classMap;

	public AdapterDetector(Classes classes) {
		classMap = classes.getClasses();
	}

	public void detectPattern(String[] args, List<DesignPatternInstance> instances) {
		for (String className : classMap.keySet()) {
			IClassDecorator clazz = (IClassDecorator) classMap.get(className);
			AdapterDecorator adapter = getAdapter(clazz);
			if (adapter != null) {
				addAdaptee(adapter);
				addTarget(adapter);
			}
		}
	}

	private void addAdaptee(AdapterDecorator adapter) {
		String adaptee = adapter.getAdaptee();
		IClassDecorator clazz = (IClassDecorator) classMap.get(adaptee);
		removeUsesArrow(adapter, adaptee);
		if (clazz != null)
			clazz.decorate(new AdapteeDecorator());
	}

	private void removeUsesArrow(IClass adapter, String adaptee) {
		List<String> list = adapter.getUsedClasses();
		list.remove(adaptee);
	}

	private void addTarget(AdapterDecorator adapter) {
		String target = adapter.getTarget();
		IClassDecorator clazz = (IClassDecorator) classMap.get(target);
		if (clazz != null)
			clazz.decorate(new AdaptionTargetDecorator());
	}

	private AdapterDecorator getAdapter(IClass clazz) {
		if (clazz instanceof UMLClass) {
			return null;
		} else if (clazz instanceof AdapterDecorator) {
			return (AdapterDecorator) clazz;
		}
		IClassDecorator decorator = (IClassDecorator) clazz;
		return getAdapter(decorator.getDecorates());
	}
}
