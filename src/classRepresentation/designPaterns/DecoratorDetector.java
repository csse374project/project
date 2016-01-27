package classRepresentation.designPaterns;

import java.util.ArrayList;
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
	private List<String> discoveredDecorators;
	
	public DecoratorDetector(Classes classes) {
		this.classMap = classes.getClasses();
		discoveredDecorators = new ArrayList<String>();
	}
		
	public void findDecorators() {
		for(String className : classMap.keySet()) {
			IClass currentClass = classMap.get(className);
			checkForDecorator(currentClass, currentClass.getSuperClass());
			checkForInterfaceClassDecorator(currentClass);
		}
		
		for(String className : classMap.keySet()){
			IClass currentClass = classMap.get(className);
			checkChildDecorators(currentClass);
		}
	}
	
	private void checkChildDecorators(IClass clazz){
		List<String> interfaces = clazz.getInterfaces();
		String superClass = clazz.getSuperClass();
		//System.out.println("SuperClass: " + superClass);
		if(discoveredDecorators.contains(superClass)) applyDecorator(clazz, null);
		for(String interfaze : interfaces){
			if(discoveredDecorators.contains(interfaze)) applyDecorator(clazz, null);
		}
	}

	private void checkForInterfaceClassDecorator(IClass clazz) {
		for(String interfaceName : clazz.getInterfaces()) {
			checkForDecorator(clazz, interfaceName);
		}
	}
	
	private void checkForDecorator(IClass clazz, String component) {
		List<IField> fields = clazz.getFields();
		for (int i = 0; i < fields.size(); i++) {
			if ((fields.get(i).getType()).replace(".", "/").equals(component)) {
				applyDecorator(clazz, component);
				discoveredDecorators.add(clazz.getName());
				break;
			}
		}
	}
	private void applyDecorator(IClass clazz, String component) {
		IClassDecorator decoratedClass = (IClassDecorator) clazz;
		decoratedClass.decorate(new DecoratorDecorator(component));
		if(component != null) setComponentToInterface(component);
	}
	private void setComponentToInterface(String component) {
		IClassDecorator compClass = (IClassDecorator) classMap.get(component);
		compClass.decorate(new ComponentDecorator());
	}
	
}
