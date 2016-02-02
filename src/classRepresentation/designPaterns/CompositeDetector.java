package classRepresentation.designPaterns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import classRepresentation.Classes;
import interfaces.IClass;
import interfaces.IField;

public class CompositeDetector {
	
	private Map<String,IClass> classMap;
	private List<String> detectedComposites;
	private List<String> detectedLeaves;
	
	public CompositeDetector(Classes classes){
		classMap = new HashMap<String, IClass>();
		this.classMap = classes.getClasses();
		
	}
	
	public void findComposites(){
		for (IClass clazz : classMap.values()){
			detectComposite(clazz);
		}
	}
	
	
	private void detectComposite(IClass clazz) {
		List<IField> fields = clazz.getFields();
		for (IField f : fields) {
			if (f.getType().contains("Collection")) {
				// TODO 
			}
		}
	}
	
	private void findSuperClassesAndInterfaces(){
		
	}
}
