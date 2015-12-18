package classRepresentation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import interfaces.IClass;
import interfaces.IField;
import interfaces.IMethod;

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
		StringBuilder string = new StringBuilder();
		
		Set<String> keys = classes.keySet();
		
		string.append("digraph UML {\n\n\tnode [\n\t\tshape = "
				+ "\"record\"\n\t]\n\n\t");
		
		
		for (String key : keys) {
			IClass cls = classes.get(key);
			int lastFwdSlash = cls.getName().lastIndexOf('/');
			string.append(cls.getName().substring(lastFwdSlash + 1) + " [\n\t\tlabel = \"{");
			string.append(cls.getName().substring(lastFwdSlash + 1) + "|");
			
			for (IField field : cls.getFields()) {
				string.append(field.getVisibility() + " " + field.getName()
						+ ": " + field.getType() + "\\l");
			}
			
			string.append("|");
			
			for (IMethod method : cls.getMethods()) {
				String methodName = method.getName();
				if (methodName.equals("<init>")) {
					methodName = cls.getName().substring(lastFwdSlash + 1);
				}
				int lastPeriod = method.getReturnType().lastIndexOf('.');
				String methodReturnType = method.getReturnType();
				if (lastPeriod > -1) {
					methodReturnType = methodReturnType.substring(lastPeriod + 1);
				}
				string.append(method.getVisibility() + " " + methodReturnType
						+ " " + methodName + "\\l");
			}
			
			string.append("}\"\n\t]\n\n");
		}
		
		string.append("}");
		
		return string.toString();
	}
	
	
}
