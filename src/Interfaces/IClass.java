package interfaces;

import java.util.HashMap;
import classRepresentation.*;

public interface IClass {

	void setVisability(int vis);
	int getVisability();
	
	void setFields(HashMap<String, IField> fields);
	HashMap<String, IField> getFields();
	
	void setMethods(HashMap<String, IMethod> methods);
	HashMap<String, IMethod> getMethods();
	
	
}

