package classRepresentation;

import java.util.HashMap;
import java.util.Map;

import interfaces.IClass;

public class Classes {
	private Map<String, IClass> classes;
	
	public Classes(){
		this.classes = new HashMap<String, IClass>();
	}
	
	public Map<String, IClass> getClasses(){
		return this.classes;
	}
	
	public void setClasses(Map<String, IClass> classes){
		this.classes = classes;
	}
	
	public void addClass(IClass newClass){
		if(!classes.containsKey(newClass.getName()))
			classes.put(newClass.getName(), newClass);
	}
	
	public String toGraphViz(){
		return "Parse output first!!";
	}
	
	
}
