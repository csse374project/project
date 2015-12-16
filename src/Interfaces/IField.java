package interfaces;

import java.util.List;

public interface IField {
	
	String getType();
	void setType(String type);
	
	String getName();
	void setName(String name);
	
	char getVisibility();
	void setVisibility(char vis);
	
	List<String> getNonAccessModifiers();
	void setNonAccessModifiers(List<String> mods);
	
}
