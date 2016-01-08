package classRepresentation;

import java.util.ArrayList;
import java.util.List;

import interfaces.*;

public class Method implements IMethod {

	private List<String> parameters;
	private String name;
	private char visibility;
	private List<String> nonAccessModifiers;
	private String returnType;

	public Method() {
		this.parameters = new ArrayList<String>();
		this.name = "";
		this.visibility = ' ';
		this.nonAccessModifiers = new ArrayList<String>();
		this.returnType = "";
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(visibility);
		str.append(" ");
		str.append(name);
		str.append("(");
		if (parameters.size() > 0) {
			str.append(parameters.get(0));
		}
		for (int i = 1; i < parameters.size(); i++) {
			str.append(", ");
			str.append(parameters.get(i));
		}
		str.append(") : ");
		str.append(returnType);
		if (nonAccessModifiers.size() == 0) {
			str.append("\n\tno non-access-mods");
		} else {
			str.append("\n\t");
			str.append(nonAccessModifiers.get(0));
			for (int i = 1; i < nonAccessModifiers.size(); i++) {
				str.append(", ");
				str.append(nonAccessModifiers.get(i));
			}
		}
		str.append("\n");
		return str.toString();
	}

	@Override
	public String getReturnType() {
		return returnType;
	}

	@Override
	public void setReturnType(String type) {
		returnType = type;
	}

	@Override
	public List<String> getParameters() {
		return parameters;
	}

	@Override
	public void setParameters(List<String> params) {
		parameters = params;
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

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Method))
			return false;
		Method m = (Method) o;
		// Compare the fields
		if (this.parameters.equals(m.getParameters()) && this.name.equals(m.getName())
				&& this.returnType.equals(m.getReturnType()) && this.visibility == m.getVisibility()
				&& this.nonAccessModifiers.equals(m.getNonAccessModifiers())) {
			return true;
		}
		return false;
	}
}
