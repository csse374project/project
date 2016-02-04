package classRepresentation.designPaterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import classRepresentation.Classes;
import classRepresentation.UMLClass;
import classRepresentation.decorators.DecoratorComponentDecorator;
import classRepresentation.decorators.DecoratorDecorator;
import classRepresentation.decorators.IClassDecorator;
import interfaces.IClass;
import interfaces.IField;

public class DecoratorDetector {

	private Map<String, IClass> classMap;
	private List<String> discoveredDecorators;

	public DecoratorDetector(Classes classes) {
		this.classMap = classes.getClasses();
		discoveredDecorators = new ArrayList<String>();
	}

	public void findDecorators() {
		for (String className : classMap.keySet()) {
			IClass currentClass = classMap.get(className);
			if (!isDecorator(currentClass)) {
				checkForDecorator(currentClass, currentClass.getSuperClass());
				checkForInterfaceClassDecorator(currentClass);
			}
		}

		for (String className : classMap.keySet()) {
			IClass currentClass = classMap.get(className);
			checkChildDecorators(currentClass);
		}
	}

	private void checkChildDecorators(IClass clazz) {
		List<String> interfaces = clazz.getInterfaces();
		String superClass = clazz.getSuperClass();
		if (discoveredDecorators.contains(superClass))
			applyDecorator(clazz, null);
		for (String interfaze : interfaces) {
			if (discoveredDecorators.contains(interfaze))
				applyDecorator(clazz, null);
		}
	}

	private void checkForInterfaceClassDecorator(IClass clazz) {
		IClass cls = clazz;
		while (classMap.containsKey(cls.getSuperClass())) {
			cls = classMap.get(cls.getSuperClass());
		}
		for (int x = 0; x < cls.getInterfaces().size(); x++) {
			for (IField fld : clazz.getFields()) {
				if (fld.getType().replace('.', '/').equals(cls.getInterfaces().get(x))) {
					applyDecorator(clazz, cls.getInterfaces().get(x));
					discoveredDecorators.add(clazz.getName());
					break;
				}
			}
		}
	}

	private void checkForDecorator(IClass clazz, String component) {
		List<IField> fields = clazz.getFields();
		IClass cls = clazz;
		while (classMap.containsKey(cls.getSuperClass())) {
			String curSuper = cls.getSuperClass();
			for (int i = 0; i < fields.size(); i++) {
				if ((fields.get(i).getType()).replace(".", "/").equals(curSuper)) {
					if (!isDecorator(clazz))
						applyDecorator(clazz, curSuper);
					discoveredDecorators.add(clazz.getName());
					break;
				}
			}
			cls = classMap.get(curSuper);
		}
	}

	private void applyDecorator(IClass clazz, String component) {
		IClassDecorator decoratedClass = (IClassDecorator) clazz;
		IClassDecorator cls = decoratedClass;
		while (cls.getSuperClass() != component && !cls.getInterfaces().contains(component)) {
			if(!isDecorator(cls))cls.decorate(new DecoratorDecorator(null));
			cls = (IClassDecorator) classMap.get(cls.getSuperClass());
			if (cls == null) {
				return;
			}
		}
		if (!!isDecorator(cls))
			cls.decorate(new DecoratorDecorator(component));
		if (component != null)
			setComponentToInterface(component);
	}

	private void setComponentToInterface(String component) {
		IClassDecorator compClass = (IClassDecorator) classMap.get(component);
		if (!(compClass.getDecorates() instanceof DecoratorComponentDecorator))
			compClass.decorate(new DecoratorComponentDecorator());
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

}
