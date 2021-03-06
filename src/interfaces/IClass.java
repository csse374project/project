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
//	boolean isInterface();
//	void setIsInterface(boolean isInterface);
	void addAssociatedClass(String arg);
	void addUsedClass(String arg);
	List<String> getAssociatedClasses();
	List<String> getUsedClasses();
	void toGraphViz(StringBuilder string);
//	void addStereotype(String stereotypes);
//	void setColor(String color);
//	String getColor();
	
	// declares a node and starts the label with label = \"
	void appendGraphVizHeader(StringBuilder str);
	void appendGraphVizStereotype(StringBuilder str);
	void appendGraphVizClassName(StringBuilder str);
	void appendGraphVizFields(StringBuilder str);
	void appendGraphVizMethods(StringBuilder str);
	void appendGraphVizColor(StringBuilder str);
	void appendGraphVizFooter(StringBuilder str);
}

