package classRepresentation.designPaterns;

import java.util.Map;

import classRepresentation.Classes;
import interfaces.IClass;

public class CompositeDetector {
	
	private Map<String,IClass> classMap;
	
	public CompositeDetector(Classes classes){
		this.classMap = classes.getClasses();
	}
	
	public void findComposites(){
		
	}
}
