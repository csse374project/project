package classRepresentation;

import java.util.List;

import interfaces.*;

public class Field implements IField {

	private String type;
	private String name;
	private char visibility;
	private List<String> nonAccessModifiers;

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

	public boolean equals(Object o) {
		if (!(o instanceof Field))
			return false;
		Field f = (Field) o;
		// Compare the important fields
		if (this.getName().equals(f.getName()) && this.getType().equals(f.getType())
				&& this.getVisibility() == f.getVisibility()
				&& this.getNonAccessModifiers().equals(f.getNonAccessModifiers())) {
			return true;
		}
		return false;
	}

}
