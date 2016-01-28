package classRepresentation.designPaterns;

import java.util.Map;

import classRepresentation.Classes;
import interfaces.IClass;
import interfaces.IField;

public class AdapterDetector {
	
	private static String OBJECT = "java.lang.Object";

	private Map<String, IClass> classMap;
	
	public AdapterDetector(Classes classes) {
		classMap = classes.getClasses();
	}
	
	public void findAdapters() {
		for (String className : classMap.keySet()) {
			IClass clazz = classMap.get(className);
			checkIfClassIsAdapter(clazz);
		}
	}
	
	private void checkIfClassIsAdapter(IClass clazz) {
		if (clazz.getSuperClass().equals(OBJECT) && (clazz.getInterfaces().size() == 0)) {
			return;
		}
	}
	
	private void checkIfFieldIsAdapted(IField field) {
		
	}
}
