package classRepresentation.designPaterns;

import java.util.List;
import java.util.Map;

import classRepresentation.Classes;
import classRepresentation.decorators.ComponentDecorator;
import classRepresentation.decorators.DecoratorDecorator;
import classRepresentation.decorators.IClassDecorator;
import interfaces.IClass;
import interfaces.IField;

public class DecoratorDetector {
	
	private Map<String, IClass> classMap;
	
	public DecoratorDetector(Classes classes) {
		this.classMap = classes.getClasses();
	}
		
	public void findDecorators() {
		for(String className : classMap.keySet()) {
			IClass currentClass = classMap.get(className);
			checkForDecoratorPattern(currentClass);
		}
	}
	
	private void checkForDecoratorPattern(IClass clazz) {
		String superClass = clazz.getSuperClass();
		List<IField> fields = clazz.getFields();
		for (int i = 0; i < fields.size(); i++) {
			if (fields.get(i).getType().equals(superClass)) {
				applyDecorator(clazz, superClass);
				break;
			}
		}
	}
	private void applyDecorator(IClass clazz, String component) {
		IClassDecorator decoratedClass = (IClassDecorator) clazz;
		decoratedClass.decorate(new DecoratorDecorator(component));
		setComponentToInterface(component);
	}
	private void setComponentToInterface(String component) {
		IClassDecorator compClass = (IClassDecorator) classMap.get(component);
		compClass.decorate(new ComponentDecorator());
	}
	
}
