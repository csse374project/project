package interfaces;

import java.util.List;

public interface IClass {
	
	void setName(String name);
	String getName();
	
	void setSuperClass(String superName);
	String getSuperClass();
	
	void setInterfaces(List<String> interfaceNames);
	List<String> getInterfaces();
	
	void setFields(List<IField> fields);
	List<IField> getFields();
	
	void setMethods(List<IMethod> methods);
	List<IMethod> getMethods();
	
	void addMethod(IMethod method);
	
	void addField(IField field);
	
	boolean getIsInterface();
	void setIsInterface(boolean isInterface);
	
	
}

