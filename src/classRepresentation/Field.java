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
		return this.type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public char getVisibility() {
		return this.visibility;
	}

	@Override
	public void setVisibility(char vis) {
		this.visibility = vis;
	}

	@Override
	public List<String> getNonAccessModifiers() {
		return this.nonAccessModifiers;
	}

	@Override
	public void setNonAccessModifiers(List<String> mods) {
		this.nonAccessModifiers = mods;
	}

}
