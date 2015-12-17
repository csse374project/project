package classRepresentation;

import java.util.Map;

import interfaces.IClass;

public class Classes {
	private Map<String, IClass> classes;
	
	public Map<String, IClass> getClasses(){
		return this.classes;
	}
	
	public void setClasses(Map<String, IClass> classes){
		this.classes = classes;
	}
	
	
}
