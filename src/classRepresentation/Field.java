package classRepresentation;

import java.util.List;

import interfaces.*;

public class Field implements IField {
	
	String type;
	String name;
	char visibility;
	List<String> nonAccessModifiers;

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public char getVisibility() {
		return visibility;
	}

	@Override
	public void setVisibility(char vis) {
		visibility = vis;
	}

	@Override
	public List<String> getNonAccessModifiers() {
		return nonAccessModifiers;
	}

	@Override
	public void setNonAccessModifiers(List<String> mods) {
		nonAccessModifiers = mods;
	}

}
