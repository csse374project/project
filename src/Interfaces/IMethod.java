package interfaces;

import java.util.List;

import org.objectweb.asm.Type;

public interface IMethod {

	Type getReturnType();
	void setReturnType(Type t);
	
	List<Type> getParameters();
	void setParameters(List<Type> params);
	
	String getName();
	void setName(String name);
	
	char getVisibility();
	void setVisibility(char vis);
	
	List<String> getNonAccessModifiers();
	void setNonAccessModifiers(List<String> mods);
	
	
}
