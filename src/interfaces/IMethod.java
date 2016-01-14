package interfaces;

import java.util.List;

public interface IMethod {

	String getReturnType();
	void setReturnType(String type);
	
	List<String> getParameters();
	void setParameters(List<String> params);
	
	String getName();
	void setName(String name);
	
	char getVisibility();
	void setVisibility(char vis);
	
	List<String> getNonAccessModifiers();
	void setNonAccessModifiers(List<String> mods);	
	
	String getInvoker();
	String getOwner();
	void setInvoker(String ownerName);
	void setOwner(String recieverName);
	
}
