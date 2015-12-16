package Interfaces;

import java.util.List;

import org.objectweb.asm.*;

public interface IField {
	
	Type getType();
	void setType(Type t);
	
	String getName();
	void setName(String name);
	
	char getVisibility();
	void setVisibility(char vis);
	
	List<String> getNonAccessModifiers();
	void setNonAccessModifiers(List<String> mods);
	
}
