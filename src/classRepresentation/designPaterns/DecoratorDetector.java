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
		IClass cls = clazz;
		while (classMap.containsKey(cls.getSuperClass())) {
			String curSuper = cls.getSuperClass();
			for (int i = 0; i < fields.size(); i++) {
				if ((fields.get(i).getType()).replace(".", "/").equals(curSuper)) {
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
		while (cls.getSuperClass() != component) {
			cls.decorate(new DecoratorDecorator(null));
			cls = (IClassDecorator) classMap.get(cls.getSuperClass());
		}
		if (!(cls.getDecorates() instanceof DecoratorDecorator))
			cls.decorate(new DecoratorDecorator(component));
		if(component != null) setComponentToInterface(component);
	}
	
	private void setComponentToInterface(String component) {
		IClassDecorator compClass = (IClassDecorator) classMap.get(component);
		if(!(compClass.getDecorates() instanceof ComponentDecorator)) 
			compClass.decorate(new ComponentDecorator());
	}
	
}
