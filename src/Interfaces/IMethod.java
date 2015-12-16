package interfaces;

import java.util.List;

import classRepresentation.Parameter;

public interface IMethod {

	String getReturnType();
	void setReturnType(String type);
	
	List<Parameter> getParameters();
	void setParameters(List<Parameter> params);
	
	String getName();
	void setName(String name);
	
	char getVisibility();
	void setVisibility(char vis);
	
	List<String> getNonAccessModifiers();
	void setNonAccessModifiers(List<String> mods);
	
	
}
