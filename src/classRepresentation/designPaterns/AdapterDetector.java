package classRepresentation.designPaterns;

import java.util.List;
import java.util.Map;

import classRepresentation.Classes;
import classRepresentation.UMLClass;
import classRepresentation.decorators.AdapteeDecorator;
import classRepresentation.decorators.AdapterDecorator;
import classRepresentation.decorators.AdaptionTargetDecorator;
import classRepresentation.decorators.IClassDecorator;
import interfaces.IClass;

public class AdapterDetector {
	
	private static String OBJECT = "java.lang.Object";

	private Map<String, IClass> classMap;
	
	public AdapterDetector(Classes classes) {
		classMap = classes.getClasses();
	}
	
	public void findAdapters() {
		for (String className : classMap.keySet()) {
			IClassDecorator clazz = (IClassDecorator) classMap.get(className);
			AdapterDecorator adapter = getAdapter(clazz);
			if(adapter != null) {
				addAdaptee(adapter);
				addTarget(adapter);
			}
		}
	}
	private void addAdaptee(AdapterDecorator adapter) {
		String adaptee = adapter.getAdaptee();
		IClassDecorator clazz = (IClassDecorator) classMap.get(adaptee);
		removeUsesArrow(adapter, adaptee);
		clazz.decorate(new AdapteeDecorator());
	}
	private void removeUsesArrow(IClass adapter, String adaptee) {
		List<String> list = adapter.getUsedClasses();
		list.remove(adaptee);
//		for (String currentClass : list) {
//			if (currentClass.equals(adaptee)) {
//				list.remove(currentClass);
//				return;
//			}
//		}
	}
	
	private void addTarget(AdapterDecorator adapter) {
		String target = adapter.getTarget();
		IClassDecorator clazz = (IClassDecorator) classMap.get(target);
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
